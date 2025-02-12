package keletu.forbiddenmagicre.enchantments.inchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.InfusionRecipe;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class InfusionEnchantmentRecipeFM extends InfusionRecipe {
    Enchantment enchantment;

    public InfusionEnchantmentRecipeFM(Enchantment ench, AspectList as, Object... components) {
        super("INFUSIONENCHANTMENTFM", null, 4, as, Ingredient.EMPTY, components);
        this.enchantment = ench;
    }

    public InfusionEnchantmentRecipeFM(InfusionEnchantmentRecipeFM recipe, ItemStack in) {
        super("INFUSIONENCHANTMENTFM", null, recipe.instability, recipe.aspects, in, recipe.components.toArray());
        this.enchantment = recipe.enchantment;
    }

    @Override
    public boolean matches(List<ItemStack> input, ItemStack central, World world, EntityPlayer player) {
        if (central == null || central.isEmpty() || !ThaumcraftCapabilities.knowsResearch(player, this.research)) {
            return false;
        }
        if (EnchantmentHelper.getEnchantmentLevel(this.enchantment, central) >= this.enchantment.getMaxLevel()) {
            return false;
        }
        if (!enchantment.canApply(central))
            return false;
        return (this.getRecipeInput() == Ingredient.EMPTY || this.getRecipeInput().apply(central)) && RecipeMatcher.findMatches(input, this.getComponents()) != null;
    }

    @Override
    public Object getRecipeOutput(EntityPlayer player, ItemStack input, List<ItemStack> comps) {
        if (input == null)
            return null;
        ItemStack out = input.copy();
        int cl = EnchantmentHelper.getEnchantmentLevel(this.enchantment, out);
        if (cl >= this.enchantment.getMaxLevel())
            return null;
        Map<Enchantment, Integer> el = EnchantmentHelper.getEnchantments(input);
        Random rand = new Random(System.nanoTime());
        if (rand.nextInt(10) < el.size()) {
            int base = 1;
            if (input.hasTagCompound()) {
                assert input.getTagCompound() != null;
                base += input.getTagCompound().getByte("TC.WARP");
            }
            out.setTagInfo("TC.WARP", new NBTTagByte((byte) base));
        }
        EnumInfusionEnchantmentFM.addInfusionEnchantment(out, this.enchantment, cl + 1);
        return out;
    }

    @Override
    public AspectList getAspects(EntityPlayer player, ItemStack input, List<ItemStack> comps) {
        AspectList out = new AspectList();
        if (input == null || input.isEmpty())
            return out;
        int cl = EnchantmentHelper.getEnchantmentLevel(this.enchantment, input) + 1;
        if (cl > this.enchantment.getMaxLevel())
            return out;
        for (Aspect a : this.getAspects().getAspects()) {
            out.add(a, (int) (this.getAspects().getAmount(a) * (float) cl));
        }
        return out;
    }
}