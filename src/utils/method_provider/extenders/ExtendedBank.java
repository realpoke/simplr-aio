package utils.method_provider.extenders;

import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.util.CachedWidget;
import utils.Sleep;

public class ExtendedBank extends Bank {

    private static final int CONFIG_ID = 1666;
    private static final int BANK_WIDGET_ROOT_ID = 12;

    private CachedWidget quantityLabelWidget;

    /*
    1666: 0 -> ONE
    1666: 4 -> FIVE
    1666: 8 -> TEN
    1666: 12 -> X
    1666: 16 -> ALL
     */
    public Quantity getQuantity() {
        return Quantity.values()[getConfigs().get(CONFIG_ID) / 4];
    }

    public boolean setQuantity(final Quantity quantity) {
        if (quantity == getQuantity()) {
            return true;
        }

        if (!getWidgets().isVisible(BANK_WIDGET_ROOT_ID)) {
            return false;
        }

        if (quantityLabelWidget == null || !quantityLabelWidget.initialized()) {
            quantityLabelWidget = new CachedWidget(this, w -> w != null && w.getMessage().equals("Quantity:"), BANK_WIDGET_ROOT_ID);
            quantityLabelWidget.cache();
        }

        if (quantityLabelWidget.initialized()) {
            int rootID = quantityLabelWidget.getRootId();
            int secondLevelID = quantityLabelWidget.getSecondLevelId();

            if (quantity == Quantity.ONE) {
                secondLevelID += 1;
            } else {
                secondLevelID += (quantity.ordinal() + 1) * 2;
            }

            RS2Widget quantityWidget = getWidgets().get(rootID, secondLevelID);

            if (quantityWidget != null && quantityWidget.interact()) {
                Sleep.sleepUntil(() -> getQuantity() == quantity, 1_800, 600);
                return true;
            }
        }
        return false;
    }

    public enum Quantity {
        ONE,
        FIVE,
        TEN,
        X,
        ALL
    }
}
