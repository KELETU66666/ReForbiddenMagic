package keletu.forbiddenmagicre.items;

import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobCrystal extends Item implements IHasModel{

    public ItemMobCrystal() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        setTranslationKey("mob_crystal");
        setRegistryName("mob_crystal");
        this.setCreativeTab(ReForbiddenMagic.TabCrystal);
        this.addPropertyOverride(new ResourceLocation("tag"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getTagCompound() != null) {
                    return 1.0F;
                }
                return 0.0F;
            }
        });

        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if(nbttagcompound.hasKey("mob")) {
                String string = nbttagcompound.getString("mob");

                if (!string.isEmpty()) {
                    Aspect mobAspect = Aspect.getAspect(ConfigFM.spawnerMobs.get(string));
                    if (mobAspect != null)
                        tooltip.add(mobAspect.getName());
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (!isInCreativeTab(tab)) {
            return;
        }

        list.add(new ItemStack(this, 1, 0));

        for (String mob : ConfigFM.spawnerMobs.keySet()) {
            ItemStack crystal = new ItemStack(this, 1, 0);
            crystal.setTagCompound(new NBTTagCompound());
            crystal.getTagCompound().setString("mob", mob);
            list.add(crystal);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if(nbttagcompound.hasKey("mob")) {
                String string = nbttagcompound.getString("mob");

                if (!string.isEmpty())
                    return ("" + I18n.translateToLocal("item.mobcrystal.name").replace("%s", I18n.translateToLocal("entity." + string + ".name"))).trim();
            }
        }

        return ("" + I18n.translateToLocal("item.mobcrystalempty.name")).trim();
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
