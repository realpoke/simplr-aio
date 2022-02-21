package activities.combat;

import activities.activity.Activity;
import activities.activity.ActivityType;

public class CombatActivity extends Activity {

    private final CombatGear gear;
    private final MonsterType monster;
    private final CombatMethod method;

    CombatActivity(final MonsterType monster, final CombatGear gear, final CombatMethod method) {
        super(ActivityType.COMBAT);

        this.method = method;
        this.monster = monster;
        this.gear = gear;
    }

    @Override
    public void runActivity() throws InterruptedException {

    }

    @Override
    public Activity copy() {
        return new CombatActivity(this.monster, this.gear, this.method);
    }
}
