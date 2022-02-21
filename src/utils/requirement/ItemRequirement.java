package utils.requirement;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.util.ItemContainer;

import java.util.stream.Stream;

public class ItemRequirement {

    public static final int QUANTITY_ALL = Integer.MIN_VALUE;

    private final String name;
    private final int minQuantity;
    private final int maxQuantity;

    private boolean noted;
    private boolean stackable;
    private boolean equiable;

    // Used for "tools", where the player should only ever have 1 of the item
    // For example, axes when woodcutting, or pickaxes when mining
    public ItemRequirement(final String name) {
        this.name = name;
        this.minQuantity = 1;
        this.maxQuantity = 1;
    }

    // Used when the player should have at least "minQuantity" of the item to perform the task
    // But should preferably have as many of the item as possible
    public ItemRequirement(final String name, final int minQuantity) {
        this.name = name;
        this.minQuantity = minQuantity;
        this.maxQuantity = QUANTITY_ALL;
    }

    // Used when the player should have at least "minQuantity" of the item, and at most
    // "maxQuantity" of the item. For example, you may want the player to have at least
    // 60 coins to charter a ship, but at most 5k coins so that the full cash stack isn't used
    public ItemRequirement(final String name, final int minQuantity, final int maxQuantity) {
        this.name = name;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public static boolean isRequirementItem(final ItemRequirement[] itemReqs, final Item item) {
        for (ItemRequirement itemReq : itemReqs) {
            if (itemReq.isRequirementItem(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasItemRequirements(final ItemRequirement[] itemReqs, final ItemContainer... itemContainers) {
        for (ItemRequirement req : itemReqs) {
            if (!req.hasRequirement(itemContainers)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasNonItemRequirement(final ItemRequirement[] itemReqs, final ItemContainer itemContainer, final Filter<Item> allowedItems) {
        return itemContainer.contains(item -> !ItemRequirement.isRequirementItem(itemReqs, item) && !allowedItems.match(item));
    }

    public static boolean hasNonItemRequirement(final ItemRequirement[] itemReqs, final ItemContainer itemContainer) {
        return itemContainer.contains(item -> !ItemRequirement.isRequirementItem(itemReqs, item));
    }

    public final String getName() {
        return name;
    }

    public final int getMinQuantity() {
        return minQuantity;
    }

    public final int getMaxQuantity() {
        return maxQuantity;
    }

    public final boolean isNoted() {
        return noted;
    }

    public final boolean isStackable() {
        return stackable;
    }

    public final boolean isEquipable() {
        return equiable;
    }

    public final int getMinNumSlots() {
        if (isStackable()) {
            return 1;
        }
        return getMinQuantity();
    }

    public final int getMaxNumSlots() {
        if (isStackable()) {
            return 1;
        }
        return getMaxQuantity();
    }

    public final ItemRequirement setNoted() {
        noted = true;
        stackable = true;
        return this;
    }

    public final ItemRequirement setStackable() {
        stackable = true;
        return this;
    }

    public final ItemRequirement setEquipable() {
        equiable = true;
        return this;
    }

    public final boolean isRequirementItem(final Item item) {
        return item.getName().equals(getName()) &&
                (item.isNote() == isNoted()) || (isStackable() && item.getDefinition().getNotedId() == -1);
    }

    public final boolean hasRequirement(final ItemContainer... itemContainers) {
        return hasRequirement(getAmount(itemContainers));
    }

    public final boolean hasRequirement(final long amount) {
        return amount >= getMinQuantity() && (getMaxQuantity() == ItemRequirement.QUANTITY_ALL || amount <= getMaxQuantity());
    }

    public final long getAmount(final ItemContainer... itemContainers) {
        return Stream.of(itemContainers)
                .mapToLong(itemContainer ->
                        itemContainer.getAmount(item ->
                                item.getName().equals(getName()) &&
                                        (
                                                itemContainer instanceof Bank ||
                                                        item.isNote() == isNoted() ||
                                                        (isStackable() && item.getDefinition().getNotedId() == -1) // If an item is stackable, it's noted ID will be -1
                                        )
                        )
                ).sum();
    }

    @Override
    public String toString() {
        return name;
    }
}
