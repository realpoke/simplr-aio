package paint;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.input.mouse.BotMouseListener;
import tasks.Task;
import utils.RSUnits;
import utils.file_manager.FontManager;
import utils.method_provider.providers.SkillTracker;

import java.awt.*;
import java.awt.event.MouseEvent;

public class InfoPaint implements Painter {

    private final Bot bot;
    private final SkillTracker skillTracker;
    private int offsetY;
    private int offsetX;
    private long lastCheckTime = -1;
    private long elapsedTime = -1;
    private Task currentTask;
    private final String title;
    private Rectangle infoBoxRectangle;

    private int x = 10;
    private int y = 25;
    private int draggedFromX;
    private int draggedFromY;
    private boolean dragging = false;
    private int extraHeight;

    private boolean paused;

    public InfoPaint(final Bot bot, final SkillTracker skillTracker, String title) {
        this.title = title;
        this.bot = bot;
        this.skillTracker = skillTracker;

        bot.addMouseListener(new BotMouseListener() {
            @Override
            public void checkMouseEvent(final MouseEvent e) {
                if (getPaintBorder().contains(e.getPoint())) {
                    switch (e.getID()) {
                        case MouseEvent.MOUSE_PRESSED:
                            dragging = true;
                            draggedFromX = e.getX() - x;
                            draggedFromY = e.getY() - y;
                            e.consume();
                            break;
                        case MouseEvent.MOUSE_RELEASED:
                        case MouseEvent.MOUSE_EXITED:
                            if (dragging) {
                                dragging = false;
                            }
                            break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && mouseIsOnScreen(e)) {
                    int newX = 0;
                    int newY = 0;
                    newX = e.getX() - draggedFromX;
                    newY = e.getY() - draggedFromY;
                    x = newX;
                    y = newY;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                checkMouseEvent(e);
            }
        });
    }

    private boolean mouseIsOnScreen(MouseEvent e) {
        return e.getX() <= bot.getCanvas().getWidth() &&
                e.getX() >= 0 &&
                e.getY() <= bot.getCanvas().getHeight() &&
                e.getY() >= 0;
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        drawScriptInfo(g);
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        lastCheckTime = System.currentTimeMillis();
    }

    public void setCurrentTask(final Task currentTask) {
        this.currentTask = currentTask;
    }

    private void drawScriptInfo(Graphics2D g) {
        setOffset();
        drawScriptInfoBackground(g);
        Color oldColor = g.getColor();
        g.setFont(FontManager.ROBOTO_REGULAR);
        drawTitle(g);
        g.setFont(FontManager.ROBOTO_REGULAR);
        drawRunTime(g);
        drawTaskInfo(g);
        drawActivityInfo(g);
        drawSkillsInfo(g);
        g.setColor(oldColor);
        g.setFont(FontManager.ROBOTO_REGULAR);
    }

    private void setOffset() {
        offsetX = x;
        offsetY = y;
        extraHeight = skillTracker.getTrackedSkills().size() * 20;
    }

    private void drawScriptInfoBackground(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(offsetX, offsetY, 604, extraHeight + 109);
        g.setColor(Color.decode("#1b1919"));
        infoBoxRectangle = new Rectangle(offsetX + 2, offsetY + 2, 600, extraHeight + 105);
        g.fill(infoBoxRectangle);
    }

    private void drawTitle(Graphics2D g) {
        offsetY = offsetY + 30;
        g.setFont(g.getFont().deriveFont(24f));
        g.setColor(Color.WHITE);
        g.drawString(title, offsetX + 10, offsetY);
        offsetY = offsetY + 5;
    }

    private void drawRunTime(Graphics2D g) {
        offsetY = offsetY + 20;
        if (lastCheckTime == -1) {
            lastCheckTime = System.currentTimeMillis();
            elapsedTime = 0;
        }

        if (!paused) {
            long currentTime = System.currentTimeMillis();
            elapsedTime += currentTime - lastCheckTime;
            lastCheckTime = currentTime;
            elapsedTime += System.currentTimeMillis() - lastCheckTime;
        }

        g.drawString("Run time: " + formatTime(elapsedTime), offsetX + 10, offsetY);
    }

    private void drawTaskInfo(Graphics2D g) {
        offsetY = offsetY + 20;
        if (currentTask != null) {
            g.drawString(currentTask.toString(), offsetX + 10, offsetY);
        }
    }

    private void drawActivityInfo(Graphics2D g) {
        offsetY = offsetY + 20;
        if (currentTask != null && currentTask.getActivity() != null) {
            g.drawString("Activity: " + currentTask.getActivity().toString(), offsetX + 10, offsetY);
        }
    }

    private void drawSkillsInfo(Graphics2D g) {
        offsetY = offsetY + 20;
        int x = offsetX + 10;
        int y = offsetY;
        for (final Skill skill : skillTracker.getTrackedSkills()) {
            String output = String.format("%s lvl %d (+%d lvls) +%s xp (%s xp / hr)",
                    skill.toString(),
                    skillTracker.getLevel(skill),
                    skillTracker.getGainedLevels(skill),
                    RSUnits.valueToFormatted(skillTracker.getGainedXP(skill)),
                    RSUnits.valueToFormatted(skillTracker.getGainedXPPerHour(skill)));
            g.drawString(output, x, y);
            y += 20;
        }
    }

    private Rectangle getPaintBorder() {
        return infoBoxRectangle;
    }

    private String formatTime(long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        h %= 24;
        m %= 60;
        s %= 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
