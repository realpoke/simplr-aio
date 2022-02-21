package utils.method_provider.extenders;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.model.Entity;
import utils.Sleep;
import utils.method_provider.CustomMethodProvider;

public class ExtendedInventory extends Inventory {

    private final CustomMethodProvider customMethodProvider;

    public ExtendedInventory(CustomMethodProvider customMethodProvider) {
        this.customMethodProvider = customMethodProvider;
    }

    public boolean use(final String itemName) {
        if (itemName.equals(getSelectedItemName())) {
            return true;
        }
        if (getInventory().interact("Use", itemName)) {
            Sleep.sleepUntil(() -> itemName.equals(getSelectedItemName()), 1_000);
            return true;
        }
        return false;
    }

    public boolean useOn(String itemName, Entity e) {
        if (!e.isVisible()) {
            customMethodProvider.getCamera().toEntityMouse(e);
        }
        if(this.use(itemName)) {
            return e.interact("Use");
        }
        return false;
    }

    public boolean useOn(String itemName, String entityName) {
        return useOn(itemName, getObjects().closest(entityName));
    }

    public boolean equip(final String itemName) {
        return getInventory().getItem(itemName).interact("Wield", "Equip", "Wear");
    }
}