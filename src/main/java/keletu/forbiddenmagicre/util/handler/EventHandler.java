package keletu.forbiddenmagicre.util.handler;

import keletu.forbiddenmagicre.compat.botania.RegisterHandlerBota;
import keletu.forbiddenmagicre.event.LivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class EventHandler {
    public static void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new LivingEvent());
        if(Loader.isModLoaded("botania"))
            MinecraftForge.EVENT_BUS.register(RegisterHandlerBota.class);
    }
}
