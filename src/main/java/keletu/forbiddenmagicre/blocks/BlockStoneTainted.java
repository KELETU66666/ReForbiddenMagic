package keletu.forbiddenmagicre.blocks;

import keletu.forbiddenmagicre.ReForbiddenMagic;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import thaumcraft.api.ThaumcraftMaterials;



public class BlockStoneTainted extends Block implements IHasModel {

    public BlockStoneTainted(){
        super(ThaumcraftMaterials.MATERIAL_TAINT);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(ReForbiddenMagic.tab);
        this.setHarvestLevel("pickaxe", 0);
        setTranslationKey("tainted_stone");
        setRegistryName("tainted_stone");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        ReForbiddenMagic.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
