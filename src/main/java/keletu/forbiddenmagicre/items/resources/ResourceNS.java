package keletu.forbiddenmagicre.items.resources;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class ResourceNS extends Item implements IHasModel {

    public ResourceNS() {
        super();
        setRegistryName("resourcenethershard");
        setCreativeTab(tab);
        setTranslationKey(this.getRegistryName().getPath());
        setHasSubtypes(true);
        this.addPropertyOverride(new ResourceLocation("meta"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getMetadata() == 1) {
                    return 1.0F;
                }
                if (stack.getMetadata() == 2) {
                    return 2.0F;
                }
                if (stack.getMetadata() == 3) {
                    return 3.0F;
                }
                if (stack.getMetadata() == 4) {
                    return 4.0F;
                }
                if (stack.getMetadata() == 5) {
                    return 5.0F;
                }
                if (stack.getMetadata() == 5) {
                    return 6.0F;
                }
                return 0.0F;
            }
        });

        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumRarity getRarity (final ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }

        for (int x = 0; x < 6; x++) {
            items.add(new ItemStack(this, 1, x));
        }
    }

    @Override
    public String getTranslationKey(ItemStack item) {
        return super.getTranslationKey() + "." + item.getItemDamage();
    }

    @Override
    public void registerModels() {
        for (int i = 0; i < 6; i++) {
            forbiddenmagicre.proxy.registerItemRenderer(this, i, "inventory");
        }
    }
}
