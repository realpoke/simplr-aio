package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;

public class FightingSection extends SectionBase {

    private static final Area LADDER_AREA = new Area(3109, 9525, 3112, 9528);
    private final CachedWidget VIEW_EQUIPMENT_STATS_WIDGET = new CachedWidget(new WidgetActionFilter("View equipment stats"));

    public FightingSection() {
        super(TutorialSectionInstructor.FIGHTING);
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
            case 370:
                talkToInstructor();
                break;
            case 390:
                if (getTabs().open(Tab.EQUIPMENT)) {
                    Sleep.sleepUntil(() -> getProgress() != 390, 2_000);
                }
                break;
            case 400:
                if (VIEW_EQUIPMENT_STATS_WIDGET.interact(getWidgets())) {
                    Sleep.sleepUntil(() -> getProgress() != 400, 3_000);
                }
                break;
            case 405:
                wieldItem("Bronze dagger");
                break;
            case 410:
                talkToInstructor();
                break;
            case 420:
                if (!getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Bronze sword")) {
                    wieldItem("Bronze sword");
                } else if (!getEquipment().isWearingItem(EquipmentSlot.SHIELD, "Wooden shield")) {
                    wieldItem("Wooden shield");
                }
                break;
            case 430:
                if (getTabs().open(Tab.ATTACK)) {
                    Sleep.sleepUntil(() -> getProgress() != 430, 2_000);
                }
                break;
            case 450:
            case 460:
                if (!isAttackingRat()) {
                    attackRat();
                }
                break;
            case 470:
                if (getMap().canReach(getInstructor())) {
                    talkToInstructor();
                } else if (getObjects().closest("Gate").interact("Open")) {
                    Sleep.sleepUntil(() -> getMap().canReach(getInstructor()), 5_000);
                }
                break;
            case 480:
            case 490:
                if (!getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Shortbow")) {
                    wieldItem("Shortbow");
                } else if (!getEquipment().isWearingItem(EquipmentSlot.ARROWS, "Bronze arrow")) {
                    wieldItem("Bronze arrow");
                } else if (!isAttackingRat()) {
                    attackRat();
                    Sleep.sleepUntil(() -> getProgress() != 490, 30_000);
                }
                break;
            case 500:
                if (!LADDER_AREA.contains(myPosition())) {
                    walkTo(LADDER_AREA);
                }
                if (getObjects().closest("Ladder").interact("Climb-up")) {
                    Sleep.sleepUntil(() -> !LADDER_AREA.contains(myPosition()), 7_000);
                    if (LADDER_AREA.contains(myPosition())) {
                        getCamera().toEntityMouse(getObjects().closest("Ladder"));
                        getMouse().moveOutsideScreen();
                        if (getObjects().closest("Ladder") != null)
                            getObjects().closest("Ladder").interact();
                    }
                }
                if (getObjects().closest("Ladder") != null) {
                    walkTo(LADDER_AREA);
                    getCamera().toEntityMouse(getObjects().closest("Ladder"));
                    getObjects().closest("Ladder").interact("Climb-up");
                }
                break;
            default:
                if (hintArrowExists())
                    gotoHintArrow();
                break;
        }
    }

    private boolean isAttackingRat() {
        return myPlayer().getInteracting() != null && myPlayer().getInteracting().getName().equals("Giant rat");
    }

    private void attackRat() {
        NPC giantRat = getNpcs().closest(npc -> npc.getName().equals("Giant rat") && npc.isAttackable());
        if (giantRat != null && giantRat.interact("Attack")) {
            Sleep.sleepUntil(() -> myPlayer().getInteracting() != null, 5_000);
        }
    }

    private void wieldItem(String name) {
        getWidgets().closeOpenInterface();
        if (getInventory().equip(name)) {
            Sleep.sleepUntil(() -> !getInventory().contains(name), 1_500);
        }
        if (!getEquipment().contains(name)){
            getMouse().moveOutsideScreen();
        }
    }
}
