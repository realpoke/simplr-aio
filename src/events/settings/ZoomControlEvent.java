package events.settings;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.input.mouse.PointDestination;
import org.osbot.rs07.input.mouse.WidgetDestination;
import org.osbot.rs07.utility.Condition;
import org.osbot.rs07.utility.ConditionalSleep2;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;

public final class ZoomControlEvent extends BlockingExecutable {

    private final CachedWidget displaySettingsWidget = new CachedWidget(new WidgetActionFilter("Display"));
    private final CachedWidget cameraZoomWidget = new CachedWidget(new WidgetActionFilter("Adjust Camera Zoom"));

    private final int zoom;

    public static boolean isInRange(int currentZoom, int requestedZoom) {
        int currentPos = (int) getPosition(currentZoom);
        int requestedPos = (int) getPosition(requestedZoom);
        return currentPos > requestedPos - 5 &&
                currentPos < requestedPos + 5;
    }

    public ZoomControlEvent(int zoom) {
        this.zoom = zoom;
    }

    private static double getPosition(int zoom) {
        double a = 415.7837;
        double power = 0.07114964;

        return a * Math.pow(zoom, power);
    }

    private Condition moveMouse(RS2Widget slider) {
        return new Condition() {

            @Override
            public boolean evaluate() {
                // Generate the X position for the slider of the requested zoom
                double widgetDestX = getPosition(zoom);
                // Generate the mouse's end X position for moving the slider
                int mouseDestX = (int) widgetDestX +
                        (int) (getMouse().getPosition().getX() - slider.getBounds().getX());
                // Generate the mouse's end Y position for moving the slider
                int mouseDestY = (int) slider.getPosition().getY() + (int) (slider.getHeight() / 2.00);
                return getMouse().move(new PointDestination(getBot(), mouseDestX, mouseDestY));
            }
        };
    }

    @Override
    protected void blockingRun() throws InterruptedException {
        if (isInRange(getCamera().getScaleZ(), zoom)) {
            setFinished();
        } else if (Tab.SETTINGS.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to set zoom level!");
        } else if (!getTabs().isOpen(Tab.SETTINGS)) {
            getSettings().open();
        } else if (!cameraZoomWidget.isVisible(getWidgets())) {
            displaySettingsWidget.interact(getWidgets());
        } else {
            RS2Widget slider = getWidgets().getWidgetContainingSprite(116, 1201);
            if (slider != null) {
                if (getMouse().continualClick(new WidgetDestination(getBot(), slider, 3), moveMouse(slider)) &&
                        ConditionalSleep2.sleep(1_000, () -> isInRange(getCamera().getScaleZ(), zoom))) {
                }
            } else {
                throw new ExecutionFailedException("Could not find slider!");
            }
        }
    }
}