package activities.skills.prayer;

import utils.requirement.ItemRequirement;
import utils.requirement.SkillRequirement;

public enum EnsouledHeadType {

    GOBLIN("Ensouled goblin head"),
    MONKEY("Ensouled monkey head"),
    IMP("Ensouled imp head"),
    MINOTAUR("Ensouled minotaur head"),
    SCORPION("Ensouled scorpion head"),
    BEAR("Ensouled bear head"),
    UNICORN("Ensouled unicorn head"),
    DOG("Ensouled dog head"),
    CHAOS_DRUID("Ensouled chaos druid head"),
    GIANT("Ensouled giant head"),
    OGRE("Ensouled ogre head"),
    ELF("Ensouled elf head"),
    TROLL("Ensouled troll head"),
    HORRO("Ensouled horror head"),
    KALPHITE("Ensouled kalphite head"),
    DAGANNOTH("Ensouled dagannoth head"),
    BLOODVELD("Ensouled bloodveld head"),
    TZHAAR("Ensouled tzhaar head"),
    DEMON("Ensouled demon head"),
    AVIANSIE("Ensouled aviansie head"),
    ABYSSAL("Ensouled abyssal head"),
    Dragon("Ensouled dragon head");

    String name;
    SkillRequirement[] skillReq;

    EnsouledHeadType(final String name) {
        this.name = name;
    }

    EnsouledHeadType(final String name, final SkillRequirement... skillReq) {
        this.name = name;
        this.skillReq = skillReq;
    }

    @Override
    public String toString() {
        return name;
    }
}