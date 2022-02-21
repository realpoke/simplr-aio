package utils.method_provider.extenders;

import events.settings.ZoomControlEvent;
import org.osbot.rs07.api.Camera;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.event.Event;
import utils.Sleep;
import utils.method_provider.CustomMethodProvider;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public class ExtendedCamera extends Camera {

    private static final int MAX_PITCH_ANGLE = 67;
    private static final int MAX_ZOOM_OUT_SCALE = 714;
    private final CustomMethodProvider customMethodProvider;

    public ExtendedCamera(final CustomMethodProvider customMethodProvider) {
        this.customMethodProvider = customMethodProvider;
    }

    public boolean isAtTop() {
        return getCamera().getPitchAngle() >= MAX_PITCH_ANGLE;
    }

    public int clampAngle(final int angle) {
        if (angle < 0) {
            return angle + 360;
        } else if (angle > 360) {
            return angle - 360;
        }
        return angle;
    }

    public int getLowestPitchAngle() {
        return getCamera().getLowestPitchAngle();
    }

    public int getX() {
        return getCamera().getX();
    }

    public int getY() {
        return getCamera().getY();
    }

    public int getZ() {
        return getCamera().getZ();
    }

    public int getYawAngle() {
        return getCamera().getYawAngle();
    }

    public int getPitchAngle() {
        return getCamera().getPitchAngle();
    }

    public void moveYawUntilObjectVisible(final Supplier<Entity> entitySupplier) {
        execute(new Event() {
            @Override
            public int execute() {
                if (entitySupplier.get().isVisible()) {
                    setFinished();
                } else {
                    int nextYaw = clampAngle(getYawAngle() + random(5, 10));
                    moveYaw(nextYaw);
                }
                return 0;
            }
        });
    }

    public void moveNorth() {
        int r = random(0, 30);
        if (r > 15)
            r = 375 - r;
        moveYaw(r);
    }

    public void moveWest() {
        moveYaw(75 + random(0, 30));
    }

    public void moveSouth() {
        moveYaw(165 + random(0, 30));
    }

    public void moveEast() {
        moveYaw(255 + random(0, 30));
    }

    public boolean movePitch(int pitch) {
        moveCamera(getYawAngle(), pitch);
        return true;
    }

    //public boolean moveYaw(int yaw) {
    //    moveCamera(yaw, getPitchAngle());
    //    return true;
    //}

    public void moveCamera(int yaw, int pitch) {
        if (pitch > 67)
            pitch = 67;
        else if (pitch < 22)
            pitch = 22;

        int pitchCur = getPitchAngle(),
                yawCur = getYawAngle(),
                pitchDir = pitch < pitchCur ? -1 : 1,
                pitchDiff = Math.abs(pitch - pitchCur),
                yawDir = yaw > yawCur ? -1 : 1,
                yawDiff = Math.abs(yaw - yawCur);

        if (yawDiff > 180) {
            // Flip how we get there
            yawDiff = 360 - yawDiff;
            yawDir *= -1;
        }

        if (yawDiff < 22 && pitchDiff < 14)
            return;

        int x = yawDir * yawDiff * 3,
                y = pitchDir * pitchDiff * 3;

        int minX = 20 - (yawDir == -1 ? x : 0),
                maxX = (customMethodProvider.getDisplay().getScreenWidth() - 20) - (yawDir == 1 ? x : 0),
                minY = 20 + (pitchDir == -1 ? y : 0),
                maxY = (customMethodProvider.getDisplay().getScreenHeight() - 20) + (pitchDir == 1 ? y: 0);

        Point mp = customMethodProvider.getMouse().getPosition();

        for (int i = 0; i < 5 && !customMethodProvider.getMouse().isOnScreen(); i++) {
            customMethodProvider.getMouse().move(random(minX, maxX), random(minY, maxY));
            Sleep.sleepUntil(() -> false, 5, 50);
        }

        if (mp.x < minX || mp.x > maxX
                || mp.y < minY || mp.y > maxY) {
            customMethodProvider.getMouse().move(random(minX, maxX), random(minY, maxY));
            Sleep.sleepUntil(() -> false, 5, 50);
        }

        mousePress(true);

        mp = customMethodProvider.getMouse().getPosition();

        int newX = Math.min(customMethodProvider.getDisplay().getScreenWidth(), Math.max(0, mp.x + x)),
                newY = Math.min(customMethodProvider.getDisplay().getScreenHeight(), Math.max(0, mp.y + y));

        customMethodProvider.getMouse().move(newX, newY);

        Sleep.sleepUntil(() -> false, 5, 50);

        mousePress(false);
    }

    //public void toPosition(Position Position) {
    //    toPosition(myPosition(), Position);
    //}

    public void toPositionMouse(Position Position) {
        toPosition(myPosition(), Position);
    }

    private void toPosition(Position origin, Position position) {
        if (position.distance(myPosition()) > 16)
            return;
        moveCamera(getAngleTo(origin, position), getPitchTo(origin, position));
    }

    //public void toEntity(Entity e) {
    //    toEntity(myPosition(), e);
    //}

    public void toEntityMouse(Entity e) {
        toEntity(myPosition(), e);
    }

    public void toEntity(Position origin, Entity e) {
        if (e == null)
            return;
        moveCamera(getAngleTo(origin, e.getPosition()), getPitchTo(origin, e.getPosition()));
    }

    private int getPitchTo(Position origin, Position t) {
        int pitch = 67 - (int) (t.distance(origin) * 5);

        if (pitch > 67) {
            pitch = 67;
        } else if (pitch < 22) {
            pitch = 22;
        }

        return pitch;
    }

    private int getAngleTo(Position origin, Position Position) {
        int degree = (int) Math.toDegrees(Math.atan2(
                Position.getY() - origin.getY(), Position.getX() - origin.getX()));
        int a = ((degree >= 0 ? degree : 360 + degree) - 90) % 360;
        return a < 0 ? a + 360 : a;
    }

    private void mousePress(boolean press) {
        customMethodProvider.getBot()
                .getMouseEventHandler()
                .generateBotMouseEvent(press ? MouseEvent.MOUSE_PRESSED : MouseEvent.MOUSE_RELEASED,
                        System.currentTimeMillis(), 0,
                        getMouse().getPosition().x,
                        getMouse().getPosition().y, 1, false,
                        MouseEvent.BUTTON2, true);
    }

    public boolean isZoomedOut() {
        return getScaleZ() <= MAX_ZOOM_OUT_SCALE;
    }

    public void zoomOut() throws InterruptedException {
        if (isZoomedOut()) {
            logger.debug("IS zoomed out");
            return;
        }
        if (!ZoomControlEvent.isInRange(getScaleZ(), MAX_ZOOM_OUT_SCALE)) {
            customMethodProvider.execute(new ZoomControlEvent(MAX_ZOOM_OUT_SCALE));
        }
    }

    public boolean isDefaultScaleZ() {
        return getCamera().isDefaultScaleZ();
    }

    @Override
    public void initializeModule() {
    }
}