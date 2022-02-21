package events.settings;

import org.osbot.rs07.api.Settings;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;

public class EnableShiftDroppingEvent extends BlockingExecutable {

    @Override
    protected void blockingRun() {
        if (getSettings().isShiftDropActive()) {
            setFinished();
        } else if (Tab.SETTINGS.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to enable shift dropping!");
        } else if (getTabs().getOpen() != Tab.SETTINGS) {
            getTabs().open(Tab.SETTINGS);
        } else if(!getSettings().isAllSettingsWidgetVisible()) {
            getSettings().openAllSettingsWidget();
        } else {
            boolean enabled = getSettings().isShiftDropActive();
            if (getSettings().setSetting(Settings.AllSettingsTab.CONTROLS, "Shift click to drop items", !enabled)) {
                Sleep.sleepUntil(() -> getSettings().isShiftDropActive() != enabled, 5_000);
            }
        }
    }
}
