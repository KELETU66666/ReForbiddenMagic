package keletu.forbiddenmagicre.items.tools;

import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;

public class ItemDistortionPick extends ItemPickaxe implements IHasModel {
    public ItemDistortionPick(String name, CreativeTabs tab, ToolMaterial material) {

        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(tab);

        ModItems.ITEMS.add(this);
    }


    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) || super.getIsRepairable(stack, stack2);
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}