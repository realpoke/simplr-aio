package utils.method_provider.providers;

import org.osbot.rs07.Bot;
import utils.method_provider.CustomMethodProvider;

import javax.swing.*;
import java.awt.*;

public class CanvasUtil extends CustomMethodProvider {

    private final Bot bot;
    private final String originalTitle;
    private final Dimension originalSize;
    private final Integer MIN_CLIENT_WIDTH = 1100;
    private final Integer MIN_CLIENT_HEIGHT = 800;

    public CanvasUtil(Bot bot) {
        this.bot = bot;
        this.originalTitle = getJFrame().getTitle();
        this.originalSize = getJFrame().getSize();
    }

    public void resetTitle() {
        JFrame frame = getJFrame();
        frame.setTitle(originalTitle);
        logger.info("Resetting window title (" + originalTitle + ")...");
    }

    public void resetSize() {
        if (originalSize != getJFrame().getSize()) {
            JFrame frame = getJFrame();
            frame.setSize(originalSize);
            logger.info("Resetting window size (" + originalSize.getWidth() + ", " + originalSize.getHeight() + ")...");
        }
    }

    public void setTitle(String title, String status) {
        JFrame frame = getJFrame();

        String finalTitle = "RuneLite | " + getCommandLine().getString(CommandLine.Commands.BOT_LOGIN) + " | " + status + " | " + title;
        if (finalTitle.equals(frame.getTitle())) {
            return;
        }

        frame.setTitle(finalTitle);

        logger.debug("Setting window title (" + finalTitle + ")...");
    }

    private JFrame getJFrame() {
        Window w = SwingUtilities.getWindowAncestor(bot.getCanvas());
        return (JFrame)w;
    }

    public void setRandomSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Integer clientWidth = random(1100, Math.max(1100, (int)screenSize.getWidth()));
        Integer clientHeight = random(800, Math.max(800, (int)screenSize.getHeight()));

        setSize(clientWidth, clientHeight);
    }

    public void setSize(Integer width, Integer height) {
        width = Math.max(width, MIN_CLIENT_WIDTH);
        height = Math.max(height, MIN_CLIENT_HEIGHT);
        logger.debug("Setting window size (" + width + ", " + height + ")...");

        JFrame frame = getJFrame();
        frame.setSize(width, height);
    }
}
