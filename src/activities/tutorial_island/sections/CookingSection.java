package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import utils.Sleep;

public class CookingSection extends SectionBase {

    public CookingSection() {
        super(TutorialSectionInstructor.COOKING);
    }

    @Override
    public void run() throws InterruptedException {
        super.setProgress(getProgress());

        if (pendingQuestion()) {
            selectQuestionOption();
            return;
        }

        if (pendingContinue()) {
            selectContinue();
            return;
        }

        switch (getProgress()) {
            case 140:
                talkToInstructor();
                break;
            case 150:
                makeDough();
                break;
            case 160:
                bakeDough();
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private void makeDough() {
        if (!"Pot of flour".equals(getInventory().getSelectedItemName())) {
            getInventory().interact("Use", "Pot of flour");
        } else if (getInventory().getItem("Bucket of water").interact()) {
            Sleep.sleepUntil(() -> getInventory().contains("Bread dough"), 3_000);
        }
    }

    private void bakeDough() {
        if (!"Bread dough".equals(getInventory().getSelectedItemName())) {
            getInventory().interact("Use", "Bread dough");
        } else if (getObjects().closest("Range").interact()) {
            Sleep.sleepUntil(() -> getInventory().contains("Bread"), 5_000);
        }
    }
}
