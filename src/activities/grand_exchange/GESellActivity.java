package activities.grand_exchange;

import activities.banking.DepositAllBanking;
import activities.banking.ItemRequirementBanking;
import events.grand_exchange.GrandExchangeSellEvent;
import utils.requirement.ItemRequirement;
import utils.grand_exchange.GEItem;

public class GESellActivity extends GEActivity {

    private final GEItem geItem;
    private final DepositAllBanking depositAllBanking;
    private final ItemRequirementBanking itemReqBanking;
    private boolean checkedBank;

    public GESellActivity(final GEItem geItem) {
        this.geItem = geItem;
        depositAllBanking = new DepositAllBanking();
        itemReqBanking = new ItemRequirementBanking(
                new ItemRequirement(geItem.getName(), 1, geItem.getQuantity()).setStackable().setNoted()
        );
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (box != null) {
            return;
        }

        if (!GRAND_EXCHANGE.contains(myPosition())) {
            getWalking().webWalk(GRAND_EXCHANGE);
        } else if (!checkedBank && getInventory().getAmount(geItem.getName()) < geItem.getQuantity()) {
            if (!getInventory().isEmpty()) {
                execute(depositAllBanking);
            } else {
                execute(itemReqBanking);
                checkedBank = true;
            }
        } else {
            GrandExchangeSellEvent sellEvent = new GrandExchangeSellEvent(
                    geItem.getName(), geItem.getPrice(), geItem.getQuantity()
            );
            execute(sellEvent);
            box = sellEvent.getBoxUsed();
        }
    }

    @Override
    public String toString() {
        return "Selling";
    }

    @Override
    public GESellActivity copy() {
        return new GESellActivity(geItem);
    }
}