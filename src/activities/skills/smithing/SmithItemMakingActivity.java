package activities.skills.smithing;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.api.ui.RS2Widget;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;
import utils.widget.CachedWidget;

import java.util.ArrayList;
import java.util.List;

public class SmithItemMakingActivity extends Activity {

    private final BarType bar;
    private final SmithItemType smithItem;
    private final SmithLocation smithLocation;
    private final CachedWidget smithItemWidget;
    private final ItemRequirement[] itemReqs;

    private Executable bankNode;

    public SmithItemMakingActivity(final BarType bar, final SmithItemType smithItem, final SmithLocation smithLocation) {
        super(ActivityType.SMITHING);
        this.bar = bar;
        this.smithItem = smithItem;
        this.smithLocation = smithLocation;
        smithItemWidget = new CachedWidget(smithItem.name);

        List<ItemRequirement> itemReqs = new ArrayList();
        itemReqs.add(new ItemRequirement(bar.toString(), smithItem.barsRequired));
        itemReqs.add(new ItemRequirement("Hammer"));
        this.itemReqs = itemReqs.toArray(new ItemRequirement[0]);
        bankNode = new ItemRequirementBanking(this.itemReqs);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (canSmithItem()) {
            if (!smithLocation.location.getArea().contains(myPosition())) {
                getWalking().webWalk(smithLocation.location.getArea());
            } else if (smithItemWidget.getParent(getWidgets()).map(RS2Widget::isVisible).isPresent()) {
                smithAll();
            } else {
                useAnvil();
            }
        } else {
            execute(bankNode);
        }
    }

    private boolean canSmithItem() {
        return getInventory().contains("Hammer") &&
                getInventory().getAmount(bar.toString()) >= smithItem.barsRequired;
    }

    private void smithAll() {
        if (smithItemWidget.getParent(getWidgets()).get().interact("Smith")) {
            Sleep.sleepUntil(() -> !canSmithItem() || getDialogues().isPendingContinuation(), 100_000);
        }
    }

    private void useAnvil() {
        if (getObjects().closest("Anvil").interact("Smith")) {
            Sleep.sleepUntil(() -> smithItemWidget.getParent(getWidgets()).map(RS2Widget::isVisible).isPresent(), 5_000);
        }
    }

    @Override
    public SmithItemMakingActivity copy() {
        return new SmithItemMakingActivity(bar, smithItem, smithLocation);
    }
}