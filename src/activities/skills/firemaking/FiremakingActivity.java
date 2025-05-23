package activities.skills.firemaking;

import activities.activity.Activity;
import activities.activity.ActivityType;
import activities.banking.ItemRequirementBanking;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundDecoration;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WalkingEvent;
import utils.requirement.ItemRequirement;
import utils.Location;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.Executable;

import java.util.*;

public class FiremakingActivity extends Activity {

    private static final String TINDERBOX = "Tinderbox";
    private final LogType log;
    private final Location location;
    private final ItemRequirement[] itemReqs;
    private final Executable bankNode;

    public FiremakingActivity(final LogType log, final Location location) {
        super(ActivityType.FIREMAKING);
        this.log = log;
        this.location = location;

        itemReqs = new ItemRequirement[]{
                new ItemRequirement("Tinderbox"),
                new ItemRequirement(log.NAME, 1)
        };

        bankNode = new ItemRequirementBanking(itemReqs);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (ItemRequirement.hasItemRequirements(itemReqs, getInventory())) {
            if (!location.getArea().contains(myPosition())) {
                getWalking().webWalk(location.getArea());
            } else {

                Queue<Position> fmPositions = getFMPositions();

                log("Longest chain:");
                for (Position position : fmPositions) {
                    log(position.toString());
                }

                execute(new BlockingExecutable() {
                    @Override
                    protected void blockingRun() throws InterruptedException {
                        if (fmPositions.isEmpty() || !getInventory().contains(log.toString())) {
                            setFinished();
                            return;
                        }

                        Position nextFirePosition = fmPositions.peek();

                        if (!myPosition().equals(nextFirePosition)) {
                            if (myPosition().distance(nextFirePosition) > 5) {
                                getWalking().webWalk(nextFirePosition);
                            } else {
                                WalkingEvent walkingEvent = new WalkingEvent(nextFirePosition);
                                walkingEvent.setMinDistanceThreshold(0);
                                walkingEvent.setMiniMapDistanceThreshold(0);
                                execute(walkingEvent);
                            }
                            return;
                        }

                        if (!TINDERBOX.equals(getInventory().getSelectedItemName())) {
                            getInventory().use(TINDERBOX);
                            return;
                        }

                        if (getInventory().getItem(log.toString()).interact()) {
                            Sleep.sleepUntil(() -> (!myPosition().equals(nextFirePosition) && !myPlayer().isMoving()), 30_000);
                            fmPositions.poll();
                        }
                    }
                });
            }
        } else {
            execute(bankNode);
        }
    }

    private Queue<Position> getFMPositions() {
        List<Position> allPositions = location.getArea().getPositions();

        // Remove any position with an object (except ground decorations, as they can be walked on)
        for (RS2Object object : getObjects().getAll()) {
            if (object instanceof GroundDecoration) {
                continue;
            }
            allPositions.removeIf(position -> object.getPosition().equals(position));
        }

        // Sort positions into rows
        HashMap<Integer, List<Position>> rows = new HashMap<>();
        for (Position position : allPositions) {
            rows.putIfAbsent(position.getY(), new ArrayList<>());
            rows.get(position.getY()).add(position);
        }

        if (rows.isEmpty()) {
            return new LinkedList<>();
        }

        // Find the longest consecutive row
        Queue<Position> longestConsecutiveRow = new LinkedList<>();
        for (List<Position> row : rows.values()) {

            row.sort((p1, p2) -> Integer.compare(p2.getX(), p1.getX()));

            ArrayDeque<Position> current = new ArrayDeque<>();

            for (Position position : row) {
                if (current.isEmpty()) {
                    current.addLast(position);
                } else if (position.getX() == current.getLast().getX() - 1) {
                    current.addLast(position);
                } else if (current.size() > longestConsecutiveRow.size()) {
                    longestConsecutiveRow = new LinkedList<>(current);
                    current.clear();
                } else {
                    current.clear();
                }
            }
        }

        return longestConsecutiveRow;
    }

    @Override
    public FiremakingActivity copy() {
        return new FiremakingActivity(log, location);
    }
}