package keletu.forbiddenmagicre.blocks;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.common.lib.SoundsTC;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fox.spiteful.lostmagic.LostMagic.tab;

//public class BlockTBLeaves extends Block implements IShearable {
public class BlockLeavesTainted extends BlockLeaves implements IShearable, IHasModel { // End of modification

    public static final PropertyEnum<LeafGrowth> GROWTH = PropertyEnum.create("growth", LeafGrowth.class);

    int[] surroundings; // AeXiaohu modified

    public enum LeafGrowth implements IStringSerializable {
        FRESH(0), GROWING(1), READY(2), MATURED(3);

        private int meta;

        LeafGrowth(int meta) {
            this.meta = meta;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public boolean canGrow() {
            return this != MATURED;
        }

        public int getMeta() {
            return meta;
        }

    }


    public BlockLeavesTainted() {
        setTickRandomly(true);
        setHardness(0.2F);
        this.setCreativeTab(tab);
        this.setTranslationKey("leaves_tainted");
        this.setRegistryName("leaves_tainted");
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(GROWTH, LeafGrowth.FRESH));

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public SoundType getSoundType() {
        return SoundsTC.GORE;
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return ThaumcraftMaterials.MATERIAL_TAINT;
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 60;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 30;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return super.getMapColor(state, worldIn, pos);
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack((Block)this));
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack((Block)this);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

        if (!worldIn.isRemote) {
            if (state.getValue(GROWTH) == LeafGrowth.MATURED) {
                int i = 4;
                int j = 5;
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();
                int j1 = 32;
                int k1 = 1024;
                int l1 = 16;

                if (this.surroundings == null) {
                    this.surroundings = new int[32768];
                }

                if (!worldIn.isAreaLoaded(pos, 1))
                    return; // Forge: prevent decaying leaves from updating neighbors and loading unloaded chunks
                if (worldIn.isAreaLoaded(pos, 6)) // Forge: extend range from 5 to 6 to account for neighbor checks in world.markAndNotifyBlock -> world.updateObservingBlocksAt
                {
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                    for (int i2 = -4; i2 <= 4; ++i2) {
                        for (int j2 = -4; j2 <= 4; ++j2) {
                            for (int k2 = -4; k2 <= 4; ++k2) {
                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
                                Block block = iblockstate.getBlock();

                                if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                                    if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
                                    } else {
                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
                                    }
                                } else {
                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
                                }
                            }
                        }
                    }

                    for (int i3 = 1; i3 <= 4; ++i3) {
                        for (int j3 = -4; j3 <= 4; ++j3) {
                            for (int k3 = -4; k3 <= 4; ++k3) {
                                for (int l3 = -4; l3 <= 4; ++l3) {
                                    if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
                                        if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
                                        }

                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2) {
                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if (l2 >= 0) {
                    worldIn.setBlockState(pos, state.withProperty(GROWTH, LeafGrowth.MATURED), 4);
                } else {
                    this.destroy(worldIn, pos);
                }
            }
        } // End of modification


        int blocks = 0;
        for (int i = 0; i < EnumFacing.VALUES.length; i++) {
            EnumFacing dir = EnumFacing.VALUES[i];
            IBlockState neighbour = worldIn.getBlockState(pos.offset(dir));
            Block block = neighbour.getBlock();
            if (!block.isAir(neighbour, worldIn, pos)) {
                blocks++;
            }
        }
        if (blocks != 6) {
            int randInt;
                randInt = rand.nextInt(8);
            if (randInt <= 2) {
                LeafGrowth leaf = state.getValue(GROWTH);
                if (leaf.canGrow()) {
                    worldIn.setBlockState(pos, state.cycleProperty(GROWTH));
                }
            }
        }
    }

    private void destroy(World worldIn, BlockPos pos) {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    public void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if (world.rand.nextInt(20) == 0)
            spawnAsEntity(world, pos, new ItemStack(ModItems.TAINTED_FRUIT));
    }

    @Override
    public int tickRate(World world) {
        return 8;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }



    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, EnumFacing face) {
        return 150;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        if (state.getValue(GROWTH) != LeafGrowth.MATURED) {
            world.setBlockState(pos, state.withProperty(GROWTH, LeafGrowth.MATURED), 4);
        }
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos posz) {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
            ret.add(new ItemStack(this));
        return ret;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    public LeafGrowth getVariant(int metadata) {
        for (LeafGrowth colour : LeafGrowth.values()) {
            if (colour.meta == metadata) {
                return colour;
            }
        }
        return LeafGrowth.FRESH;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(GROWTH, getVariant(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWTH).getMeta();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWTH);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return Minecraft.isFancyGraphicsEnabled() ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    } // End of modification


    public boolean isVisuallyOpaque() {
        return false;
    }

    public int getSaplingDropChance(IBlockState state)
    {
        return 20;
    }




    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int chance = this.getSaplingDropChance(state);

        if (fortune > 0)
        {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (rand.nextInt(chance) == 0)
            ret.add(new ItemStack(ModBlocks.BLOCK_SAPLING_TAINTED));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        if (world instanceof World)
            this.dropApple((World)world, pos, state, chance);
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}