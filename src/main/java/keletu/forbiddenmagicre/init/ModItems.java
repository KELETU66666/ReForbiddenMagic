package keletu.forbiddenmagicre.init;

import keletu.forbiddenmagicre.items.ItemMobCrystal;
import keletu.forbiddenmagicre.items.food.ItemGluttonyShard;
import keletu.forbiddenmagicre.items.food.ItemTaintFurit;
import keletu.forbiddenmagicre.items.resources.ResourceFM;
import keletu.forbiddenmagicre.items.resources.ResourceNS;
import keletu.forbiddenmagicre.items.resources.ResourceTaintCharcoal;
import keletu.forbiddenmagicre.items.tools.*;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Item.ToolMaterial MATERIAL_MORPH = EnumHelper.addToolMaterial("morph", 3, 1500, 10.0F, 3.0F, 18);
public static final ItemSpade MorphShovel = new MorphShovel("morph_shovel", tab, MATERIAL_MORPH);
    public static final ItemSword MorphSword = new MorphSword("morph_sword", tab, MATERIAL_MORPH);
    public static final ItemPickaxe MorphPickaxe = new MorphPick("morph_pick", tab, MATERIAL_MORPH);
    public static final ItemAxe MorphAxe = new MorphAxe("morph_axe", tab, MATERIAL_MORPH);
    public static final ItemPickaxe DISTORTIONPICK = new DistortionPick("distortion_pick", tab, MATERIAL_MORPH);
    public static final ItemSword RIDINGCROP = new RidingCrop("riding_crop", tab, Item.ToolMaterial.WOOD);
    public static final ItemSword DIABOLISTFORK = new ItemDiabolistFork(MATERIAL_MORPH);
    public static final ItemSword SkullAxe = new SkullAxe("skull_axe", tab, MATERIAL_MORPH);
    public static final Item ResourceFM = new ResourceFM();
    public static final Item ResourceNS = new ResourceNS();
    public static final ItemFood GluttonyShard = new ItemGluttonyShard();
    public static final ItemFood TAINTED_FRUIT = new ItemTaintFurit();
    public static final Item TAINTCHARCOAL = new ResourceTaintCharcoal();
    public static final Item MOB_CRYSTAL = new ItemMobCrystal();
}