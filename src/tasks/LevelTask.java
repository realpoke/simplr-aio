package tasks;

import activities.activity.Activity;
import org.osbot.rs07.api.ui.Skill;
import utils.Time;

public class LevelTask extends Task {

    private final Skill skill;
    private final int targetLevel;

    public LevelTask(final Activity activity, final Skill skill, final int targetLevel) {
        super(TaskType.LEVEL, activity);
        this.skill = skill;
        this.targetLevel = targetLevel;
    }

    @Override
    public boolean isComplete() {
        return getSkills().getStatic(skill) >= targetLevel;
    }

    @Override
    public String toString() {
        return String.format(
                "Level task: %s (%d/%d) (%s)",
                skill.toString(),
                getSkills().getStatic(skill),
                targetLevel,
                getSkillTracker().getGainedXP(skill) > 0 ? Time.formatTime(getSkillTracker().getTimeToLevel(skill, targetLevel)) : "Calculating..."
        );
    }

    @Override
    public Task copy() {
        return new LevelTask(getActivity().copy(), skill, targetLevel);
    }
}