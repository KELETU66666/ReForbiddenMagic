package keletu.forbiddenmagicre.items.tools;

import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.items.IScribeTools;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.common.lib.SoundsTC;

public class ItemCrystalWell extends Item implements IScribeTools, IHasModel {
    public ItemCrystalWell() {
        this.setRegistryName("crystal_scribing_tools");
        this.setTranslationKey("crystal_scribing_tools");
        this.setCreativeTab(ReForbiddenMagic.tab);
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.setHasSubtypes(false);

        ModItems.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        ResearchCategory[] rc = ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);
        int oProg = IPlayerKnowledge.EnumKnowledgeType.OBSERVATION.getProgression();
        int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();

        if (stack.getItemDamage() >= stack.getMaxDamage()) {
            if (!world.isRemote) {
                ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.OBSERVATION, rc[player.getRNG().nextInt(rc.length)], MathHelper.getInt(player.getRNG(), oProg * 2, oProg));
                ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[player.getRNG().nextInt(rc.length)], MathHelper.getInt(player.getRNG(), tProg, tProg / 2));
                player.swingArm(EnumHand.MAIN_HAND);
                player.playSound(SoundsTC.scan, 0.8F, 0.8F + (float) player.getEntityWorld().rand.nextGaussian() * 0.05F);
                player.sendStatusMessage(new TextComponentTranslation("message.forbiddenmagic.knowledge_scribing_tools.deplete").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE)), true);
                EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ItemsTC.scribingTools, 1, ItemsTC.scribingTools.getMaxDamage()));
                stack.shrink(1);
                world.spawnEntity(item);
            }
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }
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
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}