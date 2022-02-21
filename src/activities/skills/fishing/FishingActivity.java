package activities.skills.fishing;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.DepositAllBanking;
import activities.banking.ItemRequirementBanking;
import utils.file_manager.FontManager;
import utils.method_provider.Executable;
import org.osbot.rs07.api.model.NPC;
import utils.requirement.ItemRequirement;
import utils.ResourceMode;
import utils.Sleep;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FishingActivity extends Activity {

    private final FishType fish;
    private final FishingLocation location;
    private final ResourceMode resourceMode;
    private final ItemRequirement[] itemReqs;

    private NPC currentFishingSpot;
    private final Executable itemReqBankNode;
    private final Executable depositAllBankNode;

    public FishingActivity(final FishType fish, final FishingLocation location, final ResourceMode resourceMode) {
        super(ActivityType.FISHING);
        this.fish = fish;
        this.location = location;
        this.resourceMode = resourceMode;

        List<ItemRequirement> itemReqs = new ArrayList<>();
        Collections.addAll(itemReqs, fish.fishingMethod.itemReqs);

        if (location == FishingLocation.MUSA_POINT) {
            itemReqs.add(new ItemRequirement("Coins", 60, 5_000).setStackable());
        }

        this.itemReqs = itemReqs.toArray(new ItemRequirement[0]);
        itemReqBankNode = new ItemRequirementBanking(this.itemReqs);
        depositAllBankNode = new DepositAllBanking(this.itemReqs);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (!ItemRequirement.hasItemRequirements(itemReqs, getInventory())
                || ItemRequirement.hasNonItemRequirement(itemReqs, getInventory(), FishType.RAW_FISH_FILTER)) {
            super.setStatus("Getting required items");
            execute(itemReqBankNode);
        } else if (getInventory().isFull()) {
            if (resourceMode == ResourceMode.BANK) {
                super.setStatus("Banking " + fish.name);
                execute(depositAllBankNode);
            } else if (resourceMode == ResourceMode.DROP) {
                super.setStatus("Dropping " + fish.name);
                getInventory().dropAll(FishType.RAW_FISH_FILTER);
            }
        } else if (!location.location.getArea().contains(myPosition())) {
            super.setStatus("Walking to " + location.location);
            getWalking().webWalk(location.location.getArea());
        } else if (!myPlayer().isInteracting(currentFishingSpot) || getDialogues().isPendingContinuation()) {
            super.setStatus("Catching " + fish.name);
            getCamera().toTop();
            fish();
        }
    }

    private void fish() {
        currentFishingSpot = getFishingSpot();
        if (currentFishingSpot != null) {
            if (!currentFishingSpot.isVisible()) {
                getWalking().walk(currentFishingSpot);
            }
            if (currentFishingSpot.interact(fish.fishingMethod.action)) {
                Sleep.sleepUntil(() -> myPlayer().isInteracting(currentFishingSpot) || !currentFishingSpot.exists(), 5_000);
            }
        }
    }

    private NPC getFishingSpot() {
        return getNpcs().closest(npc ->
                npc.getName().equals(fish.fishingMethod.spotName) &&
                        npc.hasAction(fish.fishingMethod.action) &&
                        map.canReach(npc) &&
                        location.location.getArea().contains(npc.getPosition())
        );
    }

    @Override
    public void onPaint(Graphics2D g) {
        super.onPaint(g);
        g.setColor(Color.WHITE);
        g.setBackground(new Color(0, 0, 0, (float)0.3));
        g.setStroke(new BasicStroke(2));
        g.setFont(FontManager.ROBOTO_REGULAR);
        g.setFont(g.getFont().deriveFont(24f));
        getPaint().drawEntity(g, currentFishingSpot, fish.name + " (" + fish.fishingMethod + ")", false, false, false, true, true, false, false);
    }

    @Override
    public FishingActivity copy() {
        return new FishingActivity(fish, location, resourceMode);
    }
}