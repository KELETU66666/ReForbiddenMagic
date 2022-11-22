package keletu.forbiddenmagicre.items.tools;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import thaumcraft.api.items.ItemsTC;

public class SkullAxe extends ItemSword implements IHasModel {
    public SkullAxe(String name, CreativeTabs tab, ToolMaterial material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);

        ModItems.ITEMS.add(this);
    }
    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(stack, stack2);
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
