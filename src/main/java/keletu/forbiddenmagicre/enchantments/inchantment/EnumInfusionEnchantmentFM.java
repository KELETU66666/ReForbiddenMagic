package keletu.forbiddenmagicre.enchantments.inchantment;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public enum EnumInfusionEnchantmentFM {
    VOIDTOUCHED(ImmutableSet.of("morph_tools"), 1, "INFUSIONENCHANTMENTFM&&MORPH_TOOLS"),
    CONSUMING(ImmutableSet.of("axe", "pickaxe", "shovel"), 1, "INFUSIONENCHANTMENTFM"),
    GREEDY(ImmutableSet.of("weapon"), 1, "INFUSIONENCHANTMENTFM"),
    EDUCATIONAL(ImmutableSet.of("weapon"), 5, "INFUSIONENCHANTMENTFM");

    public Set<String> toolClasses;
    public int maxLevel;
    public String research;

    EnumInfusionEnchantmentFM(Set<String> toolClasses, int ml, String research) {
        this.toolClasses = toolClasses;
        this.maxLevel = ml;
        this.research = research;
    }

    public static NBTTagList getInfusionEnchantmentTagList(ItemStack stack) {
        return stack == null || stack.isEmpty() || stack.getTagCompound() == null ? null : stack.getTagCompound().getTagList("infenchkp", 10);
    }

    public static List<EnumInfusionEnchantmentFM> getInfusionEnchantments(ItemStack stack) {
        NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
        ArrayList<EnumInfusionEnchantmentFM> list = new ArrayList<EnumInfusionEnchantmentFM>();
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if (k < 0 || k >= EnumInfusionEnchantmentFM.values().length)
                    continue;
                list.add(EnumInfusionEnchantmentFM.values()[k]);
            }
        }
        return list;
    }

    public static int getInfusionEnchantmentLevel(ItemStack stack, EnumInfusionEnchantmentFM enchantment) {
        NBTTagList nbttaglist = EnumInfusionEnchantmentFM.getInfusionEnchantmentTagList(stack);
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if (k < 0 || k >= EnumInfusionEnchantmentFM.values().length || EnumInfusionEnchantmentFM.values()[k] != enchantment)
                    continue;
                return l;
            }
        }
        return 0;
    }

    public static void addInfusionEnchantment(ItemStack stack, EnumInfusionEnchantmentFM ie, int level) {
        if (stack == null || stack.isEmpty() || level > ie.maxLevel) {
            System.out.println("uwu");
            return;
        }
        NBTTagList nbttaglist = EnumInfusionEnchantmentFM.getInfusionEnchantmentTagList(stack);
        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if (k != ie.ordinal())
                    continue;
                if (level <= l) {
                    return;
                }
                nbttaglist.getCompoundTagAt(j).setShort("lvl", (short) level);
                stack.setTagInfo("infenchkp", nbttaglist);
                return;
            }
        } else {
            nbttaglist = new NBTTagList();
        }
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short) ie.ordinal());
        nbttagcompound.setShort("lvl", (short) level);
        nbttaglist.appendTag(nbttagcompound);
        if (nbttaglist.tagCount() > 0) {
            stack.setTagInfo("infenchkp", nbttaglist);
        }
    }

    private static void handleProjecting(ItemStack stack) {
        // Add an nbt tag to give Projecting items extended reach.
        NBTTagCompound nbt = stack.getTagCompound();

        Multimap<String, AttributeModifier> map = stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        Collection<AttributeModifier> reachCollection = map.get(EntityPlayer.REACH_DISTANCE.getName());
        Collection<AttributeModifier> damageCollection = map.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        Collection<AttributeModifier> speedCollection = map.get(SharedMonsterAttributes.ATTACK_SPEED.getName());
        double reach;
        try {
            AttributeModifier reachModifier = (AttributeModifier) reachCollection.toArray()[0];
            System.out.println(reachModifier.getAmount());
            reach = reachModifier.getAmount() + 2;
        } catch (ArrayIndexOutOfBoundsException e) {
            reach = 2;
            System.out.println("WHAT");
        }
        AttributeModifier reachDistance = new AttributeModifier("reachDistance", reach, 0);
        AttributeModifier attackDamage = (AttributeModifier) damageCollection.toArray()[0];
        AttributeModifier attackSpeed = (AttributeModifier) speedCollection.toArray()[0];
        NBTTagCompound reachNBT = writeAttributeModifierToNBT(EntityPlayer.REACH_DISTANCE, reachDistance, EntityEquipmentSlot.MAINHAND);
        NBTTagCompound damageNBT = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_DAMAGE, attackDamage, EntityEquipmentSlot.MAINHAND);
        NBTTagCompound speedNBT = writeAttributeModifierToNBT(SharedMonsterAttributes.ATTACK_SPEED, attackSpeed, EntityEquipmentSlot.MAINHAND);
        NBTTagList list = new NBTTagList();
        list.appendTag(reachNBT);
        list.appendTag(damageNBT);
        list.appendTag(speedNBT);
        nbt.setTag("AttributeModifiers", list);
    }

    private static NBTTagCompound writeAttributeModifierToNBT(IAttribute attribute, AttributeModifier modifier, EntityEquipmentSlot slot) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setString("AttributeName", attribute.getName());
        nbt.setString("Name", modifier.getName());
        nbt.setString("Slot", slot.getName());
        nbt.setDouble("Amount", modifier.getAmount());
        nbt.setInteger("Operation", modifier.getOperation());
        nbt.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
        nbt.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());

        return nbt;
    }
}