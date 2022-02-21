package tasks;

import activities.activity.Activity;

public class ShopTask extends Task {

    private final String items[];

    public ShopTask(final Activity activity, final String... items) {
        super(TaskType.SHOPPING, activity);
        this.items = items;
    }

    @Override
    public boolean isComplete() {
        return getInventory().contains(items);
    }

    @Override
    public String toString() {
        return "Shopping Task";
    }

    @Override
    public Task copy() {
        return new ShopTask(getActivity().copy(), items);
    }
}