package utils.method_provider;

import utils.method_provider.extenders.*;
import utils.method_provider.providers.*;
import org.osbot.rs07.Bot;
import org.osbot.rs07.script.MethodProvider;

public class CustomMethodProvider extends MethodProvider {

    private ExtendedBank extendedBank;
    private ExtendedCamera extendedCamera;
    private ExtendedGroundItems extendedGroundItems;
    private ExtendedInventory extendedInventory;
    private ExtendedNPCS extendedNPCS;
    private ExtendedSettings extendedSettings;
    private Paint paint;
    private InteractionHelper interactionHelper;
    private MakeAllInterface makeAllInterface;
    private SkillTracker skillTracker;
    private ExecutableUtil executableUtil;
    private Account account;
    private CanvasUtil canvasUtil;
    private CommandLine commandLine;
    private boolean hasContext;

    public void init(final Bot bot, String parameters) {
        super.exchangeContext(bot);
        //this.extendedBank = new ExtendedBank();
        this.extendedCamera = new ExtendedCamera(this);
        this.extendedGroundItems = new ExtendedGroundItems();
        this.extendedInventory = new ExtendedInventory(this);
        this.extendedNPCS = new ExtendedNPCS();
        this.extendedSettings = new ExtendedSettings();
        this.paint = new Paint();
        this.interactionHelper = new InteractionHelper();
        this.makeAllInterface = new MakeAllInterface();
        this.skillTracker = new SkillTracker();
        this.executableUtil = new ExecutableUtil();
        this.account = new Account();
        this.canvasUtil = new CanvasUtil(bot);
        this.commandLine = new CommandLine(parameters);
        //extendedBank.exchangeContext(bot);
        extendedCamera.exchangeContext(bot);
        extendedGroundItems.exchangeContext(bot);
        extendedInventory.exchangeContext(bot);
        extendedNPCS.exchangeContext(bot);
        extendedSettings.exchangeContext(bot);
        paint.exchangeContext(bot, this);
        interactionHelper.exchangeContext(bot, this);
        makeAllInterface.exchangeContext(bot, this);
        skillTracker.exchangeContext(bot, this);
        executableUtil.exchangeContext(bot, this);
        account.exchangeContext(bot, this);
        canvasUtil.exchangeContext(bot, this);
        commandLine.exchangeContext(bot, this);
        commandLine.compileParameters();
        hasContext = true;
    }

    public boolean hasContext() {
        return hasContext;
    }

    // Deprecated as exchangeContext(Bot bot, CustomMethodProvider methodProvider) should be used instead.
    @Deprecated
    public MethodProvider exchangeContext(final Bot bot) { return super.exchangeContext(bot); }

    public CustomMethodProvider exchangeContext(final Bot bot, final CustomMethodProvider methodProvider) {
        //this.extendedBank = methodProvider.extendedBank;
        this.extendedCamera = methodProvider.extendedCamera;
        this.extendedGroundItems = methodProvider.extendedGroundItems;
        this.extendedInventory = methodProvider.extendedInventory;
        this.extendedNPCS = methodProvider.extendedNPCS;
        this.extendedSettings = methodProvider.extendedSettings;
        this.paint = methodProvider.paint;
        this.interactionHelper = methodProvider.interactionHelper;
        this.makeAllInterface = methodProvider.makeAllInterface;
        this.skillTracker = methodProvider.skillTracker;
        this.executableUtil = methodProvider.executableUtil;
        this.account = methodProvider.account;
        this.canvasUtil = methodProvider.canvasUtil;
        this.commandLine = methodProvider.commandLine;
        super.exchangeContext(bot);
        hasContext = true;
        return this;
    }

    //@Override
    //public ExtendedBank getBank() {
     //   return extendedBank;
    //}

    @Override
    public ExtendedCamera getCamera() {
        return extendedCamera;
    }

    @Override
    public ExtendedGroundItems getGroundItems() { return extendedGroundItems; }

    @Override
    public ExtendedInventory getInventory() { return extendedInventory; }

    @Override
    public ExtendedNPCS getNpcs() { return extendedNPCS; }

    @Override
    public ExtendedSettings getSettings() { return extendedSettings; }

    public Paint getPaint() { return paint; }

    public InteractionHelper getInteractionHelper() { return interactionHelper; }

    public MakeAllInterface getMakeAllInterface() { return makeAllInterface; }

    public SkillTracker getSkillTracker() { return skillTracker; }

    public ExecutableUtil getExecutableUtil() { return executableUtil; }

    public CanvasUtil getCanvasUtil() { return canvasUtil; }

    public Account getAccount() { return account; }

    public CommandLine getCommandLine() { return commandLine; }

    public void execute(Executable executable) throws InterruptedException {
        getExecutableUtil().execute(executable);
    }
}