package keletu.forbiddenmagicre.items.tools;

import cofh.redstoneflux.api.IEnergyContainerItem;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemDragonslayer extends ItemSword implements IHasModel {
    public Item repair;

    public ItemDragonslayer() {
        super(ToolMaterial.DIAMOND);
        this.setRegistryName("dragon_slayer");
        this.setTranslationKey("sword_dragon_slayer");
        this.setCreativeTab(ReForbiddenMagic.tab);
        MinecraftForge.EVENT_BUS.register(this);

        ModItems.ITEMS.add(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return stack2.getItem() == repair || super.getIsRepairable(stack, stack2);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase player) {
        stack.damageItem(1, player);
        if (!victim.world.isRemote && isDragon(victim)) {
            absoluteDamage(victim, new EntityDamageSource("dragonslayer", player).setDamageBypassesArmor(), Math.max(4F, victim.getMaxHealth() / 5F));
            if(victim instanceof EntityPlayer) {
                EntityPlayer target = (EntityPlayer)victim;
                for(EntityEquipmentSlot x : EntityEquipmentSlot.values()){
                    if(target.getItemStackFromSlot(x) != ItemStack.EMPTY){
                        ItemStack equip = target.getItemStackFromSlot(x);
                        if(equip.getItem() instanceof IEnergyContainerItem){
                            IEnergyContainerItem battery = (IEnergyContainerItem)equip.getItem();
                            battery.extractEnergy(equip, battery.getMaxEnergyStored(equip) / 20, false);
                        }
                    }
                }
            }
            victim.addPotionEffect(new PotionEffect(RegistryHandler.dragonwrack, 300));
        }
        return true;
    }

    public static boolean isDragon(Entity target){
        if(target == null)
            return false;
        if(target instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)target;
            for(EntityEquipmentSlot x : EntityEquipmentSlot.values()){
                if(player.getItemStackFromSlot(x) != ItemStack.EMPTY){
                    String id = ModItems.DRAGONSLAYER.getCreatorModId(player.getItemStackFromSlot(x));
                    if(id != null && id.equals("draconicevolution"))
                        return true;
                }
            }
        }
        else if(target instanceof EntityDragon ||
                target.getName().toLowerCase().contains("dragon") ||
                target.getName().toLowerCase().contains("drake") ||
                target.getName().toLowerCase().contains("dracon"))
            return true;
        return false;
    }

    public static void absoluteDamage(EntityLivingBase target, DamageSource src, float damage){
        target.getCombatTracker().trackDamage(src, target.getHealth(), damage);
        target.setHealth(target.getHealth() - damage);
        if(target.getHealth() < 1){
            target.onDeath(src);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGetHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() != null){
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack equip = player.getHeldItemMainhand();
            if(equip != ItemStack.EMPTY && equip.getItem() == this && isDragon(event.getSource().getTrueSource())){
                event.setAmount(Math.min(event.getAmount(), 3F));
            }
        }
    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() != null){
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack equip = player.getHeldItemMainhand();
            if(equip != ItemStack.EMPTY && player.isUser() && equip.getItem() == this && isDragon(event.getSource().getTrueSource())){
                event.setCanceled(true);
                if(event.getSource().getTrueSource() instanceof EntityPlayer) {
                    EntityPlayer other = (EntityPlayer) event.getSource().getTrueSource();
                    other.addPotionEffect(new PotionEffect(RegistryHandler.dragonwrack, 300));
                    for(EntityEquipmentSlot x : EntityEquipmentSlot.values()){
                        if(other.getItemStackFromSlot(x) != ItemStack.EMPTY){
                            ItemStack gear = other.getItemStackFromSlot(x);
                            if(gear.getItem() instanceof IEnergyContainerItem){
                                IEnergyContainerItem battery = (IEnergyContainerItem)gear.getItem();
                                battery.extractEnergy(gear, battery.getMaxEnergyStored(gear) / 50, false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}