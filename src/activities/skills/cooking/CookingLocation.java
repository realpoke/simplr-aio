package activities.skills.cooking;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import utils.Location;

public enum CookingLocation {

    FALADOR_RANGE(new Location("Falador", new Area(
            new int[][]{
                    {2988, 3363},
                    {2988, 3368},
                    {2992, 3368},
                    {2992, 3366},
                    {2995, 3366},
                    {2995, 3363}
            }
    )), CookingObject.RANGE),
    EDGEVILLE_STOVE(new Location("Edgeville Stove", new Area(3077, 3493, 3079, 3496)), CookingObject.STOVE),
    EDGEVILLE_RANGE(new Location("Edgeville Range", new Area(new Position(3083, 3509, 1), new Position(3080, 3508, 1))), CookingObject.RANGE),
    CATHERBY_RANGE(new Location("Catherby Range", new Area(2815, 3439, 2818, 3444)), CookingObject.RANGE),
    LUMBRIDGE_RANGE(new Location("Lumbridge Range", new Area(3205, 3212, 3212, 3217)), CookingObject.RANGE),
    LUMBRIDGE_GENERAL_RANGE(new Location("Lumbridge General Range", new Area(3230, 3198, 3232, 3195)), CookingObject.RANGE);

    public Location location;
    public CookingObject cookingObject;

    CookingLocation(final Location location, final CookingObject cookingObject) {
        this.location = location;
        this.cookingObject = cookingObject;
    }

    @Override
    public String toString() {
        return location.toString();
    }
}