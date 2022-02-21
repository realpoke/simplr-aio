package activities.grand_exchange;

import activities.banking.ItemRequirementBanking;
import events.grand_exchange.GrandExchangeBuyEvent;
import utils.requirement.ItemRequirement;
import utils.grand_exchange.GEItem;
import utils.method_provider.ExecutionFailedException;

public class GEBuyActivity extends GEActivity {

    private final GEItem geItem;
    private final ItemRequirement coinReq;

    private ItemRequirementBanking itemReqBanking;

    public GEBuyActivity(final GEItem geItem) {
        this.geItem = geItem;

        int coinsRequired = geItem.getPrice() * geItem.getQuantity();

        coinReq = new ItemRequirement(
                "Coins",
                coinsRequired,
                coinsRequired
        ).setStackable();

        itemReqBanking = new ItemRequirementBanking(coinReq);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (box != null) {
            return;
        }

        if (!GRAND_EXCHANGE.contains(myPosition())) {
            getWalking().webWalk(GRAND_EXCHANGE);
        } else if (!coinReq.hasRequirement(getInventory())) {
            execute(itemReqBanking);
        } else {
            GrandExchangeBuyEvent buyEvent = new GrandExchangeBuyEvent(
                    geItem.getName(),
                    geItem.getPrice(),
                    geItem.getQuantity()
            );
            execute(buyEvent);
            if (buyEvent.hasFailed()) {
                throw new ExecutionFailedException("Failed to buy items");
            } else {
                box = buyEvent.getBoxUsed();
            }
        }
    }

    @Override
    public String toString() {
        return "Buying";
    }

    @Override
    public GEBuyActivity copy() {
        return new GEBuyActivity(geItem);
    }
}