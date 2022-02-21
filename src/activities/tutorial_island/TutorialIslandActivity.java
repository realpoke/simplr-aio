package activities.tutorial_island;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.tutorial_island.sections.*;
import utils.Sleep;

public final class TutorialIslandActivity extends Activity {

    private static final Integer TUTORIAL_ISLAND_SECTION_CONFIG_ID = 406;

    private final SectionBase guideSection = new GuideSection();
    private final SectionBase survivalSection = new SurvivalSection();
    private final SectionBase cookingSection = new CookingSection();
    private final SectionBase questSection = new QuestSection();
    private final SectionBase miningSection = new MiningSection();
    private final SectionBase fightingSection = new FightingSection();
    private final SectionBase bankSection = new BankSection();
    private final SectionBase priestSection = new PriestSection();
    private final SectionBase wizardSection = new WizardSection();

    public TutorialIslandActivity() {
        super(ActivityType.TUTORIAL_ISLAND);
    }

    @Override
    public void onStart() throws InterruptedException {
        Sleep.sleepUntil(() -> getClient().isLoggedIn() && myPlayer().isVisible(), 6_000, 500);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (!getCamera().isAtTop())
            getCamera().toTop();

        switch (getTutorialSection()) {
            case 0:
            case 1:
                super.setStatus(guideSection.toString());
                execute(guideSection);
                break;
            case 2:
            case 3:
                super.setStatus(survivalSection.toString());
                execute(survivalSection);
                break;
            case 4:
            case 5:
                super.setStatus(cookingSection.toString());
                execute(cookingSection);
                break;
            case 6:
            case 7:
                super.setStatus(questSection.toString());
                execute(questSection);
                break;
            case 8:
            case 9:
                super.setStatus(miningSection.toString());
                execute(miningSection);
                break;
            case 10:
            case 11:
            case 12:
                super.setStatus(fightingSection.toString());
                execute(fightingSection);
                break;
            case 14:
            case 15:
                super.setStatus(bankSection.toString());
                execute(bankSection);
                break;
            case 16:
            case 17:
                super.setStatus(priestSection.toString());
                execute(priestSection);
                break;
            case 18:
            case 19:
            case 20:
                super.setStatus(wizardSection.toString());
                execute(wizardSection);
                break;
        }
    }

    private int getTutorialSection() {
        return getConfigs().get(TUTORIAL_ISLAND_SECTION_CONFIG_ID);
    }

    @Override
    public TutorialIslandActivity copy() {
        return new TutorialIslandActivity();
    }
}