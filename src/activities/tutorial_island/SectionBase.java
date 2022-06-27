package activities.tutorial_island;

import org.osbot.rs07.api.HintArrow;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.utility.Condition;
import utils.Sleep;
import utils.method_provider.Executable;
import utils.widget.CachedWidget;

import java.awt.event.KeyEvent;

public abstract class SectionBase extends Executable {

    private TutorialSectionInstructor instructor;
    private Integer progress = -1;
    private static final Integer TUTORIAL_ISLAND_PROGRESS_CONFIG_ID = 281;
    private final String INSTRUCTOR_NAME;
    private final String[] INSTRUCTOR_DIALOGUE_OPTIONS;
    public final CachedWidget dialogueExperienceWidget = new CachedWidget("What's your experience with Old School Runescape?");

    public enum TutorialSectionInstructor {

        GUIDE("Gielinor Guide", "I am an experienced player."),
        SURVIVAL("Survival Expert"),
        COOKING("Master Chef"),
        QUEST("Quest Guide"),
        MINING("Mining Instructor"),
        FIGHTING("Combat Instructor"),
        BANK("Account Guide"),
        PRIEST("Brother Brace"),
        WIZARD("Magic Instructor", "Yes.", "No, I'm not planning to do that.", "Yes, send me to the mainland"),
        IRONMAN("Iron Man tutor",
                "Tell me about Iron Women.",
                "Tell me about Iron Man.",
                "I'd like to change my Iron Woman mode.",
                "I'd like to change my Iron Man mode.",
                "Okay, let me set a PIN");

        private final String instructor;
        private final String[] dialogueOptions;

        TutorialSectionInstructor(String instructor, String... dialogueOptions) {
            this.instructor = instructor;
            this.dialogueOptions = dialogueOptions;
        }

        public String getInstructorName() {
            return instructor;
        }

        public String[] getDialogueOptions() {
            return dialogueOptions;
        }

        @Override
        public String toString() {
            char[] name = name().toLowerCase().replace('_', ' ').toCharArray();
            name[0] = Character.toUpperCase(name[0]);
            return new String(name) + " (" + instructor + ")";
        }

    }

    public SectionBase(final TutorialSectionInstructor instructor) {
        this.instructor = instructor;
        this.INSTRUCTOR_NAME = instructor.getInstructorName();
        this.INSTRUCTOR_DIALOGUE_OPTIONS = instructor.getDialogueOptions();
    }

    @Override
    public String toString() {
        if (progress == -1)
            return instructor.toString();

        return getProgress() + " " + instructor.toString();
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    protected final Integer getProgress() {
        return getConfigs().get(TUTORIAL_ISLAND_PROGRESS_CONFIG_ID);
    }

    protected final void talkToInstructor() {
        NPC instructor = getInstructor();

        if (getMap().canReach(instructor) && getMap().distance(instructor) <= 6) {
            if (instructor.isVisible()) {
                logger.debug("Talking-to: " + instructor.getName());
                if (instructor.interact("Talk-to")) {
                    Sleep.sleepUntil(this::pendingContinue, 5_000);
                }
            } else {
                getCamera().toEntityMouse(instructor);
            }
        } else if (hintArrowExists()) {
            gotoHintArrow();
        }
    }

    protected NPC getInstructor() {
        return getNpcs().closest(INSTRUCTOR_NAME);
    }

    protected boolean pendingContinue() {
        RS2Widget continueWidget = getContinueWidget();
        return continueWidget != null && continueWidget.isVisible();
    }

    protected boolean hintArrowExists() {
        return getHintArrow().isActive();
    }

    protected void gotoHintArrow() {
        if (getHintArrow().getType().equals(HintArrow.HintArrowType.NONE)) {
            return;
        } else if (getMap().distance(getHintArrow().getPosition()) >= 6) {
            walkTo(getHintArrow().getPosition().getArea(2));
        }
        if (getHintArrow() != null && getMap().distance(getHintArrow().getPosition()) <= 6){
            if (!getHintArrow().getPosition().isVisible(getBot())) {
                getCamera().toPositionMouse(getHintArrow().getPosition());
            }
            getDoorHandler().handleNextObstacle(getHintArrow().getPosition());
        }
        Sleep.sleepUntil(() -> myPlayer().isMoving() || myPlayer().isAnimating(), 3_000);
        Sleep.sleepUntil(() -> !myPlayer().isMoving() && !myPlayer().isAnimating(), 5_000);
    }

    protected boolean pendingQuestion() {
        return getDialogues().isPendingOption() || dialogueExperienceWidget.isVisible(getWidgets());
    }

    protected boolean selectQuestionOption() {
        if (dialogueExperienceWidget.isVisible(getWidgets())) {
            if (getWidgets().getWidgetContainingText(INSTRUCTOR_DIALOGUE_OPTIONS).interact("Continue")) {
                Sleep.sleepUntil(() -> !dialogueExperienceWidget.isVisible(getWidgets()), 1_000);
            }
            return !dialogueExperienceWidget.isVisible(getWidgets());
        }
        return getDialogues().selectOption(INSTRUCTOR_DIALOGUE_OPTIONS);
    }

    protected boolean selectContinue() {
        RS2Widget continueWidget = getContinueWidget();
        if (continueWidget == null) {
            return false;
        }
        if (continueWidget.getMessage().contains("Click here to continue")) {
            getKeyboard().pressKey(KeyEvent.VK_SPACE);
            Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1_000);
            return true;
        } else if (continueWidget.interact()) {
            Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1_000);
            return true;
        }
        return false;
    }

    private RS2Widget getContinueWidget() {
        return getWidgets().singleFilter(getWidgets().getAll(),
                widget -> widget.isVisible()
                        && (widget.getMessage().contains("Click here to continue")
                        || widget.getMessage().contains("Click to continue"))
        );
    }

    public boolean walkTo(Area area) {
        WalkingEvent walkingEvent = new WalkingEvent(area);
        walkingEvent.setBreakCondition(new Condition() {
            @Override
            public boolean evaluate() {
                return getHintArrow() != null &&
                        getHintArrow().isActive() &&
                        getHintArrow().getPosition().distance(myPosition()) < 6;
            }
        });
        walkingEvent.setOperateCamera(false);
        execute(walkingEvent);
        return walkingEvent.hasFinished();
    }
}