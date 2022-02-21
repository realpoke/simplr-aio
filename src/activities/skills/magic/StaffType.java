package activities.skills.magic;

public enum StaffType {
    STAFF_OF_AIR("Staff of air", RuneType.AIR),
    STAFF_OF_WATER("Staff of water", RuneType.WATER),
    STAFF_OF_EARTH("Staff of earth", RuneType.EARTH),
    STAFF_OF_FIRE("Staff of fire", RuneType.FIRE),

    AIR_BATTLESTAFF("Air battlestaff", RuneType.AIR),
    WATER_BATTLESTAFF("Water battlestaff", RuneType.WATER),
    EARTH_BATTLESTAFF("Earth battlestaff", RuneType.EARTH),
    FIRE_BATTLESTAFF("Fire battlestaff", RuneType.FIRE),
    LAVA_BATTLESTAFF("Lava battlestaff", RuneType.EARTH, RuneType.FIRE),
    MUD_BATTLESTAFF("Mud battlestaff", RuneType.EARTH, RuneType.WATER),
    STEAM_BATTLESTAFF("Steam battlestaff", RuneType.FIRE, RuneType.WATER),
    SMOKE_BATTLESTAFF("Smoke battlestaff", RuneType.FIRE, RuneType.AIR),
    MIST_BATTLESTAFF("Mist battlestaff", RuneType.WATER, RuneType.AIR),
    DUST_BATTLESTAFF("Dust battlestaff", RuneType.EARTH, RuneType.AIR);

    private String name;
    private RuneType[] runes;

    StaffType(final String name, final RuneType... runes) {
        this.name = name;
        this.runes = runes;
    }

    public String getName() {
        return name;
    }

    public RuneType[] getRunes() {
        return runes;
    }

    @Override
    public String toString() {
        return getName();
    }
}