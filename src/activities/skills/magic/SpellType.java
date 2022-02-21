package activities.skills.magic;

import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.Spells;
import utils.requirement.ItemRequirement;

public enum SpellType {

    //HOME_TELEPORT(Spells.NormalSpells.HOME_TELEPORT, SpellType.TELEPORT),
    /*WIND_STRIKE(Spells.NormalSpells.WIND_STRIKE, SpellCastType.COMBAT, new ItemRequirement(RuneType.AIR.toString(), 1), new ItemRequirement(RuneType.MIND.toString(), 1)),
    CONFUSE(Spells.NormalSpells.CONFUSE, SpellCastType.COMBAT, new ItemRequirement(RuneType.BODY.toString(), 1), new ItemRequirement(RuneType.EARTH.toString(), 2), new ItemRequirement(RuneType.WATER.toString(), 3)),
    ENCHANT_CROSSBOW_BOLT_OPAL(Spells.NormalSpells.ENCHANT_CROSSBOW_BOLT, SpellCastType.ENCHANT, new ItemRequirement(RuneType.COSMIC.toString(), 1), new ItemRequirement(RuneType.AIR.toString(), 2), new ItemRequirement("Opal bolts", 10)),
    WATER_STRIKE(Spells.NormalSpells.WATER_STRIKE, SpellCastType.COMBAT, new ItemRequirement(RuneType.MIND.toString(), 1), new ItemRequirement(RuneType.WATER.toString(), 1), new ItemRequirement(RuneType.AIR.toString(), 1)),
    LVL_1_ENCHANT(Spells.NormalSpells.LVL_1_ENCHANT, SpellCastType.ENCHANT, new ItemRequirement(RuneType.COSMIC.toString(), 1), new ItemRequirement(RuneType.WATER.toString(), 1)),
    ENCHANT_CROSSBOW_BOLT_SAPPHIRE(Spells.NormalSpells.ENCHANT_CROSSBOW_BOLT, SpellCastType.ENCHANT, new ItemRequirement(RuneType.COSMIC.toString(), 1), new ItemRequirement(RuneType.MIND.toString(), 1), new ItemRequirement(RuneType.WATER.toString(), 1), new ItemRequirement("Sapphire bolts", 10)),
    EARTH_STRIKE(Spells.NormalSpells.EARTH_STRIKE, SpellCastType.COMBAT, new ItemRequirement(RuneType.MIND.toString(), 1), new ItemRequirement(RuneType.EARTH.toString(), 2), new ItemRequirement(RuneType.AIR.toString(), 1)),
    WEAKEN(Spells.NormalSpells.WEAKEN, SpellCastType.COMBAT, new ItemRequirement(RuneType.BODY.toString(), 1), new ItemRequirement(RuneType.EARTH.toString(), 2), new ItemRequirement(RuneType.WATER.toString(), 3)),
    FIRE_STRIKE(Spells.NormalSpells.FIRE_STRIKE, SpellCastType.COMBAT, new ItemRequirement(RuneType.MIND.toString(), 1), new ItemRequirement(RuneType.FIRE.toString(), 3), new ItemRequirement(RuneType.AIR.toString(), 2)),
    BONES_TO_BANANAS(Spells.NormalSpells.BONES_TO_BANANAS),
    WIND_BOLT(Spells.NormalSpells.WIND_BOLT),
    CURSE(Spells.NormalSpells.CURSE),
    BIND(Spells.NormalSpells.BIND),*/
    //LOW_LEVEL_ALCHEMY(Spells.NormalSpells.LOW_LEVEL_ALCHEMY, SpellCastType.ALCH, new ItemRequirement(RuneType.FIRE.toString(), 3), new ItemRequirement(RuneType.NATURE.toString(), 1));
    /*WATER_BOLT(Spells.NormalSpells.WATER_BOLT),
    VARROCK_TELEPORT(Spells.NormalSpells.VARROCK_TELEPORT),
    LVL_2_ENCHANT(Spells.NormalSpells.LVL_2_ENCHANT),
    EARTH_BOLT(Spells.NormalSpells.EARTH_BOLT),
    LUMBRIDGE_TELEPORT(Spells.NormalSpells.LUMBRIDGE_TELEPORT),
    TELEKINETIC_GRAB(Spells.NormalSpells.TELEKINETIC_GRAB),
    FIRE_BOLT(Spells.NormalSpells.FIRE_BOLT),
    FALADOR_TELEPORT(Spells.NormalSpells.FALADOR_TELEPORT),
    CRUMBLE_UNDEAD(Spells.NormalSpells.CRUMBLE_UNDEAD),
    TELEPORT_TO_HOUSE(Spells.NormalSpells.TELEPORT_TO_HOUSE),
    WIND_BLAST(Spells.NormalSpells.WIND_BLAST),
    SUPERHEAT_ITEM(Spells.NormalSpells.SUPERHEAT_ITEM),
    CAMELOT_TELEPORT(Spells.NormalSpells.CAMELOT_TELEPORT),
    WATER_BLAST(Spells.NormalSpells.WATER_BLAST),
    LVL_3_ENCHANT(Spells.NormalSpells.LVL_3_ENCHANT),
    IBAN_BLAST(Spells.NormalSpells.IBAN_BLAST),
    SNARE(Spells.NormalSpells.SNARE),
    MAGIC_DART(Spells.NormalSpells.MAGIC_DART),
    ARDOUGNE_TELEPORT(Spells.NormalSpells.ARDOUGNE_TELEPORT),
    EARTH_BLAST(Spells.NormalSpells.EARTH_BLAST),
    HIGH_LEVEL_ALCHEMY(Spells.NormalSpells.HIGH_LEVEL_ALCHEMY, new ItemRequirement(Rune.FIRE.toString(), 5), new ItemRequirement(Rune.NATURE.toString(), 1)),
    CHARGE_WATER_ORB(Spells.NormalSpells.CHARGE_WATER_ORB),
    LVL_4_ENCHANT(Spells.NormalSpells.LVL_4_ENCHANT),
    WATCHTOWER_TELEPORT(Spells.NormalSpells.WATCHTOWER_TELEPORT),
    FIRE_BLAST(Spells.NormalSpells.FIRE_BLAST),
    CHARGE_EARTH_ORB(Spells.NormalSpells.CHARGE_EARTH_ORB),
    BONES_TO_PEACHES(Spells.NormalSpells.BONES_TO_PEACHES),
    SARADOMIN_STRIKE(Spells.NormalSpells.SARADOMIN_STRIKE),
    CLAWS_OF_GUTHIX(Spells.NormalSpells.CLAWS_OF_GUTHIX),
    FLAMES_OF_ZAMORAK(Spells.NormalSpells.FLAMES_OF_ZAMORAK),
    TROLLHEIM_TELEPORT(Spells.NormalSpells.TROLLHEIM_TELEPORT),
    WIND_WAVE(Spells.NormalSpells.WIND_WAVE),
    CHARGE_FIRE_ORB(Spells.NormalSpells.CHARGE_FIRE_ORB),
    TELEPORT_TO_APE_ATOLL(Spells.NormalSpells.TELEPORT_TO_APE_ATOLL),
    WATER_WAVE(Spells.NormalSpells.WATER_WAVE),
    CHARGE_AIR_ORB(Spells.NormalSpells.CHARGE_AIR_ORB),
    VULNERABILITY(Spells.NormalSpells.VULNERABILITY),
    LVL_5_ENCHANT(Spells.NormalSpells.LVL_5_ENCHANT),
    TELEPORT_TO_KOUREND(Spells.NormalSpells.TELEPORT_TO_KOUREND),
    EARTH_WAVE(Spells.NormalSpells.EARTH_WAVE),
    ENFEEBLE(Spells.NormalSpells.ENFEEBLE),
    TELEOTHER_LUMBRIDGE(Spells.NormalSpells.TELEOTHER_LUMBRIDGE),
    FIRE_WAVE(Spells.NormalSpells.FIRE_WAVE),
    ENTANGLE(Spells.NormalSpells.ENTANGLE),
    STUN(Spells.NormalSpells.STUN),
    CHARGE(Spells.NormalSpells.CHARGE),
    WIND_SURGE(Spells.NormalSpells.WIND_SURGE),
    TELEOTHER_FALADOR(Spells.NormalSpells.TELEOTHER_FALADOR),
    WATER_SURGE(Spells.NormalSpells.WATER_SURGE),
    TELE_BLOCK(Spells.NormalSpells.TELE_BLOCK),
    TELEPORT_TO_BOUNTY_TARGET(Spells.NormalSpells.TELEPORT_TO_BOUNTY_TARGET),
    LVL_6_ENCHANT(Spells.NormalSpells.LVL_6_ENCHANT),
    TELEOTHER_CAMELOT(Spells.NormalSpells.TELEOTHER_CAMELOT),
    EARTH_SURGE(Spells.NormalSpells.EARTH_SURGE),
    LVL_7_ENCHANT(Spells.NormalSpells.LVL_7_ENCHANT),
    FIRE_SURGE(Spells.NormalSpells.FIRE_SURGE);*/

    TEST;

    private MagicSpell spell;
    private SpellCastType spellType;
    private BookType bookType;
    private ItemRequirement[] itemRequirements;

    SpellType() {

    }

    SpellType(final MagicSpell spell, final SpellCastType spellType, final BookType bookType, final ItemRequirement... itemRequirements) {
        this.spell = spell;
        this.spellType = spellType;
        this.bookType = bookType;
        this.itemRequirements = itemRequirements;
    }
}