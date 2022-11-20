package keletu.forbiddenmagicre.blocks;


import java.util.Random;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockArcaneCake extends Block implements IHasModel {
    public static final PropertyInteger CAKE_STATE = PropertyInteger.create("cake_state", 0, 12);

    protected static final AxisAlignedBB[] CAKE_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D) };

    public BlockArcaneCake() {
        super(Material.CAKE);
        setTickRandomly(true);
        setHardness(0.5F);
        this.setUnlocalizedName("arcane_cake");
        this.setRegistryName("arcane_cake");
        setDefaultState(getDefaultState().withProperty((IProperty)CAKE_STATE, Integer.valueOf(0)));

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CAKE_AABB[((Integer)state.getValue((IProperty)CAKE_STATE)).intValue() / 2];
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return state.getCollisionBoundingBox((IBlockAccess)worldIn, pos);
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        eatCakeSlice(worldIn, pos, playerIn);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty((IProperty)CAKE_STATE, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((Integer)state.getValue((IProperty)CAKE_STATE)).intValue();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { (IProperty)CAKE_STATE });
    }

    private void eatCakeSlice(World world, BlockPos pos, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(2, 1.0F);
            int l = getMetaFromState(world.getBlockState(pos)) + 1;
            if (l >= 12) {
                world.setBlockToAir(pos);
            } else {
                world.setBlockState(pos, getDefaultState().withProperty((IProperty)CAKE_STATE, Integer.valueOf(l)), 2);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        int l = getMetaFromState(state);
        if (l > 0) {
            l--;
            worldIn.setBlockState(pos, state.withProperty((IProperty)CAKE_STATE, Integer.valueOf(l)), 2);
        }
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return (super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos));
    }

    public boolean canBlockStay(World world, BlockPos pos) {
        return world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getMaterial().isSolid();
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
