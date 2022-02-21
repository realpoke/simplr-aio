package utils.method_provider;

public abstract class Executable extends CustomMethodProvider {

    public void onStart() throws InterruptedException {}

    public abstract void run() throws InterruptedException;

    public void onEnd() throws InterruptedException {}

    public boolean canExit() {
        return true;
    }
}