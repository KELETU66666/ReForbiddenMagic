package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import thaumcraft.common.lib.potions.PotionDeathGaze;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellCompilationException;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickDeathGaze extends PieceTrickPotionBase {

    public PieceTrickDeathGaze(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return PotionDeathGaze.instance;
    }

    /*@Override
    public int getPotency(int power, int time) throws SpellCompilationException {
        return (int)multiplySafe(super.getPotency(power, time), 4);
    }*/
}
