package activities.skills.agility;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public class CoursePart {

    public final Area activeArea;
    final ObstacleType obstacle;
    Position[] pathToArea;
    Position obstaclePosition;
    Boolean waitForFloor = false;

    CoursePart(final ObstacleType obstacle, final Area activeArea) {
        this.obstacle = obstacle;
        this.activeArea = activeArea;
    }

    CoursePart(final ObstacleType obstacle, final Area activeArea, final Boolean waitForFloor) {
        this.obstacle = obstacle;
        this.activeArea = activeArea;
        this.waitForFloor = waitForFloor;
    }

    CoursePart(final ObstacleType obstacle, final Area activeArea, final Position[] pathToObstacle) {
        this.obstacle = obstacle;
        this.activeArea = activeArea;
        this.pathToArea = pathToObstacle;
    }

    CoursePart(final ObstacleType obstacle, final Area activeArea, final Position obstaclePosition) {
        this.obstacle = obstacle;
        this.activeArea = activeArea;
        this.obstaclePosition = obstaclePosition;
    }

    CoursePart(final ObstacleType obstacle, final Area activeArea, final Position[] pathToObstacle, final Position obstaclePosition) {
        this.obstacle = obstacle;
        this.activeArea = activeArea;
        this.pathToArea = pathToObstacle;
        this.obstaclePosition = obstaclePosition;
    }
}