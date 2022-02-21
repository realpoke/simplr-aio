package tasks;

import activities.activity.Activity;
import utils.Time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimedTask extends Task {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private long durationMs;
    private LocalDateTime endTime;
    private String endDateStr;
    private String endTimeStr;

    private long startTimeMs;
    private boolean isStarted;

    public TimedTask(final Activity activity, final long durationMs) {
        super(TaskType.TIMED, activity);
        this.durationMs = durationMs;
    }

    public TimedTask(final Activity activity, final LocalDateTime endTime) {
        super(TaskType.TIMED, activity);
        this.endTime = endTime;
        endDateStr = dateFormatter.format(endTime);
        endTimeStr = timeFormatter.format(endTime);
    }

    @Override
    public boolean isComplete() {
        if (!isStarted) {
            return false;
        }

        if (endTime != null) {
            return LocalDateTime.now().isAfter(endTime);
        }

        return System.currentTimeMillis() - startTimeMs >= durationMs;
    }

    @Override
    public void run() throws InterruptedException {
        if (!isStarted) {
            startTimeMs = System.currentTimeMillis();
            isStarted = true;
        }
        super.run();
    }

    @Override
    public String toString() {
        if (endTime != null) {
            return String.format("Timed task: Ends on %s at %s", endDateStr, endTimeStr);
        }

        long runTime = System.currentTimeMillis() - startTimeMs;
        return String.format("Timed task: (%s/%s)", Time.formatTime(runTime), Time.formatTime(durationMs));
    }

    @Override
    public Task copy() {
        return new TimedTask(getActivity().copy(), durationMs);
    }
}