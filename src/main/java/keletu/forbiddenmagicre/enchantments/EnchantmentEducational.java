package keletu.forbiddenmagicre.enchantments;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentEducational extends Enchantment {
    protected EnchantmentEducational() {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName(Reference.MOD_ID, "educational");
        this.setName("educational");

        EnchantmentsFM.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.LOOTING && ench != EnchantmentsFM.greedy;
    }

    @Override
    public boolean isTreasureEnchantment()
    {
        return true;
    }
}
