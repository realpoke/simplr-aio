package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.WalkingEvent;
import utils.Sleep;
import utils.method_provider.providers.CommandLine;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetMessageFilter;

public class WizardSection extends SectionBase {

    private final CachedWidget selectIronmanButton = new CachedWidget(new WidgetMessageFilter(
            "<col=ffffff>Hardcore Iron Woman</col><br>In addition to the standard Iron Woman rules, a Hardcore Iron Woman only has 1 life. A dangerous death will result in being downgraded to a standard Iron Woman.",
            "<col=ffffff>Hardcore Iron Man</col><br>In addition to the standard Iron Man rules, a Hardcore Iron Man only has 1 life. A dangerous death will result in being downgraded to a standard Iron Man."));
    private final CachedWidget ironmanWidget = new CachedWidget(new WidgetMessageFilter("Solo Modes"));
    private final CachedWidget bankPinWidget = new CachedWidget(new WidgetMessageFilter("set PIN"));
    private boolean isIronman = false, walkedToWizard = false;

    private static final Area CHICKEN_AREA = new Area(
            new int[][]{
                    {3140, 3088},
                    {3140, 3089},
                    {3137, 3092},
                    {3141, 3092},
                    {3144, 3089},
                    {3144, 3088}
            }
    );

    private static final Area WIZARD_BUILDING = new Area(new int[][]{
            {3140, 3085}, {3143, 3088},
            {3140, 3083}, {3141, 3084},
            {3140, 3089}, {3143, 3089},
            {3137, 3091}, {3141, 3091},
            {3138, 3090}, {3142, 3090},
            {3139, 3089}, {3140, 3089},
            {3141, 3084}, {3143, 3084},
            {3141, 3083}, {3142, 3083},
            {3138, 3082}, {3141, 3082},
            {3138, 3083}, {3140, 3083},
            {3139, 3084}, {3141, 3084}
    });

    public WizardSection() {
        super(TutorialSectionInstructor.WIZARD);
    }

    @Override
    public void run() throws InterruptedException {
        super.setProgress(getProgress());
        logger.debug("Progress: " + getProgress());

        if ((!getCommandLine().getBoolean(CommandLine.Commands.HARDCORE_TUTORIAL_ISLAND) || isIronman) && pendingQuestion()) {
            logger.debug("Question");
            if (!selectQuestionOption()) {
                getWalking().walk(myPosition().getArea(4).getRandomPosition());
            }
            return;
        }

        if (pendingContinue()) {
            logger.debug("Continue");
            selectContinue();
            return;
        }

        switch (getProgress()) {
            case 620:
                if (!walkedToWizard && !hintArrowExists()) {
                    logger.debug("WALK");
                    walkedToWizard = true;
                    walkTo(WIZARD_BUILDING);
                } else if (bankPinWidget.isVisible(getWidgets())){
                    logger.debug("BANK");
                    Sleep.sleepUntil(() -> !bankPinWidget.isVisible(getWidgets()), 60_000, 1_000);
                    return;
                } else if (!isIronman && ironmanWidget.isVisible(getWidgets())) {
                    logger.debug("SetIRON");
                    setIronman();
                } else if (!isIronman && getCommandLine().getBoolean(CommandLine.Commands.HARDCORE_TUTORIAL_ISLAND)){
                    logger.debug("TALK");
                    talkToIronman();
                } else {
                    getWidgets().closeOpenInterface();
                    talkToInstructor();
                }
                break;
            case 630:
                if (getTabs().open(Tab.MAGIC)) {
                    Sleep.sleepUntil(() -> getProgress() != 630, 2_000);
                }
                break;
            case 640:
                talkToInstructor();
                break;
            case 650:
                if (!CHICKEN_AREA.contains(myPosition())) {
                    walkToChickenArea();
                } else {
                    attackChicken();
                }
                break;
            case 670:
                Sleep.sleepUntil(() -> pendingContinue() || pendingQuestion(), 2_000);
                if (getMagic().isSpellSelected()) {
                    getMagic().deselectSpell();
                } else if(!pendingContinue() && !pendingQuestion()) {
                    logger.debug("Talking");
                    talkToInstructor();
                    logger.debug("Done talking?");
                }
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private boolean talkToIronman() {
        NPC ironman = getNpcs().closest(TutorialSectionInstructor.IRONMAN.getInstructorName());
        logger.debug("IRONMAN");
        if (ironman != null && getMap().canReach(ironman)) {
            if (!ironman.isVisible()) {
                getCamera().toEntityMouse(ironman);
            }
            if (!pendingQuestion() && ironman.interact("Talk-to")) {
                Sleep.sleepUntil(this::pendingContinue, 8_000);
            }
            logger.debug("DIALOGUE");
            return getDialogues().selectOption(TutorialSectionInstructor.IRONMAN.getDialogueOptions());
        }
        return false;
    }

    private void setIronman() {
        if (selectIronmanButton.isVisible(getWidgets())) {
            logger.debug("CLICK");
            if (selectIronmanButton.interact(getWidgets())) {
                getDialogues().selectOption(TutorialSectionInstructor.IRONMAN.getDialogueOptions());
                Sleep.sleepUntil(() -> getBot().getRandomExecutor().isExecuting(), 10_000);
                isIronman = true;
            }
        }
    }

    private boolean walkToChickenArea() {
        WalkingEvent walkingEvent = new WalkingEvent(CHICKEN_AREA.getRandomPosition());
        walkingEvent.setMinDistanceThreshold(0);
        walkingEvent.setMiniMapDistanceThreshold(0);
        walkingEvent.setOperateCamera(false);
        execute(walkingEvent);
        return walkingEvent.hasFinished();
    }

    private boolean attackChicken() {
        NPC chicken = getNpcs().closest("Chicken");
        if (chicken != null && getMagic().castSpellOnEntity(Spells.NormalSpells.WIND_STRIKE, chicken)) {
            Sleep.sleepUntil(() -> getProgress() != 650, 3_000);
            return true;
        }
        return false;
    }
}
