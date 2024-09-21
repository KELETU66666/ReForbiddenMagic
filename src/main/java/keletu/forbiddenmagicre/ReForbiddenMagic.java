package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.compat.botania.RegisterHandlerBota;
import static keletu.forbiddenmagicre.compat.botania.RegisterHandlerBota.registerFlowers;
import keletu.forbiddenmagicre.compat.lostmagic.ItemIDFixer;
import keletu.forbiddenmagicre.init.InitRecipes;
import keletu.forbiddenmagicre.init.InitResearch;
import keletu.forbiddenmagicre.init.InitVanillaRecipes;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.proxy.CommonProxy;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
         dependencies = "required-after:thaumcraft;after:bloodmagic"
)
public class ReForbiddenMagic {

    public static CreativeTabs tab = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ResourceNS, 1, 0);
        }
    };

    public static CreativeTabs TabCrystal = new ItemTabCrystal();
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    /** This is the instance of your mod as created by Forge. It will never be null. */
    @Mod.Instance(Reference.MOD_ID)
    public static ReForbiddenMagic INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        ConfigFM.spawnilify();
        if(Loader.isModLoaded("botania"))
            registerFlowers();
        if(Loader.isModLoaded("psi"))
            keletu.forbiddenmagicre.compat.psi.Psionics.oneechan();
        proxy.registerRenderInfo();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ResearchCategories.registerCategory("APOCRYPHA", "FIRSTSTEPS", new AspectList().add(Aspect.LIGHT, 5).add(Aspect.ELDRITCH, 5).add(Aspect.AURA, 5).add(Aspect.MAGIC, 5).add(Aspect.DARKNESS, 5).add(Aspect.FLUX, 3).add(Aspect.MIND, 5), new ResourceLocation(Reference.MOD_ID, "textures/misc/forbidden.png"), new ResourceLocation(Reference.MOD_ID, "textures/misc/runecircle2.png"));

        InitRecipes.initRecipes();
        InitResearch.registerResearch();
        InitVanillaRecipes.init();
        if(Loader.isModLoaded("botania"))
            RegisterHandlerBota.lexify();

        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(Reference.MOD_ID, 1);
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemIDFixer());

        proxy.registerDisplayInformationInit();
    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(Reference.MOD_ID)
    public static class Blocks {
      /*
          public static final MySpecialBlock mySpecialBlock = null; // placeholder for special block below
      */
    }

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(Reference.MOD_ID)
    public static class Items {
      /*
          public static final ItemBlock mySpecialBlock = null; // itemblock for the block above
          public static final MySpecialItem mySpecialItem = null; // placeholder for special item below
      */
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
       /** Listen for the register event for creating custom items */
       @SubscribeEvent
       public static void addItems(RegistryEvent.Register<Item> event) {

           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            */
       }
       /** Listen for the register event for creating custom blocks */
       @SubscribeEvent
       public static void addBlocks(RegistryEvent.Register<Block> event) {
       }
    }

    }
    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
    public static class MySpecialItem extends Item {

    }

    public static class MySpecialBlock extends Block {

    }
    */
