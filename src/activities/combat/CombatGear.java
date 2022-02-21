package activities.combat;

import org.osbot.rs07.api.Quests;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import utils.requirement.QuestRequirement;
import utils.requirement.SkillRequirement;

public enum CombatGear {

    AMULET_OF_STRENGTH("Amulet of strength", EquipmentSlot.AMULET),
    IRON_FULL_HELM("Iron full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 1)),
    IRON_PLATELEGS("Iron platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 1)),
    IRON_PLATEBODY("Iron platebody", EquipmentSlot.CHEST, new SkillRequirement(Skill.DEFENCE, 1)),
    IRON_KITESHIELD("Iron kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 1)),
    IRON_SCIMITAR("Iron scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.ATTACK, 1)),
    STEEL_FULL_HELM("Steel full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 5)),
    STEEL_PLATELEGS("Steel platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 5)),
    STEEL_PLATEBODY("Steel platebody", EquipmentSlot.CHEST, new SkillRequirement(Skill.DEFENCE, 5)),
    STEEL_KITESHIELD("Steel kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 5)),
    STEEL_SCIMITAR("Steel scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.ATTACK, 5)),
    BLACK_FULL_HELM("Black full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 10)),
    BLACK_PLATELEGS("Black platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 10)),
    BLACK_PLATEBODY("Black platebody", EquipmentSlot.CHEST, new SkillRequirement(Skill.DEFENCE, 10)),
    BLACK_KITESHIELD("Black kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 10)),
    BLACK_SCIMITAR("Black scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.ATTACK, 10)),
    MITHRIL_FULL_HELM("Mithril full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 20)),
    MITHRIL_PLATELEGS("Mithril platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 20)),
    MITHRIL_PLATEBODY("Mithril platebody", EquipmentSlot.CHEST, new SkillRequirement(Skill.DEFENCE, 20)),
    MITHRIL_KITESHIELD("Mithril kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 20)),
    MITHRIL_SCIMITAR("Mithril scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.ATTACK, 20)),
    ADAMANT_FULL_HELM("Adamant full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 30)),
    ADAMANT_PLATELEGS("Adamant platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 30)),
    ADAMANT_PLATEBODY("Adamant platebody", EquipmentSlot.CHEST, new SkillRequirement(Skill.DEFENCE, 30)),
    ADAMANT_KITESHIELD("Adamant kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 30)),
    ADAMANT_SCIMITAR("Adamant scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.ATTACK, 30)),
    RUNE_FULL_HELM("Rune full helm", EquipmentSlot.HAT, new SkillRequirement(Skill.DEFENCE, 40)),
    RUNE_PLATELEGS("Rune platelegs", EquipmentSlot.LEGS, new SkillRequirement(Skill.DEFENCE, 40)),
    RUNE_PLATEBODY("Rune platebody", EquipmentSlot.CHEST, new QuestRequirement(Quests.Quest.DRAGON_SLAYER), new SkillRequirement(Skill.DEFENCE, 40)),
    RUNE_KITESHIELD("Rune kiteshield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.DEFENCE, 40)),
    RUNE_SCIMITAR("Rune scimitar", EquipmentSlot.WEAPON, new SkillRequirement(Skill.DEFENCE, 40)),

    AMULET_OF_MAGIC("Amulet of magic", EquipmentSlot.AMULET),
    BLACK_WIZARD_HAT("Black wizard hat", EquipmentSlot.HAT,  new SkillRequirement(Skill.MAGIC, 1)),
    BLACK_WIZARD_ROBE("Black wizard robe", EquipmentSlot.CHEST, new SkillRequirement(Skill.MAGIC, 1)),
    ZAMORAK_MONK_BOTTOM("Zamorak monk bottem", EquipmentSlot.LEGS, new SkillRequirement(Skill.MAGIC, 1)),
    ANTI_DRAGON_SHIELD("Anti-dragon shield", EquipmentSlot.SHIELD, new QuestRequirement(Quests.Quest.DRAGON_SLAYER), new SkillRequirement(Skill.DEFENCE, 1)),
    STAFF_OF_FIRE("Staff of fire", EquipmentSlot.WEAPON, new SkillRequirement(Skill.MAGIC, 1)),

    AMULET_OF_POWER("Amulet of power", EquipmentSlot.AMULET),
    LEATHER_COWL("Leather cowl", EquipmentSlot.HAT,  new SkillRequirement(Skill.RANGED, 1)),
    LEATHER_BODY("Leather body", EquipmentSlot.CHEST, new SkillRequirement(Skill.RANGED, 1)),
    LEATHER_CHAPS("Leather chaps", EquipmentSlot.LEGS, new SkillRequirement(Skill.RANGED, 1)),
    HARD_LEATHER_BODY("Hardleather body", EquipmentSlot.CHEST, new SkillRequirement(Skill.RANGED, 1), new SkillRequirement(Skill.DEFENCE, 10)),
    COIF("COIF", EquipmentSlot.HAT,  new SkillRequirement(Skill.RANGED, 20)),
    STUDDED_BODY("Studded body", EquipmentSlot.CHEST, new SkillRequirement(Skill.RANGED, 20), new SkillRequirement(Skill.DEFENCE, 20)),
    STUDDED_CHAPS("Studded chaps", EquipmentSlot.LEGS, new SkillRequirement(Skill.RANGED, 20)),
    GREEN_DRAGONHIDE_CHAPS("Green d'hide chaps", EquipmentSlot.LEGS, new SkillRequirement(Skill.RANGED, 40)),
    GREEN_DRAGONHIDE_BODY("Green d'hide body", EquipmentSlot.CHEST, new QuestRequirement(Quests.Quest.DRAGON_SLAYER), new SkillRequirement(Skill.RANGED, 40), new SkillRequirement(Skill.DEFENCE, 40)),
    GREEN_DRAGONHIDE_VAMBRACES("Green d'hide vambraces", EquipmentSlot.HANDS, new SkillRequirement(Skill.RANGED, 40)),
    GREEN_DRAGONHIDE_SHIELD("Green d'hide shield", EquipmentSlot.SHIELD, new SkillRequirement(Skill.RANGED, 40)),
    SHORTBOW("Shortbow", EquipmentSlot.WEAPON, new SkillRequirement(Skill.RANGED, 1)),
    IRON_ARROW("Iron arrow", EquipmentSlot.ARROWS),
    OAK_SHORTBOW("Oak shortbow", EquipmentSlot.WEAPON, new SkillRequirement(Skill.RANGED, 5)),
    STEEL_ARROW("Steel arrow", EquipmentSlot.ARROWS),
    WILLOW_SHORTBOW("Willow shortbow", EquipmentSlot.WEAPON, new SkillRequirement(Skill.RANGED, 20)),
    MITHRIL_ARROW("Mithril arrow", EquipmentSlot.ARROWS),
    MAPLE_SHORTBOW("Maple shortbow", EquipmentSlot.WEAPON, new SkillRequirement(Skill.RANGED, 30)),
    ADAMANT_ARROW("Adamant arrow", EquipmentSlot.ARROWS);

    public final String name;
    public final EquipmentSlot slot;
    public SkillRequirement[] skills;
    public QuestRequirement[] quests;

    CombatGear(String name, EquipmentSlot slot) {
        this.name = name;
        this.slot = slot;
    }


    CombatGear(String name, EquipmentSlot slot, SkillRequirement... skills) {
        this.name = name;
        this.slot = slot;
        this.skills = skills;
    }

    CombatGear(String name, EquipmentSlot slot, QuestRequirement... quests) {
        this.name = name;
        this.slot = slot;
        this.quests = quests;
    }

    CombatGear(String name, EquipmentSlot slot, QuestRequirement[] quests, SkillRequirement... skills){
        this.name = name;
        this.slot = slot;
        this.skills = skills;
        this.quests = quests;
    }

    CombatGear(String name, EquipmentSlot slot, QuestRequirement quests, SkillRequirement... skills){
        this.name = name;
        this.slot = slot;
        this.skills = skills;
        this.quests = new QuestRequirement[]{quests};
    }
}