package tasks;

import events.LoginEvent;
import events.LogoutEvent;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.file_manager.FileManager;
import utils.method_provider.ExecutionFailedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountCheckingTask extends Task {

    private Map<String, String> accounts = new HashMap<>();
    private final ArrayList<String> accountsChecked = new ArrayList<>();
    private String currentAccount;
    private boolean noAccounts = false;

    public AccountCheckingTask() {
        super(TaskType.ACCOUNT_CHECKING, false);
    }

    @Override
    public boolean isComplete() {
        return noAccounts;
    }

    @Override
    public void onStart() throws InterruptedException {
        if (getClient().isLoggedIn())
            getLogoutTab().logOut();

        Sleep.sleepUntil(() -> !getClient().isLoggedIn(), 6_000);
        accounts = getAccount().getAccountList() == null ? null : getAccount().getAccountList();
    }

    @Override
    public void run() throws InterruptedException {
        if (accounts.isEmpty()) {
            logger.info("No accounts found...");
            noAccounts = true;
        }

        logger.debug("Client Login UI State: " + getClient().getLoginUIState());

        AtomicInteger i = new AtomicInteger();

        for (Map.Entry<String, String> account : accounts.entrySet()) {

            if (getBot().getScriptExecutor().isPaused() || getBot().getScriptExecutor().isSuspended() || !getBot().getScriptExecutor().isRunning()) {
                return;
            }

            logout();

            if (getBot().getScriptExecutor().isPaused() || getBot().getScriptExecutor().isSuspended() || !getBot().getScriptExecutor().isRunning()) {
                return;
            }

            String login = account.getKey();
            String password = account.getValue();
            currentAccount = login;
            logger.info("Checking account: " + login);
            String fullAccountDetails = login + ":" + password;
            LoginEvent loginEvent = new LoginEvent(login, password, 2);
            getBot().addLoginListener(loginEvent);

            try {
                execute(loginEvent);
            } catch (InterruptedException e) {
                throw new ExecutionFailedException("Failed execute LoginEvent with: " + login);
            } catch (ExecutionFailedException e) {
                fullAccountDetails = e.getMessage() + " -" + fullAccountDetails;
                logger.info(fullAccountDetails);
            }

            getBot().removeLoginListener(loginEvent);
            logout();

            if (accountsChecked.contains(fullAccountDetails)) {
                logger.info("Deduplicate account, skipping adding the checked list...");
            } else {
                logger.info("Adding account to checked list...");
                accountsChecked.add(fullAccountDetails);
            }
        }

        noAccounts = true;

        logger.info("Finished checking accounts, saving new account file...");
        logger.debug(accountsChecked);

        FileManager file = new FileManager();
        file.exchangeContext(bot);
        file.initializeModule();

        try {
            file.save(accountsChecked);
        } catch (IOException e) {
            logger.warn("Failed saving file! " + e);
        }

        if (getBot().getScriptExecutor().isPaused() || getBot().getScriptExecutor().isSuspended() || !getBot().getScriptExecutor().isRunning()) {
            return;
        }

        super.run();
    }

    private void logout() {
        if (getClient().isLoggedIn() || (myPlayer() != null && myPlayer().isVisible()) || (Tab.LOGOUT != null && !Tab.LOGOUT.isDisabled(getBot()))) {
            logger.debug("Logged in, logging out!");
            try {
                execute(new LogoutEvent());
            } catch (InterruptedException e) {
                throw new ExecutionFailedException("Logout got interrupted: " + e);
            } catch (ExecutionFailedException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Account Checking Task: (%s/%s) %s",
                Math.min(accountsChecked.size() + 1, accounts.size()),
                accounts.size(),
                currentAccount
        );
    }

    @Override
    public Task copy() {
        return new AccountCheckingTask();
    }
}