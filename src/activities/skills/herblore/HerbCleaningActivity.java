package activities.skills.herblore;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class HerbCleaningActivity extends Activity {

    private final HerbType herb;
    private final ItemRequirement herbReq;
    private final Executable bankNode;

    public HerbCleaningActivity(final HerbType herb) {
        super(ActivityType.HERBLORE);
        this.herb = herb;
        this.herbReq = new ItemRequirement(herb.grimyName, 1);
        bankNode = new ItemRequirementBanking(herbReq);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (ItemRequirement.hasItemRequirements(new ItemRequirement[]{ herbReq }, getInventory())) {
            cleanHerbs();
        } else {
            execute(bankNode);
        }
    }

    private void cleanHerbs() {
        if (getInventory().interact("Clean", herb.grimyName)) {
            Sleep.sleepUntil(() -> {
                return !getInventory().contains(herb.grimyName) ||
                        getDialogues().isPendingContinuation();
            }, 30_000);
        }
    }

    @Override
    public HerbCleaningActivity copy() {
        return new HerbCleaningActivity(herb);
    }
}