package keletu.forbiddenmagicre.compat.psi;

import net.minecraft.potion.Potion;
import vazkii.botania.common.brew.ModPotions;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.common.spell.trick.potion.PieceTrickPotionBase;

public class PieceTrickAllure extends PieceTrickPotionBase {

    public PieceTrickAllure(Spell spell) {
        super(spell);
    }

    @Override
    public Potion getPotion() {
        return ModPotions.allure;
    }

}

