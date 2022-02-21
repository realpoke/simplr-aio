package activities.skills.cooking;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.utility.Condition;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class CookingActivity extends Activity {

    private final CookingItem cookingItem;
    private final CookingLocation cookingLocation;
    private final Executable bankNode;

    public CookingActivity(final CookingItem cookingItem, final CookingLocation cookingLocation) {
        super(ActivityType.COOKING);
        this.cookingItem = cookingItem;
        this.cookingLocation = cookingLocation;
        bankNode = new ItemRequirementBanking(cookingItem.itemReqs);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (ItemRequirement.hasItemRequirements(cookingItem.itemReqs, getInventory())) {
            if (!cookingLocation.location.getArea().contains(myPosition())) {
                setStatus("Walking to " + cookingLocation);
                WebWalkEvent webWalkEvent = new WebWalkEvent(cookingLocation.location.getArea());
                webWalkEvent.setBreakCondition(new Condition() {
                    @Override
                    public boolean evaluate() {
                        return getInteractionHelper().canInteract(getCookingObject())
                                && cookingLocation.location.getArea().contains(myPosition());
                    }
                });
                execute(webWalkEvent);
            } else {
                setStatus("Cooking " + cookingItem);
                cook();
            }
        } else {
            setStatus("Banking");
            execute(bankNode);
        }
    }

    private void cook() throws InterruptedException {
        if (!getCamera().isAtTop())
            getCamera().toTop();

        if (getMakeAllInterface().isOpen()) {
            getMakeAllInterface().makeAll(1);
            Sleep.sleepUntil(() -> getDialogues().isPendingContinuation() || !ItemRequirement.hasItemRequirements(cookingItem.itemReqs, getInventory()), 90_000);
        } else if (!cookingItem.toString().equals(getInventory().getSelectedItemName())) {
            getInventory().use(cookingItem.toString());
        } else if (getCookingObject().interact("Use")) {
            Sleep.sleepUntil(() -> getMakeAllInterface().isOpen(), 10_000);
        }
    }

    private RS2Object getCookingObject() {
        return getObjects().closest(cookingLocation.cookingObject.toString());
    }

    @Override
    public CookingActivity copy() {
        return new CookingActivity(cookingItem, cookingLocation);
    }
}