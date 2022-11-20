package keletu.forbiddenmagicre.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

public class InitResearch {

    public static void registerResearch() {

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/research.json"));
        if(Loader.isModLoaded("botania"))
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/bota.json"));
        if(Loader.isModLoaded("psi"))
            ThaumcraftApi.registerResearchLocation(new ResourceLocation("forbiddenmagicre", "research/psi.json"));
    }
}
