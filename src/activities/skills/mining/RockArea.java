package activities.skills.mining;

import org.osbot.rs07.api.map.Area;

public class RockArea {

    final RockType rock;
    final Area area;

    public RockArea(final RockType rock, final Area area) {
        this.rock = rock;
        this.area = area;
    }
}