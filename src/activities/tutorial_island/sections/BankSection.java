package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import utils.Sleep;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;

public class BankSection extends SectionBase {

    private final CachedWidget accountManagementWidget = new CachedWidget(new WidgetActionFilter("Account Management"));

    public BankSection() {
        super(TutorialSectionInstructor.BANK);
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
            case 510:
                if (getMap().canReach(getObjects().closest("Bank booth"))) {
                    if (!getObjects().closest("Bank booth").isVisible()) {
                        getCamera().toEntityMouse(getObjects().closest("Bank booth"));
                    }
                    if (getObjects().closest("Bank booth").interact("Use")) {
                        Sleep.sleepUntil(() -> getBank().isOpen(), 15_000, 1_000);
                    } else {
                        getCamera().moveEast();
                    }
                } else {
                    RS2Object door = getDoorHandler().getNextObstacle(getObjects().closest("Bank booth"));
                    if (!door.isVisible()) {
                        getCamera().toEntityMouse(door);
                    }
                    if (door.isVisible()) {
                        door.interact("Open");
                    } else {
                        getDoorHandler().handleNextObstacle(getObjects().closest("Bank booth"));
                    }
                }
                break;
            case 520:
                if (getBank().isOpen()) {
                    getBank().close();
                } else if (getObjects().closest("Poll booth").interact("Use")) {
                    Sleep.sleepUntil(this::pendingContinue, 5_000);
                }
                break;
            case 525:
                if (getWidgets().closeOpenInterface() && openDoorAtPosition(new Position(3125, 3124, 0))) {
                    Sleep.sleepUntil(() -> getProgress() != 525, 5_000);
                }
            case 530:
                talkToInstructor();
                break;
            case 531:
                openAccountManagementTab();
                break;
            case 532:
                talkToInstructor();
                break;
            case 540:
                if (openDoorAtPosition(new Position(3130, 3124, 0))) {
                    Sleep.sleepUntil(() -> getProgress() != 540, 5_000);
                }
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private boolean openDoorAtPosition(final Position position) {
        RS2Object door = getObjects().closest(obj -> obj.getName().equals("Door") && obj.getPosition().equals(position));
        return door != null && door.interact("Open");
    }

    private void openAccountManagementTab() {
        if (accountManagementWidget.isVisible(getWidgets()) && accountManagementWidget.interact(getWidgets())) {
            Sleep.sleepUntil(() -> getProgress() != 531, 5_000);
        }
    }
}
