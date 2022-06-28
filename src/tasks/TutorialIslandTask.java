package tasks;

import activities.tutorial_island.TutorialIslandActivity;
import utils.WebServer;
import utils.method_provider.providers.CommandLine;

import java.util.HashMap;

public class TutorialIslandTask extends Task {

    public TutorialIslandTask() {
        super(TaskType.TUTORIAL_ISLAND, new TutorialIslandActivity(), false);
    }

    @Override
    public boolean isComplete() {
        if (getConfigs().get(281) == 1000 && myPlayer().isVisible()) {
            // TODO: Make a save util on the custom method provider to save the current user to the webserver
            WebServer webServer = new WebServer(getCommandLine().getString(CommandLine.Commands.API_BASE), getCommandLine().getString(CommandLine.Commands.API_KEY));
            webServer.post("account/" + getCommandLine().getString(CommandLine.Commands.BOT_LOGIN) + "/update", new HashMap<String, String>() {{
                put("location", "Lumbridge");
                put("note", "oot");
            }});
            return true;
        }
        return false;
    }

    @Override
    public void run() throws InterruptedException {
        if (getSettings().getRunEnergy() > 40) {
            getSettings().setRunning(true);
        }
        super.run();
    }

    @Override
    public void onEnd() throws InterruptedException {
        super.onEnd();
    }

    @Override
    public String toString() {
        String taskText = "Tutorial island Task: (" + Math.min((int)(((float)getConfigs().get(281) / 680) * 100), 100) + "%)";
        if (getCommandLine().getBoolean(CommandLine.Commands.HARDCORE_TUTORIAL_ISLAND)) {
            taskText = taskText + " Hardcore Ironman Mode";
        } else {
            taskText = taskText + " Normal Mode";
        }
        return taskText;
    }

    @Override
    public Task copy() {
        return new TutorialIslandTask();
    }
}