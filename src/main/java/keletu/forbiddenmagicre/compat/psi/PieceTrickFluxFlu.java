package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import thaumcraft.api.potions.PotionVisExhaust;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickFluxFlu extends PieceTrickPotionBase {

    public PieceTrickFluxFlu(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return PotionVisExhaust.instance;
    }
}
