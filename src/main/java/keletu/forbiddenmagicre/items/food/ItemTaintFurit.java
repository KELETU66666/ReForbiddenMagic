package keletu.forbiddenmagicre.items.food;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.lib.potions.PotionThaumarhia;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class ItemTaintFurit extends ItemFood implements IHasModel {
    public ItemTaintFurit(){
        super(4, 0.8F, false);
        setCreativeTab(tab);
        setTranslationKey("taint_fruit");
        setRegistryName("taint_fruit");

        ModItems.ITEMS.add(this);
    }

    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        if(!world.isRemote && player instanceof EntityPlayerMP) {
            ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1, IPlayerWarp.EnumWarpType.NORMAL);
            player.addPotionEffect(new PotionEffect(PotionFluxTaint.instance, 600, 0, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0, false, false));
            if(world.rand.nextFloat() < 0.4F){
                player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("warp.text.15")), true);
            player.addPotionEffect(new PotionEffect(PotionThaumarhia.instance, 600, 0, true, false));
            }
        }
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
