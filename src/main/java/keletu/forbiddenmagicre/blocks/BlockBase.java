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

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material) {
        super(material);
        setHardness(5.0F);
        setResistance(10.0F);
        setUnlocalizedName(name).setRegistryName(name).setCreativeTab(tab);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }


}