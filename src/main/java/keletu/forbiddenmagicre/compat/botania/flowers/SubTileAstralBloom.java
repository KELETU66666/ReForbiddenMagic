package keletu.forbiddenmagicre.compat.botania.flowers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraHelper;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;

import static vazkii.botania.common.lexicon.LexiconData.lexicon;

public class SubTileAstralBloom extends SubTileFunctional {
    private World world;

    public static LexiconEntry lexicon;

    private BlockPos pos;

    private static final int cost = 50000;

    @Override
    public int getColor(){
        return 0xA9C6C1;
    }

    @Override
    public void onUpdate(){
        super.onUpdate();

        if(redstoneSignal > 0)
            return;

        if (this.world == null)
            this.world = getWorld();
        if (this.pos == null)
            this.pos = getPos();
        if(mana >= cost && !supertile.getWorld().isRemote && this.ticksExisted % 800 == 0 && ThaumcraftApi.internalMethods.getFlux(this.world, this.pos) >= 5) {
                mana -= cost;
            AuraHelper.drainFlux(this.world, this.pos, 1.0F, false);
        }
    }


    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        this.world = getWorld();
        this.pos = pos;
    }

    @Override
    public int getMaxMana() {
        return cost;
    }

    @Override
    public LexiconEntry getEntry(){
        return lexicon;
    }
}