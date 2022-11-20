package keletu.forbiddenmagicre.compat.botania.flowers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.Thaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.ItemGenericEssentiaContainer;
import thaumcraft.api.items.ItemsTC;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;

public class SubTileEuclidaisy extends SubTileFunctional {
    private static final int cost = 5000;
    public static LexiconEntry lexicon;
    private World world;

    private BlockPos pos;

    @Override
    public void onUpdate(){
        super.onUpdate();

        if(redstoneSignal > 0)
            return;

        if(mana >= cost && !supertile.getWorld().isRemote && this.ticksExisted % 400 == 0) {
           AspectList aspect;
            if(supertile.getWorld().rand.nextInt(10) < 4)
                aspect = (new AspectList()).add(Aspect.AURA, 1);
            else {
                Aspect[] aspects = Aspect.aspects.values().toArray(new Aspect[0]);
                aspect = (new AspectList()).add(aspects[supertile.getWorld().rand.nextInt(aspects.length)], 1);
            }
            ItemStack ess = new ItemStack(ItemsTC.crystalEssence);
            ((ItemGenericEssentiaContainer)ess.getItem()).setAspects(ess, aspect);
            dropItem(supertile.getWorld(), supertile.getPos().getX(), supertile.getPos().getY(), supertile.getPos().getZ(), ess);
            mana -= cost;
        }
    }

    public void dropItem(World world, int x, int y, int z, ItemStack item){
        float f = 0.7F;
        double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, item);
        entityitem.setDefaultPickupDelay();
        world.spawnEntity(entityitem);
    }

    @Override
    public int getColor(){
        return 0xFF8CFF;
    }

    @Override
    public int getMaxMana() {
        return cost;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public LexiconEntry getEntry(){
        return lexicon;
    }

}
