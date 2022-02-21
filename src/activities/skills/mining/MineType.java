package activities.skills.mining;

import org.osbot.rs07.api.map.Area;

import java.util.Arrays;

public enum MineType {

    AL_KHARID("Al Kharid Mine",
            new RockArea[]{
                    new RockArea(RockType.GOLD, new Area(3294, 3285, 3295, 3288)),
                    new RockArea(RockType.IRON, new Area(3304, 3283, 3301, 3286)),
                    new RockArea(RockType.SILVER, new Area(
                            new int[][]{
                                    {3292, 3299},
                                    {3296, 3299},
                                    {3296, 3302},
                                    {3297, 3302},
                                    {3297, 3305},
                                    {3294, 3305},
                                    {3294, 3302},
                                    {3293, 3302},
                                    {3293, 3301},
                                    {3292, 3301}
                            }
                    )),
                    new RockArea(RockType.MITHRIL, new Area(3305, 3303, 3303, 3306)),
                    new RockArea(RockType.IRON, new Area(3303, 3301, 3305, 3303)),
                    new RockArea(RockType.COAL, new Area(3302, 3298, 3304, 3300)),
                    new RockArea(RockType.IRON, new Area(3296, 3309, 3294, 3311)),
                    new RockArea(RockType.ADAMANTITE, new Area(3300, 3318, 3298, 3316)),
                    new RockArea(RockType.COPPER, new Area(3296, 3313, 3298, 3315)),
                    new RockArea(RockType.SILVER, new Area(3303, 3312, 3302, 3314))
            },
            RockType.COPPER, RockType.TIN, RockType.IRON, RockType.SILVER, RockType.COAL, RockType.GOLD, RockType.MITHRIL, RockType.ADAMANTITE),
    //ARZINIAN("Arzinian Mine", null, null, Rock.GOLD),
    BARBARIAN_VILLAGE("Barbarian Village Mine", new RockArea[]{
            new RockArea(RockType.COAL, new Area(3084, 3419, 3081, 3423)),
            new RockArea(RockType.TIN, new Area(3078, 3417, 3082, 3422))
    }, RockType.COAL, RockType.TIN),
    NORTH_BRIMHAVEN("North Brimhaven Mine", new RockArea[]{
            new RockArea(RockType.GOLD, new Area(2732, 3224, 2735, 3227))
    }, RockType.GOLD),
    SOUTH_BRIMHAVEN("South Brimhaven Mine", new RockArea[]{new RockArea(RockType.GOLD, new Area(2741, 3146, 2747, 3152))}, RockType.GOLD),
    //COAL_TRUCKS("Coal Trucks", null, null, Rock.COAL),
    CRAFTING_GUILD("Crafting Guild Mine", new RockArea[]{
            new RockArea(RockType.GOLD, new Area(2943, 3276, 2938, 3281)),
            new RockArea(RockType.CLAY, new Area(2943, 3281, 2937, 3285)),
            new RockArea(RockType.SILVER, new Area(2943, 3285, 2939, 3291))
    }, RockType.CLAY, RockType.SILVER, RockType.GOLD),
    NORTH_CRANDOR("North Crandor Mine", new RockArea[]{new RockArea(RockType.GOLD, new Area(2860, 3284, 2864, 3289)), new RockArea(RockType.COAL, new Area(2830, 3289, 2838, 3297)), new RockArea(RockType.MITHRIL, new Area(2830, 3289, 2838, 3297))}, RockType.COAL, RockType.GOLD, RockType.MITHRIL),
    SOUTH_CRANDOR("South Crandor Mine", new RockArea[]{new RockArea(RockType.MITHRIL, new Area(2818, 3240, 2823, 3249)),
            new RockArea(RockType.COAL, new Area(2834, 3242, 2839, 3248)),
            new RockArea(RockType.ADAMANTITE, new Area(2834, 3242, 2839, 3248))}, RockType.COAL, RockType.MITHRIL, RockType.ADAMANTITE),
    //DORGESHUUN("Dorgeshuun Mine", null, null, Rock.IRON, Rock.SILVER),
    DWARVEN("Dwarven Mine", new RockArea[]{new RockArea(RockType.TIN, new Area(3051, 9779, 3056, 9782)), new RockArea(RockType.COPPER, new Area(3041, 9779, 3038, 9785)), new RockArea(RockType.IRON, new Area(3040, 9774, 3036, 9778)), new RockArea(RockType.MITHRIL, new Area(3038, 9770, 3035, 9774)), new RockArea(RockType.COAL, new Area(3042, 9760, 3036, 9764)), new RockArea(RockType.ADAMANTITE, new Area(3036, 9763, 3035, 9765)), new RockArea(RockType.ADAMANTITE, new Area(3041, 9775, 3042, 9771)), new RockArea(RockType.GOLD, new Area(3051, 9761, 3048, 9760))}, RockType.COAL, RockType.MITHRIL, RockType.IRON, RockType.TIN, RockType.COPPER, RockType.ADAMANTITE, RockType.GOLD),
    EDGEVILLE_DUNGEON("Edgeville Dungeon Mine", new RockArea[]{new RockArea(RockType.ADAMANTITE, new Area(3137, 9872, 3141, 9875)), new RockArea(RockType.COAL, new Area(3140, 9868, 3134, 9873)), new RockArea(RockType.MITHRIL, new Area(3135, 9870, 3134, 9872)), new RockArea(RockType.SILVER, new Area(3140, 9874, 3136, 9880)), new RockArea(RockType.IRON, new Area(3138, 9876, 3143, 9871))}, RockType.ADAMANTITE, RockType.COAL, RockType.MITHRIL, RockType.SILVER, RockType.IRON),
    HEROES_GUILD("Heroes Guild Mine", null, null),
    //BANDIT_CAMP("Bandit Camp Mine", null, null),
    WEST_FALADOR("West Falador Mine", new RockArea[]{
            new RockArea(RockType.TIN, new Area(2904, 3356, 2909, 3363)),
            new RockArea(RockType.IRON, new Area(2903, 3352, 2905, 3356)),
            new RockArea(RockType.COAL, new Area(2909, 3362, 2911, 3367)),
            new RockArea(RockType.COPPER, new Area(2907, 3360, 2910, 3365))
    }, RockType.TIN, RockType.IRON, RockType.COAL, RockType.COPPER),
    //KELDAGRIM_ENTRANCE("Keldagrim Entrance Mine", null, null),
    //KARAMJA_VOLCANO("Karamja Volcano Mine", null, null),
    LUMBRIDGE_SWAMP_TRAINING("Lumbridge Swamp Training Mine", new RockArea[]{
            new RockArea(RockType.TIN, new Area(3221, 3145, 3226, 3149)),
            new RockArea(RockType.COPPER, new Area(3227, 3143, 3231, 3148))
    }, RockType.TIN, RockType.COPPER),
    WEST_LUMBRIDGE_SWAMP("West Lumbridge Swamp Mine", new RockArea[]{
            new RockArea(RockType.COAL, new Area(3143, 3148, 3147, 3154)),
            new RockArea(RockType.MITHRIL, new Area(3143, 3143, 3149, 3147)),
            new RockArea(RockType.ADAMANTITE, new Area(3146, 3145, 3148, 3147))
    }, RockType.COAL, RockType.MITHRIL, RockType.ADAMANTITE),
    MINING_GUILD("Mining Guild", new RockArea[]{new RockArea(RockType.COAL, new Area(3027, 9727, 3059, 9749)), new RockArea(RockType.MITHRIL, new Area(3049, 9737, 3054, 9740)), new RockArea(RockType.MITHRIL, new Area(3045, 9732, 3048, 9734))}, RockType.COAL, RockType.MITHRIL),
    RIMMINGTON("Rimmington Mine", new RockArea[]{
            new RockArea(RockType.COPPER, new Area(2975, 3244, 2980, 3249)),
            new RockArea(RockType.IRON, new Area(2967, 3236, 2972, 3243)),
            new RockArea(RockType.GOLD, new Area(2974, 3232, 2978, 3235)),
            new RockArea(RockType.TIN, new Area(2983, 3234, 2987, 3238)),
            new RockArea(RockType.CLAY, new Area(2985, 3238, 2988, 3241))
    }, RockType.COPPER, RockType.IRON, RockType.GOLD, RockType.TIN, RockType.CLAY),
    SOUTH_EAST_VARROCK("South East Varrock Mine", new RockArea[]{
            new RockArea(RockType.IRON, new Area(3284, 3367, 3289, 3371)),
            new RockArea(RockType.TIN, new Area(3280, 3361, 3283, 3366)),
            new RockArea(RockType.COPPER, new Area(3290, 3365, 3284, 3361))
    }, RockType.IRON, RockType.TIN, RockType.COPPER),
    SOUTH_WEST_VARROCK("South West Varrock Mine", new RockArea[]{
            new RockArea(RockType.TIN, new Area(3181, 3374, 3184, 3378)),
            new RockArea(RockType.CLAY, new Area(3178, 3370, 3181, 3373)),
            new RockArea(RockType.IRON, new Area(3174, 3365, 3176, 3368))
    }, RockType.TIN, RockType.CLAY, RockType.IRON),
    EAST_ARDOUGNE("East Ardougne Mine", new RockArea[]{
            new RockArea(RockType.IRON, new Area(2707, 3333, 2715, 3328))
    }, RockType.IRON);

    String name;
    RockArea[] rockAreas;
    RockType[] rockTypes;

    MineType(final String name, final RockArea[] rockAreas, final RockType... rockTypes) {
        this.name = name;
        this.rockAreas = rockAreas;
        this.rockTypes = rockTypes;
    }

    public static MineType[] getMinesWithRock(RockType rock) {
        return Arrays.stream(values()).filter(mine -> mine.rockTypes != null && Arrays.asList(mine.rockTypes).contains(rock)).toArray(MineType[]::new);
    }

    @Override
    public String toString() {
        return name;
    }
}