package keletu.forbiddenmagicre;

import keletu.forbiddenmagicre.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemTabCrystal extends CreativeTabs {

    public ItemTabCrystal( ) {
        super("fm_crystal" );
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.MOB_CRYSTAL);
    }
}
