package tasks;

import activities.tutorial_island.TutorialIslandActivity;
import utils.WebServer;
import utils.method_provider.providers.CommandLine;

public class TutorialIslandTask extends Task {

    public TutorialIslandTask() {
        super(TaskType.TUTORIAL_ISLAND, new TutorialIslandActivity(), false);
    }

    @Override
    public boolean isComplete() {
        return getConfigs().get(281) == 1000 && myPlayer().isVisible();
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
        if (getConfigs().get(281) == 1000) {
            // TODO: Save correctly
            //WebServer webServer = new WebServer("https://peachcorp.nl/api/");
            //webServer.saveAccountByEmail(getBot().getUsername());
        }
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