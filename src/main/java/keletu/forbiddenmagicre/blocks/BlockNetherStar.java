package keletu.forbiddenmagicre.blocks;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class BlockNetherStar extends Block implements IHasModel {

    public BlockNetherStar(){
        super(Material.IRON);
        setHardness(5.0F);
        setCreativeTab(tab);
        this.setHarvestLevel("pickaxe", 3);
        setUnlocalizedName("netherstar_block");
        setRegistryName("netherstar_block");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
