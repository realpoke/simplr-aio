package utils;

import org.osbot.rs07.Bot;
import org.osbot.rs07.event.Event;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public final class Loop extends Event {

    private final IntSupplier task;

    private final BooleanSupplier condition;

    private final int maxLoops;

    private int loopCounter = 0;

    public static boolean until(Bot bot, IntSupplier task, BooleanSupplier condition, int maxLoops) {
        Event loop = new Loop(task, condition, maxLoops);
        bot.getEventExecutor().execute(loop);
        return loop.hasFinished();
    }

    // Similar method to the above but with a default maxLoops
    public static boolean until(Bot bot, IntSupplier task, BooleanSupplier condition) {
        return until(bot, task, condition, 100);
    }

    // Similar method to the above but with sleep time given as a parameter
    public static boolean until(Bot bot, Runnable task, BooleanSupplier condition, int sleepTime,  int maxLoops) {
        IntSupplier taskWithReturn = () -> {
            task.run();
            return sleepTime;
        };
        return until(bot, taskWithReturn, condition, maxLoops);
    }

    // Similar method to the above but with default sleep time and max loops
    public static boolean until(Bot bot, Runnable task, BooleanSupplier condition) {
        return until(bot, task, condition, 600, 100);
    }

    private Loop(IntSupplier task, BooleanSupplier condition, int maxLoops) {
        this.task = task;
        this.condition = condition;
        this.maxLoops = maxLoops;
    }

    @Override
    public int execute() {
        if (loopCounter++ > maxLoops)
            // Too many loops, something went wrong, exit
            setFailed();
        else if (condition.getAsBoolean())
            // Condition successfully met, exit
            setFinished();
        else return task.getAsInt();
        return 0;
    }

}