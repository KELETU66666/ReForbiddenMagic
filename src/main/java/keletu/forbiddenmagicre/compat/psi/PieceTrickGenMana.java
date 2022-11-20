package keletu.forbiddenmagicre.compat.psi;

import keletu.forbiddenmagicre.compat.botania.flowers.SubTileMindLotus;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.subtile.ISubTileContainer;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickGenMana extends PieceTrick {

    SpellParam position;
    SpellParam power;
    SpellParam stability;

    public PieceTrickGenMana(Spell spell) {
        super(spell);
    }

    @Override
    public void initParams() {
        addParam(position = new ParamVector("psi.spellparam.position", SpellParam.GREEN, false, false));
        addParam(power = new ParamNumber("psi.spellparam.power", SpellParam.RED, false, true));
        addParam(stability = new ParamNumber("psi.spellparam.stability", SpellParam.PURPLE, true, true));
    }

    @Override
    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        Double powerVal = this.<Double>getParamEvaluation(power);
        Double stabVal = this.<Double>getParamEvaluation(stability);
        if(powerVal == null || powerVal <= 0)
            throw new SpellCompilationException(SpellCompilationException.NON_POSITIVE_VALUE, x, y);
        if(stabVal == null || stabVal < 0)
            stabVal = 0D;

        double absPower = Math.max(Math.abs(powerVal), 1);
        double abstability = Math.abs(stabVal);
        double abstotal = absPower + abstability;
        meta.addStat(EnumSpellStat.POTENCY, (int) multiplySafe(abstotal, abstotal, 5.0));
        meta.addStat(EnumSpellStat.COST, (int) multiplySafe(abstotal, 100));
    }

    @Override
    public Object execute(SpellContext context) throws SpellRuntimeException {
        if(context.caster.world.isRemote)
            return null;
        Vector3 targetPos = this.<Vector3>getParamValue(context, position);

        Double powerVal = this.<Double>getParamValue(context, power);
        Double stabVal = this.<Double>getParamValue(context, stability);
        if(stabVal == null || stabVal < 0)
            stabVal = 0D;
        int burnout = 50 * Math.max(10 - stabVal.intValue(), 0);

        TileEntity tile = context.caster.world.getTileEntity(targetPos.toBlockPos());
        if(tile == null)
            throw new SpellRuntimeException(SpellRuntimeException.NULL_TARGET);
        else if(!(tile instanceof ISubTileContainer))
            throw new SpellRuntimeException(SpellRuntimeException.NULL_TARGET);
        else {
            SubTileEntity subtile = ((ISubTileContainer)tile).getSubTile();
            if(subtile instanceof SubTileMindLotus){
                SubTileMindLotus lotus = (SubTileMindLotus)subtile;
                lotus.passiveDecayTicks += burnout;
                lotus.addMana(powerVal.intValue() * 150);
            }
            else
                throw new SpellRuntimeException(SpellRuntimeException.NULL_TARGET);
        }

        return null;
    }
}
