package activities.shopping;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.event.WalkingEvent;
import utils.Location;
import utils.Sleep;
import utils.method_provider.Executable;
import utils.requirement.ItemRequirement;

public class ShoppingActivity extends Activity {

    public String npcName;
    public Location shopLocation;
    public int coins;
    public String[] buyItems;
    private final Executable bankNode;

    public ShoppingActivity(final String npcName, final Location shopLocation, final int coins, final String... buyItems) {
        super(ActivityType.SHOPPING);
        this.npcName = npcName;
        this.shopLocation = shopLocation;
        this.coins = coins;
        this.buyItems = buyItems;
        bankNode = new ItemRequirementBanking(new ItemRequirement("Coins", coins));
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (getInventory().getAmount("Coins") < coins) {
            setStatus("Getting coins");
            execute(bankNode);
        } else if (!shopLocation.getArea().contains(myPosition())) {
            setStatus("Walking to shop " + shopLocation);
            if (myPosition().distance(shopLocation.getArea().getRandomPosition()) > 10) {
                getWalking().webWalk(shopLocation.getArea());
            } else {
                WalkingEvent walkingEvent = new WalkingEvent(shopLocation.getArea());
                walkingEvent.setMinDistanceThreshold(0);
                execute(walkingEvent);
            }
        } else if (!getStore().isOpen() && getNpcs().closest(npcName) != null && getMap().distance(getNpcs().closest(npcName)) < 8) {
            setStatus("Trading " + npcName);
            getNpcs().closest(npcName).interact("Trade");
            Sleep.sleepUntil(() -> getStore().isOpen(), 10_000);
        } else if (getStore().isOpen()) {
            for (int i = 0; i < buyItems.length; i++) {
                setStatus("Buying item (" + i + "/" + buyItems.length + ")" + buyItems[i]);
                getStore().buy(buyItems[i], 1);
            }
        }
    }

    @Override
    public Activity copy() {
        return new ShoppingActivity(npcName, shopLocation, coins, buyItems);
    }
}
