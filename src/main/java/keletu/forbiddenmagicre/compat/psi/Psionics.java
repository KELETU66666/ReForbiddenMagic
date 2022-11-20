package keletu.forbiddenmagicre.compat.psi;

import fox.spiteful.lostmagic.Lumberjack;
import org.apache.logging.log4j.Level;
import vazkii.psi.api.PsiAPI;

public class Psionics {

    public static void oneechan(){

        try {
            try {
                PsiAPI.registerSpellPieceAndTexture("trick_warpward", PieceTrickWarpWard.class);
                PsiAPI.registerSpellPieceAndTexture("trick_deathgaze", PieceTrickDeathGaze.class);
                PsiAPI.registerSpellPieceAndTexture("trick_fluxflu", PieceTrickFluxFlu.class);
                PsiAPI.registerSpellPieceAndTexture("trick_fluxtaint", PieceTrickFluxTaint.class);
                PsiAPI.setGroupRequirements("thaumic", 25, "eidosReversal");
                PsiAPI.addPieceToGroup(PieceTrickWarpWard.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickDeathGaze.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickFluxFlu.class, "thaumic", false);
                PsiAPI.addPieceToGroup(PieceTrickFluxTaint.class, "thaumic", false);

            } catch (Throwable e) {
                Lumberjack.log(Level.INFO, e, "Schools of Magic gained too much Warp.");
            }
            try {
                PsiAPI.registerSpellPieceAndTexture("trick_genmana", PieceTrickGenMana.class);
                PsiAPI.registerSpellPieceAndTexture("trick_soulcross", PieceTrickSoulCross.class);
                PsiAPI.registerSpellPieceAndTexture("trick_allure", PieceTrickAllure.class);
                PsiAPI.registerSpellPieceAndTexture("trick_emptiness", PieceTrickEmptiness.class);
                PsiAPI.setGroupRequirements("botany", 25, "eidosReversal");
                PsiAPI.addPieceToGroup(PieceTrickGenMana.class, "botany", true);
                PsiAPI.addPieceToGroup(PieceTrickSoulCross.class, "botany", false);
                PsiAPI.addPieceToGroup(PieceTrickAllure.class, "botany", false);
                PsiAPI.addPieceToGroup(PieceTrickEmptiness.class, "botany", false);

            } catch (Throwable e) {
                Lumberjack.log(Level.INFO, e, "Schools of Magic decayed passively.");
            }
        } catch (Throwable e) {
            Lumberjack.log(Level.INFO, e, "Schools of Magic was just a weed.");
        }
    }

}
