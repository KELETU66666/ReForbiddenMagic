package keletu.forbiddenmagicre.compat.botania.flowers;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;

import java.util.List;

public class SubTileWhisperweed extends SubTileFunctional {
    private static final int cost = 6000;
    private final static int range = 3;
    public static LexiconEntry lexicon;

    ResearchCategory[] rc = ResearchCategories.researchCategories.values().toArray(new ResearchCategory[0]);

    int tProg = IPlayerKnowledge.EnumKnowledgeType.THEORY.getProgression();

    @Override
    public void onUpdate(){
        super.onUpdate();

        if(redstoneSignal > 0)
            return;

        if(!supertile.getWorld().isRemote && mana >= cost && this.ticksExisted % 300 == 0) {
            List<EntityPlayer> players = supertile.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(supertile.getPos().getX() - range, supertile.getPos().getY() - range, supertile.getPos().getZ() - range, supertile.getPos().getX() + range + 1, supertile.getPos().getY() + range + 1, supertile.getPos().getZ() + range + 1));
            if(players.size() > 0) {
                EntityPlayer player = players.get(supertile.getWorld().rand.nextInt(players.size()));
                int amt = 1 + player.world.rand.nextInt(3);
                if(player.world.rand.nextInt(10) < 2){
                    amt += 1 + player.world.rand.nextInt(3);
                    ThaumcraftApi.internalMethods.addWarpToPlayer(player, 1 + player.world.rand.nextInt(5), IPlayerWarp.EnumWarpType.TEMPORARY);
                }

                for(int a = 0; a < amt; ++a) {
                    ThaumcraftApi.internalMethods.addKnowledge(player, IPlayerKnowledge.EnumKnowledgeType.THEORY, rc[player.getRNG().nextInt(rc.length)], MathHelper.getInt(player.getRNG(), tProg / 12, tProg / 6));
                }
                mana -= cost;
            }

        }
    }

    @Override
    public int getColor(){
        return 0x745380;
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
