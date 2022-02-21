package activities.combat;

public enum MonsterLoot {

    CHICKEN("Feather", "Bones", "Raw chicken");

    String[] loot;

    MonsterLoot(String... loot) {
        this.loot = loot;
    }
}
