package activities.skills.prayer;

import org.osbot.rs07.api.ui.Skill;
import utils.requirement.SkillRequirement;

public enum BoneType {

    BONE("Bones"),
    WOLF("Wolf bones"),
    BURNT("Burnt bones"),
    MONKEY("Monkey bones"),
    BAT("Bat bones"),
    BIG("Big bones"),
    JOGRE("Jogre bones"),
    ZOGRE("Zogre bones"),
    SHAIKAHAN("Shaikahan bones"),
    BABY_DRAGON("Babydragon bones"),
    WYRM("Wyrm bones"),
    WYVERN("Wyvern bones"),
    DRAGON("Dragon bones"),
    DRAKE("Drake bones"),
    FAYRG("Fayrg bones"),
    LAVA_DRAGON("Lava dragon bones"),
    RAURG("Raurg bones"),
    HYDRA("Hydra bones"),
    DAGANNOTH("Dagannoth bones"),
    OURG("Ourg bones"),
    SUPERIOR_DRAGON("Superior dragon bones", new SkillRequirement(Skill.PRAYER, 70));

    String name;
    SkillRequirement[] skillReq;

    BoneType(final String name) {
        this.name = name;
    }

    BoneType(final String name, final SkillRequirement skillReq) {
        this.name = name;
        this.skillReq = new SkillRequirement[]{skillReq};
    }

    @Override
    public String toString() {
        return name;
    }
}