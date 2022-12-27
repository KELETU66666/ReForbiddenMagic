package keletu.forbiddenmagicre.event;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

public class BMEvent {
    HashMap<String, Integer> lastLP = new HashMap<String, Integer>();

    @SubscribeEvent
    public void onEntityUpdate(net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent event){
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
