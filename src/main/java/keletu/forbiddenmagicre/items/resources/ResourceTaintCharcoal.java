package keletu.forbiddenmagicre.items.resources;


import keletu.forbiddenmagicre.ReForbiddenMagic;
import static keletu.forbiddenmagicre.ReForbiddenMagic.tab;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.item.Item;

import java.util.Objects;

public class ResourceTaintCharcoal extends Item implements IHasModel {
    public ResourceTaintCharcoal()
    {
        this.setCreativeTab(tab);
        setRegistryName("taint_charcoal");
        setTranslationKey(Objects.requireNonNull(this.getRegistryName()).getPath());

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
            ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
