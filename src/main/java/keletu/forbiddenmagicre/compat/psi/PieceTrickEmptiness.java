package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import vazkii.botania.common.brew.ModPotions;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellCompilationException;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickEmptiness extends PieceTrickPotionBase {

    public PieceTrickEmptiness(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return ModPotions.emptiness;
    }

    @Override
    public int getPotency(int power, int time) throws SpellCompilationException {
        return super.getPotency(power, time) * 2;
    }
}
