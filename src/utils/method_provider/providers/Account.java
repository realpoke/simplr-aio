package utils.method_provider.providers;

import events.SetupClientEvent;
import org.osbot.rs07.api.ui.Tab;
import utils.method_provider.CustomMethodProvider;

import java.util.ArrayList;
import java.util.Map;

public class Account extends CustomMethodProvider {

    private Map<String, String> accounts;
    private ArrayList<String> setupComplete = new ArrayList<>();

    public void setAccountList(Map<String, String> accounts) {
        this.accounts = accounts;
    }

    public Map<String, String> getAccountList() {
        return accounts;
    }

    public boolean hasSetupDefaultSettings() {
        return setupComplete.contains(myPlayer().getName());
    }

    public void applyDefaultSettings() throws InterruptedException {
        if (!hasSetupDefaultSettings() && !Tab.SETTINGS.isDisabled(getBot())) {
            setupComplete.add(myPlayer().getName());
            logger.info("Settings up account settings...");
            execute(new SetupClientEvent());
        } else {
            logger.debug("Failed to apply default account settings");
        }
    }
}
