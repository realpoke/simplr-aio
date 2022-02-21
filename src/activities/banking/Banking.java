package activities.banking;

import utils.method_provider.BlockingExecutable;
import org.osbot.rs07.api.map.Area;
import utils.Sleep;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;

import java.util.stream.Stream;

public abstract class Banking extends BlockingExecutable {

    private static final Area[] ALL_BANK_AND_DEPOSIT_BOX_AREAS = Stream.concat(Stream.of(activities.banking.BankType.AREAS), Stream.of(DepositBox.AREAS)).toArray(Area[]::new);
    private static final CachedWidget CONTINUE_BANK_INSTRUCTIONS_WIDGET = new CachedWidget(new WidgetActionFilter("Continue"));

    private final boolean useDepositBoxes;

    protected enum BankType {
        BANK,
        DEPOSIT_BOX
    }

    private BankType currentBankType;
    private boolean finishedBanking;

    public Banking() {
        this.useDepositBoxes = false;
    }

    public Banking(final boolean useDepositBoxes) {
        this.useDepositBoxes = useDepositBoxes;
    }

    /**
     * Override BlockingExecutable::setFinished
     * to prevent finishing immediately upon a super class
     * setFinished() call. We need to ensure that the bank is closed
     * before we end the executable.
     */
    @Override
    public void setFinished() {
        finishedBanking = true;
    }

    @Override
    public void blockingRun() throws InterruptedException {
        if (getDialogues().isPendingContinuation())
            getDialogues().clickContinue();

        if (finishedBanking) {
            if (isBankOpen()) {
                closeBank();
            } else {
                super.setFinished();
            }
        } else if (!playerInBank()) {
            walkToBank();
        } else if (getInventory().contains("Coin pouch")) {
            getInventory().getItem("Coin pouch").interact();
        } else if (!isBankOpen()) {
            openBank();
        } else {
            if (getBank() != null && getBank().isOpen()) {
                currentBankType = Banking.BankType.BANK;
            } else {
                currentBankType = Banking.BankType.DEPOSIT_BOX;
            }

            if (CONTINUE_BANK_INSTRUCTIONS_WIDGET.isVisible(getWidgets())) {
                CONTINUE_BANK_INSTRUCTIONS_WIDGET.interact(getWidgets(), "Continue");
            } else {
                bank(currentBankType);
            }
        }
    }

    private boolean playerInBank() {
        if (useDepositBoxes) {
            return Stream.of(ALL_BANK_AND_DEPOSIT_BOX_AREAS).anyMatch(area -> area.contains(myPosition()));
        }
        return Stream.of(activities.banking.BankType.AREAS).anyMatch(area -> area.contains(myPosition()));
    }

    private boolean walkToBank() {
        if (useDepositBoxes) {
            return getWalking().webWalk(ALL_BANK_AND_DEPOSIT_BOX_AREAS);
        }
        return getWalking().webWalk(activities.banking.BankType.AREAS);
    }

    boolean isBankOpen() {
        if (useDepositBoxes && getBank() != null && getDepositBox() != null) {
            return getBank().isOpen() || getDepositBox().isOpen();
        }

        if (getBank() != null) {
            return getBank().isOpen();
        }

        if (useDepositBoxes && getDepositBox() != null) {
            return getDepositBox().isOpen();
        }

        return false;
    }

    private void openBank() throws InterruptedException {
        if (getBank() != null && getBank().open()) {
            Sleep.sleepUntil(() -> getBank().isOpen(), 5_000);
            return;
        }

        if (useDepositBoxes && getDepositBox() != null) {
            if (getDepositBox().open()) {
                Sleep.sleepUntil(() -> getDepositBox().isOpen(), 5_000);
            }
        }

        throw new IllegalStateException("Cannot open bank, no bank or deposit box found.");
    }

    /**
     * Execute banking operation
     */
    protected abstract void bank(final BankType currentBankType) throws InterruptedException;

    @Override
    public void onEnd() {
        closeBank();
    }

    private void closeBank() {
        if (currentBankType == Banking.BankType.DEPOSIT_BOX) {
            if (getDepositBox() != null && getDepositBox().isOpen() && getDepositBox().close()) {
                Sleep.sleepUntil(() -> !getDepositBox().isOpen(), 2_500);
            }
            return;
        }

        if (getBank() != null && getBank().isOpen() && getBank().close()) {
            Sleep.sleepUntil(() -> !getBank().isOpen(), 2_500);
        }
    }

    @Override
    public String toString() {
        return "ItemReqBanking";
    }
}