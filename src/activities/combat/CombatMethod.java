package activities.combat;

import org.osbot.rs07.api.ui.Skill;

public enum CombatMethod {

    MELEE(Skill.HITPOINTS, Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE),
    RANGED(Skill.HITPOINTS, Skill.RANGED, Skill.DEFENCE),
    MAGIC(Skill.HITPOINTS, Skill.MAGIC);

    public final Skill[] skills;

    CombatMethod(Skill... skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        char[] name = name().toLowerCase().toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        return new String(name);
    }
}
