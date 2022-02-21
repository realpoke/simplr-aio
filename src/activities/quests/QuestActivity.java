package activities.quests;

import activities.activity.Activity;
import activities.activity.ActivityType;

public abstract class QuestActivity extends Activity {

    private final QuestType questType;

    public QuestActivity(final QuestType questType) {
        super(ActivityType.QUEST);
        this.questType = questType;
    }

    protected int getProgress() {
        return getConfigs().get(questType.configID);
    }
}