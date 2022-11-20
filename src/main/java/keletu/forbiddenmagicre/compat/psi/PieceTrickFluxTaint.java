package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import thaumcraft.api.potions.PotionFluxTaint;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellCompilationException;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickFluxTaint extends PieceTrickPotionBase {

    public PieceTrickFluxTaint(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return PotionFluxTaint.instance;
    }

    @Override
    public int getPotency(int power, int time) throws SpellCompilationException {
        return super.getPotency(power, time) * 2;
    }
}
