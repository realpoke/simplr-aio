package utils.method_provider.providers;

import utils.method_provider.CustomMethodProvider;

import java.util.HashMap;
import java.util.Map;

public class CommandLine extends CustomMethodProvider {

    public enum Commands {

        KILL_ON_STOP("KILLONSTOP", true),
        DUMP_ON_STOP("DUMPONSTOP", true),
        ACCOUNT_CHECKER("ACCOUNTCHECKER", true),
        MULTI_ACCOUNT("MULTI", true),
        MULTI_ACCOUNT_FILE("MULTIACCOUNT", "string"),
        HARDCORE_TUTORIAL_ISLAND("HARDCORE", true),
        BOT_LOGIN("LOGIN", "string"),
        BOT_PASSWORD("PASSWORD", "string"),
        API_BASE("APIBASE", "string"),
        API_KEY("APIKEY", "string");

        public final String command;
        public final String type;

        Commands(String command, String type) {
            this.command = command;
            this.type = "string";
        }

        Commands(String command, Boolean type) {
            this.command = command;
            this.type = "boolean";
        }
    }

    private final Map<String, String> parameters = new HashMap<>();
    private final Map<String, String> availableCommands = new HashMap<>();
    private final String rawParameters;
    private boolean hasCompiled = false;

    public CommandLine(String rawParameters) {
        this.rawParameters = rawParameters;

        for (Commands command : Commands.values()) {
            availableCommands.put(command.command, command.type);
        }
    }

    public boolean getBoolean(Commands command) {
        if (!command.type.equals("boolean")) {
            return false;
        }
        return Boolean.parseBoolean(parameters.get(command.command));
    }

    public String getString(Commands command) {
        if (!command.type.equals("string")) {
            return null;
        }
        return parameters.get(command.command);
    }

    public void compileParameters() {
        if (hasCompiled)
            return;

        availableCommands.forEach((command, type) -> logger.info("Command available: " + command + " - " + type));

        String[] contents;
        String key;
        String value;
        if (rawParameters != null && !rawParameters.isEmpty()) {
            logger.debug("rawParameters: " + rawParameters);
            contents = rawParameters.split("\\.\\.");
            if (contents.length > 0) {
                logger.info("Compiling raw command line parameters...");
                for (int i = 0; i < contents.length; i += 2) {
                    key = contents[i];
                    key = key.toUpperCase();

                    if (!availableCommands.containsKey(key)) {
                        logger.error("Command parameter not available: " + key);
                    } else {
                        logger.debug("Command parameter read: " + key);
                        value = (i + 1 < contents.length ? contents[i + 1] : "");
                        value = value.replaceAll("-", " ");
                        parameters.put(key, value);
                    }
                }
            } else {
                logger.error("No valid parameters found!");
            }
        }
        hasCompiled = true;
    }
}
