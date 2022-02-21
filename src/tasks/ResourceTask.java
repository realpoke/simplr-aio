package tasks;

import activities.activity.Activity;

public class ResourceTask extends Task {

    private final String resource;
    private final int targetQuantity;
    private long quantityObtained;
    private long prevInventoryCount;

    public ResourceTask(final Activity activity, final String resource, final int targetQuantity) {
        super(TaskType.RESOURCE, activity);
        this.resource = resource;
        this.targetQuantity = targetQuantity;
    }

    @Override
    public void onStart() throws InterruptedException {
        super.onStart();
        prevInventoryCount = getInventory().getAmount(resource);
    }

    @Override
    public boolean isComplete() {
        return quantityObtained >= targetQuantity;
    }

    @Override
    public void run() throws InterruptedException {
        long invCount = getInventory().getAmount(resource);
        if (invCount > prevInventoryCount) quantityObtained += invCount - prevInventoryCount;
        prevInventoryCount = invCount;
        super.run();
    }

    @Override
    public String toString() {
        return String.format("Resource task: %s (%d/%d)", resource, quantityObtained, targetQuantity);
    }

    @Override
    public Task copy() {
        return new ResourceTask(getActivity().copy(), resource, targetQuantity);
    }
}