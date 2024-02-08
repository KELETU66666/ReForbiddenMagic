package keletu.forbiddenmagicre.blocks;

import keletu.forbiddenmagicre.forbiddenmagicre;
import keletu.forbiddenmagicre.init.ModBlocks;
import keletu.forbiddenmagicre.init.ModItems;
import keletu.forbiddenmagicre.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import thaumcraft.api.ThaumcraftMaterials;

import static fox.spiteful.lostmagic.LostMagic.tab;

public class BlockBrickTainted extends Block implements IHasModel {

    public BlockBrickTainted(){
        super(ThaumcraftMaterials.MATERIAL_TAINT);
        setHardness(2.0F);
        setResistance(10.0F);
        setCreativeTab(tab);
        this.setHarvestLevel("pickaxe", 0);
        setTranslationKey("tainted_brick");
        setRegistryName("tainted_brick");

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        forbiddenmagicre.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
