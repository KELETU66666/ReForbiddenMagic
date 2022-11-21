package keletu.forbiddenmagicre.event;

import com.google.common.collect.Multimap;
import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.enchantments.inchantment.EnumInfusionEnchantmentFM;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.items.tools.DistortionPick;
import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class LivingEvent {
    Random randy = new Random();
    @SubscribeEvent
    public static void onPlayerBreaking(PlayerEvent.BreakSpeed event) {
        BlockPos pos = event.getPos();
        if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND) != null) {
            ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
            if (stack.getItem() instanceof DistortionPick) {
                World world = event.getEntityPlayer().world;
                BlockPos pos1;
                pos1 = pos.add(0, 0, 0);
                float hardness = world.getBlockState(pos1).getBlockHardness(world, pos1);
                if (hardness == 0.0F)
                    event.setNewSpeed(0.0F);
                else if (hardness < 5.0F)
                    event.setNewSpeed(0.1F);
                else if (hardness < 20.0F)
                    event.setNewSpeed(hardness / 2.0F);
                else
                    event.setNewSpeed(5.0F + hardness);
            }
        }
    }

    @SubscribeEvent
    public void onHarvest(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.getHarvester();
        if (player != null) {
            player.inventory.getCurrentItem();
            ItemStack equip = player.inventory.getCurrentItem();
            if (EnumInfusionEnchantmentFM.getInfusionEnchantmentLevel(equip, EnumInfusionEnchantmentFM.CONSUMING) > 0) {
                for (int x = 0; x < event.getDrops().size(); x++) {
                    ItemStack drop = (ItemStack) event.getDrops().get(x);
                    if (drop != null && isGarbage(drop))
                        event.getDrops().remove(x);
                }
            }
        }
    }

    private boolean isGarbage(ItemStack drop) {
        for (int id : OreDictionary.getOreIDs(drop)) {
            for (String ore : ConfigFM.configfm.trashpile) {
                if (OreDictionary.getOreName(id).equals(ore))
                    return true;
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent event) {
        if (event.getEntityLiving().world.provider.getDimension() == -1 && event.getEntityLiving() instanceof EntityPigZombie && randy.nextInt(175) == 1)
            event.getEntityLiving().setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.ResourceNS, 1, 1));
    }

    public void addDrop(LivingDropsEvent event, ItemStack drop) {
        EntityItem entityitem = new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, drop);
        event.getDrops().add(entityitem);
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        Random rand = new Random();
        if ((event.getEntityLiving() instanceof EntityVillager || event.getEntityLiving() instanceof IMob) && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack equip = player.getHeldItem(EnumHand.MAIN_HAND);
            if (equip != null && EnumInfusionEnchantmentFM.getInfusionEnchantmentLevel(equip, EnumInfusionEnchantmentFM.CONSUMING) > 0 && event.getLootingLevel() <= 0) {
                if (event.getEntityLiving() instanceof EntityVillager) {
                    addDrop(event, new ItemStack(Items.EMERALD, 1, 0));
                } else if (rand.nextInt(35) < 3)
                    addDrop(event, new ItemStack(ModItems.ResourceFM, 1, 0));
            }
        }

        if (event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer)) {
            if (event.getEntityLiving() instanceof IMob) {
                int wrath = 2;
                int greed = 0;
                ItemStack heldItem = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (heldItem != null) {
                    if (heldItem.getItem() instanceof ItemTool) {
                        Multimap map = heldItem.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, heldItem);
                        Collection collect = map.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
                        if (collect instanceof AttributeModifier) {
                            wrath += (int) ((AttributeModifier) collect).getAmount();
                        }
                    } else if (heldItem.getItem() instanceof ItemSword) {
                        wrath += (int) (((ItemSword) heldItem.getItem()).getAttackDamage() + 4.0F);
                    }

                    wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem);
                    wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, heldItem);

                    if (!event.getEntityLiving().isImmuneToFire()) {
                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, heldItem);
                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, heldItem);
                    }

                    if (event.getEntityLiving().isEntityUndead()) {
                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, heldItem);
                    }

                    if (event.getEntityLiving().getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, heldItem);
                    }

                    greed += EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, heldItem);
                }
            }
            if (event.getEntityLiving().getClass() == EntitySkeleton.class && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
                ItemStack weap = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (weap != null && weap.getItem() == ModItems.SkullAxe && rand.nextInt(26) <= (3 + EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, weap))) {
                    addDrop(event, new ItemStack(Items.SKULL, 1, 0));
                }
            }

            if (event.getEntityLiving().getClass() == EntityWitherSkeleton.class && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
                ItemStack weap = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (weap != null && weap.getItem() == ModItems.SkullAxe && rand.nextInt(26) <= (3 + EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, weap))) {
                    addDrop(event, new ItemStack(Items.SKULL, 1, 1));
                }
            }

            if (event.getEntityLiving().getClass() == EntityZombie.class && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
                ItemStack weap = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (weap != null && weap.getItem() == ModItems.SkullAxe && rand.nextInt(26) <= (2 + 2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, weap))) {
                    addDrop(event, new ItemStack(Items.SKULL, 1, 2));
                }
            }

            if (event.getEntityLiving().getClass() == EntityCreeper.class && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
                ItemStack weap = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (weap != null && weap.getItem() == ModItems.SkullAxe && rand.nextInt(26) <= (2 + 2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, weap))) {
                    addDrop(event, new ItemStack(Items.SKULL, 1, 4));
                }
            }

            if (event.getEntityLiving() instanceof EntityPlayer && event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
                ItemStack weap = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                if (weap != null && weap.getItem() == ModItems.SkullAxe && rand.nextInt(11) <= (1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, weap))) {
                    ItemStack head = new ItemStack(Items.SKULL, 1, 3);
                    NBTTagCompound nametag = new NBTTagCompound();
                    nametag.setString("SkullOwner", ((EntityPlayer) event.getEntityLiving()).getName());
                    head.setTagCompound(nametag);
                    addDrop(event, head);
                }
            }

            if (event.getEntityLiving().world.provider.getDimension() != -1)
                return;

            if (!event.isRecentlyHit() || event.getSource().getTrueSource() == null || !(event.getSource().getTrueSource() instanceof EntityPlayer) || event.getSource().getTrueSource() instanceof FakePlayer) {
                if (randy.nextInt(30) < 4) {
                    addDrop(event, new ItemStack(ModItems.ResourceNS, 1 + randy.nextInt(3), 4));
                }
            }

            if (event.isRecentlyHit() && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer)) {
                if (event.getEntityLiving() instanceof IMob) {
                    int wrath = 2;
                    int greed = 0;
                    ItemStack heldItem = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItem(EnumHand.MAIN_HAND);
                    if (heldItem != null) {if (heldItem.getItem() instanceof ItemSword) {
                            wrath += (int) (((ItemSword) heldItem.getItem()).getAttackDamage() + 4.0F);
                        }

                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, heldItem);
                        wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, heldItem);

                        if (!event.getEntityLiving().isImmuneToFire()) {
                            wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, heldItem);
                            wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, heldItem);
                        }

                        if (event.getEntityLiving().isEntityUndead()) {
                            wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, heldItem);
                        }

                        if (event.getEntityLiving().getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                            wrath += EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, heldItem);
                        }

                        greed += EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, heldItem);
                    }

                    if (randy.nextInt(61) <= wrath) {
                        addDrop(event, new ItemStack(ModItems.ResourceNS, 1, 0));
                    }

                    if (event.getEntityLiving() instanceof EntityWither) {
                        addDrop(event, new ItemStack(ModItems.ResourceNS, 2 + randy.nextInt(1 + event.getLootingLevel()), 2));
                    }

                    if(greed > 0 && randy.nextInt(20) <= greed){
                        addDrop(event, new ItemStack(ModItems.ResourceNS, 1, 5));
                    }
                }

                if (event.getEntityLiving() instanceof EntityPigZombie && !(event.getSource().getTrueSource() instanceof FakePlayer)) {
                    if (event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND) != null && event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.ResourceNS && event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND).getItemDamage() == 1) {
                        addDrop(event, new ItemStack(ModItems.ResourceNS, 1, 1));
                    }
                }
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        event.getItemStack();
        NBTTagList nbttaglist = EnumInfusionEnchantmentFM.getInfusionEnchantmentTagList(event.getItemStack());
        if(nbttaglist != null) {
            for(int j = 0; j < nbttaglist.tagCount(); ++j) {
                short k = nbttaglist.getCompoundTagAt(j).getShort("id");
                short l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
                if(k < 0 || k >= EnumInfusionEnchantmentFM.values().length)
                    continue;
                String s = TextFormatting.GOLD + I18n
                        .translateToLocal("enchantment.infusion." + EnumInfusionEnchantmentFM.values()[k].toString());
                if(EnumInfusionEnchantmentFM.values()[k].maxLevel > 1) {
                    s = s + " " + I18n.translateToLocal("enchantment.level." + l);
                }
                event.getToolTip().add(1, s);
            }
        }
    }

    @SubscribeEvent
    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event){
        if(event.getEntityLiving().world.isRemote)
            return;
        if(event.getItem().getItem() instanceof ItemFood){
            if(event.getEntityLiving().world.provider.getDimension() == -1 && event.getItem().getItem() != ModItems.GluttonyShard
                    && randy.nextInt(10) < 2
        ){

                EntityItem ent = event.getEntityLiving().entityDropItem(new ItemStack(ModItems.GluttonyShard, 1), 1.0F);
                if(ent != null) {
                    ent.motionY += randy.nextFloat() * 0.05F;
                    ent.motionX += (randy.nextFloat() - randy.nextFloat()) * 0.1F;
                    ent.motionZ += (randy.nextFloat() - randy.nextFloat()) * 0.1F;
                }
            }
        }
    }
}