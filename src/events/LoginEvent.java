package events;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.input.mouse.RectangleDestination;
import org.osbot.rs07.listener.LoginResponseCodeListener;
import utils.Sleep;
import utils.WebServer;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;
import utils.method_provider.providers.CommandLine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginEvent extends BlockingExecutable implements LoginResponseCodeListener {
    public enum LoginEventResult {
        UNKNOWN(9999, "UNKNOWN"),
        UNEXPECTED_SERVER_ERROR(1, "Unexpected server error"),
        LOG_IN(2, "Log in"),
        INVALID_CREDENTIALS(3, "Invalid username or password"),
        BANNED(4, "User banned"),
        ACCOUNT_ALREADY_LOGGED_IN(5, "Account is already logged in try again in 60 seconds"),
        RUNESCAPE_UPDATED(6, "Runescape has been updated! Please reload this page."),
        WORLD_IS_FULL(7, "This world is full. Please use a different world."),
        LOGIN_SERVER_OFFLINE(8, "Unable to connect. login server offline."),
        TOO_MANY_CONNECTIONS_FROM_ADDRESS(9, "Login limit exceeded. Too many connections from you address."),
        BAD_SESSION_ID(10, "Unable to connect. Bad session id."),
        PASSWORD_CHANGE_REQUIRED(11, "We suspect someone knows your password. Press 'change your password' on the front page."),
        MEMBERS_ACCOUNT_REQUIRED(12, "You need a members account to login to this world. Please subscribe, or use a different world."),
        TRY_DIFFERENT_WORLD(13, "Could not complete login. Please try using a different world."),
        TRY_AGAIN(14, "The server is being updated. Please wait 1 minute and try again."),
        SERVER_UPDATE(15, "The server is being updated. Please wait 1 minute and try again."),
        TOO_MANY_INCORRECT_LOGINS(16, "Too many incorrect longs from your address. Please wait 5 minutes before trying again."),
        STANDING_IN_MEMBERS_ONLY_AREA(17, "You are standing in a members-only area. To play on this world move to a free area first."),
        ACCOUNT_LOCKED(18, "Account locked as we suspect it has been stolen. Please visit the support page for assistance."),
        CLOSED_BETA(19, "This world is running a closed beta. sorry invited players only. please use a different world."),
        INVALID_LOGIN_SERVER(20, "Invalid loginserver requested please try using a different world."),
        PROFILE_WILL_BE_TRANSFERRED(21, "You have only just left another world. your profile will be transferred in 4seconds."),
        MALFORMED_LOGIN_PACKET(22, "Malformed login packet. Please try again"),
        NO_REPLY_FROM_LOGIN_SERVER(23, "No reply from loginserver. Please wait 1 minute and try again."),
        ERROR_LOADING_PROFILE(24, "Error loading your profile. please contact customer support."),
        UNEXPECTED_LOGIN_SERVER_RESPONSE(25, "Unexpected loginserver response"),
        COMPUTER_ADDRESS_BANNED(26, "This computers address has been blocked as it was used to break our rules."),
        SERVICE_UNAVAILABLE(27, "Service unavailable."),
        BILLING_SYSTEM(32, "Your attempt to log into your account was unsuccessful. Don't worry, you can sort this out by visiting the billing system.");

        int code;
        String message;

        LoginEventResult(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    private static final Map<Integer, LoginEventResult> responseCodeLoginResultMap = new HashMap<>();
    static {
        for (LoginEventResult result : LoginEventResult.values()) {
            responseCodeLoginResultMap.put(result.code, result);
        }
    }

    private static final Rectangle TRY_AGAIN_BUTTON = new Rectangle(318, 262, 130, 26);
    private static final Rectangle LOGIN_BUTTON = new Rectangle(240, 310, 120, 20);
    private static final Rectangle EXISTING_USER_BUTTON = new Rectangle(400, 280, 120, 20);
    private static final Rectangle CANCEL_LOGIN_BUTTON = new Rectangle(398, 308, 126, 27);
    private static final Rectangle CANCEL_WORLD_SELECTOR_BUTTON = new Rectangle(712, 8, 42, 8);
    private static final Rectangle WORLD_SELECTOR_BUTTON = new Rectangle(15, 470, 80, 20);
    private static final Rectangle WORLD = new Rectangle(440, 52, 20, 4);
    private static final Rectangle BACK = new Rectangle(460, 62, 5, 5);

    private final String username, password;
    private Boolean ShouldJumpWorld;
    private int maxRetries = 5;
    private Boolean worldHopping = false;
    private Boolean resetSize = true;

    private LoginEventResult loginEventResult;
    private int retryNumber = 0;

    public LoginEvent(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.ShouldJumpWorld = false;
    }

    public LoginEvent(final String username, final String password, final int maxRetries) {
        this(username, password);
        this.maxRetries = maxRetries;
        this.ShouldJumpWorld = false;
    }

    public LoginEvent(final String username, final String password, final int maxRetries, final Boolean ShouldJumpWorld) {
        this(username, password);
        this.maxRetries = maxRetries;
        this.ShouldJumpWorld = ShouldJumpWorld;
    }

    @Override
    protected void blockingRun() throws InterruptedException {
        if (resetSize) {
            getCanvasUtil().resetSize();
            resetSize = false;
        }

        if (loginEventResult != null) {
            handleLoginResponse();
            loginEventResult = null;
            return;
        }

        if (retryNumber >= maxRetries) {
            throw new ExecutionFailedException("Reached max tries(" + maxRetries + ") to login!");
        }

        if (!getBot().isLoaded()) {
            logger.debug("Waiting for bot to load...");
            Sleep.sleepUntil(() -> getBot().isLoaded(), 10_000);
        } else if (getClient().isLoggedIn() && getLobbyButton() == null) {
            logger.debug("Finished logging in!");
            // TODO: Make a save util on the custom method provider to save the current user to the webserver
            WebServer webServer = new WebServer(getCommandLine().getString(CommandLine.Commands.API_BASE), getCommandLine().getString(CommandLine.Commands.API_KEY));
            logger.debug(webServer.post("account/" + getCommandLine().getString(CommandLine.Commands.BOT_LOGIN) + "/update", new HashMap<String, String>() {{
                put("state", "running");
            }}));
            Sleep.sleepUntil(() -> myPlayer().isVisible(), 5_000, 1_000);
            getLogoutTab().open();
            Sleep.sleepUntil(() -> getLogoutTab().isOpen(), 5_000, 1_000);
            setFinished();
        }

        if (getLobbyButton() != null) {
            logger.debug("Clicking lobby button...");
            clickLobbyButton();
        } else if (isOnWorldSelectorScreen()) {
            logger.debug("Leaving world select screen...");
            cancelWorldSelection();
        } else if (!isPasswordEmpty()) {
            logger.debug("Password not empty, cancel...");
            clickButton(CANCEL_LOGIN_BUTTON);
        } else if (isRecoverScreen()) {
            logger.debug("On recovery page screen...");
            clickButton(CANCEL_LOGIN_BUTTON);
            Sleep.sleepUntil(() -> !isRecoverScreen(), 6_000);
            clickButton(CANCEL_LOGIN_BUTTON);
        } else {
            login();
        }
    }

    public LoginEventResult getLoginEventResult() {
        return loginEventResult;
    }

    private void handleLoginResponse() {
        switch (loginEventResult) {
            case MEMBERS_ACCOUNT_REQUIRED:
            case STANDING_IN_MEMBERS_ONLY_AREA:
                if (ShouldJumpWorld) {
                    GotoWorldScreen();
                } else {
                    setFinished();
                }
                break;
            case BANNED:
            case PASSWORD_CHANGE_REQUIRED:
            case ACCOUNT_LOCKED:
            case COMPUTER_ADDRESS_BANNED:
            case UNEXPECTED_SERVER_ERROR:
            case INVALID_CREDENTIALS:
            case RUNESCAPE_UPDATED:
            case LOGIN_SERVER_OFFLINE:
            case TOO_MANY_CONNECTIONS_FROM_ADDRESS:
            case BAD_SESSION_ID:
            case UNEXPECTED_LOGIN_SERVER_RESPONSE:
            case SERVICE_UNAVAILABLE:
            case TOO_MANY_INCORRECT_LOGINS:
            case ERROR_LOADING_PROFILE:
            case BILLING_SYSTEM:
                // TODO: Make a save util on the custom method provider to save the current user to the webserver
                WebServer webServer = new WebServer(getCommandLine().getString(CommandLine.Commands.API_BASE), getCommandLine().getString(CommandLine.Commands.API_KEY));
                logger.debug(webServer.post("account/" + getCommandLine().getString(CommandLine.Commands.BOT_LOGIN) + "/update", new HashMap<String, String>() {{
                    put("state", loginEventResult.toString());
                    put("note", "Could not login: " + loginEventResult.message);
                }}));
                throw new ExecutionFailedException(loginEventResult.message);
            case ACCOUNT_ALREADY_LOGGED_IN:
            case TRY_AGAIN:
            case SERVER_UPDATE:
            case NO_REPLY_FROM_LOGIN_SERVER:
            case MALFORMED_LOGIN_PACKET:
                Sleep.sleepUntil(() -> getLobbyButton() != null, random((int)TimeUnit.MINUTES.toMillis(1), (int)TimeUnit.MINUTES.toMillis(2)));
                retryNumber++;
                break;
            case PROFILE_WILL_BE_TRANSFERRED:
                Sleep.sleepUntil(() -> getLobbyButton() != null, random((int)TimeUnit.MINUTES.toMillis(5), (int)TimeUnit.MINUTES.toMillis(10)));
                retryNumber++;
                break;
            case WORLD_IS_FULL:
            case TRY_DIFFERENT_WORLD:
            case CLOSED_BETA:
            case INVALID_LOGIN_SERVER:
                GotoWorldScreen();
                break;
            case UNKNOWN:
            default:
                throw new ExecutionFailedException("Unknown response code! (" + loginEventResult + ")");
        }
    }

    private boolean isOnWorldSelectorScreen() {
        return getColorPicker().isColorAt(50, 50, Color.BLACK);
    }

    private void cancelWorldSelection() {
        if (worldHopping && clickButton(WORLD)) {
            Sleep.sleepUntil(() -> !isOnWorldSelectorScreen(), 10_000);
            worldHopping = false;
            retryNumber++;
        } else if (clickButton(CANCEL_WORLD_SELECTOR_BUTTON)) {
            Sleep.sleepUntil(() -> !isOnWorldSelectorScreen(), 3_000);
        }
    }

    private void GotoWorldScreen() {
        worldHopping = true;
        if (!isOnWorldSelectorScreen()) {
            clickButton(WORLD_SELECTOR_BUTTON);
            Sleep.sleepUntil(this::isOnWorldSelectorScreen, 10_000);
        }
    }

    private boolean isPasswordEmpty() {
        return !getColorPicker().isColorAt(350, 260, Color.WHITE);
    }

    private boolean isRecoverScreen() { return getClient().getLoginUIState() == 5; }

    private void login() {
        switch (getClient().getLoginUIState()) {
            case 0:
                clickButton(EXISTING_USER_BUTTON);
                break;
            case 1:
                clickButton(LOGIN_BUTTON);
                break;
            case 2:
                enterUserDetails();
                break;
            case 3:
                clickButton(TRY_AGAIN_BUTTON);
                break;
            case 14:
                clickButton(BACK);
                break;
        }
    }

    private void enterUserDetails() {
        logger.debug("Writing user details...");
        if (!getKeyboard().typeString(username)) {
            return;
        }

        if (!getKeyboard().typeString(password)) {
            return;
        }

        logger.debug("Waiting for login result...");
        Sleep.sleepUntil(() ->
                        getLobbyButton() != null ||
                                getClient().isLoggedIn() ||
                                getClient().getLoginUIState() == 3 ||
                                getClient().getLoginUIState() == 5 ||
                                loginEventResult != null,
                30_000
                );
    }

    private void clickLobbyButton() {
        if (getLobbyButton() != null && getLobbyButton().interact()) {
            Sleep.sleepUntil(() -> getLobbyButton() == null, 10_000);
        }
    }

    private RS2Widget getLobbyButton() {
        try {
            return getWidgets().getWidgetContainingText("CLICK HERE TO PLAY");
        } catch (NullPointerException e) {
           return null;
        }
    }

    private boolean clickButton(final Rectangle rectangle) {
        return getMouse().click(new RectangleDestination(getBot(), rectangle));
    }

    @Override
    public final void onResponseCode(final int responseCode) {
        if (!responseCodeLoginResultMap.containsKey(responseCode)) {
            retryNumber++;
            this.loginEventResult = LoginEventResult.UNKNOWN;
            logger.debug("Got unknown login response code " + responseCode);
            return;
        }

        this.loginEventResult = responseCodeLoginResultMap.get(responseCode);
        logger.debug(String.format("Got login response: %d '%s'", responseCode, loginEventResult.message));
    }
}
