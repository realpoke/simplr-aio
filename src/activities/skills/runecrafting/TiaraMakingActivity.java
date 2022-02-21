package activities.skills.runecrafting;


import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.utility.Condition;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;

public class TiaraMakingActivity extends Activity {

    private final ItemRequirement[] itemReqs;
    private final Executable bankNode;
    private final AltarType altar;

    public TiaraMakingActivity(final AltarType altar) {
        super(ActivityType.RUNECRAFTING);
        this.altar = altar;
        itemReqs = new ItemRequirement[]{new ItemRequirement("Tiara", 1), new ItemRequirement(altar.talisman, 1)};
        bankNode = new ItemRequirementBanking(itemReqs);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (ItemRequirement.hasItemRequirements(itemReqs, getInventory())) {
            if (getAltar() != null) {
                makeTiaras();
            } else if (getObjects().closest("Mysterious ruins") != null && altar.area.contains(myPosition())) {
                enterAltar();
            } else {
                walkToAltar();
            }
        } else if (getAltar() != null) {
            leaveAltar();
        } else {
            execute(bankNode);
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

    void leaveAltar() {
        if (getObjects().closest("Portal").interact("Use")) {
            Sleep.sleepUntil(() -> getObjects().closest("Altar") == null, 10_000);
        }
    }

    private void makeTiaras() throws InterruptedException {
        if (!"Tiara".equals(getInventory().getSelectedItemName())) {
            if (getInventory().isItemSelected()) {
                getInventory().deselectItem();
            } else {
                getInventory().interact("Use", "Tiara");
            }
        } else {
            getAltar().interact("Use");
        }
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

    RS2Object getAltar() {
        return getObjects().closest(obj -> obj.getName().equals("Altar") && obj.hasAction("Craft-rune"));
    }

    @Override
    public TiaraMakingActivity copy() {
        return new TiaraMakingActivity(altar);
    }
}