package aio;

import org.osbot.rs07.Bot;
import tasks.*;
import utils.file_manager.FileManager;
import utils.method_provider.CustomMethodProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AccountCheckerMode extends AIOMode {

    private Bot bot;
    private CustomMethodProvider customMethodProvider;

    public void setup(Bot bot, CustomMethodProvider customMethodProvider, String scriptInfo) {
        super.setup(bot, customMethodProvider, scriptInfo);
        this.bot = bot;
        this.customMethodProvider = customMethodProvider;

        customMethodProvider.getAccount().setAccountList(getAccounts(bot));
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        int taskIndex = 0;

        Task accountCheckerTask = new AccountCheckingTask();
        accountCheckerTask.setExecutionOrder(taskIndex);
        taskIndex++;

        tasks.add(accountCheckerTask);

        return tasks;
    }

    private Map<String, String> getAccounts(Bot bot) {
        Map<String, String> accounts = new HashMap<>();

        FileManager file = new FileManager();
        file.exchangeContext(bot);
        file.initializeModule();

        try {
            Arrays.stream(file.open().split(System.lineSeparator())).forEach((String s) -> {
                customMethodProvider.logger.debug("Line: " + s);
                String[] account = s.split(":");
                if (account[1] != null) {
                    customMethodProvider.logger.debug("Added account to check: " + account[0]);
                    accounts.put(account[0], account[1]);
                }
            });
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return accounts;
    }
}
