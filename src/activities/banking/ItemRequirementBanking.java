package activities.banking;

import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import utils.requirement.ItemRequirement;
import utils.Sleep;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemRequirementBanking  extends Banking {

    private final ItemRequirement[] itemReqs;
    private final Map<ItemRequirement, Integer> reqTargetAmountMap = new HashMap<>();
    private final Filter<Item> itemReqFilter;

    public ItemRequirementBanking(final ItemRequirement... itemReqs) {
        this.itemReqs = itemReqs;
        itemReqFilter = item -> Stream.of(itemReqs).anyMatch(req -> req.isRequirementItem(item));
        loadItemReqTargetAmounts();
    }

    private void loadItemReqTargetAmounts() {
        int slotsRemaining = 28;

        for (ItemRequirement itemReq : itemReqs) {
            if (itemReq.isStackable()) {
                reqTargetAmountMap.put(itemReq, itemReq.getMaxQuantity());

                // If the item is equipable it won't take any slots in the inventory
                if (!itemReq.isEquipable()) {
                    slotsRemaining--;
                }
            }
        }

        while (slotsRemaining > 0) {
            boolean noChange = true;

            for (final ItemRequirement itemReq : itemReqs) {
                if (!itemReq.isStackable()) {
                    if (itemReq.getMinQuantity() > slotsRemaining) {
                        break;
                    }
                    Integer existingAmount = reqTargetAmountMap.get(itemReq);

                    if (existingAmount == null) {
                        reqTargetAmountMap.put(itemReq, itemReq.getMinQuantity());

                        // If the item is equipable it won't take any slots in the inventory
                        if (!itemReq.isEquipable()) {
                            slotsRemaining -= itemReq.getMinQuantity();
                        }
                        noChange = false;
                    } else if (itemReq.getMaxQuantity() == ItemRequirement.QUANTITY_ALL || existingAmount < itemReq.getMaxQuantity()) {
                        reqTargetAmountMap.put(itemReq, existingAmount + itemReq.getMinQuantity());

                        // If the item is equipable it won't take any slots in the inventory
                        if (!itemReq.isEquipable()) {
                            slotsRemaining -= itemReq.getMinQuantity();
                        }
                        noChange = false;
                    }
                }
            }

            if (noChange) {
                break;
            }
        }
    }

    @Override
    protected void bank(final BankType currentBankType) throws InterruptedException {
        reqTargetAmountMap.forEach((itemRequirement, integer) -> {
            if (itemRequirement != null && integer != null) {
                logger.info(itemRequirement.getName() + ": " + integer);
            }
        });

        if (getInventory().contains(item -> !itemReqFilter.match(item))) {
            logger.info("Depositing");
            depositNonItemReqs();
        } else if (!ItemRequirement.hasItemRequirements(itemReqs, getInventory(), getEquipment())) {
            logger.info("Withdrawing item requirements");
            getItemRequirements(itemReqs);
        } else {
            setFinished();
        }
    }

    private void depositNonItemReqs() throws InterruptedException {
        getExecutableUtil().execute(new BlockingExecutable() {
            @Override
            protected void blockingRun() throws InterruptedException {
                if (!getBank().isOpen()) {
                    getBank().open();
                } else if (getInventory().contains(item -> !itemReqFilter.match(item))) {
                    getBank().depositAll(item -> !itemReqFilter.match(item));
                } else {
                    setFinished();
                }
            }
        });
    }

    private void getItemRequirements(final ItemRequirement... itemReqs) throws InterruptedException {
        final List<ItemRequirement> itemReqList = Arrays.asList(itemReqs);

        final List<ItemRequirement> equipableItemReqs = itemReqList.stream().filter(ItemRequirement::isEquipable).collect(Collectors.toList());
        final Filter<Item> equipableItemReqFilter = item -> equipableItemReqs.stream().anyMatch(req -> req.isRequirementItem(item));
        final List<ItemRequirement> nonEquipableItemReqsList = itemReqList.stream().filter(req -> !req.isEquipable()).collect(Collectors.toList());

        // First we deposit any items that are not a requirement
        execute(new BlockingExecutable() {
            @Override
            protected void blockingRun() throws InterruptedException {
                if (!getInventory().contains(item -> !itemReqFilter.match(item))) {
                    setFinished();
                    return;
                }

                if (!getBank().isOpen()) {
                    getBank().open();
                } else {
                    getBank().depositAllExcept(itemReqFilter);
                }
            }
        });

        // Then we deposit any excess item reqs we have
        execute(new BlockingExecutable() {
            final Queue<ItemRequirement> nonEquipableItemReqs = new LinkedList<>(nonEquipableItemReqsList);

            @Override
            protected void blockingRun() throws InterruptedException {
                if (nonEquipableItemReqs.isEmpty()) {
                    setFinished();
                    return;
                }

                if (!getBank().isOpen()) {
                    getBank().open();
                } else {
                    ItemRequirement itemReq = nonEquipableItemReqs.peek();

                    int targetAmount = reqTargetAmountMap.get(itemReq);
                    long amountOnPlayer = itemReq.getAmount(getInventory(), getEquipment());

                    if (getBank().getAmount(itemReq.getName()) == 0) {
                        throw new ExecutionFailedException("No " + itemReq.getName() + " in bank.");
                    }

                    if (amountOnPlayer > targetAmount && targetAmount != ItemRequirement.QUANTITY_ALL) {
                        depositExcess(itemReq);
                    } else {
                        nonEquipableItemReqs.poll();
                    }
                }
            }
        });

        if (!equipableItemReqs.isEmpty()) {
            // Now we want to withdraw any equipable item reqs
            execute(new BlockingExecutable() {
                Queue<ItemRequirement> equipableItemReqQueue = new LinkedList<>(equipableItemReqs);

                @Override
                protected void blockingRun() throws InterruptedException {
                    if (equipableItemReqQueue.isEmpty()) {
                        setFinished();
                        return;
                    }

                    if (!getBank().isOpen()) {
                        getBank().open();
                    } else if (getInventory().contains(item -> !equipableItemReqFilter.match(item))) {
                        getBank().depositAllExcept(equipableItemReqFilter);
                    } else if (getEquipment().contains(item -> !equipableItemReqFilter.match(item))) {
                        getBank().depositWornItems();
                    } else {
                        ItemRequirement itemReq = equipableItemReqQueue.peek();
                        if (!itemReq.hasRequirement(getInventory(), getEquipment())) {
                            withdrawItemReq(itemReq);
                        } else {
                            equipableItemReqQueue.poll();
                        }
                    }
                }
            });

            // Now we want to equip the item reqs
            execute(new BlockingExecutable() {
                Queue<ItemRequirement> equipableItemReqQueue = new LinkedList<>(equipableItemReqs);

                @Override
                protected void blockingRun() throws InterruptedException {
                    if (equipableItemReqQueue.isEmpty()) {
                        setFinished();
                    } else if (getBank().isOpen()) {
                        getBank().close();
                    } else {
                        ItemRequirement itemReq = equipableItemReqQueue.peek();
                        if (getInventory().contains(itemReq.getName())) {
                            getInventory().getItem(itemReq.getName()).interact();
                        } else {
                            equipableItemReqQueue.poll();
                        }
                    }
                }
            });
        }

        // Finally we want to withdraw any remaining item reqs
        execute(new BlockingExecutable() {
            final Queue<ItemRequirement> nonEquipableItemReqs = new LinkedList<>(nonEquipableItemReqsList);

            @Override
            protected void blockingRun() throws InterruptedException {
                if (nonEquipableItemReqs.isEmpty()) {
                    logger.info("Finished");
                    setFinished();
                    return;
                }

                if (!getBank().isOpen()) {
                    getBank().open();
                    return;
                }

                ItemRequirement itemReq = nonEquipableItemReqs.peek();

                int targetAmount = reqTargetAmountMap.get(itemReq);
                long amountOnPlayer = itemReq.getAmount(getInventory(), getEquipment());

                if (amountOnPlayer < targetAmount && getBank().contains(itemReq.getName())) {
                    withdrawItemReq(itemReq);
                } else {
                    nonEquipableItemReqs.poll();
                }

                if (!getBank().contains(itemReq.getName())) {
                    setFinished();
                }
            }
        });
    }

    private boolean depositExcess(final ItemRequirement itemReq) {
        int amountOnPlayer = (int) getAmountOnPlayer(itemReq);

        int excessAmount = amountOnPlayer - reqTargetAmountMap.get(itemReq);
        if (getBank().deposit(itemReq.getName(), excessAmount)) {
            Sleep.sleepUntil(() -> getAmountOnPlayer(itemReq) < amountOnPlayer, 1200, 600);
            return true;
        }

        return false;
    }

    private void withdrawItemReq(final ItemRequirement itemReq) {
        logger.info("Withdrawing item requirement: " + itemReq + " (" + reqTargetAmountMap.get(itemReq) + ")");
        if (itemReq.isNoted() && getBank().getWithdrawMode() != org.osbot.rs07.api.Bank.BankMode.WITHDRAW_NOTE) {
            getBank().enableMode(org.osbot.rs07.api.Bank.BankMode.WITHDRAW_NOTE);
        } else if (!itemReq.isNoted() && getBank().getWithdrawMode() != org.osbot.rs07.api.Bank.BankMode.WITHDRAW_ITEM) {
            getBank().enableMode(org.osbot.rs07.api.Bank.BankMode.WITHDRAW_ITEM);
        } else {
            int amountOnPlayer = (int) getAmountOnPlayer(itemReq);
            int targetAmount = reqTargetAmountMap.get(itemReq);

            logger.debug(amountOnPlayer + " : " + targetAmount);

            if (amountOnPlayer < itemReq.getMinQuantity()) {
                int requiredAmountForMinQuantity = Math.max(0, itemReq.getMinQuantity() - amountOnPlayer);
                int bankAmount = (int) itemReq.getAmount(getBank());
                logger.debug(requiredAmountForMinQuantity + " : " + bankAmount);
                if (bankAmount < requiredAmountForMinQuantity) {
                    throw new ExecutionFailedException("Not enough " + itemReq.getName() + " in bank. Required: " + requiredAmountForMinQuantity + ", Found: " + bankAmount);
                }
            }
            if (targetAmount == getInventory().getEmptySlots()) {
                getBank().withdrawAll(itemReq.getName());
            } else {
                int requiredTargetAmount = reqTargetAmountMap.get(itemReq) - amountOnPlayer;
                if (getBank().withdraw(itemReq.getName(), requiredTargetAmount)) {
                    Sleep.sleepUntil(() -> getAmountOnPlayer(itemReq) > amountOnPlayer, 1_200);
                }
            }
        }
    }

    private long getAmountOnPlayer(final ItemRequirement itemReq) {
        return itemReq.getAmount(getInventory(), getEquipment());
    }
}
