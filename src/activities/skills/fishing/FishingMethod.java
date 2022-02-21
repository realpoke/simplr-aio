package activities.skills.fishing;

import utils.requirement.ItemRequirement;

public enum FishingMethod {

    NET("Fishing spot", new String[]{"Small Net", "Net"}, new ItemRequirement("Small fishing net")),
    BIG_NET("Fishing spot", new String[]{"Big Net", "Net"}, new ItemRequirement("Big fishing net")),
    BAIT("Rod Fishing spot", "Bait", new ItemRequirement("Fishing rod"), new ItemRequirement("Fishing bait", 1).setStackable()),
    OILY_BAIT("Rod Fishing spot", "Bait", new ItemRequirement("Oily fishing rod"), new ItemRequirement("Fishing bait", 1).setStackable()),
    LURE("Rod Fishing spot", "Lure", new ItemRequirement("Fly fishing rod"), new ItemRequirement("Feather", 1).setStackable()),
    STRIPY_LURE("Rod Fishing spot", "Lure", new ItemRequirement("Fly fishing rod"), new ItemRequirement("Stripy Feather", 1).setStackable()),
    CAGE("Fishing spot", "Cage", new ItemRequirement("Lobster pot")),
    HARPOON("Fishing spot", "Harpoon", new ItemRequirement("Harpoon")),
    VESSEL("Fishing spot", "Vessel", new ItemRequirement("Karambwan vessel")),
    TRAWLER("Fishing spot", "Trawler", null),
    SANDWORM_BAIT("Fishing spot", "", null),
    BAIT_POT("Fishing spot", "", null),
    SWAMP_BAIT("Fishing spot", "", null);

    String spotName;
    String[] action;
    ItemRequirement[] itemReqs;

    FishingMethod(final String spotName, final String action, final ItemRequirement... itemReqs) {
        this.spotName = spotName;
        this.action = new String[]{action};
        this.itemReqs = itemReqs;
    }

    FishingMethod(final String spotName, final String[] action, final ItemRequirement... itemReqs) {
        this.spotName = spotName;
        this.action = action;
        this.itemReqs = itemReqs;
    }
}