package activities.skills.fletching;

import utils.requirement.ItemRequirement;

import java.util.Arrays;

public enum FletchingItem {

    ARROW_SHAFTS("15 arrow shafts", "15 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Logs", 1)),
    ARROW_SHAFTS_30("30 arrow shafts", "30 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Oak logs", 1)),
    ARROW_SHAFTS_45("45 arrow shafts", "45 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Willow logs", 1)),
    ARROW_SHAFTS_60("60 arrow shafts", "60 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Maple logs", 1)),
    ARROW_SHAFTS_75("75 arrow shafts", "75 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Yew logs", 1)),
    ARROW_SHAFTS_90("90 arrow shafts", "90 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Magic logs", 1)),
    ARROW_SHAFTS_105("105 arrow shafts", "105 arrow shafts", FletchingType.ARROW_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Redwood logs", 1)),

    OAK_SHIELD("Oak shield", "Oak shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Oak logs", 1)),
    WILLOW_SHIELD("Willow shield", "Willow shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Willow logs", 1)),
    MAPLE_SHIELD("Maple shield", "Maple shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Maple logs", 1)),
    YEW_SHIELD("Yew shield", "Yew shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Yew logs", 1)),
    MAGIC_SHIELD("Magic shield", "Magic shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Magic logs", 1)),
    REDWOOD_SHIELD("Redwood shield", "Redwood shield", FletchingType.SHIELD, new ItemRequirement("Knife"), new ItemRequirement("Redwood logs", 1)),

    JAVELIN_SHAFTS("Javelin Shafts", "15 javelin Shafts", FletchingType.JAVELIN_SHAFT, new ItemRequirement("Knife"), new ItemRequirement("Logs", 1)),

    HEADLESS_ARROWS("Headless arrow", "Headless arrow", FletchingType.HEADLESS_ARROW, new ItemRequirement("Arrow shaft", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),

    BRONZE_ARROW("Bronze arrow", "Bronze arrow", FletchingType.ARROW, new ItemRequirement("Bronze arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    IRON_ARROW("Iron arrow", "Iron arrow", FletchingType.ARROW, new ItemRequirement("Iron arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    STEEL_ARROW("Steel arrow", "Steel arrow", FletchingType.ARROW, new ItemRequirement("Steel arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    MITHRIL_ARROW("Mithril arrow", "Mithril arrow", FletchingType.ARROW, new ItemRequirement("Mithril arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    ADAMANT_ARROW("Adamant arrow", "Adamant arrow", FletchingType.ARROW, new ItemRequirement("Adamant arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    RUNE_ARROW("Rune arrow", "Rune arrow", FletchingType.ARROW, new ItemRequirement("Rune arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),
    DRAGON_ARROW("Dragon arrow", "Dragon arrow", FletchingType.ARROW, new ItemRequirement("Dragon arrowtips", 1).setStackable(), new ItemRequirement("Headless arrow", 1).setStackable()),

    SHORT_BOW_U("Shortbow (u)", "Shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Logs", 1)),
    LONG_BOW_U("Longbow (u)", "Longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Logs", 1)),
    OAK_SHORT_BOW_U("Oak shortbow (u)", "Oak shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Oak logs", 1)),
    OAK_LONG_BOW_U("Oak longbow (u)", "Oak longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Oak logs", 1)),
    //OGRE_COMPOSITE_BOW_U ("Comp ogre bow (u)", FletchItemType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("", 1)),
    WILLOW_SHORTBOW_U("Willow shortbow (u)", "Willow shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Willow logs", 1)),
    WILLOW_LONGBOW_U("Willow longbow (u)", "Willow longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Willow logs", 1)),
    MAPLE_SHORTBOW_U("Maple shortbow (u)", "Maple shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Maple logs", 1)),
    MAPLE_LONGBOW_U("Maple longbow (u)", "Maple longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Maple logs", 1)),
    YEW_SHORTBOW_U("Yew shortbow (u)", "Yew shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Yew logs", 1)),
    YEW_LONGBOW_U("Yew longbow (u)", "Yew longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Yew logs", 1)),
    MAGIC_SHORTBOW_U("Magic shortbow (u)", "Magic shortbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Magic logs", 1)),
    MAGIC_LONGBOW_U("Magic longbow (u)", "Magic longbow", FletchingType.BOW_U, new ItemRequirement("Knife"), new ItemRequirement("Magic logs", 1)),

    SHORT_BOW("Shortbow", "Shortbow", FletchingType.BOW, new ItemRequirement("Shortbow (u)", 1), new ItemRequirement("Bow string", 1)),
    LONG_BOW("Longbow", "Longbow", FletchingType.BOW, new ItemRequirement("Longbow (u)", 1), new ItemRequirement("Bow string", 1)),
    OAK_SHORT_BOW("Oak shortbow", "Oak shortbow", FletchingType.BOW, new ItemRequirement("Oak shortbow (u)", 1), new ItemRequirement("Bow string", 1)),
    OAK_LONG_BOW("Oak longbow", "Oak longbow", FletchingType.BOW, new ItemRequirement("Oak longbow (u)", 1), new ItemRequirement("Bow string", 1)),
    OGRE_COMPOSITE_BOW("Comp ogre bow", "Comp ogre bow", FletchingType.BOW, new ItemRequirement("Ogre composite bow (u)", 1), new ItemRequirement("Bow string", 1)),
    WILLOW_SHORTBOW("Willow shortbow", "Willow shortbow", FletchingType.BOW, new ItemRequirement("Willow shortbow (u)", 1), new ItemRequirement("Bow string", 1)),
    WILLOW_LONGBOW("Willow longbow", "Willow longbow", FletchingType.BOW, new ItemRequirement("Willow longbow (u)", 1), new ItemRequirement("Bow string", 1)),
    MAPLE_SHORTBOW("Maple shortbow", "Maple shortbow", FletchingType.BOW, new ItemRequirement("Maple shortbow (u)", 1), new ItemRequirement("Bow string", 1)),
    MAPLE_LONGBOW("Maple longbow", "Maple longbow", FletchingType.BOW, new ItemRequirement("Maple longbow (u)", 1), new ItemRequirement("Bow string", 1)),
    YEW_SHORTBOW("Yew shortbow", "Yew shortbow", FletchingType.BOW, new ItemRequirement("Yew shortbow (u", 1), new ItemRequirement("Bow string", 1)),
    YEW_LONGBOW("Yew longbow", "Yew longbow", FletchingType.BOW, new ItemRequirement("Yew longbow (u)", 1), new ItemRequirement("Bow string", 1)),
    MAGIC_SHORTBOW("Magic shortbow", "Magic shortbow", FletchingType.BOW, new ItemRequirement("Magic shortbow (u)", 1), new ItemRequirement("Bow string", 1)),
    MAGIC_LONGBOW("Magic longbow", "Magic longbow", FletchingType.BOW, new ItemRequirement("Magic longbow (u)", 1), new ItemRequirement("Bow string", 1)),

    CROSSBOW_STOCK("Wooden stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Logs", 1)),
    OAK_CROSSBOW_STOCK("Oak stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Oak logs", 1)),
    WILLOW_CROSSBOW_STOCK("Willow stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Willow logs", 1)),
    TEAK_CROSSBOW_STOCK("Teak stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Teak logs", 1)),
    MAPLE_CROSSBOW_STOCK("Maple stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Maple logs", 1)),
    MAHOGANY_CROSSBOW_STOCK("Mahogany stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Mahogany logs", 1)),
    YEW_CROSSBOW_STOCK("Yew stock", "Crossbow stock", FletchingType.CROSSBOW_STOCK, new ItemRequirement("Knife"), new ItemRequirement("Yew logs", 1)),

    BRONZE_CROSSBOW_U("Bronze crossbow (u)", "Bronze crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Bronze limbs", 1), new ItemRequirement("Wooden stock", 1), new ItemRequirement("Hammer")),
    IRON_CROSSBOW_U("Iron crossbow (u)", "Iron crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Iron limbs", 1), new ItemRequirement("Willow stock", 1), new ItemRequirement("Hammer")),
    STEEL_CROSSBOW_U("Steel crossbow (u)", "Steel crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Steel limbs", 1), new ItemRequirement("Teak stock", 1), new ItemRequirement("Hammer")),
    MITHRIL_CROSSBOW_U("Mithril crossbow (u)", "Mithril crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Mithril limbs", 1), new ItemRequirement("Maple stock", 1), new ItemRequirement("Hammer")),
    ADAMANTITE_CROSSBOW_U("Adamant crossbow (u)", "Adamant crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Adamantite limbs", 1), new ItemRequirement("Mahogany stock", 1), new ItemRequirement("Hammer")),
    RUNITE_CROSSBOW_U("Runite crossbow (u)", "Rune crossbow (u)", FletchingType.CROSSBOW_U, new ItemRequirement("Runite limbs", 1), new ItemRequirement("Yew stock", 1), new ItemRequirement("Hammer")),

    BRONZE_CROSSBOW("Bronze crossbow", "Bronze crossbow", FletchingType.CROSSBOW, new ItemRequirement("Bronze crossbow (u)", 1), new ItemRequirement("Crossbow string", 1)),
    IRON_CROSSBOW("Iron crossbow", "Iron crossbow", FletchingType.CROSSBOW, new ItemRequirement("Iron crossbow (u)", 1), new ItemRequirement("Crossbow string", 1)),
    STEEL_CROSSBOW("Steel crossbow", "Steel crossbow", FletchingType.CROSSBOW, new ItemRequirement("Steel crossbow (u)", 1), new ItemRequirement("Crossbow string", 1)),
    MITHRIL_CROSSBOW("Mithril crossbow", "Mithril crossbow", FletchingType.CROSSBOW, new ItemRequirement("Mithril crossbow (u)", 1), new ItemRequirement("Crossbow string", 1)),
    ADAMANT_CROSSBOW("Adamant crossbow", "Adamant crossbow", FletchingType.CROSSBOW, new ItemRequirement("Adamant crossbow (u)", 1), new ItemRequirement("Crosbow string", 1)),
    RUNE_CROSSBOW("Rune crossbow", "Rune crossbow", FletchingType.CROSSBOW, new ItemRequirement("Runite crossbow (u)", 1), new ItemRequirement("Crossbow string", 1)),

    BRONZE_DART("Bronze dart", null, FletchingType.DART, new ItemRequirement("Bronze dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    IRON_DART("Iron dart", null, FletchingType.DART, new ItemRequirement("Iron dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    STEEL_DART("Steel dart", null, FletchingType.DART, new ItemRequirement("Steel dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    MITHRIL_DART("Mithril dart", null, FletchingType.DART, new ItemRequirement("Mithril dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    ADAMANT_DART("Adamant dart", null, FletchingType.DART, new ItemRequirement("Adamant dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    RUNE_DART("Rune dart", null, FletchingType.DART, new ItemRequirement("Rune dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable()),
    DRAGON_DART("Dragon dart", null, FletchingType.DART, new ItemRequirement("Dragon dart tip", 1).setStackable(), new ItemRequirement("Feather", 1).setStackable());

    String name;
    String widgetText;
    FletchingType type;
    ItemRequirement[] itemRequirements;

    FletchingItem(final String name, final String widgetText, final FletchingType type, final ItemRequirement... itemRequirements) {
        this.name = name;
        this.widgetText = widgetText;
        this.type = type;
        this.itemRequirements = itemRequirements;
    }

    public static FletchingItem[] getAllWithType(FletchingType fletchItemType) {
        return Arrays.stream(values()).filter(item -> item.type == fletchItemType).toArray(FletchingItem[]::new);
    }

    @Override
    public String toString() {
        return name;
    }
}