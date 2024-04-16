package keletu.forbiddenmagicre.event;

import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.entities.ITaintedMob;

import java.util.HashMap;

public class BMEvent {
    HashMap<String, Integer> lastLP = new HashMap<String, Integer>();

    public void addDrop(LivingDropsEvent event, ItemStack drop) {
        EntityItem entityitem = new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, drop);
        event.getDrops().add(entityitem);
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event){
        if (event.getSource().getDamageType().equals("player") && event.getEntityLiving() instanceof ITaintedMob)
        {
            PotionEffect effect = event.getEntityLiving().getActivePotionEffect(RegistrarBloodMagic.SOUL_SNARE);
            if (effect != null)
            {
                if (effect.getAmplifier() >= 2) {
                    double rand1 = Math.random();
                    if (rand1 < 0.50d) {
                        addDrop(event, new ItemStack(ModItems.ResourceFM, 1, 2));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event){
        if(event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote){
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            String name = player.getPersistentID().toString();
            if(player.isPotionActive(RegistryHandler.bloodSeal)){
                if (lastLP.containsKey(name)) {
                    if (NetworkHelper.getSoulNetwork(name).getCurrentEssence() > lastLP.get(name))
                        NetworkHelper.getSoulNetwork(name).setCurrentEssence(lastLP.get(name));
                    else
                        lastLP.put(name, NetworkHelper.getSoulNetwork(name).getCurrentEssence());
                }
                else
                    lastLP.put(name, NetworkHelper.getSoulNetwork(name).getCurrentEssence());
            }
            else if(lastLP.containsKey(name))
                lastLP.remove(name);
        }
    }
}
