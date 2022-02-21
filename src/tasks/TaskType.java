package tasks;

public enum TaskType {

    LEVEL("Level Task"),
    LOOP("Loop Task"),
    RESOURCE("Resource Task"),
    KILL("Kill Task"),
    TIMED("Timed Task"),
    QUEST("Quest Task"),
    GRAND_EXCHANGE("Grand Exchange Task"),
    TUTORIAL_ISLAND("Tutorial Island Task"),
    BREAK("Break Task"),
    ACCOUNT_CHECKING("Account Checking Task"),
    SHOPPING("Shopping Task");

    String name;

    TaskType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}