package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Config(modid = Reference.MOD_ID, category = "")
public class ConfigFM {


    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        private EventHandler() {
        }

        public static ArrayList<String> trash = new ArrayList<String>();

        String trashlist = "dirt;sand;gravel;cobblestone;netherrack";
        String[] trashpile = trashlist.split(";");{
            for(String garbage : trashpile) {
                trash.add(garbage);
            }
        }

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

    @Config.LangKey("forbiddenmagic.configfm.langkey")
    @Config.Comment("forbiddenmagic.configfm.comment")
    public static final configfm configfm = new configfm();

    public static class configfm {
        @Config.LangKey("forbiddenmagic.configfm.enablecrystalcaster")
        @Config.Comment("forbiddenmagic.configfm.enablecrystalcaster.comment")
        public String trashlist = "dirt;sand;gravel;cobblestone;netherrack";
        public String[] trashpile = trashlist.split(";");
    }
}
