package keletu.forbiddenmagicre.event;

import WayofTime.bloodmagic.ConfigHandler;
import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.core.registry.OrbRegistry;
import WayofTime.bloodmagic.orb.BloodOrb;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.Reference;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.items.ItemsTC;

import java.util.HashMap;

public class BMEvent {

    public BloodOrb DIVINE = new BloodOrb("eldritch", ConfigHandler.general.enableTierSixEvenThoughThereIsNoContent ? 6 : 5, 80000000, 140);
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

    @SubscribeEvent
    public void registerBloodOrb(RegistryEvent.Register<BloodOrb> event) {
        event.getRegistry().register(DIVINE.withModel(new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "eldritch_orb"), "inventory")).setRegistryName("divine"));

        ItemStack archOrb = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("bloodmagic", "blood_orb")));
        NBTTagCompound tag = new NBTTagCompound();
        if(!ConfigHandler.general.enableTierSixEvenThoughThereIsNoContent)
            tag.setString("orb", "bloodmagic:archmage");
        else
            tag.setString("orb", "bloodmagic:transcendent");
        archOrb.setTagCompound(tag);

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "fm_divine_orb"), new InfusionRecipe(
                "DIVINE_ORB",
                OrbRegistry.getOrbStack(this.DIVINE),
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
}
