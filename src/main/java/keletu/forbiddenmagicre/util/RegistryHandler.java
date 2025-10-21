package keletu.forbiddenmagicre.util;

import keletu.forbiddenmagicre.blocks.tiles.TileEntityWrathCage;
import keletu.forbiddenmagicre.compat.bloodmagic.ItemBloodRapier;
import keletu.forbiddenmagicre.compat.bloodmagic.ItemScribeBlood;
import keletu.forbiddenmagicre.compat.psi.ItemAmuletPsi;
import keletu.forbiddenmagicre.compat.psi.ItemExtraColorizer;
import keletu.forbiddenmagicre.enchantments.EnchantmentWrath;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.potions.PotionBloodSeal;
import keletu.forbiddenmagicre.potions.PotionDragonwrack;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.*;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static final Aspect ENVY;
    public static final Aspect GLUTTONY;
    public static final Aspect LUST;
    public static final Aspect NETHER;
    public static final Aspect PRIDE;
    public static final Aspect SLOTH;
    public static final Aspect WRATH;

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
        if (Loader.isModLoaded("psi")) {
            event.getRegistry().registerAll(new ItemExtraColorizer());
            event.getRegistry().registerAll(new ItemAmuletPsi());
        }
        if (Loader.isModLoaded("bloodmagic")) {
            event.getRegistry().registerAll(new ItemBloodRapier());
            event.getRegistry().registerAll(new ItemScribeBlood());
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityWrathCage.register("wrath_cage", TileEntityWrathCage.class);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(bloodSeal, dragonwrack);
    }

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
            ENVY = new Aspect("invidia", 0x00ba00, new Aspect[]{Aspect.SENSES, Aspect.DESIRE}, new ResourceLocation("forbiddenmagicre", "textures/aspects/invidia.png"), 1);

            GLUTTONY = new Aspect("gula", 0xd59c46, new Aspect[]{Aspect.DESIRE, Aspect.VOID}, new ResourceLocation("forbiddenmagicre", "textures/aspects/gula.png"), 1);

            LUST = new Aspect("luxuria", 0xffc1ce, new Aspect[]{Aspect.BEAST, Aspect.DESIRE}, new ResourceLocation("forbiddenmagicre", "textures/aspects/luxuria.png"), 1);

            NETHER = new Aspect("infernus", 0xff0000, new Aspect[]{Aspect.FIRE, Aspect.MAGIC}, new ResourceLocation("forbiddenmagicre", "textures/aspects/infernus.png"), 771);

            PRIDE = new Aspect("superbia", 0x9639ff, new Aspect[]{Aspect.FLIGHT, Aspect.VOID}, new ResourceLocation("forbiddenmagicre", "textures/aspects/superbia.png"), 1);

            SLOTH = new Aspect("desidia", 0x6e6e6e, new Aspect[]{Aspect.TRAP, Aspect.SOUL}, new ResourceLocation("forbiddenmagicre", "textures/aspects/desidia.png"), 771);

            WRATH = new Aspect("ira", 0x870404, new Aspect[]{Aspect.AVERSION, Aspect.FIRE}, new ResourceLocation("forbiddenmagicre", "textures/aspects/ira.png"), 771);
        }
    }

    @SubscribeEvent
    public static void registerAspects(AspectRegistryEvent event) {
        AspectEventProxy proxy = event.register;
        proxy.registerComplexObjectTag(new ItemStack(ModBlocks.BLACK_FLOWER), new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.SENSES, 2).add(Aspect.PLANT, 2).add(Aspect.LIFE, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 0), new AspectList().add(NETHER, 2).add(WRATH, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 1), new AspectList().add(NETHER, 2).add(ENVY, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 2), new AspectList().add(NETHER, 2).add(PRIDE, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 3), new AspectList().add(NETHER, 2).add(LUST, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 4), new AspectList().add(NETHER, 2).add(SLOTH, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceNS, 1, 5), new AspectList().add(NETHER, 2).add(Aspect.DESIRE, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.GluttonyShard), new AspectList().add(NETHER, 2).add(GLUTTONY, 2).add(Aspect.CRYSTAL, 2));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.TAINTED_FRUIT), new AspectList().add(Aspect.PLANT, 5).add(Aspect.FLUX, 5));
        proxy.registerComplexObjectTag(new ItemStack(ModBlocks.BLOCK_LOG_TAINTED), new AspectList().add(Aspect.PLANT, 20).add(Aspect.FLUX, 5));
        proxy.registerComplexObjectTag(new ItemStack(ModBlocks.BLOCK_LEAVES_TAINTED), new AspectList().add(Aspect.PLANT, 3).add(Aspect.FLUX, 1));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.TAINTCHARCOAL), new AspectList().add(Aspect.FIRE, 5).add(Aspect.ENERGY, 5).add(Aspect.FLUX, 3));
        proxy.registerComplexObjectTag(new ItemStack(ModItems.ResourceFM, 1, 1), new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.SENSES, 2));

        proxy.registerObjectTag(new ItemStack(Items.GHAST_TEAR), AspectHelper.getObjectAspects(new ItemStack(Items.GHAST_TEAR)).add(ENVY, 5));
        proxy.registerObjectTag(new ItemStack(Blocks.TNT), AspectHelper.getObjectAspects(new ItemStack(Blocks.TNT)).add(WRATH, 15));
        proxy.registerObjectTag(new ItemStack(Items.FIRE_CHARGE), AspectHelper.getObjectAspects(new ItemStack(Items.FIRE_CHARGE)).add(WRATH, 2));
        proxy.registerObjectTag(new ItemStack(Items.SKULL, 1, 4), AspectHelper.getObjectAspects(new ItemStack(Items.SKULL, 1, 4)).add(WRATH, 5));
        proxy.registerObjectTag(new ItemStack(Items.SADDLE), AspectHelper.getObjectAspects(new ItemStack(Items.SADDLE)).add(LUST, 5));
        proxy.registerObjectTag(new ItemStack(Items.GOLDEN_SWORD, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.GOLDEN_SWORD, 1, 32767)).add(PRIDE, 8));
        proxy.registerObjectTag(new ItemStack(Items.GOLDEN_HELMET, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.GOLDEN_HELMET, 1, 32767)).add(PRIDE, 8));
        proxy.registerObjectTag(new ItemStack(Items.GOLDEN_CHESTPLATE, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.GOLDEN_CHESTPLATE, 1, 32767)).add(PRIDE, 4));
        proxy.registerObjectTag(new ItemStack(Items.GOLDEN_LEGGINGS, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.GOLDEN_LEGGINGS, 1, 32767)).add(PRIDE, 4));
        proxy.registerObjectTag(new ItemStack(Items.GOLDEN_BOOTS, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.GOLDEN_BOOTS, 1, 32767)).add(PRIDE, 4));
        proxy.registerObjectTag(new ItemStack(Items.NETHER_STAR), AspectHelper.getObjectAspects(new ItemStack(Items.NETHER_STAR)).add(PRIDE, 10).add(NETHER, 20));
        proxy.registerObjectTag(new ItemStack(Items.LEAD), AspectHelper.getObjectAspects(new ItemStack(Items.LEAD)).add(LUST, 5));
        proxy.registerObjectTag(new ItemStack(Items.BED, 1, 32767), AspectHelper.getObjectAspects(new ItemStack(Items.BED, 1, 32767)).add(SLOTH, 10));
        proxy.registerObjectTag(new ItemStack(Items.ENDER_PEARL), AspectHelper.getObjectAspects(new ItemStack(Items.ENDER_PEARL)).add(ENVY, 5));
        proxy.registerObjectTag(new ItemStack(Items.COMPARATOR), AspectHelper.getObjectAspects(new ItemStack(Items.COMPARATOR)).add(ENVY, 10));
        proxy.registerObjectTag(new ItemStack(Items.CAKE), AspectHelper.getObjectAspects(new ItemStack(Items.CAKE)).add(GLUTTONY, 10));
        proxy.registerObjectTag(new ItemStack(Items.COOKIE), AspectHelper.getObjectAspects(new ItemStack(Items.COOKIE)).add(GLUTTONY, 1));

        proxy.registerObjectTag(new ItemStack(Blocks.NETHERRACK), AspectHelper.getObjectAspects(new ItemStack(Blocks.NETHERRACK)).add(NETHER, 2));
        proxy.registerObjectTag(new ItemStack(Blocks.QUARTZ_ORE), AspectHelper.getObjectAspects(new ItemStack(Blocks.QUARTZ_ORE)).add(NETHER, 5));
        proxy.registerObjectTag(new ItemStack(Items.NETHER_WART), AspectHelper.getObjectAspects(new ItemStack(Items.NETHER_WART)).add(NETHER, 2));
        proxy.registerObjectTag(new ItemStack(Items.SKULL, 1, 1), AspectHelper.getObjectAspects(new ItemStack(Items.SKULL, 1, 1)).add(NETHER, 10));
    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().register(new EnchantmentWrath());
    }

    @SubscribeEvent
    public static void OreRegister(RegistryEvent.Register<Enchantment> event) {
        OreDictionary.registerOre("dyeBlack", new ItemStack(ModItems.ResourceFM, 1, 1));
        OreDictionary.registerOre("nuggetEmerald", new ItemStack(ModItems.ResourceFM, 1, 0));
        OreDictionary.registerOre("blockNetherStar", new ItemStack(ModBlocks.BLOCK_NETHER_STAR, 1, 0));
    }

    public static final PotionBloodSeal bloodSeal = new PotionBloodSeal();
    public static final PotionDragonwrack dragonwrack = new PotionDragonwrack();
}
