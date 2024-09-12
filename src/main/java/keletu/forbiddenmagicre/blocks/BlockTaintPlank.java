package keletu.forbiddenmagicre.blocks;

import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;


public class BlockTaintPlank extends Block implements IHasModel {
    public BlockTaintPlank() {
        super(Material.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);
        setTranslationKey("taint_planks").setRegistryName("taint_planks").setCreativeTab(ReForbiddenMagic.tab);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }


}