package keletu.forbiddenmagicre.blocks.tiles;


import keletu.forbiddenmagicre.ConfigFM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;
import thaumcraft.api.aspects.Aspect;

public class WrathSpawnerLogic {
    /** The mob spawner we deal with */
    final TileEntityWrathCage mobSpawnerEntity;

    public boolean mobSet = false;

    /** The delay to spawn. */
    public int spawnDelay = 20;
    private int mobID = 0;
    Aspect aspect = Aspect.DESIRE;
    private boolean slothful = false;
    private int fuel = 0;

    public double mobRotation;
    public double prevMobRotation;
    private final int minSpawnDelay = 200;
    private final int maxSpawnDelay = 300;

    /** A counter for spawn tries. */
    private final int spawnCount = 3;
    private Entity renderEntity;
    private int maxNearbyEntities = 6;

    /** The distance from which a player activates the spawner. */
    private final int activatingRangeFromPlayer = 16;

    /** The range coefficient for spawning entities around. */
    private int spawnRange = 4;

    WrathSpawnerLogic(TileEntityWrathCage par1TileEntityMobSpawner) {
        this.mobSpawnerEntity = par1TileEntityMobSpawner;
    }

    /**
     * Gets the entity name that should be spawned.
     */
    public int getEntityNameToSpawn() {
        return this.mobID;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public static Class <? extends Entity > getClassFromID(int entityID)
    {
        EntityEntry entry = GameData.getEntityRegistry().getValue(entityID);
        return entry == null ? null : entry.getEntityClass();
    }

    public static Entity createEntityByID(int entityID, World worldIn)
    {
        EntityEntry entry = GameData.getEntityRegistry().getValue(entityID);
        return entry == null ? null : entry.newInstance(worldIn);
    }

    public void setMobID(int par1Str) {
        this.mobID = par1Str;
        if (ConfigFM.spawnerMobs.containsKey(getClassFromID(mobID)))
            aspect = Aspect.getAspect(ConfigFM.spawnerMobs.get(getClassFromID(mobID)));
        else
            aspect = Aspect.DESIRE;
    }

    public void mobIsSet(boolean inp) {
        mobSet = inp;
    }

    public boolean isMobSet() {
        return mobSet;
    }

    public void updateSpawnerBlock(int par1) {
        this.mobSpawnerEntity.getWorld().addBlockEvent(this.mobSpawnerEntity.getPos(), Blocks.MOB_SPAWNER, par1, 0);
    }

    public World getSpawnerWorld() {
        return this.mobSpawnerEntity.getWorld();
    }

    public int getSpawnerX() {
        return this.mobSpawnerEntity.getPos().getX();
    }

    public int getSpawnerY() {
        return this.mobSpawnerEntity.getPos().getY();
    }

    public int getSpawnerZ() {
        return this.mobSpawnerEntity.getPos().getZ();
    }

    public void updateSpawner() {
        IBlockState iblockstate = getSpawnerWorld().getBlockState(new BlockPos(getSpawnerX(), getSpawnerY(), getSpawnerZ()));
        if (!mobSet)
            return;

        double d0;

        if (this.getSpawnerWorld().isRemote && (fuel > 0 || ConfigFM.wrathCost <= 0)) {
            double d1 = (float) this.getSpawnerX() + this.getSpawnerWorld().rand.nextFloat();
            double d2 = (float) this.getSpawnerY() + this.getSpawnerWorld().rand.nextFloat();
            d0 = (float) this.getSpawnerZ() + this.getSpawnerWorld().rand.nextFloat();
            // this.getSpawnerWorld().spawnParticle("smoke", d1, d2, d0, 0.0D,
            // 0.0D, 0.0D);
            // this.getSpawnerWorld().spawnParticle("flame", d1, d2, d0, 0.0D,
            // 0.0D, 0.0D);

            if (this.spawnDelay > 0) {
                --this.spawnDelay;
            }

            this.prevMobRotation = this.mobRotation;
            this.mobRotation = (this.mobRotation + (double) (1000.0F / ((float) this.spawnDelay + 200.0F))) % 360.0D;
        } else if (true) {
            if (ConfigFM.wrathCost > 0 && fuel <= 0) {
                if (mobSpawnerEntity.special >= ConfigFM.wrathCost) {
                    mobSpawnerEntity.special -= ConfigFM.wrathCost;
                    slothful = false;
                    fuel = ConfigFM.wrathEff;
                    mobSpawnerEntity.getWorld().notifyBlockUpdate(new BlockPos(getSpawnerX(), getSpawnerY(), getSpawnerZ()), iblockstate, iblockstate, 0);
                } else if (mobSpawnerEntity.wrath >= ConfigFM.wrathCost) {
                    mobSpawnerEntity.wrath -= ConfigFM.wrathCost;
                    slothful = false;
                    fuel = ConfigFM.wrathEff;
                    mobSpawnerEntity.getWorld().notifyBlockUpdate(new BlockPos(getSpawnerX(), getSpawnerY(), getSpawnerZ()), iblockstate, iblockstate, 0);
                } else if (mobSpawnerEntity.sloth >= ConfigFM.wrathCost) {
                    mobSpawnerEntity.sloth -= ConfigFM.wrathCost;
                    slothful = true;
                    fuel = ConfigFM.wrathEff;
                    mobSpawnerEntity.getWorld().notifyBlockUpdate(new BlockPos(getSpawnerX(), getSpawnerY(), getSpawnerZ()), iblockstate, iblockstate, 0);
                }
            }

            if (this.spawnDelay == -1) {
                this.updateDelay();
            }

            if (fuel <= 0 && ConfigFM.wrathCost > 0)
                return;

            if (this.spawnDelay > 0) {
                --this.spawnDelay;
                return;
            }

            for (int i = 0; i < this.spawnCount && (fuel > 0 || ConfigFM.wrathCost <= 0); ++i) {
                Entity entity = createEntityByID(this.getEntityNameToSpawn(), this.getSpawnerWorld());

                if (entity == null) {
                    return;
                }

                //int j = this.getSpawnerWorld().getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().getAABB((double) this.getSpawnerX(), (double) this.getSpawnerY(), (double) this.getSpawnerZ(), (double) (this.getSpawnerX() + 1), (double) (this.getSpawnerY() + 1), (double) (this.getSpawnerZ() + 1)).expand((double) (this.spawnRange * 2), 4.0D, (double) (this.spawnRange * 2))).size();
                int j = this.getSpawnerWorld().getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), this.getSpawnerX() + 1, this.getSpawnerY() + 1, this.getSpawnerZ() + 1).expand(this.spawnRange * 2, 4.0D, this.spawnRange * 2)).size();

                if (j >= this.maxNearbyEntities) {
                    this.updateDelay();
                    return;
                }

                d0 = (double) this.getSpawnerX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double) this.spawnRange;
                double d3 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
                double d4 = (double) this.getSpawnerZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double) this.spawnRange;
                EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;
                entity.setLocationAndAngles(d0, d3, d4, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);

                if (
                    // entityliving == null ||
                        entityCanSpawn(entityliving))
                // entityliving.getCanSpawnHere())
                {
                    this.spawnMob(entity);

                    if (entityliving != null) {
                        entityliving.spawnExplosionParticle();
                        fuel--;
                    }

                    this.updateDelay();
                } else if (entityliving != null)
                    entityliving.isDead = true;
            }

        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    public boolean entityCanSpawn(EntityLiving entity) {
        return entity.world.checkNoEntityCollision(entity.getEntityBoundingBox()) && entity.world.getEntitiesWithinAABB(entity.getClass(), entity.getEntityBoundingBox()).isEmpty() && !entity.world.containsAnyLiquid(entity.getEntityBoundingBox());
    }

    private void updateDelay() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        } else {
            int i = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(i);
        }

        if (slothful)
            spawnDelay += 200;

        this.updateSpawnerBlock(1);
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        mobID = par1NBTTagCompound.getInteger("EntityId");
        if (ConfigFM.spawnerMobs.containsKey(getClassFromID(mobID)))
            aspect = Aspect.getAspect(ConfigFM.spawnerMobs.get(getClassFromID((mobID))));
        else
            aspect = Aspect.DESIRE;
        mobSet = par1NBTTagCompound.getBoolean("MobSet");
        slothful = par1NBTTagCompound.getBoolean("Slothful");
        this.fuel = par1NBTTagCompound.getShort("Fuel");
        this.spawnDelay = par1NBTTagCompound.getShort("Delay");

        // if (par1NBTTagCompound.hasKey("MinSpawnDelay"))
        // {
        // this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
        // this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
        // this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
        // }

        if (par1NBTTagCompound.hasKey("MaxNearbyEntities")) {
            this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
        }

        if (par1NBTTagCompound.hasKey("SpawnRange")) {
            this.spawnRange = par1NBTTagCompound.getShort("SpawnRange");
        }

        if (this.getSpawnerWorld() != null && this.getSpawnerWorld().isRemote) {
            this.renderEntity = null;
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("EntityId", this.getEntityNameToSpawn());
        par1NBTTagCompound.setBoolean("MobSet", mobSet);
        par1NBTTagCompound.setBoolean("Slothful", slothful);
        par1NBTTagCompound.setShort("Fuel", (short) this.fuel);
        par1NBTTagCompound.setShort("Delay", (short) this.spawnDelay);
        // par1NBTTagCompound.setShort("MinSpawnDelay",
        // (short)this.minSpawnDelay);
        // par1NBTTagCompound.setShort("MaxSpawnDelay",
        // (short)this.maxSpawnDelay);
        // par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
        par1NBTTagCompound.setShort("SpawnRange", (short) this.spawnRange);

    }

    public Entity spawnMob(Entity par1Entity) {
        if (par1Entity instanceof EntityLivingBase && par1Entity.world != null) {
            ((EntityLiving) par1Entity).onInitialSpawn(getSpawnerWorld().getDifficultyForLocation(par1Entity.getPosition()), null);
            AnvilChunkLoader.spawnEntity(par1Entity, this.getSpawnerWorld());
        }

        return par1Entity;
    }

    /**
     * Sets the delay to minDelay if parameter given is 1, else return false.
     */
    public boolean setDelayToMin(int par1) {
        if (par1 == 1 && this.getSpawnerWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        } else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public Entity getEntityForRender() {
        if (this.renderEntity == null) {
            Entity entity = createEntityByID(this.getEntityNameToSpawn(), null);
            entity = this.spawnMob(entity);
            this.renderEntity = entity;
        }

        return this.renderEntity;
    }

}
