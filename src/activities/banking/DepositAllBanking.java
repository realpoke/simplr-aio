package activities.banking;

import utils.requirement.ItemRequirement;

import java.util.stream.Stream;

public class DepositAllBanking extends Banking {

    private String[] exceptItems;

    public DepositAllBanking(final String... exceptItems) {
        super(true);
        this.exceptItems = exceptItems;
    }

    public DepositAllBanking() {
        this(new String[]{});
    }

    public DepositAllBanking(final ItemRequirement... exceptItems) {
        this(Stream.of(exceptItems).map(ItemRequirement::getName).toArray(String[]::new));
    }

    public void setExceptItems(final String... exceptItems) {
        this.exceptItems = exceptItems;
    }

    @Override
    protected void bank(final BankType currentBankType) {
        boolean success;

        if (exceptItems == null) {
            if (currentBankType == BankType.DEPOSIT_BOX) {
                success = getDepositBox().depositAll();
            } else {
                success = getBank().depositAll();
            }
        } else if (currentBankType == BankType.DEPOSIT_BOX) {
            success = getDepositBox().depositAllExcept(exceptItems);
        } else {
            success = getBank().depositAllExcept(exceptItems);
        }

        if (success) {
            setFinished();
        }
    }
}