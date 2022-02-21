package activities.skills.smithing;

import utils.requirement.ItemRequirement;

public enum BarType {

    BRONZE("Bronze bar", 1, true, new ItemRequirement("Copper ore", 1), new ItemRequirement("Tin ore", 1)),
    IRON("Iron bar", 15, true, new ItemRequirement("Iron ore", 1)),
    SILVER("Silver bar", 20, false, new ItemRequirement("Silver ore", 1)),
    STEEL("Steel bar", 30, true, new ItemRequirement("Coal", 2), new ItemRequirement("Iron ore", 1)),
    GOLD("Gold bar", 40, false, new ItemRequirement("Gold ore", 1)),
    MITHRIL("Mithril bar", 50, true, new ItemRequirement("Coal", 4), new ItemRequirement("Mithril ore", 1)),
    ADAMANTITE("Adamantite bar", 70, true, new ItemRequirement("Coal", 6), new ItemRequirement("Adamantite ore", 1)),
    RUNITE("Runite bar", 85, true, new ItemRequirement("Coal", 8), new ItemRequirement("Runite ore", 1));

    public boolean smithable;
    public ItemRequirement[] oresRequired;
    String name;
    int levelRequired;

    BarType(final String name, final int levelRequired, final boolean smithable, final ItemRequirement... oresRequired) {
        this.name = name;
        this.smithable = smithable;
        this.levelRequired = levelRequired;
        this.oresRequired = oresRequired;
    }

    @Override
    public String toString() {
        return name;
    }
}