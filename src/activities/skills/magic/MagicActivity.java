package activities.skills.magic;

import activities.activity.Activity;
import activities.activity.ActivityType;

public class MagicActivity extends Activity {

    private final SpellType spell;

    public MagicActivity(final SpellType spell) {
        super(ActivityType.MAGIC);
        this.spell = spell;
    }

    @Override
    public void runActivity() throws InterruptedException {
    }

    @Override
    public MagicActivity copy() {
        return new MagicActivity(spell);
    }
}