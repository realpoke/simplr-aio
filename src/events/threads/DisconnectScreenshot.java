package events.threads;

import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.util.Utilities;
import org.osbot.rs07.event.Event;

public class DisconnectScreenshot extends Event {

    @Override
    public int execute() throws InterruptedException {
        if (getClient().getLoginState() == Client.LoginState.LOGIN_CONNECTING && myPlayer() != null && myPlayer().isVisible()) {
            log("Client connection lost!");
            Utilities.takeScreenshot(); // TODO: Upload screenshot to web server
        }
        return 300;
    }
}