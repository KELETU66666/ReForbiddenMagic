package keletu.forbiddenmagicre.init;


import keletu.forbiddenmagicre.enchantments.inchantment.EnumInfusionEnchantmentFM;
import keletu.forbiddenmagicre.enchantments.inchantment.InfusionEnchantmentRecipeFM;
import keletu.forbiddenmagicre.util.Reference;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
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
import thaumcraft.common.items.resources.ItemCrystalEssence;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class InitRecipes {
    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void initRecipes() {
        initArcaneRecipes();
        initCrucibleRecipes();
        initInfusionRecipes();
        if(Loader.isModLoaded("botania"))
            initBotaniaRecipe();
    }

    public static ItemStack taintCrystal(Aspect asp, int quantity){
        ItemStack crystal = new ItemStack(ItemsTC.crystalEssence, quantity);
        ((ItemCrystalEssence) ItemsTC.crystalEssence).setAspects(crystal, new AspectList().add(asp, 100));
        return crystal;
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
}
private static void initInfusionRecipes() {
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_shovel"), new InfusionRecipe(
            "MORPH_TOOLS",
            new ItemStack(ModItems.MorphShovel),
            1,
            new AspectList().add(Aspect.TOOL, 30).add(Aspect.SENSES, 30).add(RegistryHandler.ENVY, 30),
            new ItemStack(ItemsTC.thaumiumShovel),
            new Object[]{
                    new ItemStack(ItemsTC.quicksilver),
                    new ItemStack(ItemsTC.nuggets, 1, 10),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(BlocksTC.logSilverwood)
            }
    ));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_axe"), new InfusionRecipe(
            "MORPH_TOOLS",
            new ItemStack(ModItems.MorphAxe),
            1,
            new AspectList().add(Aspect.TOOL, 15).add(Aspect.SENSES, 30).add(RegistryHandler.ENVY, 30).add(Aspect.AVERSION, 15),
            new ItemStack(ItemsTC.thaumiumAxe),
            new Object[]{
                    new ItemStack(ItemsTC.quicksilver),
                    new ItemStack(ItemsTC.nuggets, 1, 10),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(BlocksTC.logSilverwood)
            }
    ));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_pick"), new InfusionRecipe(
            "MORPH_TOOLS",
            new ItemStack(ModItems.MorphPickaxe),
            1,
            new AspectList().add(Aspect.TOOL, 30).add(Aspect.SENSES, 30).add(RegistryHandler.ENVY, 30),
            new ItemStack(ItemsTC.thaumiumPick),
            new Object[]{
                    new ItemStack(ItemsTC.quicksilver),
                    new ItemStack(ItemsTC.nuggets, 1, 10),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(BlocksTC.logSilverwood)
            }
    ));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "morph_sword"), new InfusionRecipe(
            "MORPH_TOOLS",
            new ItemStack(ModItems.MorphSword),
            1,
            new AspectList().add(Aspect.AVERSION, 30).add(Aspect.SENSES, 30).add(RegistryHandler.ENVY, 30),
            new ItemStack(ItemsTC.thaumiumSword),
            new Object[]{
                    new ItemStack(ItemsTC.quicksilver),
                    new ItemStack(ItemsTC.nuggets, 1, 10),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(ModItems.ResourceNS, 1, 1),
                    new ItemStack(BlocksTC.logSilverwood)
            }
    ));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "distortion_pick"), new InfusionRecipe(
            "DISTORTION_PICK",
            new ItemStack(ModItems.DISTORTIONPICK),
            1,
            new AspectList().add(Aspect.ENTROPY, 30).add(Aspect.TOOL, 30).add(Aspect.FLUX, 45),
            new ItemStack(ItemsTC.thaumiumPick),
            new Object[]{
                    new ItemStack(ItemsTC.nuggets,1 ,10),
                    taintCrystal(Aspect.FLUX, 1),
                    ThaumcraftApiHelper.makeCrystal(Aspect.ENTROPY),
                    new ItemStack(BlocksTC.logGreatwood)
            }
    ));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "skull_axe"), new InfusionRecipe(
            "SKULLAXE",
            new ItemStack(ModItems.SkullAxe),
            1,
            new AspectList().add(Aspect.AVERSION, 60).add(RegistryHandler.NETHER, 30).add(RegistryHandler.WRATH, 30),
            new ItemStack(ItemsTC.thaumiumAxe),
            new ItemStack(ModItems.ResourceNS, 1, 0),
            new ItemStack(ModItems.ResourceNS, 1, 0),
            "gemDiamond",
            new ItemStack(Items.SKULL, 1, 1)));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "arcane_cake"), new InfusionRecipe(
            "ARCANECAKE",
            new ItemStack(ModBlocks.BLOCK_ARCANE_CAKE),
            3,
             new AspectList().add(Aspect.DESIRE, 30).add(RegistryHandler.GLUTTONY, 20).add(Aspect.CRAFT, 45),
            new ItemStack(Items.CAKE),
            new ItemStack(ItemsTC.salisMundus),
            new ItemStack(Items.EGG),
            new ItemStack(ModItems.GluttonyShard),
            new ItemStack(Items.MILK_BUCKET),
            new ItemStack(Items.EGG),
            new ItemStack(ModItems.GluttonyShard)));

    ItemStack greedy = new ItemStack(Items.GOLDEN_SWORD);
    EnumInfusionEnchantmentFM.addInfusionEnchantment(greedy, EnumInfusionEnchantmentFM.GREEDY, 1);
    InfusionEnchantmentRecipeFM IEGREEDY = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.GREEDY, (new AspectList()).add(Aspect.EARTH, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 50), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), new ItemStack(BlocksTC.hungryChest), new ItemStack(Items.EMERALD));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IEGREEDY"), (InfusionRecipe)IEGREEDY);
    ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IEGREEDYFAKE"), new InfusionEnchantmentRecipeFM(IEGREEDY, greedy));

    ItemStack consuming = new ItemStack(Items.GOLDEN_PICKAXE);
    EnumInfusionEnchantmentFM.addInfusionEnchantment(consuming, EnumInfusionEnchantmentFM.CONSUMING, 1);
    InfusionEnchantmentRecipeFM IECONSUMING = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.CONSUMING, (new AspectList()).add(Aspect.FIRE, 40).add(Aspect.TOOL, 40).add(Aspect.VOID, 40), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.LAVA_BUCKET));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECONSUMING"), (InfusionRecipe)IECONSUMING);
    ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECONSUMINGFAKE"), new InfusionEnchantmentRecipeFM(IECONSUMING, consuming));

    ItemStack educational = new ItemStack(ItemsTC.voidSword);
    EnumInfusionEnchantmentFM.addInfusionEnchantment(educational, EnumInfusionEnchantmentFM.EDUCATIONAL, 1);
    InfusionEnchantmentRecipeFM IECEDU = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.EDUCATIONAL, (new AspectList()).add(Aspect.MIND, 30).add(Aspect.MAGIC, 20).add(Aspect.AVERSION, 20), new IngredientNBTTC(new ItemStack(Items.ENCHANTED_BOOK)), new ItemStack(ItemsTC.brain));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECEDU"), (InfusionRecipe)IECEDU);
    ThaumcraftApi.addFakeCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECEDUFAKE"), new InfusionEnchantmentRecipeFM(IECEDU, educational));

    ItemStack voidtouched = new ItemStack(ModItems.MorphPickaxe);
    EnumInfusionEnchantmentFM.addInfusionEnchantment(voidtouched, EnumInfusionEnchantmentFM.VOIDTOUCHED, 1);
    InfusionEnchantmentRecipeFM IECVOID = new InfusionEnchantmentRecipeFM(EnumInfusionEnchantmentFM.VOIDTOUCHED, (new AspectList()).add(Aspect.ELDRITCH, 50).add(RegistryHandler.ENVY, 30).add(Aspect.DARKNESS, 50).add(Aspect.VOID, 50), new IngredientNBTTC(new ItemStack(ItemsTC.salisMundus)), "ingotVoid", "ingotVoid", "ingotVoid", "ingotVoid", new ItemStack(ModItems.ResourceNS, 1, 1), new ItemStack(ModItems.ResourceNS, 1, 1), new ItemStack(ModItems.ResourceNS, 1, 1));
    ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("forbiddenmagicre:IECVOID"), (InfusionRecipe)IECVOID);
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
    }
}
