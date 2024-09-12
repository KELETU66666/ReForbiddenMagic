package keletu.forbiddenmagicre.items.food;


import keletu.forbiddenmagicre.ReForbiddenMagic;
import static keletu.forbiddenmagicre.ReForbiddenMagic.tab;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemGluttonyShard extends ItemFood implements IHasModel {
    public ItemGluttonyShard () {
        super(2, 0.1F, false);
        setTranslationKey("gluttony_shard");
        setRegistryName("gluttony_shard");
        setCreativeTab(tab);
        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumRarity getRarity (final ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
