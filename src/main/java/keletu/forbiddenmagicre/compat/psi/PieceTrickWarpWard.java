package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import thaumcraft.common.lib.potions.PotionWarpWard;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickWarpWard extends PieceTrickPotionBase {

    public PieceTrickWarpWard(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return PotionWarpWard.instance;
    }

}
