package keletu.forbiddenmagicre.compat.botania.flowers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileMindLotus extends SubTileGenerating {

    public static LexiconEntry lexicon;

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!supertile.getWorld().isRemote) {
            int muhBalance = BotaniaAPI.internalHandler.getPassiveFlowerDecay();
            if (muhBalance <= 0 && passiveDecayTicks > 72000) {
                supertile.getWorld().playEvent(2001, supertile.getPos(), Block.getIdFromBlock(supertile.getBlockType()));
                if (supertile.getWorld().getBlockState(supertile.getPos().down()).getBlock().isSideSolid(supertile.getWorld().getBlockState(supertile.getPos()), supertile.getWorld(), supertile.getPos().down(), EnumFacing.UP))
                    supertile.getWorld().setBlockState(supertile.getPos(), Blocks.DEADBUSH.getDefaultState());
                else supertile.getWorld().setBlockToAir(supertile.getPos());
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 6500;
    }

    @Override
    public boolean isPassiveFlower() {
        return true;
    }

    @Override
    public LexiconEntry getEntry() {
        return lexicon;
    }
}
