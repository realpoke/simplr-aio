package events;

import events.settings.*;
import org.osbot.rs07.api.ui.RS2Widget;
import utils.Sleep;
import utils.method_provider.BlockingExecutable;
import utils.method_provider.ExecutionFailedException;

import java.awt.event.KeyEvent;

public class SetupClientEvent extends BlockingExecutable {

    private final DisableAidEvent aidsEvent = new DisableAidEvent();
    private final DisableAudioEvent audioEvent = new DisableAudioEvent();
    private final DisableRoofEvent roofEvent = new DisableRoofEvent();
    private final EnableShiftDroppingEvent shiftDroppingEvent = new EnableShiftDroppingEvent();
    private final EnableSkullPreventionEvent enableSkullPreventionEvent = new EnableSkullPreventionEvent();

    @Override
    protected void blockingRun() throws InterruptedException {
        if (getDialogues().isPendingContinuation()) {
            getDialogues().clickContinue();
        } else if (pendingContinue()) {
            selectContinue();
            return;
        }

        if (getSettings().isAcceptAidActive()) {
            logger.info("Disabling aid...");
            execute(aidsEvent);
        } else if (!getSettings().isAudioDisabled()) {
            logger.info("Disabling audio...");
            execute(audioEvent);
        } else if (!getSettings().areRoofsEnabled()) {
            logger.info("Disabling roofs...");
            execute(roofEvent);
        } else if (!getSettings().isShiftDropActive()) {
            logger.info("Enabling shift dropping...");
            execute(shiftDroppingEvent);
        } else if (!getSettings().isPKSkullPreventionEnabled()) {
            logger.info("Enabling PK skull prevention...");
            execute(enableSkullPreventionEvent);
        } else {
            logger.info("Client setup complete!");
            setFinished();
        }
    }

    // Tutorial Island stuff
    private boolean selectContinue() {
        RS2Widget continueWidget = getContinueWidget();
        if (continueWidget == null) {
            return false;
        }
        if (continueWidget.getMessage().contains("Click here to continue")) {
            getKeyboard().pressKey(KeyEvent.VK_SPACE);
            Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1_000);
            return true;
        } else if (continueWidget.interact()) {
            Sleep.sleepUntil(() -> !continueWidget.isVisible(), 1_000);
            return true;
        }
        return false;
    }

    private RS2Widget getContinueWidget() {
        return getWidgets().singleFilter(getWidgets().getAll(),
                widget -> widget.isVisible()
                        && (widget.getMessage().contains("Click here to continue")
                        || widget.getMessage().contains("Click to continue"))
        );
    }

    protected boolean pendingContinue() {
        RS2Widget continueWidget = getContinueWidget();
        return continueWidget != null && continueWidget.isVisible();
    }
}
