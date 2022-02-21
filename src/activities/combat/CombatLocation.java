package activities.combat;

import org.osbot.rs07.api.map.Area;
import utils.Location;

public enum CombatLocation {

    Champions_Guild(new Location("Champions' Guild", new Area(3195, 3359, 3198, 3352))),
    Farm_south_of_Falador(new Location("Farm south of Falador", new Area(
            new int[][]{
                    { 3037, 3291 },
                    { 3038, 3290 },
                    { 3038, 3285 },
                    { 3041, 3285 },
                    { 3041, 3284 },
                    { 3042, 3283 },
                    { 3040, 3281 },
                    { 3028, 3281 },
                    { 3027, 3282 },
                    { 3014, 3282 },
                    { 3014, 3299 },
                    { 3021, 3299 },
                    { 3021, 3285 },
                    { 3026, 3285 },
                    { 3026, 3290 },
                    { 3029, 3290 },
                    { 3030, 3291 }
            }
    ))),
    Lumbridge_East_Farm(new Location("Lumbridge East Farm", new Area(
            new int[][]{
                    { 3226, 3302 },
                    { 3225, 3301 },
                    { 3225, 3295 },
                    { 3231, 3295 },
                    { 3231, 3287 },
                    { 3237, 3287 },
                    { 3237, 3290 },
                    { 3238, 3291 },
                    { 3238, 3293 },
                    { 3237, 3294 },
                    { 3237, 3297 },
                    { 3238, 3298 },
                    { 3238, 3300 },
                    { 3236, 3302 }
            }
    ))),
    Lumbridge_West_Farm(new Location("Lumbridge West Farm", new Area(
            new int[][]{
                    { 3173, 3308 },
                    { 3180, 3308 },
                    { 3180, 3304 },
                    { 3182, 3304 },
                    { 3183, 3303 },
                    { 3184, 3303 },
                    { 3186, 3301 },
                    { 3186, 3299 },
                    { 3187, 3298 },
                    { 3187, 3296 },
                    { 3186, 3295 },
                    { 3186, 3291 },
                    { 3184, 3289 },
                    { 3178, 3289 },
                    { 3177, 3288 },
                    { 3175, 3288 },
                    { 3174, 3289 },
                    { 3171, 3289 },
                    { 3169, 3291 },
                    { 3169, 3295 },
                    { 3170, 3296 },
                    { 3170, 3298 },
                    { 3169, 3299 },
                    { 3169, 3300 },
                    { 3173, 3304 }
            }
    )));

    static CombatLocation[] CHICKEN = {
            Champions_Guild,
            Farm_south_of_Falador,
            Lumbridge_East_Farm,
            Lumbridge_West_Farm
    };

    public Location location;

    CombatLocation(final Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location.toString();
    }
}