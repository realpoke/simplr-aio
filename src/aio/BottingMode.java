package aio;

import activities.quests.QuestType;
import activities.quests.RuneMysteries;
import activities.shopping.ShoppingActivity;
import activities.skills.agility.AgilityActivity;
import activities.skills.agility.AgilityCourse;
import activities.skills.cooking.CookingActivity;
import activities.skills.cooking.CookingItem;
import activities.skills.cooking.CookingLocation;
import activities.skills.fishing.FishType;
import activities.skills.fishing.FishingActivity;
import activities.skills.fishing.FishingLocation;
import activities.skills.mining.RuneEssMiningActivity;
import activities.skills.thieving.ThievingActivity;
import activities.skills.thieving.ThievingObject;
import events.threads.DisconnectScreenshot;
import org.osbot.rs07.Bot;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import tasks.*;
import utils.Food;
import utils.Location;
import utils.ResourceMode;
import utils.method_provider.CustomMethodProvider;

import java.util.ArrayList;

public class BottingMode extends AIOMode {

    public void setup(Bot bot, CustomMethodProvider customMethodProvider, String scriptInfo) {
        super.setup(bot, customMethodProvider, scriptInfo);

        DisconnectScreenshot dcSs = new DisconnectScreenshot();
        dcSs.setAsync();
        customMethodProvider.execute(dcSs);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        int taskIndex = 0;

        Task tutorialIslandTask = new TutorialIslandTask();
        tutorialIslandTask.setExecutionOrder(taskIndex);
        taskIndex++;

        Location LUMBRIDGE_AREA = new Location("Lumbridge", new Area(
                new int[][]{
                        { 3207, 3229 },
                        { 3207, 3227 },
                        { 3213, 3227 },
                        { 3213, 3212 },
                        { 3207, 3212 },
                        { 3207, 3201 },
                        { 3213, 3201 },
                        { 3215, 3203 },
                        { 3221, 3203 },
                        { 3227, 3209 },
                        { 3227, 3216 },
                        { 3228, 3217 },
                        { 3230, 3217 },
                        { 3231, 3216 },
                        { 3231, 3213 },
                        { 3237, 3213 },
                        { 3237, 3225 },
                        { 3231, 3225 },
                        { 3231, 3222 },
                        { 3230, 3221 },
                        { 3228, 3221 },
                        { 3227, 3222 },
                        { 3227, 3229 },
                        { 3221, 3235 },
                        { 3215, 3235 },
                        { 3213, 3237 },
                        { 3207, 3237 }
                }
        ));

        Area BOBS_SHOP_AREA = new Area(3228, 3205, 3233, 3201);

        Task Fish = new ResourceTask(new FishingActivity(FishType.SHRIMP, FishingLocation.LUMBRIDGE_SWAMP, ResourceMode.BANK), FishType.SHRIMP.getRawFishName(), 27*2);
        Fish.setExecutionOrder(taskIndex);
        taskIndex++;
        Task Cooking = new ResourceTask(new CookingActivity(CookingItem.SHRIMPS, CookingLocation.LUMBRIDGE_GENERAL_RANGE), "Shrimps", 27);
        Cooking.setExecutionOrder(taskIndex);
        taskIndex++;

        Task Thieve = new ResourceTask(new ThievingActivity(ThievingObject.MAN, Food.SHRIMP, 40, LUMBRIDGE_AREA, ResourceMode.BANK), "Coins", 2_000);
        Thieve.setExecutionOrder(taskIndex);
        taskIndex++;
        // AIR EARTH WATER x 6 at Aubury's Rune Shop
        // Rope from Ned
        // Bucket at lumb general store
        // Jug of wine at Draynor Village
        Task ShopAxe = new ShopTask(new ShoppingActivity("Bob", new Location("Bob's shop", BOBS_SHOP_AREA), 182, "Mithril battleaxe"), "Mithril battleaxe");
        ShopAxe.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi10 = new LevelTask(new AgilityActivity(AgilityCourse.GNOME_STRONGHOLD), Skill.AGILITY, 10);
        agi10.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi20 = new LevelTask(new AgilityActivity(AgilityCourse.DRAYNOR_VILLAGE_ROOFTOP), Skill.AGILITY, 20);
        agi20.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi30 = new LevelTask(new AgilityActivity(AgilityCourse.AL_KHARID_ROOFTOP), Skill.AGILITY, 30);
        agi30.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi40 = new LevelTask(new AgilityActivity(AgilityCourse.VARROCK_ROOFTOP), Skill.AGILITY, 40);
        agi40.setExecutionOrder(taskIndex);
        taskIndex++;
        Task RM = new QuestTask(new RuneMysteries(), QuestType.RUNE_MYSTERIES);
        RM.setExecutionOrder(taskIndex);
        taskIndex++;
        Task Runes = new ResourceTask(new RuneEssMiningActivity(ResourceMode.BANK), "Pure essence", 50);
        Runes.setExecutionOrder(taskIndex);
        taskIndex++;
        // QUEST Quests.Quest.WATERFALL_QUEST Jug of Wine
        // QUEST Quests.Quest.PRIEST_IN_PERIL Flinch
        Task agi60 = new LevelTask(new AgilityActivity(AgilityCourse.CANAFIS_ROOFTOP), Skill.AGILITY, 60);
        agi60.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi70 = new LevelTask(new AgilityActivity(AgilityCourse.SEERS_VILLAGE_ROOFTOP), Skill.AGILITY, 70);
        agi70.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi80 = new LevelTask(new AgilityActivity(AgilityCourse.POLLNIVNEACH_ROOFTOP), Skill.AGILITY, 80);
        agi80.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi90 = new LevelTask(new AgilityActivity(AgilityCourse.RELLEKA_ROOFTOP), Skill.AGILITY, 90);
        agi90.setExecutionOrder(taskIndex);
        taskIndex++;
        Task agi99 = new LevelTask(new AgilityActivity(AgilityCourse.ARDOUGNE), Skill.AGILITY, 99);
        agi99.setExecutionOrder(taskIndex);

        tasks.add(tutorialIslandTask);
        //tasks.add(Fish);
        //tasks.add(Cooking);
        //tasks.add(Thieve);
        //tasks.add(ShopAxe);
        //tasks.add(agi10);
        //tasks.add(agi20);
        //tasks.add(agi30);
        //tasks.add(agi40);
        //tasks.add(RM);
        //tasks.add(Runes);
        //tasks.add(agi60);
        //tasks.add(agi70);
        //tasks.add(agi80);
        //tasks.add(agi90);
        //tasks.add(agi99);

        return tasks;
    }
}
