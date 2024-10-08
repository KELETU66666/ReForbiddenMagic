package keletu.forbiddenmagicre.items.tools;

import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.enchantments.inchantment.EnumInfusionEnchantmentFM;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.ItemsTC;

import javax.annotation.Nullable;

public class ItemMorphSword extends ItemSword implements IHasModel {

    public ItemMorphSword(String name, CreativeTabs tab, ToolMaterial material) {

        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(tab);
        this.addPropertyOverride(new ResourceLocation("morphsword"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getTagCompound() != null && stack.getTagCompound().getByte("phase") == 2) {
                    return 2.0F;
                }
                if (stack.getTagCompound() != null && stack.getTagCompound().getByte("phase") == 1) {
                    return 1.0F;
                }
                return 0.0F;
            }
        });

        ModItems.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (player.isSneaking() && itemstack.hasTagCompound() && getMaxDamage(itemstack) - itemstack.getItemDamage() > 5) {
            NBTTagCompound tags = itemstack.getTagCompound();
            byte phase = tags.getByte("phase");
            if (tags.hasKey("ench")) {
                NBTTagList enchants = itemstack.getEnchantmentTagList();
                tags.setTag("enchants" + phase, enchants);
            } else
                tags.removeTag("enchants" + phase);

            if (tags.hasKey("display")) {
                String name = tags.getCompoundTag("display").getString("Name");
                if (name != null && !name.equals(""))
                    tags.getCompoundTag("display").setString("Name" + phase, name);
                else
                    tags.getCompoundTag("display").removeTag("Name" + phase);
            }
            if (++phase > 2)
                phase = 0;
            tags.setByte("phase", phase);
            if (tags.hasKey("enchants" + phase)) {
                NBTTagList enchants = (NBTTagList) (tags.getTag("enchants" + phase));
                tags.setTag("ench", enchants);
            } else
                tags.removeTag("ench");

            if (tags.hasKey("display")) {
                String name = tags.getCompoundTag("display").getString("Name" + phase);
                if (name != null && !name.equals(""))
                    tags.getCompoundTag("display").setString("Name", name);
                else
                    tags.getCompoundTag("display").removeTag("Name");
            }

            itemstack.setTagCompound(tags);
            itemstack.damageItem(5, player);
            player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.5F, 2.6F + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.8F, false);
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) || super.getIsRepairable(stack, stack2);
    }
    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }
    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
    public void onUpdate(ItemStack stack, World world, Entity entity, int fuckObfuscation, boolean fuckObfuscation2) {
        super.onUpdate(stack, world, entity, fuckObfuscation, fuckObfuscation2);
        if(EnumInfusionEnchantmentFM.getInfusionEnchantmentLevel(stack, EnumInfusionEnchantmentFM.VOIDTOUCHED) > 0 && stack.isItemDamaged() && entity != null && entity.ticksExisted % 10 == 0 && entity instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase)entity);
        }

    }
}
