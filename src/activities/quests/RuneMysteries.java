package activities.quests;

import activities.activity.Activity;
import activities.banking.BankType;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Tab;
import utils.DialogueCompleter;
import utils.Sleep;

public class RuneMysteries extends QuestActivity {
    private final Position HIDING_PLACE = new Position(3233, 3405, 0);

    private final DialogueCompleter dukeHoracioDialogueCompleter = new DialogueCompleter(
            "Duke Horacio",
            new Area(new Position(3208, 3218, 1), new Position(3213, 3225, 1)),
            "Duke Horacio", "Have you any quests for me?", "Yes"
    );

    private final DialogueCompleter sedridorDialogueCompleter = new DialogueCompleter(
            "Sedridor",
            new Area(3105, 9570, 3103, 9572),
            "I'm looking for the head wizard.", "Ok, here you are.", "Yes, certainly."
    );

    private final DialogueCompleter auburyDialogueCompleter = new DialogueCompleter(
            "Aubury",
            new Area(3251, 3400, 3254, 3402),
            "I have been sent here with a package for you."
    );

    public RuneMysteries() {
        super(QuestType.RUNE_MYSTERIES);
    }

    @Override
    public void runActivity() throws InterruptedException {
        if (!getInventory().contains("Air talisman", "Research package") && getInventory().isFull()) {
            if (BankType.inAnyBank(myPosition())) {
                if (!getBank().isOpen()) {
                    if (getBank().open()) {
                        Sleep.sleepUntil(() -> getBank().isOpen(), 5_000);
                    }
                } else getBank().depositAllExcept("Air talisman", "Research package");
            } else {
                getWalking().webWalk(BankType.AREAS);
            }
        } else {

            if (getTabs().getOpen() != Tab.INVENTORY) {
                getTabs().open(Tab.INVENTORY);
            }

            switch (getProgress()) {
                case 0:
                    super.setStatus("Talking to Duke Horacio");
                    execute(dukeHoracioDialogueCompleter);
                    break;
                case 1:
                    if (!getInventory().contains("Air talisman")) {
                        super.setStatus("Talking to Duke Horacio");
                        execute(dukeHoracioDialogueCompleter);
                    } else {
                        super.setStatus("Talking to Sedridor");
                        execute(sedridorDialogueCompleter);
                    }
                    break;
                case 2:
                    super.setStatus("Talking to Sedridor");
                    execute(sedridorDialogueCompleter);
                    break;
                case 3:
                    if (myPlayer().isUnderAttack()) {
                        getWalking().walk(HIDING_PLACE.getArea(2));
                        Sleep.sleepUntil(() -> !myPlayer().isHitBarVisible(), 10_000);
                        Sleep.sleepUntil(() -> myPlayer().isUnderAttack(), 8_000);
                    }
                    if (!getInventory().contains("Research package")) {
                        super.setStatus("Talking to Sedridor");
                        execute(sedridorDialogueCompleter);
                    } else {
                        super.setStatus("Talking to Aubury");
                        execute(auburyDialogueCompleter);
                    }
                    break;
                case 4:
                    super.setStatus("Talking to Aubury");
                    execute(auburyDialogueCompleter);
                    break;
                case 5:
                    super.setStatus("Talking to Sedridor");
                    execute(sedridorDialogueCompleter);
                    break;

            }
        }
    }

    @Override
    public Activity copy() {
        return new RuneMysteries();
    }
}