package activities.skills.smithing;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class CannonballActivity extends Activity {

    private final SmeltLocation smeltLocation;
    private final ItemRequirement[] itemReqs = {
            new ItemRequirement("Ammo mould"),
            new ItemRequirement("Steel bar", 1)
    };
    private final Executable bankNode = new ItemRequirementBanking(itemReqs);

    public CannonballActivity(final SmeltLocation smeltLocation) {
        super(ActivityType.SMITHING);
        this.smeltLocation = smeltLocation;
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (canMakeCannonballs()) {
            if (!smeltLocation.location.getArea().contains(myPosition())) {
                getWalking().webWalk(smeltLocation.location.getArea());
            } else {
                makeCannonballs();
            }
        } else {
            execute(bankNode);
        }
    }

    private boolean canMakeCannonballs() {
        return ItemRequirement.hasItemRequirements(itemReqs, getInventory());
    }

    private void makeCannonballs() throws InterruptedException {
        if (!getMakeAllInterface().isOpen()) {
            useFurance();
        } {
            getMakeAllInterface().makeAll(1);
            Sleep.sleepUntil(() -> !canMakeCannonballs() || getDialogues().isPendingContinuation(), 150_000);
        }
    }

    private void useFurance() {
        if (getObjects().closest("Furnace").interact("Smelt")) {
            Sleep.sleepUntil(() -> getMakeAllInterface().isOpen(), 5000);
        }
    }

    @Override
    public CannonballActivity copy() {
        return new CannonballActivity(smeltLocation);
    }
}