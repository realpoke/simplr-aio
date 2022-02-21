package utils.random_solver;

import org.osbot.rs07.script.RandomEvent;
import org.osbot.rs07.script.RandomSolver;
import utils.method_provider.CustomMethodProvider;

public class CustomLoginHandler extends RandomSolver {

    public CustomLoginHandler(CustomMethodProvider customMethodProvider) {
        super(RandomEvent.AUTO_LOGIN);
    }

    @Override
    public int onLoop() throws InterruptedException {
        return 0;
    }

    @Override
    public boolean shouldActivate() {
        return false;
    }
}
