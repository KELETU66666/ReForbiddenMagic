package keletu.forbiddenmagicre.blocks;


import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.blocks.tiles.TileEntityWrathCage;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockWrathCage extends BlockContainer implements IHasModel {
    public BlockWrathCage() {
        super(Material.IRON);
        setTranslationKey("wrath_cage").setRegistryName("wrath_cage");
        this.setCreativeTab(ReForbiddenMagic.tab);
        setHardness(5.0F);
        setResistance(2000.0F);
        setSoundType(SoundType.METAL);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityWrathCage();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItemMainhand();
        if (held != ItemStack.EMPTY && held.getItem() == ModItems.MOB_CRYSTAL) {
            NBTTagCompound nbttagcompound = held.getTagCompound();
            if (nbttagcompound == null)
                return false;
            if(!nbttagcompound.hasKey("mob"))
                return false;
            String string = nbttagcompound.getString("mob");
            if (string != null) {
                if (!world.isRemote) {
                    TileEntityWrathCage spawner = (TileEntityWrathCage) world.getTileEntity(pos);
                    String mob = null;
                    ItemStack crystal = ItemStack.EMPTY;
                    if (spawner.getSpawnerLogic().isMobSet()) {
                        mob = spawner.getSpawnerLogic().getEntityNameToSpawn();
                        crystal = new ItemStack(ModItems.MOB_CRYSTAL, 1);
                        NBTTagString mobTag = new NBTTagString(mob);
                        crystal.setTagInfo("mob", mobTag);
                    }
                    spawner.getSpawnerLogic().setMobID(string);
                    spawner.getSpawnerLogic().mobIsSet(true);
                    spawner.checkAspect();
                    world.notifyBlockUpdate(pos, state, state, 0);
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, crystal);
                }
                return true;
            }
        } else if (held != ItemStack.EMPTY && held.getItem() == ModItems.DIABOLISTFORK && ConfigFM.wrathCost > 0) {
            TileEntityWrathCage spawner = (TileEntityWrathCage) world.getTileEntity(pos);
            if (++spawner.mode > 2)
                spawner.mode = 0;
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(world, pos, state);
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
