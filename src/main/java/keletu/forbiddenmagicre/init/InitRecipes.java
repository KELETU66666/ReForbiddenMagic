package keletu.forbiddenmagicre.init;


import static fox.spiteful.lostmagic.compat.Compat.getItem;
import keletu.forbiddenmagicre.LogHandler;
import keletu.forbiddenmagicre.enchantments.inchantment.EnumInfusionEnchantmentFM;
import keletu.forbiddenmagicre.enchantments.inchantment.InfusionEnchantmentRecipeFM;
import keletu.forbiddenmagicre.items.tools.ItemDragonslayer;
import keletu.forbiddenmagicre.util.CompatIsorropia;
import keletu.forbiddenmagicre.util.Reference;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.IngredientNBTTC;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class InitRecipes {
    public static final Aspect ENVY;
    public static final Aspect GLUTTONY;
    public static final Aspect LUST;
    public static final Aspect NETHER;
    public static final Aspect PRIDE;
    public static final Aspect SLOTH;
    public static final Aspect WRATH;

    static {
        if (Loader.isModLoaded("isorropia")) {
            WRATH = CompatIsorropia.WRATH;
            ENVY = CompatIsorropia.ENVY;
            GLUTTONY = CompatIsorropia.GLUTTONY;
            LUST = CompatIsorropia.LUST;
            NETHER = CompatIsorropia.NETHER;
            PRIDE = CompatIsorropia.PRIDE;
            SLOTH = CompatIsorropia.SLOTH;
        } else {
            WRATH = RegistryHandler.WRATH;
            ENVY = RegistryHandler.ENVY;
            GLUTTONY = RegistryHandler.GLUTTONY;
            LUST = RegistryHandler.LUST;
            NETHER = RegistryHandler.NETHER;
            PRIDE = RegistryHandler.PRIDE;
            SLOTH = RegistryHandler.SLOTH;
        }
    }

    private static final ResourceLocation defaultGroup = new ResourceLocation("");

    public static void initRecipes() {
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
        if (Loader.isModLoaded("botania"))
            initBotaniaRecipe();
    }

    private static void initArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "arcane_to_tainted_stone"), new ShapedArcaneRecipe(
                defaultGroup,
                "TAINTED_BLOCKS",
                50,
                new AspectList(),
                new ItemStack(ModBlocks.BLOCKSTONETAINTED, 9, 0),
                "AAA",
                "ATA",
                "AAA",
                'T', ThaumcraftApiHelper.makeCrystal(Aspect.FLUX),
                'A', new ItemStack(BlocksTC.stoneArcane)));
        
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "crystal_scribing_tools"), new ShapedArcaneRecipe(
                defaultGroup,
                "FM_CRYSTAL_SCRIBING_TOOLS",
                15,
                new AspectList(),
                new ItemStack(ModItems.CRYSTAL_WELL),
                "SWW",
                'W', ThaumcraftApiHelper.makeCrystal(Aspect.WATER),
                'S', new ItemStack(ItemsTC.scribingTools, 1, OreDictionary.WILDCARD_VALUE)));
    }

    private static void initCrucibleRecipes() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "fm_black_rose"), new CrucibleRecipe(
                "SAVE_THE_SQUID",
                new ItemStack(ModBlocks.BLACK_FLOWER_BUSH),
                new ItemStack(Blocks.DOUBLE_PLANT, 1, 4),
                new AspectList().add(Aspect.DARKNESS, 30).add(Aspect.LIFE, 30)

        ));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "fm_emerald_DUPE"), new CrucibleRecipe(
                "EMERALD_DUPE",
                new ItemStack(ModItems.ResourceFM, 4, 0),
                new ItemStack(ModItems.ResourceFM, 1, 0),
                new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.DESIRE, 8)
        ));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "fm_tainted_sapling"), new CrucibleRecipe(
                "TAINTED_BLOCKS",
                new ItemStack(ModBlocks.BLOCK_SAPLING_TAINTED),
                new ItemStack(Blocks.SAPLING, 1, 0),
                new AspectList().add(Aspect.DEATH, 30).add(Aspect.FLUX, 30)
        ));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "fm_mob_crystal"), new CrucibleRecipe(
                "WRATH_CAGE@1",
                new ItemStack(ModItems.MOB_CRYSTAL),
                new ItemStack(Items.DIAMOND),
                new AspectList().add(Aspect.MIND, 20).add(Aspect.ENERGY, 20)
        ));
    }

    private static void initInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_shovel"), new InfusionRecipe(
                "MORPH_TOOLS",
                new ItemStack(ModItems.MorphShovel),
                1,
                new AspectList().add(Aspect.TOOL, 30).add(Aspect.SENSES, 30).add(ENVY, 30),
                new ItemStack(ItemsTC.thaumiumShovel),
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_axe"), new InfusionRecipe(
                "MORPH_TOOLS",
                new ItemStack(ModItems.MorphAxe),
                1,
                new AspectList().add(Aspect.TOOL, 15).add(Aspect.SENSES, 30).add(ENVY, 30).add(Aspect.AVERSION, 15),
                new ItemStack(ItemsTC.thaumiumAxe),
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_pick"), new InfusionRecipe(
                "MORPH_TOOLS",
                new ItemStack(ModItems.MorphPickaxe),
                1,
                new AspectList().add(Aspect.TOOL, 30).add(Aspect.SENSES, 30).add(ENVY, 30),
                new ItemStack(ItemsTC.thaumiumPick),
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_sword"), new InfusionRecipe(
                "MORPH_TOOLS",
                new ItemStack(ModItems.MorphSword),
                1,
                new AspectList().add(Aspect.AVERSION, 30).add(Aspect.SENSES, 30).add(ENVY, 30),
                new ItemStack(ItemsTC.thaumiumSword),
                new ItemStack(ItemsTC.quicksilver),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(ModItems.ResourceNS, 1, 1),
                new ItemStack(BlocksTC.logSilverwood)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "distortion_pick"), new InfusionRecipe(
                "DISTORTION_PICK",
                new ItemStack(ModItems.DISTORTIONPICK),
                1,
                new AspectList().add(Aspect.ENTROPY, 30).add(Aspect.TOOL, 30).add(Aspect.FLUX, 45),
                new ItemStack(ItemsTC.thaumiumPick),
                new ItemStack(ItemsTC.nuggets, 1, 10),
                ThaumcraftApiHelper.makeCrystal(Aspect.FLUX),
                ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY),
                new ItemStack(BlocksTC.logGreatwood)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "skull_axe"), new InfusionRecipe(
                "SKULLAXE",
                new ItemStack(ModItems.SkullAxe),
                1,
                new AspectList().add(Aspect.AVERSION, 60).add(NETHER, 30).add(WRATH, 30),
                new ItemStack(ItemsTC.thaumiumAxe),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                "gemDiamond",
                new ItemStack(Items.SKULL, 1, 1)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "arcane_cake"), new InfusionRecipe(
                "FM_ARCANECAKE",
                new ItemStack(ModBlocks.BLOCK_ARCANE_CAKE),
                3,
                new AspectList().add(Aspect.DESIRE, 30).add(GLUTTONY, 20).add(Aspect.CRAFT, 45),
                new ItemStack(Items.CAKE),
                new ItemStack(ItemsTC.salisMundus),
                new ItemStack(Items.EGG),
                new ItemStack(ModItems.GluttonyShard),
                new ItemStack(Items.MILK_BUCKET),
                new ItemStack(Items.EGG),
                new ItemStack(ModItems.GluttonyShard)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "wrath_cage"), new InfusionRecipe(
                "WRATH_CAGE",
                new ItemStack(ModBlocks.WRATH_CAGE),
                10,
                new AspectList().add(RegistryHandler.WRATH, 125).add(Aspect.MAGIC, 125).add(Aspect.BEAST, 125).add(Aspect.MECHANISM, 75),
                new ItemStack(BlocksTC.metalBlockThaumium),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(BlocksTC.jarNormal),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(BlocksTC.jarNormal),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(BlocksTC.jarNormal),
                new ItemStack(Items.DIAMOND),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(BlocksTC.jarNormal)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "diabolist_fork"), new InfusionRecipe(
                "DIABOLIST_FORK",
                new ItemStack(ModItems.DIABOLISTFORK),
                1,
                new AspectList().add(RegistryHandler.NETHER, 15).add(Aspect.MECHANISM, 15).add(Aspect.ENERGY, 15),
                new ItemStack(ItemsTC.thaumiumSword),
                new ItemStack(Items.REDSTONE),
                new ItemStack(Items.QUARTZ),
                new ItemStack(Items.QUARTZ),
                new ItemStack(Items.QUARTZ)));
        try {

            Item ingot = Items.IRON_INGOT;
            Item sword = Items.IRON_SWORD;
            if (Loader.isModLoaded("twilightforest")) {
                try {
                    ingot = getItem("twilightforest", "knightmetal_ingot");
                    sword = getItem("twilightforest", "knightmetal_sword");
                } catch (Exception e) {
                    LogHandler.log(Level.INFO, e, "Forbidden Magic couldn't figure out Twilight Forest's progression.");
                }
            }
            ((ItemDragonslayer) ModItems.DRAGONSLAYER).repair = ingot;
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "dragon_slayer"), new InfusionRecipe(
                    "DRAGONSLAYER",
                    new ItemStack(ModItems.DRAGONSLAYER, 1),
                    6,
                    new AspectList().add(Aspect.LIGHT, 60).add(Aspect.ORDER, 30).add(Aspect.MAN, 60).add(Aspect.METAL, 100),
                    new ItemStack(sword),
                    new ItemStack(Items.NETHER_STAR, 1),
                    new ItemStack(Items.GOLDEN_HELMET, 1, 0),
                    new ItemStack(Items.WRITABLE_BOOK, 1),
                    new ItemStack(Items.ENDER_PEARL, 1)));
        } catch (Exception e) {
            LogHandler.log(Level.INFO, e, "Forbidden Magic wasn't unbalanced enough for Draconic Evolution.");
        }

        ItemStack greedy = new ItemStack(Items.GOLDEN_SWORD);
        EnumInfusionEnchantmentFM.addInfusionEnchantment(greedy, EnumInfusionEnchantmentFM.GREEDY, 1);
        InfusionEnchantmentRecipeFM IEGREEDY = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.GREEDY, (new AspectList()).add(Aspect.EARTH, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 50), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), new ItemStack(BlocksTC.hungryChest), new ItemStack(Items.EMERALD));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IEGREEDY"), IEGREEDY);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IEGREEDYFAKE"), new InfusionEnchantmentRecipeFM(IEGREEDY, greedy));

        ItemStack consuming = new ItemStack(Items.GOLDEN_PICKAXE);
        EnumInfusionEnchantmentFM.addInfusionEnchantment(consuming, EnumInfusionEnchantmentFM.CONSUMING, 1);
        InfusionEnchantmentRecipeFM IECONSUMING = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.CONSUMING, (new AspectList()).add(Aspect.FIRE, 40).add(Aspect.TOOL, 40).add(Aspect.VOID, 40), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.LAVA_BUCKET));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECONSUMING"), IECONSUMING);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECONSUMINGFAKE"), new InfusionEnchantmentRecipeFM(IECONSUMING, consuming));

        ItemStack educational = new ItemStack(ItemsTC.voidSword);
        EnumInfusionEnchantmentFM.addInfusionEnchantment(educational, EnumInfusionEnchantmentFM.EDUCATIONAL, 1);
        InfusionEnchantmentRecipeFM IECEDU = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.EDUCATIONAL, (new AspectList()).add(Aspect.MIND, 30).add(Aspect.MAGIC, 20).add(Aspect.AVERSION, 20), new IngredientNBTTC(new ItemStack(Items.ENCHANTED_BOOK)), new ItemStack(ItemsTC.brain));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECEDU"), IECEDU);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECEDUFAKE"), new InfusionEnchantmentRecipeFM(IECEDU, educational));

        ItemStack voidtouched = new ItemStack(ModItems.MorphPickaxe);
        EnumInfusionEnchantmentFM.addInfusionEnchantment(voidtouched, EnumInfusionEnchantmentFM.VOIDTOUCHED, 1);
        InfusionEnchantmentRecipeFM IECVOID = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.VOIDTOUCHED, (new AspectList()).add(Aspect.ELDRITCH, 50).add(ENVY, 30).add(Aspect.DARKNESS, 50).add(Aspect.VOID, 50), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), "ingotVoid", "ingotVoid", "ingotVoid", "ingotVoid", new ItemStack(ModItems.ResourceNS, 1, 1), new ItemStack(ModItems.ResourceNS, 1, 1), new ItemStack(ModItems.ResourceNS, 1, 1));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECVOID"), IECVOID);
        ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECVOIDFAKE"), new InfusionEnchantmentRecipeFM(IECVOID, voidtouched));

    }

    private static void initBotaniaRecipe() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "whisper_weed"), new ShapedArcaneRecipe(
                defaultGroup,
                "MAGICAL_FLOWER",
                100,
                new AspectList(),
                ItemBlockSpecialFlower.ofType("whisperweed"),
                "GMR",
                "Wgp",
                "BA ",
                'G', new ItemStack(Blocks.TALLGRASS, 1, 1),
                'M', "manaDiamond",
                'R', new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 6),
                'W', new ItemStack(ItemsTC.curio, 1, 5),
                'g', "petalGray",
                'p', "petalPink",
                'B', "runeEnvyB",
                'A', "gemAmber"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "astral_bloom"), new InfusionRecipe(
                "MAGICAL_FLOWER",
                ItemBlockSpecialFlower.ofType("astralbloom"),
                4,
                new AspectList().add(Aspect.LIGHT, 60).add(Aspect.AURA, 15).add(Aspect.LIFE, 40),
                new ItemStack(BlocksTC.shimmerleaf),
                new ItemStack(ItemsTC.filter),
                new ItemStack(ItemsTC.filter),
                "manaDiamond",
                new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 6),
                "petalLightBlue",
                "petalLightGray",
                "runePrideB",
                "runeAirB"));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "euclidaisy"), new InfusionRecipe(
                "MAGICAL_FLOWER",
                ItemBlockSpecialFlower.ofType("euclidaisy"),
                4,
                new AspectList().add(Aspect.ELDRITCH, 40).add(Aspect.AURA, 15).add(Aspect.PLANT, 30),
                "mysticFlowerPink",
                new ItemStack(ItemsTC.salisMundus),
                "manaPearl",
                new ItemStack(vazkii.botania.common.item.ModItems.manaResource, 1, 6),
                "petalPink",
                "petalPink",
                "runeSlothB",
                "runeGreedB"));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "tainthistle"), new InfusionRecipe(
                "MAGICAL_FLOWER",
                ItemBlockSpecialFlower.ofType("tainthistle"),
                4,
                new AspectList().add(Aspect.AIR, 30).add(Aspect.WATER, 30).add(RegistryHandler.GLUTTONY, 30).add(Aspect.FLUX, 45),
                ThaumcraftApiHelper.makeCrystal(Aspect.FLUX),
                new ItemStack(vazkii.botania.common.item.ModItems.petal, 1, 10),
                new ItemStack(vazkii.botania.common.item.ModItems.petal, 1, 10),
                new ItemStack(vazkii.botania.common.item.ModItems.petal, 1, 10),
                "runeManaB",
                "runeGluttonyB"));
    }
}