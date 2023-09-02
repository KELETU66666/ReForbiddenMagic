package keletu.forbiddenmagicre.compat.bloodmagic;

import WayofTime.bloodmagic.altar.AltarTier;
import WayofTime.bloodmagic.api.BloodMagicPlugin;
import WayofTime.bloodmagic.api.IBloodMagicPlugin;
import WayofTime.bloodmagic.api.IBloodMagicRecipeRegistrar;
import net.minecraftforge.oredict.OreIngredient;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@BloodMagicPlugin
public class BMRecipes implements IBloodMagicPlugin {
    @Override
    public void registerRecipes(IBloodMagicRecipeRegistrar recipeRegistrar) {
        recipeRegistrar.addBloodAltar(new OreIngredient("mysticFlowerRed"), ItemBlockSpecialFlower.ofType("bloodthorn"), AltarTier.FOUR.ordinal(), 15000, 50, 25);
    }
}
