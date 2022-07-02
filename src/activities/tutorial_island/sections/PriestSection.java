package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;

public class PriestSection extends SectionBase {

    private static final Area CHURCH_AREA = new Area(3120, 3103, 3128, 3110);

    public PriestSection() {
        super(TutorialSectionInstructor.PRIEST);
    }

    @Override
    public void run() throws InterruptedException {
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
            case 550:
                if (getInstructor() == null) {
                    walkTo(CHURCH_AREA);
                } else {
                    talkToInstructor();
                }
                break;
            case 560:
                if (getTabs().open(Tab.PRAYER)) {
                    Sleep.sleepUntil(() -> getProgress() != 560, 2_000);
                }
                break;
            case 570:
                talkToInstructor();
            case 580:
                if (getTabs().open(Tab.FRIENDS)) {
                    Sleep.sleepUntil(() -> getProgress() != 580, 2_000);
                }
                break;
            case 600:
                talkToInstructor();
            case 610:
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }
}
