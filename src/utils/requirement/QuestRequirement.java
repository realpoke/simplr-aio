package utils.requirement;

import org.osbot.rs07.api.Quests;
import org.osbot.rs07.api.Skills;

public class QuestRequirement {

    private final Quests.Quest quest;

    public QuestRequirement(final Quests.Quest quest) {
        this.quest = quest;
    }

    public boolean isRequiredQuest(final QuestRequirement[] questReqs, final Quests.Quest quest) {
        for (QuestRequirement req : questReqs) {
            if (req.isRequiredQuest(quest)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSkillRequirements(final SkillRequirement[] skillReqs, Skills playerSkills) {
        for (SkillRequirement req : skillReqs) {
            if (!req.hasRequirement(playerSkills)) {
                return false;
            }
        }
        return true;
    }

    public final String getName() {
        return quest.name();
    }

    public final boolean isRequiredQuest(final Quests.Quest quest) {
        return quest.equals(this.quest);
    }

    public final boolean hasRequirement(final Quests playerQuests) {
        return playerQuests.isComplete(quest);
    }

    @Override
    public String toString() {
        return getName();
    }
}
