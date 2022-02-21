package activities.skills.herblore;

import utils.requirement.ItemRequirement;

public enum PotionType {

    GUAM_POTION("Guam potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Guam leaf", 1)),
    ATTACK("Attack potion(3)", new ItemRequirement("Guam potion (unf)", 1), new ItemRequirement("Eye of newt", 1)),
    GUAM_TAR("Guam tar", new ItemRequirement("Guam potion (unf)", 1), new ItemRequirement("Swamp tar", 15)),

    MARRENTIL_POTION("Marrentill potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Marrentill", 1)),
    ANTIPOISION("Antipoison", new ItemRequirement("Marrentill potion (unf)", 1), new ItemRequirement("Unicorn horn dust", 1)),
    MARRENTILL_TAR("Marrentill tar", new ItemRequirement("Marrentill potion (unf)", 1), new ItemRequirement("Swamp tar", 15)),

    ROGUES_PURSE_POTION("Rogue's purse potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Rogue's purse", 1)),
    RELICYMS_BALM("Relicym's balm", new ItemRequirement("Rogue's purse potion (unf)", 1), new ItemRequirement("Snake weed", 1)),

    TARROMIN_POTION("Tarromin potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Tarromin", 1)),
    STRENGTH("Strength potion", new ItemRequirement("Tarromin potion (unf)", 1), new ItemRequirement("Limpwurt root", 1)),
    SERUM_207("Serum 207", new ItemRequirement("Tarromin potion (unf)", 1), new ItemRequirement("Ashes", 1)),
    TARROMIN("Tarromin tar", new ItemRequirement("Tarromin potion (unf)", 1), new ItemRequirement("Swamp tar", 15)),
    SHRINK_ME_QUICK("Shrink-me-quick", new ItemRequirement("Tarromin potion (unf)", 1), new ItemRequirement("Shrunk ogleroot", 1)),

    HARRALANDER_POTION("Harralander potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Harralander", 1)),
    RESTORE("Restore potion", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Red spiders' eggs", 1)),
    GUTHIX_BALANCE("Guthix balance", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Red spiders' eggs", 1), new ItemRequirement("Garlic", 1), new ItemRequirement("Silver dust", 1)),
    BLAMISH_OIL("Blamish oil", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Blamish snail slime", 1)),
    ENERGY("Energy potion", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Chocolate dust", 1)),
    COMBAT("Combat potion", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Goat horn dust", 1)),
    HARRALANDER("Harralander tar", new ItemRequirement("Harralander potion (unf)", 1), new ItemRequirement("Swamp tar", 15)),


    RANARR_POTION("Ranarr potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Ranarr weed", 1)),
    DEFENCE("Defence potion", new ItemRequirement("Ranarr potion (unf)", 1), new ItemRequirement("White berries", 1)),
    PRAYER("Prayer potion", new ItemRequirement("Ranarr potion (unf)", 1), new ItemRequirement("Snape grass", 1)),

    TOADFLAX_POTION("Toadflax potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Toadflax", 1)),
    AGILITY("Agility potion", new ItemRequirement("Toadflax potion (unf)", 1), new ItemRequirement("Toad's legs", 1)),
    ANTIDOTE_PLUS("Antidote+", new ItemRequirement("Toadflax potion (unf)", 1), new ItemRequirement("Yew roots", 1)),
    SARADOMIN_BREW("Saradomin brew", new ItemRequirement("Toadflax potion (unf)", 1), new ItemRequirement("Crushed birds nest", 1)),

    IRIT_POTION("Irit potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Irit leaf", 1)),
    SUPER_ATTACK("Super attack", new ItemRequirement("Irit potion (unf)", 1), new ItemRequirement("Eye of newt", 1)),
    SUPER_ANTIPOISON("Super antipoison", new ItemRequirement("Irit potion (unf)", 1), new ItemRequirement("Unicorn horn dust", 1)),

    AVANTOE_POTION("Avantoe potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Avantoe", 1)),

    FISHING("Fishing potion", new ItemRequirement("Avantoe potion (unf)", 1), new ItemRequirement("Snape grass", 1)),
    SUPER_ENERGY("Super energy potion", new ItemRequirement("Avantoe potion (unf)", 1), new ItemRequirement("Mort myre fungi", 1)),
    HUNTER("Hunter potion", new ItemRequirement("Avantoe potion (unf)", 1), new ItemRequirement("Kebbit teeth dust", 1)),

    KWUARM_POTION("Kwuarm potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Kwuarm", 1)),
    SUPER_STRENGTH("Super strength", new ItemRequirement("Kwuarm potion (unf)", 1), new ItemRequirement("Limpwurt root", 1)),
    WEAPON_POISON("Weapon poison", new ItemRequirement("Kwuarm potion (unf)", 1), new ItemRequirement("Blue dragon scale", 1)),

    SNAPDRAGON_POTION("Snapdragon potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Snapdragon", 1)),
    SUPER_RESTORE("Super restore", new ItemRequirement("Snapdragon potion (unf)", 1), new ItemRequirement("Red spiders' eggs", 1)),
    SANFEW_SERUM("Sanfew serum", new ItemRequirement("Snapdragon potion (unf)", 1), new ItemRequirement("Red spiders' eggs", 1), new ItemRequirement("Unicorn horn dust", 1), new ItemRequirement("Snake weed", 1), new ItemRequirement("Nail beast nails", 1)),

    CADANTINE_POTION("Cadantine potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Cadantine", 1)),
    SUPER_DEFENCE("Super defence", new ItemRequirement("Cadantine potion (unf)", 1), new ItemRequirement("White berries", 1)),

    LANTADYME_POTION("Lantadyme potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Lantadyme", 1)),
    ANTI_FIRE("Anti-fire potion", new ItemRequirement("Lantadyme potion (unf)", 1), new ItemRequirement("Blue dragon scale", 1)),
    MAGIC("Magic potion", new ItemRequirement("Lantadyme potion (unf)", 1), new ItemRequirement("Potato cactus", 1)),

    DWARF_WEED_POTION("Dwarf weed potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Dwarf weed", 1)),
    RANGING("Ranging potion", new ItemRequirement("Dwarf weed potion (unf)", 1), new ItemRequirement("Wine of Zamorak", 1)),

    TOSRTOL_WEED_POTION("Torstol weed potion (unf)", Constants.VIAL_OF_WATER, new ItemRequirement("Torstol", 1)),
    ZAMORAK_BREW("Zamorak brew", new ItemRequirement("Torstol weed potion (unf)", 1), new ItemRequirement("Jangerberries", 1)),

    ANTIDOTE_PLUS_PLUS_UNF("Antidote++ (unf)", new ItemRequirement("Coconut milk", 1), new ItemRequirement("Irit leaf", 1)),
    ANTIDOTE_PLUS_PLUS("Antidote++", new ItemRequirement("Antidote++ (unf)", 1), new ItemRequirement("Magic roots", 1)),

    WEAPON_POISON_PLUS_UNF("Weapon poison+ (unf)", new ItemRequirement("Coconut milk", 1), new ItemRequirement("Cactus spine", 1)),
    WEAPON_POISION_PLUS("Weapon poison+", new ItemRequirement("Weapon poison+ (unf)", 1), new ItemRequirement("Red spiders' eggs", 1)),

    WEAPON_POISON_PLUS_PLUS_UNF("Weapon poision++ (unf)", new ItemRequirement("Coconut milk", 1), new ItemRequirement("Nightshade", 1)),
    WEAPON_POISON_PLUS_PLUS("Weapon poison++", new ItemRequirement("Weapon poision++ (unf)", 1), new ItemRequirement("Poison ivy berries", 1)),

    STAMINA("Stamina potion", new ItemRequirement("Super energy (3)", 1), new ItemRequirement("Amylase crystal", 1)),
    EXTENDED_ANTIFIRE("Extended antifire", new ItemRequirement("Antifire potion (4)", 1), new ItemRequirement("Lava scale shard", 1)),
    ANTI_VENOM("Anti-venom", new ItemRequirement("Antidote++ (3)", 1), new ItemRequirement("Zulrah's scales", 1)),
    SUPER_COMBAT("Super combat potion", new ItemRequirement("Torstol", 1), new ItemRequirement("Super attack", 1), new ItemRequirement("Super strength", 1), new ItemRequirement("Super defence", 1)),
    ANTI_VENOM_PLUS("Anti-venom+", new ItemRequirement("Anti-venom (4)", 1), new ItemRequirement("Torstol", 1));

    public String name;
    public ItemRequirement[] itemRequirements;
    PotionType(final String name, final ItemRequirement... itemRequirements) {
        this.name = name;
        this.itemRequirements = itemRequirements;
    }

    @Override
    public String toString() {
        return name;
    }

    protected interface Constants {
        ItemRequirement VIAL_OF_WATER = new ItemRequirement("Vial of water", 1);
    }
}