package keletu.forbiddenmagicre.compat.psi;

import fox.spiteful.lostmagic.Lumberjack;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;

import static vazkii.psi.api.PsiAPI.registerSpellPiece;
import static vazkii.psi.api.PsiAPI.simpleSpellTextures;

public class Psionics {

    private static void registerSpellPieceAndTexture(String key, Class<? extends SpellPiece> clazz) {
        registerSpellPiece(key, clazz);
        String textureName = key.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
        if(Loader.isModLoaded("magipsi")) {
            simpleSpellTextures.put(key, new ResourceLocation(Reference.MOD_ID, String.format("textures/spell/magipsi/%s.png", textureName)));
        }else
            simpleSpellTextures.put(key, new ResourceLocation(Reference.MOD_ID, String.format("textures/spell/%s.png", textureName)));
    }

    public static void oneechan(){

        try {
            try {
                registerSpellPieceAndTexture("trick_warpward", PieceTrickWarpWard.class);
                registerSpellPieceAndTexture("trick_deathgaze", PieceTrickDeathGaze.class);
                registerSpellPieceAndTexture("trick_fluxflu", PieceTrickFluxFlu.class);
                registerSpellPieceAndTexture("trick_fluxtaint", PieceTrickFluxTaint.class);
                registerSpellPieceAndTexture("trick_siphonaura", PieceTrickSiphonAura.class);
                PsiAPI.setGroupRequirements("thaumic", 25, "eidosReversal");
                PsiAPI.addPieceToGroup(PieceTrickWarpWard.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickDeathGaze.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickFluxFlu.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickSiphonAura.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickFluxTaint.class, "thaumic", false);

            } catch (Throwable e) {
                Lumberjack.log(Level.INFO, e, "Schools of Magic gained too much Warp.");
            }
            try {
                if(Loader.isModLoaded("botania")) {
                    registerSpellPieceAndTexture("trick_genmana", PieceTrickGenMana.class);
                    registerSpellPieceAndTexture("trick_soulcross", PieceTrickSoulCross.class);
                    registerSpellPieceAndTexture("trick_allure", PieceTrickAllure.class);
                    registerSpellPieceAndTexture("trick_emptiness", PieceTrickEmptiness.class);
                    PsiAPI.setGroupRequirements("botany", 25, "eidosReversal");
                    PsiAPI.addPieceToGroup(PieceTrickGenMana.class, "botany", true);
                    PsiAPI.addPieceToGroup(PieceTrickSoulCross.class, "botany", false);
                    PsiAPI.addPieceToGroup(PieceTrickAllure.class, "botany", false);
                    PsiAPI.addPieceToGroup(PieceTrickEmptiness.class, "botany", false);
                }
            } catch (Throwable e) {
                Lumberjack.log(Level.INFO, e, "Schools of Magic decayed passively.");
            }
        } catch (Throwable e) {
            Lumberjack.log(Level.INFO, e, "Schools of Magic was just a weed.");
        }
    }

}
