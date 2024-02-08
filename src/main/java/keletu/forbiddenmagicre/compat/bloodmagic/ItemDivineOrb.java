package keletu.forbiddenmagicre.compat.bloodmagic;


import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.item.ItemBindableBase;
import WayofTime.bloodmagic.orb.BloodOrb;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.PlayerHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.api.items.ItemsTC;

import java.util.List;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class ItemDivineOrb extends ItemBindableBase implements IBloodOrb, IWarpingGear, IHasModel {
    public ItemDivineOrb() {
        setMaxStackSize(1);
        setMaxDamage(0);
        setCreativeTab(tab);
        setTranslationKey("eldritch_orb");
        setRegistryName("eldritch_orb");

        ModItems.ITEMS.add(this);

        ItemStack archOrb = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bloodmagic", "blood_orb")));
        NBTTagCompound tag = new NBTTagCompound();
        if(!ConfigHandler.general.enableTierSixEvenThoughThereIsNoContent)
        tag.setString("orb", "bloodmagic:archmage");
        else
            tag.setString("orb", "bloodmagic:transcendent");
        archOrb.setTagCompound(tag);

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "fm_divine_orb"), new InfusionRecipe(
                "DIVINE_ORB",
                new ItemStack(this),
                3,
                new AspectList().add(Aspect.LIFE, 250).add(Aspect.VOID, 250).add(Aspect.ELDRITCH, 200).add(Aspect.DARKNESS, 125),
                archOrb,
                Ingredient.fromItem(ItemsTC.primordialPearl),
                new ItemStack(ItemsTC.causalityCollapser),
                new ItemStack(ItemsTC.ingots, 1, 1),
                new ItemStack(ItemsTC.ingots, 1, 1),
                new ItemStack(Items.NETHER_STAR),
                new ItemStack(ItemsTC.ingots, 1, 1),
                new ItemStack(ItemsTC.ingots, 1, 1),
                new ItemStack(ItemsTC.causalityCollapser)
        ));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.orb.desc"));

        if (flag.isAdvanced() && !stack.isEmpty())
            tooltip.add(TextHelper.localizeEffect("tooltip.bloodmagic.orb.owner", stack.getItem().getRegistryName().toString()));

        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.isEmpty())
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        if (world == null)
            return super.onItemRightClick(world, player, hand);

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

        if (PlayerHelper.isFakePlayer(player))
            return super.onItemRightClick(world, player, hand);

        if (!stack.hasTagCompound())
            return super.onItemRightClick(world, player, hand);

        Binding binding = getBinding(stack);
        if (binding == null)
            return super.onItemRightClick(world, player, hand);

        if (world.isRemote)
            return super.onItemRightClick(world, player, hand);

        SoulNetwork ownerNetwork = NetworkHelper.getSoulNetwork(binding);
        if (binding.getOwnerId().equals(player.getGameProfile().getId()))
            ownerNetwork.setOrbTier(6);

        ownerNetwork.add(SoulTicket.item(stack, world, player, 200), 80000000); // Add LP to owner's network
        ownerNetwork.hurtPlayer(player, 200); // Hurt whoever is using it
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 5;
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean hasContainerItem() {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public BloodOrb getOrb(ItemStack stack) {
        return new BloodOrb("bloodmagic:archmage", 5, 80000000, 140);
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
