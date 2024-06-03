package keletu.forbiddenmagicre.compat.bloodmagic;


import WayofTime.bloodmagic.util.helper.NetworkHelper;
import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import keletu.forbiddenmagicre.util.Reference;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.api.items.ItemsTC;

public class ItemBloodRapier extends ItemSword implements IWarpingGear, IHasModel {

    static final ToolMaterial bloodsucker = EnumHelper.addToolMaterial("bloodsucker", 3, 700, 10F, -3F, 18);

    public ItemBloodRapier(){
        super(bloodsucker);
        setRegistryName("blood_rapier");
        setTranslationKey("blood_rapier");

        ModItems.ITEMS.add(this);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "fm_blood_rapier"), new InfusionRecipe(
                "BLOOD_RAPIER",
                new ItemStack(this),
                6,
                new AspectList().add(Aspect.DESIRE, 100).add(Aspect.LIFE, 30).add(Aspect.AVERSION, 30),
                new ItemStack(ItemsTC.voidSword),
                ThaumcraftApiHelper.makeCrystal(Aspect.FLUX),
                new ItemStack(ModItems.ResourceNS, 1, 0),
                new ItemStack(ModItems.GluttonyShard),
                "feather",
                "gemAmber"
        ));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase player) {
        stack.damageItem(1, player);
        victim.motionY *= 0.8;
        if (victim.hurtResistantTime > 18)
            victim.hurtResistantTime -= 5;
        if (victim instanceof EntityPlayer) {
            String target = victim.getPersistentID().toString();
            int lp = NetworkHelper.getSoulNetwork(target).getCurrentEssence();
            int damage = Math.max(4000, lp / 4);
            if (lp >= damage)
                lp -= damage;
            else
                lp = 0;
            NetworkHelper.getSoulNetwork(target).setCurrentEssence(lp);
            victim.addPotionEffect(new PotionEffect(RegistryHandler.bloodSeal, 1200));
        }
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int wat, boolean fuckObfuscation) {
        super.onUpdate(stack, world, entity, wat, fuckObfuscation);
        if(stack.isItemDamaged() && entity != null && entity.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase)entity);
        }
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 2;
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
