package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundDecoration;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;
import utils.Sleep;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SurvivalSection extends SectionBase {

    public SurvivalSection() {
        super(TutorialSectionInstructor.SURVIVAL);
    }

    @Override
    public void run() throws InterruptedException {
        super.setProgress(getProgress());

        if (pendingQuestion()) {
            if (!selectQuestionOption()) {
                getWalking().walk(myPosition().getArea(4).getRandomPosition());
            }
            return;
        }

        if (pendingContinue()) {
            selectContinue();
            return;
        }

        switch (getProgress()) {
            case 20:
                talkToInstructor();
                break;
            case 30:
                if (getTabs().open(Tab.INVENTORY)) {
                    Sleep.sleepUntil(() -> getProgress() != 30, 2_000);
                }
                break;
            case 40:
                fish();
                break;
            case 50:
                if (getTabs().open(Tab.SKILLS)) {
                    Sleep.sleepUntil(() -> getProgress() != 50, 2_000);
                }
                break;
            case 60:
                talkToInstructor();
                break;
            case 70:
                chopTree();
                break;
            case 80:
            case 90:
            case 100:
            case 110:
                if (!getTabs().isOpen(Tab.INVENTORY)) {
                    getTabs().open(Tab.INVENTORY);
                } else if (!getInventory().contains("Raw shrimps")) {
                    fish();
                } else if (getObjects().closest("Fire") == null || getWidgets().getWidgetContainingText("time to light a fire") != null) {
                    if (!getInventory().contains("Logs")) {
                        chopTree();
                    } else {
                        lightFire();
                    }
                } else {
                    cook();
                }
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private Optional<Position> getEmptyPosition() {
        List<Position> allPositions = myPlayer().getArea(10).getPositions();

        // Remove any position with an object (except ground decorations, as they can be walked on)
        for (RS2Object object : getObjects().getAll()) {
            if (object instanceof GroundDecoration) {
                continue;
            }
            allPositions.removeIf(position -> object.getPosition().equals(position));
        }

        allPositions.removeIf(position -> !getMap().canReach(position));

        return allPositions.stream().min(Comparator.comparingInt(p -> myPosition().distance(p)));
    }

    private void chopTree() {
        Entity tree = getObjects().closest("Tree");
        if (tree != null && !tree.isVisible()) {
            getCamera().toEntityMouse(tree);
        } else if (tree != null && tree.interact("Chop down")) {
            Sleep.sleepUntil(() -> getInventory().contains("Logs") || !tree.exists(), 10_000);
        }
    }

    private void fish() {
        NPC fishingSpot = getNpcs().closest("Fishing spot");
        if (fishingSpot != null && !fishingSpot.isVisible()) {
            getCamera().toEntityMouse(fishingSpot);
        } else if (fishingSpot != null && fishingSpot.interact("Net")) {
            long rawShrimpCount = getInventory().getAmount("Raw shrimps");
            Sleep.sleepUntil(() -> getInventory().getAmount("Raw shrimps") > rawShrimpCount, 10_000);
        }
    }

    private void lightFire() {
        if (standingOnFire()) {
            getEmptyPosition().ifPresent(position -> {
                WalkingEvent walkingEvent = new WalkingEvent(position);
                walkingEvent.setOperateCamera(false);
                walkingEvent.setMinDistanceThreshold(0);
                execute(walkingEvent);
            });
        } else if (!"Tinderbox".equals(getInventory().getSelectedItemName())) {
            getInventory().use("Tinderbox");
        } else if (getInventory().getItem("Logs").interact()) {
            Position playerPos = myPosition();
            Sleep.sleepUntil(() -> !myPosition().equals(playerPos) && !myPlayer().isAnimating(), 10_000, 1_000);
        }
    }

    private boolean standingOnFire() {
        return getObjects().singleFilter(getObjects().getAll(), obj -> obj.getPosition().equals(myPosition()) && obj.getName().equals("Fire")) != null;
    }

    private void cook() {
        if (!"Raw shrimps".equals(getInventory().getSelectedItemName())) {
            getInventory().use("Raw shrimps");
        } else {
            RS2Object fire = getObjects().closest("Fire");
            if (fire != null && !fire.isVisible()) {
                getCamera().toEntityMouse(fire);
            } else if (fire != null && fire.interact("Use")) {
                long rawShrimpCount = getInventory().getAmount("Raw shrimps");
                Sleep.sleepUntil(() -> getInventory().getAmount("Raw shrimps") < rawShrimpCount, 5_000);
            }
        }
    }
}
