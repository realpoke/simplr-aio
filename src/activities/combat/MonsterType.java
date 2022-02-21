package activities.combat;

public enum MonsterType {

    CHICKEN("Chicken", 1, CombatLocation.CHICKEN, MonsterLoot.CHICKEN);

    public final CombatLocation[] locations;
    public final MonsterLoot loot;
    public final String name;
    public final int lvlRequirement;

    MonsterType(final String name, final int lvlRequirement, final CombatLocation[] locations, final MonsterLoot loot) {
        this.name = name;
        this.lvlRequirement = lvlRequirement;
        this.locations = locations;
        this.loot = loot;
    }

    @Override
    public String toString() {
        return name;
    }
}