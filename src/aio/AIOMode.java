package aio;

import org.osbot.rs07.Bot;
import tasks.Task;
import utils.method_provider.CustomMethodProvider;

import java.util.ArrayList;

public class AIOMode {

    public void setup(Bot bot, CustomMethodProvider customMethodProvider, String scriptInfo) {
        customMethodProvider.logger.info("Setting new window title...");
        customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Setup");
    };

    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>();
    };
}
