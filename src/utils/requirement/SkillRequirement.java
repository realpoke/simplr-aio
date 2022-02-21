package utils.requirement;

import org.osbot.rs07.api.Skills;
import org.osbot.rs07.api.ui.Skill;

public class SkillRequirement {

    private final Skill skill;
    private final int minLevel;

    public SkillRequirement(final Skill skill, final int minLevel) {
        this.skill = skill;
        this.minLevel = minLevel;
    }

    public static boolean isRequiredSkill(final SkillRequirement[] skillReqs, final Skill skill) {
        for (SkillRequirement req : skillReqs) {
            if (req.isRequiredSkill(skill)) {
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
        return skill.name();
    }

    public final int getMinLevel() {
        return minLevel;
    }

    public final boolean isRequiredSkill(final Skill skill) {
        return skill.equals(this.skill);
    }

    public final boolean hasRequirement(final Skills playerSkills) {
        return playerSkills.getDynamic(skill) >= getMinLevel();
    }

    @Override
    public String toString() {
        return getName();
    }
}
