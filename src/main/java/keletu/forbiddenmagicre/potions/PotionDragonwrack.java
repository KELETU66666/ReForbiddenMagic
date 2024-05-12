package keletu.forbiddenmagicre.potions;

import cofh.redstoneflux.api.IEnergyContainerItem;
import keletu.forbiddenmagicre.items.tools.ItemDragonslayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionDragonwrack extends Potion {

    DamageSource wrack;

    public PotionDragonwrack(){
        super(false, 0xFFFFAA);
        this.setPotionName("potion.dragonwrack");
        this.setRegistryName("dragonwrack");
        this.setIconIndex(4, 1);
        wrack = new DamageSource("dragonwrack").setDamageBypassesArmor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("forbiddenmagicre", "textures/misc/potions.png"));
        return super.getStatusIconIndex();
    }

    @Override
    public void performEffect(EntityLivingBase victim, int level)
    {
        if(!ItemDragonslayer.isDragon(victim) || victim.world.isRemote)
            return;
        ItemDragonslayer.absoluteDamage(victim, wrack, Math.max(1F, victim.getMaxHealth() / 20F));
        if(victim instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)victim;
            for(EntityEquipmentSlot x : EntityEquipmentSlot.values()){
                if(player.getItemStackFromSlot(x) != ItemStack.EMPTY){
                    ItemStack equip = player.getItemStackFromSlot(x);
                    if(equip.getItem() instanceof IEnergyContainerItem){
                        IEnergyContainerItem battery = (IEnergyContainerItem)equip.getItem();
                        battery.extractEnergy(equip, battery.getMaxEnergyStored(equip) / 50, false);
                    }
                }
            }
        }
    }

    public boolean isReady(int tick, int level)
    {
        int k;

        k = 40 >> level;
        return k <= 0 || tick % k == 0;
    }
}