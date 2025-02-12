package keletu.forbiddenmagicre.enchantments.inchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Set;

public class EnumInfusionEnchantmentFM {

    public Set<String> toolClasses;
    public int maxLevel;
    public String research;

    EnumInfusionEnchantmentFM(Set<String> toolClasses, int ml, String research) {
        this.toolClasses = toolClasses;
        this.maxLevel = ml;
        this.research = research;
    }

    public static NBTTagList getInfusionEnchantmentTagList(ItemStack stack) {
        return stack == null || stack.isEmpty() || stack.getTagCompound() == null ? null : stack.getTagCompound().getTagList("ench", 10);
    }

    public static void addInfusionEnchantment(ItemStack stack, Enchantment ie, int level) {
        if (stack == null || stack.isEmpty() || level > ie.getMaxLevel()) {
            System.out.println("uwu");
            return;
        }
        NBTTagList nbttaglist = EnumInfusionEnchantmentFM.getInfusionEnchantmentTagList(stack);
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if (k != Enchantment.getEnchantmentID(ie))
                    continue;
                if (level <= l) {
                    return;
                }
                nbttaglist.getCompoundTagAt(j).setShort("lvl", (short) level);
                stack.setTagInfo("ench", nbttaglist);
                return;
            }
        } else {
            nbttaglist = new NBTTagList();
        }
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short) Enchantment.getEnchantmentID(ie));
        nbttagcompound.setShort("lvl", (short) level);
        nbttaglist.appendTag(nbttagcompound);
        if (nbttaglist.tagCount() > 0) {
            stack.setTagInfo("ench", nbttaglist);
        }
    }
}