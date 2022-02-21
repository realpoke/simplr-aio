package utils;

import org.osbot.rs07.api.ui.Skill;
import utils.method_provider.Executable;

public class Eating extends Executable {

    private final Food food;

    public Eating(final Food food) {
        this.food = food;
    }

    public boolean getHpPercent(int hpPercentToEatAt) {
        return (float)(getSkills().getDynamic(Skill.HITPOINTS) * 100) / getSkills().getStatic(Skill.HITPOINTS) < hpPercentToEatAt;
    }

    @Override
    public void run() throws InterruptedException {
        long foodCount = getInventory().getAmount(food.toString());
        getInventory().getItem(food.toString()).interact("Eat", "Drink");
        Sleep.sleepUntil(() -> getInventory().getAmount(food.toString()) < foodCount, 5_000);
    }

    @Override
    public String toString() {
        return "Eating";
    }
}