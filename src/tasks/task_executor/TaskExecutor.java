package tasks.task_executor;

import utils.method_provider.Executable;
import utils.method_provider.ExecutionFailedException;
import utils.random_solver.CustomBreakManager;
import tasks.LoopTask;
import tasks.Task;

import java.util.*;

public final class TaskExecutor extends Executable {

    private final List<Task> allTasks;
    private final Queue<Task> taskQueue = new LinkedList<>();
    private final List<TaskChangeListener> taskChangeListeners = new ArrayList<>();
    private final CustomBreakManager breakManager = new CustomBreakManager();
    private long previousIdleTime;
    private long nextIdleTime;

    private Task currentTask;

    public TaskExecutor(final List<Task> tasks) {
        allTasks = tasks;
        this.taskQueue.addAll(tasks);
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setTaskQueue(final List<Task> taskQueue) {
        this.taskQueue.clear();
        this.taskQueue.addAll(taskQueue);
        currentTask = null;
    }

    public final void addTaskChangeListener(final TaskChangeListener taskChangeListener) {
        this.taskChangeListeners.add(taskChangeListener);
    }

    public final void addTaskChangeListeners(final Collection<TaskChangeListener> taskChangeListeners) {
        this.taskChangeListeners.addAll(taskChangeListeners);
    }

    public boolean isComplete() {
        return taskQueue.isEmpty() && (currentTask == null || currentTask.isComplete());
    }

    @Override
    public void onStart() throws InterruptedException {
        if (getBot().getRandomExecutor() != null) {
            breakManager.exchangeContext(getBot());
            getBot().getRandomExecutor().overrideOSBotRandom(breakManager);
        }

        previousIdleTime = System.currentTimeMillis();
        nextIdleTime = previousIdleTime + calculateNextIdleTime();
    }

    @Override
    public final void run() throws InterruptedException {
        if (currentTask == null || (currentTask.isComplete() && currentTask.canExit())) {
            loadNextTask(taskQueue);
        } else {
            runTask(currentTask);
        }
    }

    private void loadNextTask(Queue<Task> taskQueue) throws InterruptedException {
        if (currentTask != null) {
            getBot().removePainter(currentTask);
        }

        getSkillTracker().stopAll();

        Task prevTask = currentTask;
        currentTask = taskQueue.poll();

        if (currentTask == null) {
            return;
        }

        getBot().addPainter(currentTask);

        currentTask.exchangeContext(getBot(), this);

        if (currentTask instanceof LoopTask) {
            ((LoopTask) currentTask).setup(allTasks, taskChangeListeners);
        }

        currentTask.onStart();

        if (currentTask.getActivity() != null &&
                currentTask.getActivity().getActivityType() != null &&
                currentTask.getActivity().getActivityType().gainedXPSkills != null) {
            getSkillTracker().start(currentTask.getActivity().getActivityType().gainedXPSkills);
        }

        for (final TaskChangeListener taskChangeListener : taskChangeListeners) {
            taskChangeListener.taskChanged(prevTask, currentTask);
        }
    }

    private void runTask(final Task task) throws InterruptedException {
        try {
            execute(task);
        } catch (NullPointerException nullPointer) {
            logger.error("Found null pointer exception. Task failed, exiting.");

            StackTraceElement[] stack = nullPointer.getStackTrace();
            for (StackTraceElement element : stack) {
                logger.debug(element.toString());
            }

            currentTask = null;
            taskQueue.clear();
        } catch (ExecutionFailedException executionFailedException) {
            logger.error("Task execution failed due to error:");
            logger.debug(executionFailedException.getMessage());
            logger.info("Proceeding to next task");
            loadNextTask(taskQueue);
        }
    }

    private long calculateNextIdleTime() {
        return (1000L * 60 * random(5, 25)) + random(1000, 15 * 1000);
    }
}