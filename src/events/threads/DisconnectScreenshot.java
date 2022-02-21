package events.threads;

import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.util.Utilities;
import org.osbot.rs07.event.Event;

public class DisconnectScreenshot extends Event {

    @Override
    public int execute() throws InterruptedException {
        if (getBot().getClient().getLoginState() == Client.LoginState.LOGIN_CONNECTING) {//40 = connection lost
            log("Client connection lost!");
            Utilities.takeScreenshot();
        }
        return 300;
    }
}