package keletu.forbiddenmagicre.compat;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Compat {

    public static Item getItem(String mod, String item) throws ItemNotFoundException {
        Item target = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mod + ":" + item));
        if(target == null)
            throw new ItemNotFoundException(mod, item);
        return target;
    }

    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String mod, String item){
            super("Unable to find item " + item + " in mod " + mod + "! Are you using the correct version of the mod?");
        }
    }

}
