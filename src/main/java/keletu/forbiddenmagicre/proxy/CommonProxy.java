package keletu.forbiddenmagicre.proxy;

import keletu.forbiddenmagicre.util.handler.EventHandler;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void registerItemRenderer( Item item, int meta, String id )
    {
    }


    public void preInit( FMLPreInitializationEvent event )
    {
        EventHandler.registerEvents();
    }

    public void registerDisplayInformationInit() {}

    public void init( FMLInitializationEvent event )
    {
    }

    public void registerRenderInfo() {
    }
}
