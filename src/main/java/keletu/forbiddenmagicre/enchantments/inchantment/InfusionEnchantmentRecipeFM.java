package keletu.forbiddenmagicre.enchantments.inchantment;

import keletu.forbiddenmagicre.items.tools.IMorph;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

import java.util.List;


public class InfusionEnchantmentRecipeFM extends InfusionEnchantmentRecipe {
    EnumInfusionEnchantment enchantment;

    public InfusionEnchantmentRecipeFM(EnumInfusionEnchantment ench, AspectList as, Object... components) {
        super(ench, as, components);
        this.enchantment = ench;
    }

    public InfusionEnchantmentRecipeFM(InfusionEnchantmentRecipeFM recipe, ItemStack in) {
        super(recipe, in);
        this.enchantment = recipe.enchantment;
    }

    @Override
    public boolean matches(List<ItemStack> input, ItemStack central, World world, EntityPlayer player) {
        if (central == null || central.isEmpty() || !ThaumcraftCapabilities.knowsResearch(player, research)) {
            return false;
        }
        if (EnumInfusionEnchantment.getInfusionEnchantmentLevel(central, enchantment) >= enchantment.maxLevel) {
            return false;
        }
        if (!enchantment.toolClasses.contains("all")) {
            boolean cool = false;
            if (!cool && central.getItem() instanceof IMorph) {
                if (this.enchantment.toolClasses.contains("morph_tools")) {
                    cool = true;
                }
            }
            if (!cool) {
                return false;
            }
        }
        return (getRecipeInput() == Ingredient.EMPTY || getRecipeInput().apply(central)) && RecipeMatcher.findMatches(input, getComponents()) != null;
    }
}