package keletu.forbiddenmagicre.items.tools;

import com.google.common.collect.ImmutableSet;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXZap;

import java.util.Set;

public class ItemShovelPurifier extends ItemSpade implements IHasModel {

    public ItemShovelPurifier(){
        super(ThaumcraftMaterials.TOOLMAT_ELEMENTAL);
        this.setCreativeTab(ReForbiddenMagic.tab);
        this.setRegistryName("purifying_shovel");
        this.setTranslationKey("purifying_shovel");

        ModItems.ITEMS.add(this);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        if(state.getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT)
            return efficiency * 2f;
        return super.getDestroySpeed(stack, state);
    }

    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.RARE;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("shovel");
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0))||super.getIsRepairable(stack1, stack2);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        int purified = 0;
        for(int ex = pos.getX() - 5;ex < pos.getX() + 6;ex++){
            for(int wy = pos.getY() - 4;wy < pos.getY() + 5;wy++){
                for(int zee = pos.getZ() - 5;zee < pos.getZ() + 6;zee++){
                    BlockPos aim = new BlockPos(ex, wy, zee);
                    Block target = world.getBlockState(aim).getBlock();
                    if(target != null && target == BlocksTC.fluxGoo){
                        purified++;
                        world.setBlockToAir(new BlockPos(ex, wy, zee));
                        float d1 = ((float) ex + world.rand.nextFloat());
                        float d2 = ((float) wy + world.rand.nextFloat());
                        float d0 = ((float) zee + world.rand.nextFloat());
                        //Thaumcraft.proxy.nodeBolt(world, (float)player.posX, (float)player.posY, (float)player.posZ, d1, d2, d0);
                        PacketHandler.INSTANCE.sendToAllAround(new PacketFXZap(player.getPositionVector(), new Vec3d(d1, d2, d0), 0xFFFFFF, 5f * 0.66F), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64.0D));
                    }
                }
            }
        }
        if(purified > 0){
            player.getHeldItem(hand).damageItem(Math.min(purified, 15), player);
            player.swingArm(hand);
            world.playSound(player, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsTC.zap, SoundCategory.PLAYERS, 1.0f, 1.0f);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }


    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
