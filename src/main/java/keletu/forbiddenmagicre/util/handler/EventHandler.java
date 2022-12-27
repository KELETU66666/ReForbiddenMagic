package keletu.forbiddenmagicre.util.handler;

import keletu.forbiddenmagicre.compat.botania.RegisterHandlerBota;
import keletu.forbiddenmagicre.event.BMEvent;
import keletu.forbiddenmagicre.event.LivingEvent;
import keletu.forbiddenmagicre.util.CompatIsorropia;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class EventHandler {
    public static void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new LivingEvent());
        if(Loader.isModLoaded("bloodmagic"))
            MinecraftForge.EVENT_BUS.register(new BMEvent());
        if(Loader.isModLoaded("botania"))
            MinecraftForge.EVENT_BUS.register(RegisterHandlerBota.class);
        if(Loader.isModLoaded("isorropia"))
            MinecraftForge.EVENT_BUS.register(CompatIsorropia.class);
    }
}
