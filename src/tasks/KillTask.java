package tasks;

import activities.activity.Activity;
import org.osbot.rs07.api.model.NPC;

import java.awt.*;

public class KillTask extends Task {

    private final String npcName;
    private final int targetKills;
    private int currentKills;
    private NPC lastTarget;
    private NPC currentTarget;

    public KillTask(final Activity activity, final String npcName, final int targetKills) {
        super(TaskType.KILL, activity);
        this.npcName = npcName;
        this.targetKills = targetKills;
    }

    @Override
    public boolean isComplete() {
        return currentKills >= targetKills;
    }

    @Override
    public void run() throws InterruptedException {
        currentTarget = getNpcs().closest(c -> c.getName().equals(npcName) && c.isInteracting(myPlayer()));

        if (currentTarget != null && getCombat().isFighting() && myPlayer().isInteracting(currentTarget)) {
            currentTarget = (NPC)getCombat().getFighting();
        } else if (currentTarget != null && lastTarget != currentTarget && currentTarget.getHealthPercent() <= 0) {
            lastTarget = currentTarget;
            currentKills += 1;
        }

        super.run();
    }

    @Override
    public String toString() {
        return String.format("Kill task: %s (%d/%d)", npcName, currentKills, targetKills);
    }

    @Override
    public void onPaint(Graphics2D g) {
        getPaint().drawEntity(g, currentTarget, "Target", false, false, false, true, true, false, false);
        super.onPaint(g);
    }

    @Override
    public Task copy() {
        return new KillTask(getActivity().copy(), npcName, targetKills);
    }
}