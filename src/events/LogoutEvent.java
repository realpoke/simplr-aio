package events;

import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetMessageFilter;

public class LogoutEvent extends BlockingExecutable {

    private final CachedWidget logoutButton = new CachedWidget(new WidgetMessageFilter("Click here to logout"));

    @Override
    protected void blockingRun() {
        if (!getClient().isLoggedIn()) {
            setFinished();
        } else if (logoutButton.isVisible(getWidgets())) {
            logoutButton.interact(getWidgets(), "Logout");
            Sleep.sleepUntil(() -> !getClient().isLoggedIn(), 5_000);
        } else if (Tab.LOGOUT.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to logout!");
        } else if (getTabs().getOpen() != Tab.LOGOUT) {
            getTabs().open(Tab.LOGOUT);
        }
    }
}
