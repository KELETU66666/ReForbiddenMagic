package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.compat.botania.flowers.*;
import keletu.forbiddenmagicre.init.InitRecipes;
import keletu.forbiddenmagicre.init.InitResearch;
import keletu.forbiddenmagicre.init.InitVanillaRecipes;
import keletu.forbiddenmagicre.proxy.CommonProxy;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.psi.api.PsiAPI;

import java.util.ArrayList;
import java.util.List;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION
)
public class forbiddenmagicre {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    /** This is the instance of your mod as created by Forge. It will never be null. */
    @Mod.Instance(Reference.MOD_ID)
    public static forbiddenmagicre INSTANCE;
    public static List<SubTileFlower> subTiles = new ArrayList<>();

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        registerFlowers();
        keletu.forbiddenmagicre.compat.psi.Psionics.oneechan();
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        InitRecipes.initRecipes();
        InitResearch.registerResearch();
        InitVanillaRecipes.init();
    proxy.registerDisplayInformationInit();
    }

    private void registerFlowers() {
        addSubTile("astralbloom", SubTileAstralBloom.class);
        addSubTile("euclidaisy", SubTileEuclidaisy.class);
        addSubTile("mindlotus", SubTileMindLotus.class);
        addSubTile("whisperweed", SubTileWhisperweed.class);
    }
    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    private void addSubTile(String key, Class<? extends SubTileEntity> subTileClass) {
        subTiles.add(new SubTileFlower(key, subTileClass));
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