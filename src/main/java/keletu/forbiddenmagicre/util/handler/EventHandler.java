package keletu.forbiddenmagicre.util.handler;

import keletu.forbiddenmagicre.event.LivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new LivingEvent());
    }
}
