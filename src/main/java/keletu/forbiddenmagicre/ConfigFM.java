package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.common.config.Config;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
import thaumcraft.common.entities.monster.cult.EntityCultistKnight;
import thaumcraft.common.entities.monster.tainted.EntityTaintCrawler;
import thaumcraft.common.entities.monster.tainted.EntityTaintSwarm;

import java.util.HashMap;

@Config(modid = Reference.MOD_ID, category = "ReForbiddenMagic")
public class ConfigFM {

    public static HashMap<Class<? extends Entity>, String> spawnerMobs = new HashMap<Class<? extends Entity>, String>();

    @Config.LangKey("Wrath Cage Cries Havoc")
    @Config.Comment("Enable to let the Wrath Cage imprint on ANY non-boss mob.  May break your game or make your game Awesome.")
    public static boolean wrathCrazy = false;

    @Config.LangKey("Blood Magic")
    @Config.Comment("Disable to disable Blood Magic compatibility")
    public static boolean bloodMagic = true;

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

    @Config.LangKey("Silver Fish Drop Emerald Nuggets")
    @Config.Comment("forbiddenmagic.configfm.silverfish.comment")
    public static boolean SilverFish = true;

    public static void spawnilify() {
        spawnerMobs.put(EntityZombie.class, "exanimis");
        spawnerMobs.put(EntitySkeleton.class, "mortuus");
        spawnerMobs.put(EntityCreeper.class, "ignis");
        spawnerMobs.put(EntityHorse.class, "bestia");
        spawnerMobs.put(EntityPig.class, "bestia");
        spawnerMobs.put(EntitySheep.class, "fabrico");
        spawnerMobs.put(EntityCow.class, "bestia");
        spawnerMobs.put(EntityMooshroom.class, "herba");
        spawnerMobs.put(EntityOcelot.class, "bestia");
        spawnerMobs.put(EntityChicken.class, "volatus");
        spawnerMobs.put(EntitySquid.class, "sensus");
        spawnerMobs.put(EntityWolf.class, "bestia");
        spawnerMobs.put(EntityBat.class, "volatus");
        spawnerMobs.put(EntitySpider.class, "fabrico");
        spawnerMobs.put(EntitySlime.class, "alkimia");
        spawnerMobs.put(EntityGhast.class, "infernus");
        spawnerMobs.put(EntityPigZombie.class, "desiderium");
        spawnerMobs.put(EntityEnderman.class, "alienis");
        spawnerMobs.put(EntityCaveSpider.class, "mortuus");
        spawnerMobs.put(EntitySilverfish.class, "desiderium");
        spawnerMobs.put(EntityBlaze.class, "ignis");
        spawnerMobs.put(EntityMagmaCube.class, "ignis");
        spawnerMobs.put(EntityWitch.class, "praecantatio");
        spawnerMobs.put(EntityVillager.class, "desiderium");
        spawnerMobs.put(EntityFireBat.class, "ignis");
        spawnerMobs.put(EntityWisp.class, "auram");
        spawnerMobs.put(EntityThaumicSlime.class, "vitium");
        spawnerMobs.put(EntityBrainyZombie.class, "cognitio");
        spawnerMobs.put(EntityGiantBrainyZombie.class, "cognitio");
        spawnerMobs.put(EntityTaintCrawler.class, "vitium");
        spawnerMobs.put(EntityTaintSwarm.class, "vitium");
        spawnerMobs.put(EntityCultistKnight.class, "alienis");
        spawnerMobs.put(EntityCultistCleric.class, "alienis");
        spawnerMobs.put(EntityEldritchCrab.class, "alienis");
        spawnerMobs.put(EntityInhabitedZombie.class, "alienis");
        spawnerMobs.put(EntityPech.class, "desiderium");
        spawnerMobs.put(EntityEldritchGuardian.class, "alienis");
        spawnerMobs.put(EntityWitherSkeleton.class, "mortuus");
        // spawnerMobs.put("Taintacle", DarkAspects.LUST);*/
    }
}
