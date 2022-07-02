package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;

public class QuestSection extends SectionBase {

    public QuestSection() {
        super(TutorialSectionInstructor.QUEST);
    }

    @Override
    public void run() throws InterruptedException {
        super.setProgress(getProgress());
        logger.debug("Progress: " + getProgress());
        logger.debug("Z-Height: " + myPosition().getZ());

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
            case 200:
                toggleRunning();
                break;
            case 210:
                if (!getSettings().isRunning()) {
                    if (getSettings().setRunning(true)) {
                        Sleep.sleepUntil(() -> getSettings().isRunning(), 1_200);
                    }
                } else {
                    gotoHintArrow();
                }
                break;
            case 220:
                talkToInstructor();
                break;
            case 230:
                if (getTabs().open(Tab.QUEST)) {
                    Sleep.sleepUntil(() -> getProgress() != 230, 2_000);
                }
                break;
            case 240:
                talkToInstructor();
                break;
            case 250:
                RS2Object ladder = getObjects().closest("Ladder");
                if (ladder.isVisible()) {
                    ladder.interact("Climb-down");
                    Sleep.sleepUntil(() -> getProgress() != 250, 8_000);
                    if (getProgress() == 250) {
                        getCamera().toEntityMouse(ladder);
                        getMouse().moveOutsideScreen();
                    }
                } else {
                    getCamera().toEntityMouse(ladder);
                }
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private void toggleRunning() {
        boolean isRunning = getSettings().isRunning();
        if (getSettings().setRunning(!isRunning)) {
            Sleep.sleepUntil(() -> getSettings().isRunning() == !isRunning, 1_200);
        }
    }
}
