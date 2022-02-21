package tasks;

import activities.activity.Activity;
import activities.quests.QuestType;

public class QuestTask extends Task {

    private final QuestType questType;

    public QuestTask(final Activity activity, final QuestType questType) {
        super(TaskType.QUEST, activity);
        this.questType = questType;
    }

    @Override
    public boolean isComplete() {
        return questType.isComplete(getConfigs());
    }

    private int getProgress() {
        return getConfigs().get(this.questType.getConfigID());
    }

    @Override
    public String toString() {
        return "Quest task: " + questType.toString() + " (" + (int)(((float)getProgress() / questType.getCompletedConfigVal()) * 100) + "%) ";
    }

    @Override
    public Task copy() {
        return new QuestTask(getActivity().copy(), questType);
    }
}