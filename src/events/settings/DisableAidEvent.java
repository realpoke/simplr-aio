package events.settings;

import org.osbot.rs07.api.Settings;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;

public class DisableAidEvent extends BlockingExecutable {

    @Override
    protected void blockingRun() {
        if (!getSettings().isAcceptAidActive()) {
            setFinished();
        } else if (Tab.SETTINGS.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to disable aid!");
        } else if (getTabs().getOpen() != Tab.SETTINGS) {
            getTabs().open(Tab.SETTINGS);
        } else if(!getSettings().isAllSettingsWidgetVisible()) {
            getSettings().openAllSettingsWidget();
        } else {
            boolean enabled = getSettings().isAcceptAidActive();
            if (getSettings().setSetting(Settings.AllSettingsTab.GAMEPLAY, "Accept aid", !enabled)) {
                Sleep.sleepUntil(() -> getSettings().isAcceptAidActive() != enabled, 5_000);
            }
        }
    }
}
