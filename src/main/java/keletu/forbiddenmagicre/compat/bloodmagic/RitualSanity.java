package keletu.forbiddenmagicre.compat.bloodmagic;

/*import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.demonAura.WorldDemonWillHandler;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.ritual.types.RitualWellOfSuffering;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.common.lib.potions.PotionWarpWard;

import java.util.List;
import java.util.function.Consumer;

@RitualRegister("Sanity")
public class RitualSanity extends Ritual
{
    public static final String   ALTAR_RANGE    = "altar";
    public static final String   EFFECT_RANGE   = "effect";

    public RitualSanity() {
        super("SpiteSanity", 1, 20000, "Plea of Delayed Insanity");
        addBlockRange(EFFECT_RANGE, new AreaDescriptor.Rectangle(new BlockPos(-10, -10, -10), 24));
        setMaximumVolumeAndDistanceOfRange(EFFECT_RANGE, 1, 1, 1);
    }

    @Override
    public void performRitual(IMasterRitualStone masterRitualStone) {
        SoulNetwork owner = NetworkHelper.getSoulNetwork(masterRitualStone.getOwner());
        if (owner == null) {
            return;
        }


        World world = masterRitualStone.getWorldObj();

        BlockPos          pos           = masterRitualStone.getBlockPos();

        boolean crazy = world.getWorldTime() % (2400) == 0;

        AreaDescriptor damageRange = masterRitualStone.getBlockRange(EFFECT_RANGE);
        AxisAlignedBB  range       = damageRange.getAABB(pos);

        List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, range);

        int    entityCount = 0;
        int currentEssence = owner.getCurrentEssence();
        int cost = this.getRefreshCost();

        if (currentEssence < cost)
        {
            owner.causeNausea();

        } else {
            for (EntityPlayer livingEntity : list) {
                entityCount++;
                PotionEffect ward = new PotionEffect(PotionWarpWard.instance, 600 + 2, 0);
                livingEntity.addPotionEffect(ward);

                if (crazy && world.rand.nextInt(35) <= 3)
                    ThaumcraftApi.internalMethods.addWarpToPlayer(livingEntity, 1 + world.rand.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);

            }

            owner.syphon(new SoulTicket(new TextComponentTranslation("soultick.ritual_sanity"), getRefreshCost() * entityCount));

        }

    }

    @Override
    public int getRefreshCost() {
        return 0;
    }

    @Override
    public int getRefreshTime()
    {
        return 15000;
    }

    @Override
    public void gatherComponents(Consumer<RitualComponent> components) {
        components.accept(new RitualComponent(new BlockPos(4, 1, 0), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(-4, 1, 0), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(0, 1, 4), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(0, 1, -4), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(4, 2, 0), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(-4, 2, 0), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(0, 2, 4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(0, 2, -4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(4, 3, 0), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(-4, 3, 0), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(0, 3, 4), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(0, 3, -4), EnumRuneType.DUSK));
        components.accept(new RitualComponent(new BlockPos(4, 4, 0), EnumRuneType.AIR));
        components.accept(new RitualComponent(new BlockPos(-4, 4, 0), EnumRuneType.AIR));
        components.accept(new RitualComponent(new BlockPos(0, 4, 4), EnumRuneType.AIR));
        components.accept(new RitualComponent(new BlockPos(0, 4, -4), EnumRuneType.AIR));
        components.accept(new RitualComponent(new BlockPos(4, 4, 1), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(-4, 4, 1), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(1, 4, 4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(1, 4, -4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(4, 4, -1), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(-4, 4, -1), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(-1, 4, 4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(-1, 4, -4), EnumRuneType.EARTH));
        components.accept(new RitualComponent(new BlockPos(3, 4, 0), EnumRuneType.FIRE));
        components.accept(new RitualComponent(new BlockPos(-3, 4, 0), EnumRuneType.FIRE));
        components.accept(new RitualComponent(new BlockPos(0, 4, 3), EnumRuneType.FIRE));
        components.accept(new RitualComponent(new BlockPos(0, 4, -3), EnumRuneType.FIRE));
    }

    @Override
    public Ritual getNewCopy() {
        return new RitualSanity();
    }
}
*\
 */