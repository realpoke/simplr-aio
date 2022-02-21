package events.settings;

import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;
import utils.widget.filters.WidgetMessageFilter;

public final class DisableAudioEvent extends BlockingExecutable {

    private final CachedWidget soundSettingsWidget = new CachedWidget(new WidgetActionFilter("Audio"));
    private final CachedWidget musicVolumeWidget = new CachedWidget(new WidgetActionFilter("Adjust Music Volume"));
    private final CachedWidget audioSettingsWidget = new CachedWidget(new WidgetMessageFilter("Audio Settings"));
    private final CachedWidget soundEffectVolumeWidget = new CachedWidget(new WidgetActionFilter("Adjust Sound Effect Volume"));
    private final CachedWidget areaSoundEffectVolumeWidget = new CachedWidget(new WidgetActionFilter("Adjust Area Sound Volume"));

    @Override
    protected void blockingRun() {
        if (getSettings().isAudioDisabled()) {
            setFinished();
        } else if (Tab.SETTINGS.isDisabled(getBot())) {
            throw new ExecutionFailedException("Failed to disable audio!");
        } else if (getTabs().getOpen() != Tab.SETTINGS) {
            getTabs().open(Tab.SETTINGS);
        } else if (!audioSettingsWidget.isVisible(getWidgets())) {
            soundSettingsWidget.interact(getWidgets());
        } else if (!getSettings().isMusicDisabled()) {
            musicVolumeWidget.interact(getWidgets());
            Sleep.sleepUntil(() -> getSettings().isMusicDisabled(), 5_000);
        } else if (!getSettings().isSoundEffectsDisabled()) {
            soundEffectVolumeWidget.interact(getWidgets());
            Sleep.sleepUntil(() -> getSettings().isSoundEffectsDisabled(), 5_000);
        } else if (!getSettings().isAreaSoundEffectsDisabled()) {
            areaSoundEffectVolumeWidget.interact(getWidgets());
            Sleep.sleepUntil(() -> getSettings().isAreaSoundEffectsDisabled(), 5_000);
        }
    }
}