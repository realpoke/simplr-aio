package paint;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.canvas.paint.Painter;
import utils.MousePointPath;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.util.LinkedList;

public class MousePaint implements Painter {

    private final int size;
    private int angle;
    private final int rotateSpeed;
    private final BasicStroke cursorStroke, cursorTrailStroke;
    private final LinkedList<MousePointPath> mousePath  = new LinkedList<MousePointPath>();;
    private final int duration;
    private long lastRotateTime = System.currentTimeMillis();
    private final Mouse mouse;
    private boolean paused = false;

    public MousePaint(Mouse mouse, int duration, int rotateSpeed, int size, int thickness) {
        this.mouse = mouse;
        this.duration = duration;
        this.rotateSpeed = rotateSpeed;
        this.size = size;
        this.cursorStroke = new BasicStroke(thickness);
        this.cursorTrailStroke = new BasicStroke(2);
    }

    @Override
    public void onPaint(Graphics2D g) {

        if (paused)
            return;

        Point mPos = mouse.getPosition();
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        g.drawLine(mPos.x, mPos.y - 7, mPos.x, mPos.y + 7);
        g.drawLine(mPos.x - 7, mPos.y, mPos.x + 7, mPos.y);

        g.setColor(Color.decode("#1b1919"));
        g.setStroke(new BasicStroke(2));
        g.drawLine(mPos.x, mPos.y - 6, mPos.x, mPos.y + 6);
        g.drawLine(mPos.x - 6, mPos.y, mPos.x + 6, mPos.y);
        g.setColor(Color.WHITE);

        AffineTransform oldTransform = g.getTransform();
        Point clientCursor = mouse.getPosition();

        if (clientCursor.x != -1) {
            g.setStroke(cursorStroke);

            if (lastRotateTime < System.currentTimeMillis() - rotateSpeed) {
                while (lastRotateTime < System.currentTimeMillis() - rotateSpeed) {
                    angle += 1;

                    if (angle >= 360)
                        angle = 0;
                    lastRotateTime = lastRotateTime + rotateSpeed;
                }
            }

            g.rotate(Math.toRadians(angle), clientCursor.x, clientCursor.y);

            g.draw(new Arc2D.Double(clientCursor.x - (size / 2), clientCursor.y - (size / 2), size, size, 330, 60, Arc2D.OPEN));
            g.draw(new Arc2D.Double(clientCursor.x - (size / 2), clientCursor.y - (size / 2), size, size, 151, 60, Arc2D.OPEN));
            g.setTransform(oldTransform);
        }

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();
        MousePointPath mpp = new MousePointPath(clientCursor.x, clientCursor.y, duration);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePointPath lastPoint = null;
        for (MousePointPath a : mousePath) {
            if (lastPoint != null && a.y != -1 && a.x != -1 && lastPoint.y != -1 && lastPoint.x != -1) {
                g.setStroke(cursorTrailStroke);
                g.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
            }
            lastPoint = a;
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}