package keletu.forbiddenmagicre.enchantments;

import keletu.forbiddenmagicre.items.tools.ItemMorphAxe;
import keletu.forbiddenmagicre.items.tools.ItemMorphPick;
import keletu.forbiddenmagicre.items.tools.ItemMorphShovel;
import keletu.forbiddenmagicre.items.tools.ItemMorphSword;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentVoid extends Enchantment {
    public EnchantmentVoid() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(Reference.MOD_ID, "voidtouched");
        this.setName("voidtouched");

        EnchantmentsFM.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 30;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 30;
    }

    @Override
    public boolean canApply(ItemStack item) {
        return (item.getItem() instanceof ItemMorphPick || item.getItem() instanceof ItemMorphAxe || item.getItem() instanceof ItemMorphSword || item.getItem() instanceof ItemMorphShovel);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }
}