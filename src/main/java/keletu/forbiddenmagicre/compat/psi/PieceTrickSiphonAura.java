package keletu.forbiddenmagicre.compat.psi;

import keletu.forbiddenmagicre.Lumberjack;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import vazkii.arl.network.NetworkHandler;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.cad.EnumCADStat;
import vazkii.psi.api.cad.ICAD;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.common.core.handler.PlayerDataHandler.PlayerData;
import vazkii.psi.common.network.message.MessageDataSync;

public class PieceTrickSiphonAura extends PieceTrick {

    SpellParam power;
    private final Aspect[] primals = {Aspect.EARTH, Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY};

    public PieceTrickSiphonAura(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(power = new ParamNumber(SpellParam.GENERIC_NAME_POWER, SpellParam.RED, false, true));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        Double powerVal = this.<Double>getParamEvaluation(power);
        if(powerVal == null || powerVal <= 0)
            throw new SpellCompilationException(SpellCompilationException.NON_POSITIVE_VALUE, x, y);
        meta.addStat(EnumSpellStat.POTENCY, (int) (powerVal * 70));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        if(context.caster.world.isRemote || context.caster == null)
            return null;
        PlayerData data;
        if(PsiAPI.internalHandler.getDataForPlayer(context.caster) instanceof PlayerData)
            data = (PlayerData) (PsiAPI.internalHandler.getDataForPlayer(context.caster));
        else {
            Lumberjack.log(Level.INFO, "wat");
            return null;
        }
        int energy = 0;
        for(Aspect aspect : primals){
            energy += AuraHelper.drainVis(context.caster.world, context.caster.getPosition(), 2 * this.<Double>getParamValue(context, power).intValue(), false);
        }
        if(energy <= 0)
            return null;
        energy *= 8 * this.<Double>getParamValue(context, power).intValue();
        Lumberjack.log(Level.INFO, "Energy: " + energy);
        //data.deductPsi(Math.abs(energy) * -1, this.<Double>getParamValue(context, power).intValue() * 5, true);
        ItemStack cadStack = data.getCAD();
        if(cadStack != null) {
            ICAD cad = (ICAD) cadStack.getItem();
            int maxPsi = cad.getStatValue(cadStack, EnumCADStat.OVERFLOW);
            int currPsi = cad.getStoredPsi(cadStack);
            if(currPsi < maxPsi) {
                cad.regenPsi(cadStack, Math.min(maxPsi - currPsi, energy));
                energy = Math.max(0, energy - (maxPsi - currPsi));
            }
        }
        data.availablePsi = Math.min(data.getTotalPsi(), data.availablePsi + energy);
        data.save();
        if(context.caster instanceof EntityPlayerMP){
            NetworkHandler.INSTANCE.sendTo(new MessageDataSync(data), (EntityPlayerMP)context.caster);
        }

        return null;
    }

}
