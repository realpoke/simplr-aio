package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.HintArrow;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;

import java.util.Optional;
import java.util.Random;

public class MiningSection extends SectionBase {

    private static final Area SMITH_AREA = new Area(3076, 9497, 3082, 9504);

    enum Rock {

        COPPER((short) 4645, (short) 4510),
        TIN((short) 53);

        private final short[] COLOURS;

        Rock(final short... COLOURS) {
            this.COLOURS = COLOURS;
        }
    }

    public MiningSection() {
        super(TutorialSectionInstructor.MINING);
    }

    @Override
    public void run() throws InterruptedException {
        if (pendingQuestion()) {
            selectQuestionOption();
            return;
        }

        if (pendingContinue()) {
            selectContinue();
            return;
        }

        switch (getProgress()) {
            case 260:
                if (hintArrowExists()) {
                    talkToInstructor();
                } else {
                    walkTo(SMITH_AREA);
                }
                break;
            case 270:
                prospect(Rock.TIN);
                break;
            case 280:
                prospect(Rock.COPPER);
                break;
            case 290:
                talkToInstructor();
                break;
            case 300:
                mine(Rock.TIN);
                break;
            case 310:
                mine(Rock.COPPER);
                break;
            case 320:
                getTabs().open(Tab.INVENTORY);
                smelt();
                break;
            case 330:
                talkToInstructor();
                break;
            case 340:
                if (getTabs().open(Tab.INVENTORY)) {
                    smith();
                    Sleep.sleepUntil(() -> getProgress() != 340, 8_000);
                }
                break;
            case 350:
                Optional<RS2Widget> daggerWidgetOpt = getDaggerWidget();
                if (daggerWidgetOpt.isPresent()) {
                    if (daggerWidgetOpt.get().interact()) {
                        Sleep.sleepUntil(() -> getInventory().contains("Bronze dagger"), 6_000);
                    }
                } else {
                    smith();
                }
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private void smith() {
        if (myPosition().distance(getObjects().closest("Anvil").getPosition()) > 6) {
            getWalking().walk(getObjects().closest("Anvil").getPosition().getArea(3).getRandomPosition());
        }
        if (getInventory().useOn("Bronze bar", "Anvil")) {
            Sleep.sleepUntil(() -> getDaggerWidget().isPresent(), 7_000);
            if (!getDaggerWidget().isPresent()) {
                getCamera().toEntityMouse(getObjects().closest("Anvil"));
                getMouse().moveOutsideScreen();
            }
        }
    }

    private Optional<RS2Widget> getDaggerWidget() {
        RS2Widget daggerTextWidget = getWidgets().getWidgetContainingText(312, "Dagger");
        if (daggerTextWidget != null) {
            return Optional.ofNullable(getWidgets().get(daggerTextWidget.getRootId(), daggerTextWidget.getSecondLevelId()));
        }
        return Optional.empty();
    }

    private void smelt() {
        String ore = "Tin ore";
        if (new Random().nextInt(2) == 1) {
            ore = "Copper ore";
        }
        if (getInventory().useOn(ore, "Furnace")) {
            Sleep.sleepUntil(() -> getInventory().contains("Bronze bar") && getHintArrow().getType().equals(HintArrow.HintArrowType.NPC), 15_000, 2_000);
        }
    }

    private void prospect(Rock rock) {
        RS2Object closestRock = getClosestRockWithOre(rock);
        if (closestRock != null && !closestRock.isVisible()) {
            getCamera().toEntityMouse(closestRock);
        } else if (closestRock != null && closestRock.interact("Prospect")) {
            Sleep.sleepUntil(this::pendingContinue, 6_000);
        }
    }

    private void mine(Rock rock) {
        RS2Object closestRock = getClosestRockWithOre(rock);
        if (closestRock != null && !closestRock.isVisible()) {
            getCamera().toEntityMouse(closestRock);
        } else if (closestRock != null && closestRock.interact("Mine")) {
            Sleep.sleepUntil(this::pendingContinue, 6_000);
        }
    }

    public RS2Object getClosestRockWithOre(Rock rock) {
        return getObjects().closest(obj -> {
            short[] colours = obj.getDefinition().getModifiedModelColors();
            if (colours != null) {
                for (short c : colours) {
                    for (short col : rock.COLOURS) {
                        if (c == col) return true;
                    }
                }
            }
            return false;
        });
    }
}
