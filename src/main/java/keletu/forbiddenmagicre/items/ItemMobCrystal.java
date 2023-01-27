package keletu.forbiddenmagicre.items;

import fox.spiteful.lostmagic.LostMagic;
import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobCrystal extends Item {

    public ItemMobCrystal() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        setUnlocalizedName("mob_crystal");
        setRegistryName("mob_crystal");
        this.setCreativeTab(LostMagic.tab);

        ModItems.ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            if(nbttagcompound.hasKey("mob")) {
                String string = nbttagcompound.getString("mob");

                if (string != null) {
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

                if (string != null)
                    return ("" + I18n.translateToLocal("item.MobCrystal.name").replace("%s", I18n.translateToLocal("entity." + string + ".name"))).trim();
            }
        }

        return ("" + I18n.translateToLocal("item.MobCrystalEmpty.name")).trim();
    }
}
