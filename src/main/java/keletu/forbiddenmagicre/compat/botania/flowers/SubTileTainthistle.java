/**
 * This class based on a class created by <Pokefenn>.
 * It was distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 */

package keletu.forbiddenmagicre.compat.botania.flowers;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSlimyBubble;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileGenerating;

public class SubTileTainthistle extends SubTileGenerating {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int range = 4;
    public static LexiconEntry lexicon;
    int burnTime = 0;

    @Override
    public void onUpdate() {
        super.onUpdate();
        boolean didSomething = false;
        if (this.ticksExisted % 80 == 0) {
            if (!supertile.getWorld().isRemote) {
                for (int ex = supertile.getPos().getX() - range; ex <= supertile.getPos().getX() + range; ex++) {
                    for (int wy = supertile.getPos().getY() - range; wy <= supertile.getPos().getY() + range; wy++) {
                        for (int zee = supertile.getPos().getZ() - range; zee <= supertile.getPos().getZ() + range; zee++) {
                            if (isFlux(ex, wy, zee)) {
                                int depth = supertile.getWorld().getBlockState(new BlockPos(ex, wy, zee)).getBlock().getMetaFromState(supertile.getWorld().getBlockState(new BlockPos(ex, wy, zee)));
                                supertile.getWorld().setBlockToAir(new BlockPos(ex, wy, zee));
                                didSomething = true;
                                burnTime = Math.min(burnTime + depth * (20), 4000);
                            }
                        }
                    }
                }

                if (didSomething) {
                    playSound();
                    sync();
                }
            }
        }
        if (burnTime > 0)
            burnTime--;
        //if (supertile.getWorld().isRemote) {
        //    if (supertile.getWorld().rand.nextInt(8) == 0 && burnTime > 0)
        //        doBurnParticles();
        //}
    }

    @Override
    public boolean isPassiveFlower() {
        return false;
    }

    public void doBurnParticles() {

        float h = supertile.getWorld().rand.nextFloat() * 0.075f;
        FXSlimyBubble ef = new FXSlimyBubble(supertile.getWorld(), supertile.getPos().getX() + supertile.getWorld().rand.nextFloat(), supertile.getPos().getY() + 0.1f + 0.225f * 4, supertile.getPos().getZ() + supertile.getWorld().rand.nextFloat(), 0.075f + h);
        ef.setAlphaF(0.8f);
        ef.setRBGColorF(0.3f - supertile.getWorld().rand.nextFloat() * 0.1f, 0.0f, 0.4f + supertile.getWorld().rand.nextFloat() * 0.1f);
        ParticleEngine.addEffect(supertile.getWorld(), ef);

    }

    public boolean isFlux(int x, int y, int z) {
        Block target = supertile.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
        return target == ConfigBlocks.FluidFluxGoo.instance.getBlock();
    }

    public void playSound() {
        supertile.getWorld().playSound(supertile.getPos().getX(), supertile.getPos().getY(), supertile.getPos().getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.BLOCKS, 0.05F, 0.5F + (float) Math.random() * 0.5F, false);
    }

    @Override
    public int getMaxMana() {
        return 150;
    }

    @Override
    public int getColor() {
        return 0x4D00FF;
    }

    @Override
    public LexiconEntry getEntry() {
        return lexicon;
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setInteger(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInteger(TAG_BURN_TIME);
    }

    @Override
    public boolean canGeneratePassively() {
        return burnTime > 0;
    }

    @Override
    public int getDelayBetweenPassiveGeneration() {
        return 2;
    }

    @Override
    public int getValueForPassiveGeneration() {
        return 3;
    }
}