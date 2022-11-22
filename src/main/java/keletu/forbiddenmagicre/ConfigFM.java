package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID, category = "ReForbiddenMagic")
public class ConfigFM {


        @Config.LangKey("forbiddenmagic.configfm.trashlist")
        @Config.Comment("forbiddenmagic.configfm.trashlist.comment")
        public static String trashlist = "dirt;sand;gravel;cobblestone;netherrack";
        public static String[] trashpile = trashlist.split(";");

        @Config.LangKey("Heretic Villager's ID")
        @Config.Comment("forbiddenmagic.configfm.hereticid.comment")
        public static int hereticID = 666;
}
