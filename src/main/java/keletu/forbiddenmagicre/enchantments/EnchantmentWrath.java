package keletu.forbiddenmagicre.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EnchantmentWrath extends Enchantment {
    public EnchantmentWrath() {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("wrath");
        this.setName("wrath");

        EnchantmentsFM.ENCHANTNENTS.add(this);
    }

    public int getMinEnchantability(int level)
    {
        return 5 + (level - 1) * 11;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int level)
    {
        return this.getMinEnchantability(level) + 20;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    public float calcDamageByCreature(int level, EnumCreatureAttribute target)
    {
        return (float)level * 1.25F;
    }

    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemAxe ? true : super.canApply(stack);
    }

    public void onEntityDamaged(EntityLivingBase wut, Entity who, int level)
    {

    }
}