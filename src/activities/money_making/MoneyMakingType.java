package activities.money_making;

public enum MoneyMakingType {

    FLAX_PICKING("Flax picking");

    String name;

    MoneyMakingType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}