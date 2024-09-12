package keletu.forbiddenmagicre.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRingNutrition extends Item implements IBauble, IHasModel {

    public ItemRingNutrition(){
        this.setCreativeTab(ReForbiddenMagic.tab);
        this.setRegistryName("ring_nutrition");
        this.setTranslationKey("ring_nutrition");
        this.setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public BaubleType getBaubleType(ItemStack var1){
        return BaubleType.RING;
    }


    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
