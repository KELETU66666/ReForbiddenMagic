package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;

@Config(modid = Reference.MOD_ID, category = "ReForbiddenMagic")
public class ConfigFM {

        public static HashMap<String, String> spawnerMobs = new HashMap<String, String>();

        @Config.LangKey("Wrath Cage Cries Havoc")
        @Config.Comment("Enable to let the Wrath Cage imprint on ANY non-boss mob.  May break your game or make your game Awesome.")
        public static boolean wrathCrazy = false;

        @Config.LangKey("Wrath Cage Fuel Cost")
        @Config.Comment("Cost of essentia per round of spawns in the Wrath Cage.  Raise to increase essentia costs.  Defaults to 5.  Set to 0 to remove the need to fuel the Wrath Cage.  Setting the cost above 64 is not recommended.")
        public static int wrathCost = 20;

        @Config.LangKey("Wrath Cage Fuel Efficiency")
        @Config.Comment("Number of spawns a Wrath Cage can get per fuel cost.  Defaults to 4.  Lower to make the cage less efficient and raise to make it more efficient.")
        public static int wrathEff = 4;

        @Config.LangKey("forbiddenmagic.configfm.trashlist")
        @Config.Comment("forbiddenmagic.configfm.trashlist.comment")
        public static String trashlist = "dirt;sand;gravel;cobblestone;netherrack";
        public static String[] trashpile = trashlist.split(";");

        @Config.LangKey("Heretic Villager's ID")
        @Config.Comment("forbiddenmagic.configfm.hereticid.comment")
        public static int hereticID = 666;

        @Config.LangKey("BloodSeal's Potion ID")
        @Config.Comment("forbiddenmagic.configfm.bloodsealid.comment")
        public static int BloodSealID = 70;

        @Config.LangKey("Silver Fish Drop Emerald Nuggets")
        @Config.Comment("forbiddenmagic.configfm.silverfish.comment")
        public static boolean SilverFish = true;

        public static void spawnilify() {
                spawnerMobs.put("Zombie", "exanimis");
                spawnerMobs.put("Skeleton", "mortuus");
                spawnerMobs.put("Creeper", "ignis");
                spawnerMobs.put("EntityHorse", "bestia");
                spawnerMobs.put("Pig", "bestia");
                spawnerMobs.put("Sheep", "fabrico");
                spawnerMobs.put("Cow", "bestia");
                spawnerMobs.put("Mooshroom", "herba");
                spawnerMobs.put("Ocelot", "bestia");
                spawnerMobs.put("Chicken", "volatus");
                spawnerMobs.put("Squid", "sensus");
                spawnerMobs.put("Wolf", "bestia");
                spawnerMobs.put("Bat", "volatus");
                spawnerMobs.put("Spider", "fabrico");
                spawnerMobs.put("Slime", "alkimia");
                spawnerMobs.put("Ghast", "infernus");
                spawnerMobs.put("Zombie_Pigman", "desiderium");
                spawnerMobs.put("Enderman", "alienis");
                spawnerMobs.put("CaveSpider", "mortuus");
                spawnerMobs.put("Silverfish", "desiderium");
                spawnerMobs.put("Blaze", "ignis");
                spawnerMobs.put("Magma_Cube", "ignis");
                spawnerMobs.put("Witch", "praecantatio");
                spawnerMobs.put("Villager", "desiderium");
                spawnerMobs.put("Thaumcraft:Firebat", "ignis");
                spawnerMobs.put("Thaumcraft:Wisp", "auram");
                spawnerMobs.put("Thaumcraft:ThaumSlime", "vitium");
                spawnerMobs.put("Thaumcraft:BrainyZombie", "cognitio");
                spawnerMobs.put("Thaumcraft:GiantBrainyZombie", "cognitio");
                spawnerMobs.put("Thaumcraft:taintCrawler", "vitium");
                spawnerMobs.put("Thaumcraft:TaintSwarm", "vitium");
                spawnerMobs.put("Thaumcraft:CultistKnight", "alienis");
                spawnerMobs.put("Thaumcraft:CultistCleric", "alienis");
                spawnerMobs.put("Thaumcraft:EldritchCrab", "alienis");
                spawnerMobs.put("Thaumcraft:InhabitedZombie", "alienis");
                spawnerMobs.put("Thaumcraft:Pech", "desiderium");
                spawnerMobs.put("Thaumcraft:EldritchGuardian", "alienis");
                // spawnerMobs.put("Taintacle", DarkAspects.LUST);
        }
}
