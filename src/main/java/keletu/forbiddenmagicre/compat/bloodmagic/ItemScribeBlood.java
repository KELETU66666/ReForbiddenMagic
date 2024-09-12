package keletu.forbiddenmagicre.compat.bloodmagic;

import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.compat.Compat;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.IScribeTools;

import java.util.List;


public class ItemScribeBlood extends Item implements IScribeTools, IBindable, IHasModel {

    public ItemScribeBlood(){
        this.setCreativeTab(ReForbiddenMagic.tab);
        this.setRegistryName("scribe_blood");
        this.setTranslationKey("scribe_blood");
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setHasSubtypes(false);

        ModItems.ITEMS.add(this);
        ItemStack weakOrb = null;
        try {
            weakOrb = new ItemStack(Compat.getItem("bloodmagic", "blood_orb"));
        } catch (Compat.ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("orb", "bloodmagic:weak");
        weakOrb.setTagCompound(tag);

        ItemStack bucketBlood = FluidUtil.getFilledBucket(FluidRegistry.getFluidStack("lifeessence", 1000));

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID , "BloodScribe"), new ShapelessArcaneRecipe(new ResourceLocation(""), "BLOODSCRIBE", 50, (new AspectList()).add(Aspect.WATER, 1).add(Aspect.EARTH, 1), new ItemStack(this), new Object[]{ new ItemStack(Items.FEATHER), bucketBlood, weakOrb, new ItemStack(Items.GLASS_BOTTLE) }));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        if(!ConfigFM.bloodMagic)
            return;

        if (!stack.hasTagCompound())
            return;

        Binding binding = getBinding(stack);
        if (binding != null)
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", binding.getOwnerName()));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity holder, int itemSlot, boolean isSelected) {
        if(!ConfigFM.bloodMagic)
            return;
        if(stack.getItemDamage() > 0){
            Binding binding = getBinding(stack);
            if (binding != null) {
                if(NetworkHelper.getSoulNetwork(binding).syphon(SoulTicket.item(stack, world, holder, 25)) > 0){
                    stack.setItemDamage(stack.getItemDamage() - 1);
                }
            }
        }
    }

    @Override
    public void setDamage(ItemStack stack, int damage){
        if(!ConfigFM.bloodMagic)
            return;
        Binding binding = getBinding(stack);
        if (binding != null) {
            if(NetworkHelper.getSoulNetwork(binding).syphon(SoulTicket.item(stack, 25 * damage)) > 0){
                super.setDamage(stack, 0);
            }
            else
                super.setDamage(stack, damage);
        }
        else
            super.setDamage(stack, damage);
    }


    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
