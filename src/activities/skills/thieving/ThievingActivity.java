package activities.skills.thieving;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.Banking;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.event.WalkingEvent;
import utils.*;
import utils.method_provider.Executable;
import utils.method_provider.ExecutionFailedException;

public class ThievingActivity extends Activity {

    private final ThievingObject thievingObject;
    private final Location location;
    private final ResourceMode resourceMode;
    private Food food;
    private Eating eatNode;
    private int hpPercentToEatAt;
    private Executable bankNode = new ThievingBank();

    public ThievingActivity(final ThievingObject thievingObject, final Location location, final ResourceMode resourceMode) {
        super(ActivityType.THIEVING);
        this.thievingObject = thievingObject;
        this.location = location;
        this.resourceMode = resourceMode;
    }

    public ThievingActivity(final ThievingObject thievingObject, final Food food, final int hpPercentToEatAt, final Location location, final ResourceMode resourceMode) {
        super(ActivityType.THIEVING);
        this.thievingObject = thievingObject;
        this.location = location;
        this.resourceMode = resourceMode;
        this.food = food;
        this.hpPercentToEatAt = hpPercentToEatAt;

        if (food != null) {
            eatNode = new Eating(food);
        }
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (!getCamera().isAtTop())
            getCamera().toTop();

        if (getInventory().isFull()) {
            if (resourceMode == ResourceMode.DROP) {
                setStatus("Dropping");
                if (food == null) {
                    getInventory().dropAll();
                } else {
                    getInventory().dropAllExcept(food.toString());
                }
            } else {
                setStatus("Banking");
                execute(bankNode);
            }
        } else if (food != null && (getSkills().getDynamic(Skill.HITPOINTS) * 100) / getSkills().getStatic(Skill.HITPOINTS) < hpPercentToEatAt) {
            if (getInventory().contains(food.toString())) {
                if (getBank() != null && getBank().isOpen()) {
                    getBank().close();
                } else {
                    setStatus("Eating");
                    execute(eatNode);
                }
            } else {
                setStatus("Banking");
                execute(bankNode);
            }
        } else {
            steal();
        }
    }

    private void steal() {
        if (!location.getArea().contains(myPosition())) {
            setStatus("Walking to " + location);
            if (myPosition().distance(location.getArea().getRandomPosition()) > 10) {
                getWalking().webWalk(location.getArea());
            } else {
                WalkingEvent walkingEvent = new WalkingEvent(location.getArea());
                walkingEvent.setMinDistanceThreshold(0);
                execute(walkingEvent);
            }
        } else if (!myPlayer().isAnimating()) {
            switch (thievingObject.type) {
                case NPC:
                    pickpocket();
                    break;
                case STALL:
                    if (thievingObject == ThievingObject.BAKERS_STALL) {
                        stealFromBakeryStall();
                    } else {
                        stealFromStall();
                    }
                    break;
            }
        }
    }

    private boolean isStunned() {
        return myPlayer().getHeight() > 195;
    }

    private void pickpocket() {
        Item coinPouch = getInventory().getItem("Coin pouch");

        if (coinPouch != null && (coinPouch.getAmount() >= 28 || isStunned())) {
            setStatus("Opening coin pouch");
            getInventory().getItem("Coin pouch").interact();
            Sleep.sleepUntil(() -> !getInventory().contains("Coin pouch"), 3_000);
        } else if (!getSettings().isRunning() && getSettings().getRunEnergy() >= 30) {
            getSettings().setRunning(true);
        } else if (!isStunned()) {
            setStatus("Stealing from " + thievingObject.name);
            NPC npc = getNpcs().closest(thievingObject.name);
            if (npc != null) {
                if (!getMap().canReach(npc)) {
                    if (getDoorHandler().handleNextObstacle(npc)) {
                        Sleep.sleepUntil(() -> getMap().canReach(npc), 5_000);
                    }

                } else if (!npc.isVisible()) {
                    getCamera().toEntityMouse(npc);
                }
                if (npc.interact("Pickpocket")) {
                    Sleep.sleepUntil(() -> myPlayer().isAnimating(), 8_000);
                }
            }
        }
    }

    @Override
    public boolean canExit() {
        return !isStunned();
    }

    private void stealFromBakeryStall() {
        NPC townCrier = getNpcs().closest("Town crier");

        if (!getDialogues().inDialogue() && townCrier != null && townCrier.getPosition().equals(new Position(2667, 3312, 0))) {
            if (townCrier.interact("Talk-to")) {
                Sleep.sleepUntil(() -> getDialogues().inDialogue(), 1_500);
            }
        } else if (getDialogues().inDialogue()) {
            stealFromStall();
        }
    }

    private void stealFromStall() {
        RS2Object stall = getObjects().closest(obj -> obj.getName().equals(thievingObject.name) && obj.hasAction("Steal from", "Steal-from") && obj.getPosition().distance(myPosition()) <= 2);
        if (stall != null && stall.interact("Steal from", "Steal-from")) {
            Sleep.sleepUntil(() -> myPlayer().isAnimating(), 2_000);
        }
    }

    @Override
    public ThievingActivity copy() {
        return new ThievingActivity(thievingObject, food, hpPercentToEatAt, location, resourceMode);
    }

    private class ThievingBank extends Banking {
        @Override
        public void bank(final BankType currentBankType) {
            if (!getInventory().isEmpty()) {
                getBank().depositAll();
            } else if (food != Food.NONE) {
                if (getBank().contains(food.toString())) {
                    if (getBank().withdraw(food.toString(), 10)) {
                        setFinished();
                    }
                } else {
                    throw new ExecutionFailedException("No food found in bank");
                }
            }
        }
    }
}