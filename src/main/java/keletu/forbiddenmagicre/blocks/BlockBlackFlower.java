package keletu.forbiddenmagicre.blocks;


import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static fox.spiteful.lostmagic.LostMagic.tab;
import static net.minecraft.item.EnumDyeColor.BLUE;

public class BlockBlackFlower extends BlockBush implements IHasModel {
    public BlockBlackFlower() {
        super(Material.PLANTS);
        this.setCreativeTab(tab);
        this.setRegistryName("black_rose").setUnlocalizedName("black_rose");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    protected static float f = 0.2F;

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    public void spreadFlowers(World world, int x, int y, int z, Random rand) {
        int l = 10;
        int i1;
        int j1;
        int k1;

        for (i1 = x - 2; i1 <= x + 2; ++i1) {
            for (j1 = z - 2; j1 <= z + 2; ++j1) {
                for (k1 = y - 1; k1 <= y + 1; ++k1) {
                    if (world.getBlockState(new BlockPos(i1, k1, j1)).getBlock() == this) {
                        --l;

                        if (l <= 0) {
                            return;
                        }
                    }
                }
            }
        }

        i1 = x + rand.nextInt(3) - 1;
        j1 = y + rand.nextInt(2) - rand.nextInt(2);
        k1 = z + rand.nextInt(3) - 1;

        for (int l1 = 0; l1 < 4; ++l1) {
            if (world.isAirBlock(new BlockPos(i1, j1, k1)) && this.canBlockStay(world, new BlockPos(i1, j1, k1), this.getDefaultState())) {
                x = i1;
                y = j1;
                z = k1;
            }

            i1 = x + rand.nextInt(3) - 1;
            j1 = y + rand.nextInt(2) - rand.nextInt(2);
            k1 = z + rand.nextInt(3) - 1;
        }

        if (world.isAirBlock(new BlockPos(i1, j1, k1)) && this.canBlockStay(world, new BlockPos(i1, j1, k1), this.getDefaultState())) {
            world.setBlockState(new BlockPos(i1, j1, k1), this.getDefaultState(), 0);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos, this.getDefaultState());
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

        return net.minecraftforge.common.EnumPlantType.Plains;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        Block block = world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();
        return block.canSustainPlant(state, world, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), EnumFacing.UP, this);
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}