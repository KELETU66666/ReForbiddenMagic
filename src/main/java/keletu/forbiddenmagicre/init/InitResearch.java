package keletu.forbiddenmagicre.init;

import keletu.forbiddenmagicre.ConfigFM;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import thaumcraft.api.ThaumcraftApi;

public class InitResearch {

    public static void registerResearch() {

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/research.json"));
        if(Loader.isModLoaded("botania"))
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/bota.json"));
        if(Loader.isModLoaded("psi"))
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/psi.json"));
        if(Loader.isModLoaded("bloodmagic") && ConfigFM.bloodMagic)
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/bloodmagic.json"));
        if(Loader.isModLoaded("oldresearch"))
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/tc4research.json"));
    }
}
