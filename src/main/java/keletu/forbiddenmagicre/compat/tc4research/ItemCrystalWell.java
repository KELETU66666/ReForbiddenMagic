package keletu.forbiddenmagicre.compat.tc4research;

import com.wonginnovations.oldresearch.OldResearch;
import com.wonginnovations.oldresearch.common.lib.network.PacketAspectPool;

import fox.spiteful.lostmagic.LostMagic;
import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.network.PacketHandler;

public class ItemCrystalWell extends Item implements IScribeTools, IHasModel {
    public ItemCrystalWell() {
        super();
        this.setRegistryName("crystal_scribing_tools");
        this.setTranslationKey("crystal_scribing_tools");
        this.setCreativeTab(LostMagic.tab);
        this.maxStackSize = 1;
        this.setMaxDamage(100);
        this.setHasSubtypes(false);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            if (!world.isRemote) {
                for (Aspect a : Aspect.getPrimalAspects()) {
                    int q = world.rand.nextInt(4) + 4;
                    OldResearch.proxy.playerKnowledge.addAspectPool(player.getGameProfile().getName(), a, q);
                    PacketHandler.INSTANCE.sendTo(new PacketAspectPool(a.getTag(), q, OldResearch.proxy.playerKnowledge.getAspectPoolFor(player.getGameProfile().getName(), a)), (EntityPlayerMP) player);
                }
            }

            player.swingArm(EnumHand.MAIN_HAND);
            stack.shrink(1);
            player.addItemStackToInventory(new ItemStack(ItemsTC.scribingTools));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() >= stack.getMaxDamage();
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
