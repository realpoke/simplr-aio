package events.settings;

import org.osbot.rs07.api.Settings;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;

public class DisableRoofEvent extends BlockingExecutable {

    @Override
    protected void blockingRun() {
        if (getSettings().areRoofsEnabled()) {
            setFinished();
        } else if (Tab.SETTINGS.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to disable roofs!");
        } else if (getTabs().getOpen() != Tab.SETTINGS) {
            getTabs().open(Tab.SETTINGS);
        } else if(!getSettings().isAllSettingsWidgetVisible()) {
            getSettings().openAllSettingsWidget();
        } else {
            boolean enabled = getSettings().areRoofsEnabled();
            if (getSettings().setSetting(Settings.AllSettingsTab.DISPLAY, "Hide roofs", !enabled)) {
                Sleep.sleepUntil(() -> getSettings().areRoofsEnabled() != enabled, 5_000);
                if (getSettings().areRoofsEnabled() == enabled) {
                    getSettings().setSetting(Settings.AllSettingsTab.DISPLAY, "Hide roofs", enabled);
                }
            }
        }
    }
}
