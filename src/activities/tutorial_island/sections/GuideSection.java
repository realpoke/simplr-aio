package activities.tutorial_island.sections;

import activities.tutorial_island.SectionBase;
import org.osbot.rs07.api.Configs;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import utils.Sleep;
import utils.method_provider.CustomMethodProvider;
import utils.widget.CachedWidget;
import utils.widget.filters.WidgetActionFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuideSection extends SectionBase {

    private static final Integer USERNAME_CHECK_STATUS_CONFIG_ID = 1042;
    private final CachedWidget nameAcceptedWidget = new CachedWidget(w -> w.getMessage().contains("Great!"));
    private final CachedWidget lookupNameWidget = new CachedWidget(w -> w.getMessage().contains("Look up name"));
    private final CachedWidget inputNameWidget = new CachedWidget(w -> w.getMessage().contains("Please look up a name to see whether it is available"));
    private final CachedWidget setNameWidget = new CachedWidget("Set name");
    private final CachedWidget chooseDisplayNameWidget = new CachedWidget("Set display name");
    private final CachedWidget suggestionsWidget = new CachedWidget("one of our suggestions");
    private final CachedWidget creationScreenWidget = new CachedWidget("Character Creator");

    private enum UsernameCheckStatus {
        NOT_AVAILABLE(0),
        CHECKING(1),
        AVAILABLE(2);

        int bitNum;

        UsernameCheckStatus(int bitNum) {
            this.bitNum = bitNum;
        }

        static UsernameCheckStatus getUsernameCheckStatus(Configs configs) {
            int configVal = configs.get(USERNAME_CHECK_STATUS_CONFIG_ID);

            for (UsernameCheckStatus checkStatus : values()) {
                if ((configVal & (1 << checkStatus.bitNum)) != 0) {
                    return checkStatus;
                }
            }

            return null;
        }
    }

    @Override
    public void run() throws InterruptedException {
        super.setProgress(getProgress());

        if (pendingQuestion()) {
            if (!selectQuestionOption()) {
                getWalking().walk(myPosition().getArea(4).getRandomPosition());
            }
            return;
        }

        if (pendingContinue()) {
            selectContinue();
            return;
        }

        logger.debug("Progress: " + getProgress());

        switch (getProgress()) {
            case 0:
            case 1:
            case 2:
                if (chooseDisplayNameWidget.isVisible(getWidgets())) {
                    setDisplayName();
                } else if (creationScreenWidget.isVisible(getWidgets())) {
                    createRandomCharacter();
                } else {
                    talkToInstructor();
                }
                break;
            case 3:
                if (getTabs().open(Tab.SETTINGS)) {
                    Sleep.sleepUntil(() -> getProgress() != 3, 1_000);
                }
                break;
            case 10:
                getAccount().applyDefaultSettings();
            default:
                if (hintArrowExists()) {
                    gotoHintArrow();
                }
                talkToInstructor();
                break;
        }
    }

    public GuideSection() {
        super(TutorialSectionInstructor.GUIDE);
    }

    private void setDisplayName() {
        UsernameCheckStatus checkStatus = UsernameCheckStatus.getUsernameCheckStatus(getConfigs());

        if (checkStatus == null) {
            logger.error("Couldn't determine username check status");
            return;
        }

        switch (checkStatus) {
            case NOT_AVAILABLE:
                if (suggestionsWidget.isVisible(getWidgets())) {
                    Optional<RS2Widget> suggestionWidget = suggestionsWidget.getRelative(getWidgets(), 0, random(2, 5), 0);
                    if (suggestionWidget.isPresent() && suggestionWidget.get().interact("Set name")) {
                        Sleep.sleepUntil(() -> nameAcceptedWidget.get(getWidgets()).isPresent(), 8_000);
                    }
                } else if (inputNameWidget.isVisible(getWidgets())) {
                    final RS2Widget[] childWidgets = getWidgets().getWidgets(chooseDisplayNameWidget.get(getWidgets()).get().getRootId());
                    final WidgetActionFilter nameWidgetActionFilter = new WidgetActionFilter("Enter name");
                    final RS2Widget[] nameWidgets = Stream.of(childWidgets).filter(nameWidgetActionFilter::match).toArray(RS2Widget[]::new);
                    Arrays.stream(nameWidgets).findFirst().get().interact();

                    if (getKeyboard().typeString(generateRandomString(random(4, 8)), true)) {
                        Sleep.sleepUntil(() -> UsernameCheckStatus.getUsernameCheckStatus(getConfigs()) == UsernameCheckStatus.CHECKING, 6_000);
                    }
                } else if (lookupNameWidget.interact(getWidgets())) {
                    Sleep.sleepUntil(() -> !inputNameWidget.isVisible(getWidgets()), 8_000);
                }
                break;
            case CHECKING:
                Sleep.sleepUntil(() -> UsernameCheckStatus.getUsernameCheckStatus(getConfigs()) != UsernameCheckStatus.CHECKING, 6_000);
                break;
            case AVAILABLE:
                if (setNameWidget.interact(getWidgets())) {
                    Sleep.sleepUntil(() -> !chooseDisplayNameWidget.isVisible(getWidgets()), 8_000);
                }
                break;
        }
    }

    private String generateRandomString(int maxLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        return new Random().ints(new Random().nextInt(maxLength) + 1, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());
    }

    private void createRandomCharacter() throws InterruptedException {
        if (new Random().nextInt(2) == 1) {
            getWidgets().getWidgetContainingText("Female").interact();
        }

        final RS2Widget[] childWidgets = getWidgets().getWidgets(creationScreenWidget.get(getWidgets()).get().getRootId());

        final WidgetActionFilter selectableWidgetActionFilter = new WidgetActionFilter("Select");
        final RS2Widget[] selectableWidgets = Stream.of(childWidgets).filter(selectableWidgetActionFilter::match).toArray(RS2Widget[]::new);

        Collections.shuffle(Arrays.asList(selectableWidgets));

        int maxSelection = new Random().nextInt(Math.min(selectableWidgets.length, 22));

        for (int i = 0; i < (maxSelection + 3); i ++) {
            clickRandomTimes(selectableWidgets[i]);
        }

        if (getWidgets().getWidgetContainingText("Confirm").interact()) {
            Sleep.sleepUntil(() -> !creationScreenWidget.isVisible(getWidgets()), 3_000);
        }
    }

    private void clickRandomTimes(final RS2Widget widget) throws InterruptedException {
        int clickCount = new Random().nextInt(5);

        for (int i = 0; i < clickCount; i++) {
            if (widget.interact()) {
                CustomMethodProvider.sleep(random(150, 300));
            }
        }
    }
}
