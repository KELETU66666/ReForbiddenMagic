package keletu.forbiddenmagicre.proxy;

import keletu.forbiddenmagicre.blocks.tiles.TileEntityWrathCage;
import keletu.forbiddenmagicre.blocks.tiles.TileEntityWrathCageRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void registerItemRenderer( Item item, int meta, String id )
    {
        ModelLoader.setCustomModelResourceLocation( item, meta, new ModelResourceLocation( item.getRegistryName(), id ) );
    }

    @Override
    public void registerRenderInfo() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWrathCage.class, new TileEntityWrathCageRenderer());
    }

    public void registerDisplayInformationInit() {
        setupTileRenderers();
    }

    public void setupTileRenderers() {
    }

    public void preInit( FMLPreInitializationEvent event )
    {
        super.preInit( event );
    }


    public void init( FMLInitializationEvent event )
    {

    }
}
