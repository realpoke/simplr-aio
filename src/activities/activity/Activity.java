package activities.activity;

import utils.method_provider.Executable;
import org.osbot.rs07.canvas.paint.Painter;
import utils.Copyable;

import java.awt.*;
import java.util.Objects;

public abstract class Activity extends Executable implements Copyable<Activity>, Painter {

    private final ActivityType activityType;
    public boolean isComplete = false;
    /**
     * Current activity status to be displayed in the GUI
     */
    private String status = "";

    public Activity(final ActivityType activityType) {
        this.activityType = activityType;
    }

    /**
     * Update the activity status
     *
     * @param status to set
     */
    protected void setStatus(String status) {
        this.status = status;
    }

    public final ActivityType getActivityType() {
        return activityType;
    }

    @Override
    public void run() throws InterruptedException {
        runActivity();
    }

    public abstract void runActivity() throws InterruptedException;

    public String toString() {
        if (this.status.equals("")) {
            return activityType != null ? activityType.toString() : "";
        }
        return activityType != null ? String.format("%s: %s", activityType.toString(), this.status) : "";
    }

    @Override
    public void onPaint(Graphics2D graphics) {}
}