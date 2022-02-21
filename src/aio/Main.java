package aio;

import paint.*;
import utils.Timer;
import utils.file_manager.FileManager;
import utils.method_provider.CustomMethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.Task;
import tasks.task_executor.TaskExecutor;
import utils.method_provider.providers.CommandLine;

import java.util.ArrayList;

@ScriptManifest(name = "Simpl'r AIO", author = "ItPoke", info = "All In One", version = 2.0, logo = "")
public final class Main extends Script {

    CustomMethodProvider customMethodProvider = new CustomMethodProvider();
    private Timer runTime;
    private final String scriptInfo = this.getName() + " " + this.getVersion() + " by " + this.getAuthor();
    private TaskExecutor taskExecutor;
    private InfoPaint infoPaint;
    private MousePaint mousePaint;
    private NameBlockerPaint nameBlockerPaint;
    private String displayName;

    @Override
    public void onStart() throws InterruptedException {
        logger.clear();

        Timer startupTime = new Timer(0L);
        logger.info("Starting: " + scriptInfo);

        logger.debug("Initializing custom MethodProvider...");
        customMethodProvider.init(getBot(), getParameters());

        ArrayList<Task> tasks;
        if (customMethodProvider.getCommandLine().getBoolean(CommandLine.Commands.ACCOUNT_CHECKER)) {
            logger.info("Using account checker mode!");
            AccountCheckerMode mode = new AccountCheckerMode();
            mode.setup(getBot(), customMethodProvider, scriptInfo);
            tasks = mode.getTasks();
        } else {
            logger.info("Using botting mode!");
            BottingMode mode = new BottingMode();
            mode.setup(getBot(), customMethodProvider, scriptInfo);
            tasks = mode.getTasks();
        }

        if (tasks.isEmpty()) {
            logger.info("No tasks loaded, stopping...");
            exit();
        }

        logger.debug("Setting up task executor...");
        taskExecutor = new TaskExecutor(tasks);
        taskExecutor.exchangeContext(getBot(), customMethodProvider);
        taskExecutor.addTaskChangeListener((oldTask, newTask) -> {
            if (oldTask != null)
                logger.debug("Task executor changed task: " + oldTask + " -> " + newTask);
            infoPaint.setCurrentTask(newTask);
        });
        taskExecutor.onStart();

        startPainting();

        runTime = new Timer(0L);
        startupTime.stop();
        logger.info("Fully loaded in " + startupTime);
    }

    @Override
    public int onLoop() throws InterruptedException {

        if (!customMethodProvider.getCommandLine().getBoolean(CommandLine.Commands.ACCOUNT_CHECKER)) {
             if (!getClient().isLoggedIn() && myPlayer().isVisible()) {
                logger.debug("Waiting to be fully loaded...");
                customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Loading");
                return random(1_200, 1_800);
            } else if (customMethodProvider.getCommandLine().getBoolean(CommandLine.Commands.DUMP_ON_STOP) && displayName == null && myPlayer() != null && myPlayer().getName() != null) {
                 displayName = myPlayer().getName();
             }
        }

        if (taskExecutor.isComplete()) {
            customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Done");
            logger.info("Task executor completed, stopping...");
            exit();
        } else {
            if (customMethodProvider.getCanvasUtil() != null) {
                customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Running");
            }
            customMethodProvider.execute(taskExecutor);
        }

        return random(200, 300);
    }

    @Override
    public void pause() {
        if (customMethodProvider.getCanvasUtil() != null) {
            customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Pausing");
        }
        if (infoPaint != null) {
            logger.debug("Pausing info painter...");
            infoPaint.pause();
        }
        if (mousePaint != null) {
            logger.debug("Pausing mouse painter...");
            mousePaint.pause();
        }
        if (customMethodProvider.getSkillTracker() != null) {
            logger.debug("Pausing all skill trackers...");
            customMethodProvider.getSkillTracker().pauseAll();
        }
        if (customMethodProvider.getCanvasUtil() != null) {
            customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Paused");
        }
    }

    @Override
    public void resume() {
        if (customMethodProvider.getCanvasUtil() != null) {
            customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Resuming");
        }
        if (infoPaint != null) {
            logger.debug("Resuming info painter...");
            infoPaint.resume();
        }
        if (mousePaint != null) {
            logger.debug("Resuming mouse painter...");
            mousePaint.resume();
        }
        if (customMethodProvider.getSkillTracker() != null) {
            logger.debug("Resuming all skill trackers...");
            customMethodProvider.getSkillTracker().resumeAll();
        }
    }

    private void exit() {
        if (customMethodProvider.getCommandLine().getBoolean(CommandLine.Commands.DUMP_ON_STOP)) {
            String logName = displayName + "-LOGDUMP" + System.currentTimeMillis() + ".txt";
            logger.info(CommandLine.Commands.DUMP_ON_STOP.command + " is set, dumping full log: " + logName);

            FileManager file = new FileManager();
            file.exchangeContext(bot);
            file.initializeModule();

            file.lockWrite(logger.getBuffer(), logName);
        }
        if (customMethodProvider.getCommandLine().getBoolean(CommandLine.Commands.KILL_ON_STOP)) {
            logger.info(CommandLine.Commands.KILL_ON_STOP.command + " is set, killing client!");
            System.exit(0);
        }
        stop(false);
    }

    @Override
    public void onExit() {
        if (customMethodProvider.getCanvasUtil() != null) {
            customMethodProvider.getCanvasUtil().setTitle(scriptInfo, "Stopping");
            customMethodProvider.getCanvasUtil().resetTitle();
            customMethodProvider.getCanvasUtil().resetSize();
        }
        if (infoPaint != null) {
            logger.debug("Removing info painter...");
            getBot().removePainter(infoPaint);
        }
        if (nameBlockerPaint != null) {
            logger.debug("Removing name blocker painter...");
            getBot().removePainter(nameBlockerPaint);
        }
        if (mousePaint != null) {
            logger.debug("Removing mouse painter...");
            getBot().removePainter(mousePaint);
        }
        if (runTime != null) {
            logger.info("Time ran: " + runTime.toString());
        }
        logger.info("Stopped: " + scriptInfo);
    }

    private void startPainting() {
        logger.debug("Adding name blocker painter...");
        nameBlockerPaint = new NameBlockerPaint(customMethodProvider);
        getBot().addPainter(nameBlockerPaint);

        logger.debug("Adding mouse painter...");
        mousePaint = new MousePaint(getMouse(), 50, 4, 38, 2);
        getBot().addPainter(mousePaint);

        logger.debug("Adding info painter...");
        infoPaint = new InfoPaint(getBot(), taskExecutor.getSkillTracker(), scriptInfo);
        getBot().addPainter(infoPaint);
    }
}