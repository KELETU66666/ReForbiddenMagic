package keletu.forbiddenmagicre.compat.bloodmagic;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.ritual.*;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.common.lib.potions.PotionWarpWard;

import java.util.List;
import java.util.function.Consumer;

@RitualRegister("Sanity")
public class RitualSanity extends Ritual
{
    public RitualSanity() {
        super("SpiteSanity", 1, 20000, "ritual.reforbiddenmagic.pealOfDelayedInsanity");
    }

    @Override
    public void performRitual(IMasterRitualStone ritualStone)
    {
        SoulNetwork owner        = NetworkHelper.getSoulNetwork(ritualStone.getOwner());
        if (owner == null) {
            return;
        }

        int currentEssence = owner.getCurrentEssence();
        World world = ritualStone.getWorldObj();
        BlockPos pos = ritualStone.getBlockPos();

        boolean crazy = world.getWorldTime() % (600 * 4) == 0;

        int range = 15;
        int vertRange = 15;

        float posX = pos.getX() + 0.5f;
        float posY = pos.getY() + 0.5f;
        float posZ = pos.getZ() + 0.5f;
        List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 0.5f, posY - 0.5f, posZ - 0.5f, posX + 0.5f, posY + 0.5f, posZ + 0.5f).expand(range, vertRange, range).expand(-range, vertRange, -range));
        int entityCount = 0;

        int cost = this.getRefreshCost();

        if (currentEssence < cost)
        {
            owner.causeNausea();

        } else
        {
            for (EntityPlayer player : list)
            {
                PotionEffect effect = player.getActivePotionEffect(PotionWarpWard.instance);
                if (effect == null || effect.getDuration() <= 600)
                {
                    PotionEffect ward = new PotionEffect(PotionWarpWard.instance, 600 + 2, 0);
                    ward.getCurativeItems().clear();
                    player.addPotionEffect(ward);
                    entityCount++;
                    if(crazy && world.rand.nextInt(35) <= 3)
                        ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1 + world.rand.nextInt(3), IPlayerWarp.EnumWarpType.TEMPORARY);
                }
            }

            if (entityCount > 0)
            {
                owner.syphon(new SoulTicket(new TextComponentTranslation("soultick.ritual_sanity"), cost * entityCount));
            }
        }
    }

    @Override
    public int getRefreshCost() {
        return 500;
    }

    @Override
    public int getRefreshTime()
    {
        return 20;
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