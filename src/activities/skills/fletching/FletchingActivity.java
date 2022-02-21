package activities.skills.fletching;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class FletchingActivity extends Activity {

    private final FletchingItem fletchItem;
    private final Executable bankNode;

    public FletchingActivity(final FletchingItem fletchItem) {
        super(ActivityType.FLETCHING);
        this.fletchItem = fletchItem;
        bankNode = new ItemRequirementBanking(fletchItem.itemRequirements);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (canMake()) {
            makeAllItem(fletchItem.itemRequirements[1].toString(), fletchItem.itemRequirements[0].toString());
        } else {
            execute(bankNode);
        }
    }

    private boolean canMake() {
        return ItemRequirement.hasItemRequirements(fletchItem.itemRequirements, getInventory());
    }

    private void makeAllItem(String useItem, String interactItem) throws InterruptedException {
        if (getMakeAllInterface().isOpen()) {
            getMakeAllInterface().makeAll(fletchItem.widgetText);
            Sleep.sleepUntil(() -> !canMake() || getDialogues().isPendingContinuation(), 60_000);
        } else if (!useItem.equals(getInventory().getSelectedItemName())) {
            getInventory().use(useItem);
        } else if (getInventory().getItem(interactItem).interact()) {
            if (fletchItem.type != FletchingType.DART) {
                Sleep.sleepUntil(() -> getMakeAllInterface().isOpen(), 3_000);
            }
        }
    }

    @Override
    public FletchingActivity copy() {
        return new FletchingActivity(fletchItem);
    }
}