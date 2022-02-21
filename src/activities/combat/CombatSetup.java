package activities.combat;

import org.osbot.rs07.api.ui.Skill;
import java.util.Map;

public enum CombatSetup {

    IRON("Full iron", CombatGear.AMULET_OF_STRENGTH, CombatGear.IRON_FULL_HELM); //, CombatGear.IRON_PLATEBODY, CombatGear.IRON_KITESHIELD, CombatGear.IRON_PLATELEGS, CombatGear.IRON_SCIMITAR);

    public final CombatGear[] gear;
    public final String name;
    public final Map<Skill, Integer>[] levelsRequired;

    CombatSetup(String name, CombatGear... gear) {
        this.name = name;
        this.gear = gear;
        this.levelsRequired = getLevelsRequired();
    }

    private Map<Skill, Integer>[] getLevelsRequired() {
        Map<Skill, Integer>[] skillsRequired = new Map[0];
        for (CombatGear combatGear : gear) {
            System.arraycopy(combatGear.skills, 0, skillsRequired, 0, combatGear.skills.length);
        }
        return skillsRequired;
    }

    @Override
    public String toString() {
        return name;
    }
}