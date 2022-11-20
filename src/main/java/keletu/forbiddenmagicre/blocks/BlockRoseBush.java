package keletu.forbiddenmagicre.blocks;


import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class BlockRoseBush extends BlockBush implements IGrowable, IHasModel
{
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

    public static final PropertyEnum<BlockRoseBush.EnumBlockHalf> HALF = PropertyEnum.<BlockRoseBush.EnumBlockHalf>create("half", BlockRoseBush.EnumBlockHalf.class);

    public BlockRoseBush()
    {
        super(Material.PLANTS);
        this.setHardness(0.0F);
        this.setUnlocalizedName("black_rose_bush");
        this.setRegistryName("black_rose_bush");
        this.setCreativeTab(tab);
        this.setTickRandomly(true);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    /**
     * The type of render function that is called for this block
     * @return
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote && rand.nextInt(10) <= 2)
            spreadFlowers(world, pos.getX(), pos.getY(), pos.getZ(), rand);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && world.isAirBlock(pos.up());
    }

    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            boolean flag = state.getValue(HALF) == BlockRoseBush.EnumBlockHalf.UPPER;
            BlockPos blockpos = flag ? pos : pos.up();
            BlockPos blockpos1 = flag ? pos.down() : pos;
            Block block = (Block)(flag ? this : worldIn.getBlockState(blockpos).getBlock());
            Block block1 = (Block)(flag ? worldIn.getBlockState(blockpos1).getBlock() : this);

            if (!flag) this.dropBlockAsItem(worldIn, pos, state, 0); //Forge move above the setting to air.

            if (block == this)
            {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            }

            if (block1 == this)
            {
                worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() != this) return super.canBlockStay(worldIn, pos, state); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
        if (state.getValue(HALF) == BlockRoseBush.EnumBlockHalf.UPPER)
        {
            return worldIn.getBlockState(pos.down()).getBlock() == this;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.up());
            return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (state.getValue(HALF) == BlockRoseBush.EnumBlockHalf.UPPER)
        {
            return Items.AIR;
        }
        else
        {
            return Item.getItemFromBlock(this);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, BlockRoseBush.EnumBlockHalf.UPPER), 2);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return meta > 0 ? this.getDefaultState().withProperty(HALF, BlockRoseBush.EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(HALF, BlockRoseBush.EnumBlockHalf.LOWER);
    }

    public static enum EnumBlockHalf implements IStringSerializable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(HALF) == BlockRoseBush.EnumBlockHalf.UPPER)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());

            if (iblockstate.getBlock() == this)
            {
                state = this.getDefaultState();
            }
        }

        return state;
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(HALF) == BlockRoseBush.EnumBlockHalf.UPPER ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {HALF});
    }
    
    public void spreadFlowers(World world, int x, int y, int z, Random rand) {
        if(!world.isRemote) {
            int i1;
            int j1;
            int k1;

            i1 = x + rand.nextInt(3) - 1;
            j1 = y + rand.nextInt(2) - rand.nextInt(2);
            k1 = z + rand.nextInt(3) - 1;

            for (int l1 = 0; l1 < 8; ++l1) {
                if (world.isAirBlock(new BlockPos(i1, j1, k1)) && this.canSustainBush(world.getBlockState(new BlockPos(i1, j1, k1).down()))) {
                    x = i1;
                    y = j1;
                    z = k1;
                }

                i1 = x + rand.nextInt(3) - 1;
                j1 = y + rand.nextInt(2) - rand.nextInt(2);
                k1 = z + rand.nextInt(3) - 1;
            }

            if (world.isAirBlock(new BlockPos(i1, j1, k1)) && this.canSustainBush(world.getBlockState(new BlockPos(i1, j1, k1).down()))) {
                world.setBlockState(new BlockPos(i1, j1, k1), ModBlocks.BLACK_FLOWER.getDefaultState());
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        spreadFlowers(world, pos.getX(), pos.getY(), pos.getZ(), rand);
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 1, "inventory");
    }
}
