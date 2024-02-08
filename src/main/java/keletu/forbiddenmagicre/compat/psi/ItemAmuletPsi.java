package keletu.forbiddenmagicre.compat.psi;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.api.items.ItemsTC;
import vazkii.botania.api.item.ICosmeticAttachable;
import vazkii.psi.common.core.handler.PlayerDataHandler;

import java.util.List;

import static fox.spiteful.lostmagic.LostMagic.tab;
import static net.minecraft.util.text.translation.I18n.translateToLocal;

@Optional.Interface(iface = "vazkii.botania.api.item.ICosmeticAttachable", modid = "botania")
public class ItemAmuletPsi extends Item implements IBauble, IWarpingGear, ICosmeticAttachable, IHasModel {

    public ItemAmuletPsi(){
        super();
        setMaxDamage(60);
        setMaxStackSize(1);
        setTranslationKey("amuletmentalagony").setRegistryName("amuletmentalagony");
        setCreativeTab(tab);

        ModItems.ITEMS.add(this);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "fm_amulet_psi"), new InfusionRecipe(
                "AMULET_PSI",
                new ItemStack(this),
                7,
                new AspectList().add(Aspect.MIND, 30).add(Aspect.PROTECT, 30).add(Aspect.LIFE, 15),
                new ItemStack(ItemsTC.baubles, 1, 0),
                "dustPsi",
                "dustPsi",
                "gemPsi",
                "plateVoid",
                "ingotVoid",
                "ingotVoid"
        ));
    }

    public BaubleType getBaubleType(ItemStack itemstack){
        return BaubleType.AMULET;
    }

    public void onWornTick(ItemStack itemstack, EntityLivingBase player){}
    public void onEquipped(ItemStack itemstack, EntityLivingBase player){}
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player){}

    public boolean canEquip(ItemStack itemstack, EntityLivingBase player){
        return true;
    }

    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player){
        return true;
    }

    @Override
    public ItemStack getCosmeticItem(ItemStack stack) {
        if(stack == null || stack.getTagCompound() == null)
            return null;
        if(!stack.getTagCompound().hasKey("cosmeticItem"))
            return null;
        NBTTagCompound cosmetic = stack.getTagCompound().getCompoundTag("cosmeticItem");
        return this.getDefaultInstance();
    }

    @Override
    public void setCosmeticItem(ItemStack stack, ItemStack cosmetic) {
        if(stack == null)
            return;
        NBTTagCompound cmp = new NBTTagCompound();
        if(cosmetic != null)
            cosmetic.writeToNBT(cmp);
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        tag.setTag("cosmeticItem", cmp);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if(GuiScreen.isShiftKeyDown() && getCosmeticItem(stack) != null)
            tooltip.add(String.format(translateToLocal("botaniamisc.hasCosmetic"), getCosmeticItem(stack).getDisplayName()).replaceAll("&", "\u00a7"));

    }

    @SubscribeEvent
    public void onBacklash(LivingHurtEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer && event.getSource() == PlayerDataHandler.damageSourceOverload) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack amulet = BaublesApi.getBaubles(player).getStackInSlot(0);
            if (amulet.getItem() == this) {
                int absorbed = Math.min((int) event.getAmount() - 2, amulet.getMaxDamage() - amulet.getItemDamage());
                event.setAmount(event.getAmount() - absorbed);
                amulet.damageItem(absorbed, player);
                if (amulet.getItemDamage() >= amulet.getMaxDamage()) {
                    BaublesApi.getBaubles(player).removeStackFromSlot(0);
                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.NORMAL);
                }
            }
            if (!ThaumcraftCapabilities.knowsResearch(player, "f_overloaddamage_complete")) {
                ThaumcraftApi.internalMethods.completeResearch(player, "f_overloaddamage_complete");
                player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE.toString() + TextFormatting.ITALIC + I18n.translateToLocal("overloaddamage.complete.text")), true);
            }
        }
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
    
    public int getWarp(ItemStack itemStack, EntityPlayer entityPlayer){
        return 1;
    }
}
