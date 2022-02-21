package tasks;

import activities.activity.Activity;
import utils.method_provider.Executable;
import org.osbot.rs07.canvas.paint.Painter;
import utils.Copyable;

import java.awt.*;

public abstract class Task extends Executable implements Copyable<Task>, Painter {
    private final TaskType taskType;
    protected Activity activity;
    private int executionOrder;
    private boolean setupAccount = true, isAccountSetup = false;

    public Task(final TaskType taskType) {
        this.taskType = taskType;
    }

    public Task(final TaskType taskType, final boolean setupAccount) {
        this.taskType = taskType;
        this.setupAccount = setupAccount;
    }

    public Task(final TaskType taskType, final Activity activity) {
        this.taskType = taskType;
        this.activity = activity;
    }

    public Task(final TaskType taskType, final Activity activity, final boolean setupAccount) {
        this.taskType = taskType;
        this.activity = activity;
        this.setupAccount = setupAccount;
    }

    public abstract boolean isComplete();

    public TaskType getTaskType() {
        return taskType;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(int executionOrder) {
        this.executionOrder = executionOrder;
    }

    @Override
    public void onStart() throws InterruptedException {
        if (activity != null) {
            activity.exchangeContext(getBot(), this);
            activity.onStart();
        }
        if (setupAccount && !isAccountSetup) {
            getAccount().applyDefaultSettings();
            isAccountSetup = true;
        }
    }

    @Override
    public void run() throws InterruptedException {
        if (activity != null) {
            execute(activity);
        }
    }

    @Override
    public boolean canExit() {
        return activity == null || activity.canExit();
    }

    @Override
    public void onEnd() throws InterruptedException {
        if (activity != null) {
            activity.onEnd();
        }
    }

    @Override
    public String toString() {
        char[] name = getTaskType().name().toLowerCase().replace('_', ' ').toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        return new String(name) + " task";
    }

    @Override
    public void onPaint(Graphics2D graphics) {
        if (activity != null) {
            activity.onPaint(graphics);
        }
    }
}