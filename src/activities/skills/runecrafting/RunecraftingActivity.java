package activities.skills.runecrafting;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.Banking;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.utility.Condition;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class RunecraftingActivity extends Activity {

    protected final EssenceType essenceType;
    private final ItemRequirement essenceReq;
    protected Executable banking;
    private ItemRequirement talismanReq;
    private Executable talismanBanking = new TalismanBanking();
    private final AltarType altar;

    public RunecraftingActivity(final AltarType altar, final EssenceType essenceType) {
        super(ActivityType.RUNECRAFTING);
        this.altar = altar;
        this.essenceType = essenceType;
        this.essenceReq = new ItemRequirement(essenceType.toString(), 1);
    }

    @Override
    public void onStart() {
        if (getInventory().contains(altar.tiara) || getEquipment().isWearingItem(EquipmentSlot.HAT, altar.tiara)) {
            talismanReq = new ItemRequirement(altar.tiara).setEquipable();
        } else if (getInventory().contains(altar.talisman)) {
            talismanReq = new ItemRequirement(altar.talisman);
        }

        if (talismanReq != null) {
            banking = new ItemRequirementBanking(talismanReq, essenceReq);
        }
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (talismanReq == null) {
            execute(talismanBanking);
        } else if (banking == null) {
            banking = new ItemRequirementBanking(talismanReq, essenceReq);
        } else if (!ItemRequirement.hasItemRequirements(new ItemRequirement[]{talismanReq, essenceReq}, getInventory(), getEquipment())) {
            if (getAltar() != null) {
                leaveAltar();
            } else {
                execute(banking);
            }
        } else if (getInventory().contains(altar.tiara) && !getEquipment().isWearingItem(EquipmentSlot.HAT, altar.tiara)) {
            equipTiara();
        } else if (getAltar() != null) {
            craftRunes();
        } else if (altar.area.contains(myPosition()) && getObjects().closest("Mysterious ruins") != null) {
            enterAltar();
        } else {
            walkToAltar();
        }
    }

    private void equipTiara() {
        if (getInventory().getItem(altar.tiara).interact("Wear")) {
            Sleep.sleepUntil(() -> getEquipment().contains(altar.tiara), 2000);
        }
    }

    private void craftRunes() {
        if (getAltar().interact("Craft-rune")) {
            Sleep.sleepUntil(() -> getDialogues().isPendingContinuation() || (!myPlayer().isAnimating() && !getInventory().contains("Rune essence", "Pure essence")), 10_000);
        }
    }

    void walkToAltar() {
        WebWalkEvent webWalkEvent = new WebWalkEvent(altar.area);
        webWalkEvent.setBreakCondition(new Condition() {
            @Override
            public boolean evaluate() {
                return altar.area.contains(myPosition()) && getObjects().closest("Mysterious ruins") != null;
            }
        });
        execute(webWalkEvent);
    }

    RS2Object getAltar() {
        return getObjects().closest(obj -> obj.getName().equals("Altar") && obj.hasAction("Craft-rune"));
    }

    void enterAltar() {
        if (getInventory().contains(altar.talisman)) {
            enterAltarUsingTalisman();
        } else {
            enterAltarUsingTiara();
        }
    }

    void enterAltarUsingTalisman() {
        if (!altar.talisman.equals(getInventory().getSelectedItemName())) {
            if (getInventory().isItemSelected()) {
                getInventory().deselectItem();
            } else {
                getInventory().use(altar.talisman);
            }
        } else if (getObjects().closest("Mysterious ruins").interact("Use")) {
            Sleep.sleepUntil(() -> getAltar() != null, 5000);
        }
    }

    void enterAltarUsingTiara() {
        if (getObjects().closest("Mysterious ruins").interact("Enter")) {
            Sleep.sleepUntil(() -> getAltar() != null, 10000);
        }
    }

    void leaveAltar() {
        if (getObjects().closest("Portal").interact("Use")) {
            Sleep.sleepUntil(() -> getObjects().closest("Altar") == null, 10_000);
        }
    }

    @Override
    public RunecraftingActivity copy() {
        return new RunecraftingActivity(altar, essenceType);
    }

    private class TalismanBanking extends Banking {
        @Override
        protected void bank(final BankType currentBankType) {
            if (getBank().contains(altar.tiara)) {
                talismanReq = new ItemRequirement(altar.tiara).setEquipable();
            } else {
                talismanReq = new ItemRequirement(altar.talisman);
            }
            setFinished();
        }
    }
}