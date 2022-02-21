package activities.skills.herblore;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import utils.requirement.ItemRequirement;
import utils.Sleep;
import utils.method_provider.Executable;


public class PotionMakingActivity extends Activity {

    private final PotionType potion;
    private final Executable bankNode;

    public PotionMakingActivity(final PotionType potion) {
        super(ActivityType.HERBLORE);
        this.potion = potion;
        bankNode = new ItemRequirementBanking(potion.itemRequirements);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (ItemRequirement.hasItemRequirements(potion.itemRequirements, getInventory())) {
            makePotion();
        } else {
            execute(bankNode);
        }
    }

    private void makePotion() throws InterruptedException {
        if (getMakeAllInterface().isOpen()) {
            getMakeAllInterface().makeAll(1);
            Sleep.sleepUntil(() -> !ItemRequirement.hasItemRequirements(potion.itemRequirements, getInventory()) || getDialogues().isPendingContinuation(), 30_000);
        } else if (potion.itemRequirements[1].toString().equals(getInventory().getSelectedItemName())) {
            if (getInventory().getItem(potion.itemRequirements[0].toString()).interact()) {
                Sleep.sleepUntil(() -> getMakeAllInterface().isOpen(), 3000);
            }
        } else {
            getInventory().use(potion.itemRequirements[1].toString());
        }
    }

    @Override
    public PotionMakingActivity copy() {
        return new PotionMakingActivity(potion);
    }
}