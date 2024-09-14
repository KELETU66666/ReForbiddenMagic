/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package keletu.forbiddenmagicre.compat.lostmagic;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ItemIDFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static {
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("lostmagic", "purifying_shovel"), new ResourceLocation(Reference.MOD_ID, "purifying_shovel"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("lostmagic", "ring_nutrition"), new ResourceLocation(Reference.MOD_ID, "ring_nutrition"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("lostmagic", "scribe_blood"), new ResourceLocation(Reference.MOD_ID, "scribe_blood"));
    }

    public ItemIDFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        return compound;
    }

    @SubscribeEvent
    public void missingItemMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            ResourceLocation oldName = entry.key;
            ResourceLocation newName = ITEM_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null) {
                    entry.remap(newItem);
                }
            }
        }
    }
}