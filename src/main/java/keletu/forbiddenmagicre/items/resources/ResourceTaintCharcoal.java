package keletu.forbiddenmagicre.items.resources;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.item.Item;

import java.util.Objects;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class ResourceTaintCharcoal extends Item implements IHasModel {
    public ResourceTaintCharcoal()
    {
        this.setCreativeTab(tab);
        setRegistryName("taint_charcoal");
        setCreativeTab(tab);
        setUnlocalizedName(Objects.requireNonNull(this.getRegistryName()).getResourcePath());

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
            forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
