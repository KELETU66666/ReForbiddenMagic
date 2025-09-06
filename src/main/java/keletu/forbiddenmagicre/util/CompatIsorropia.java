package keletu.forbiddenmagicre.util;

import fr.wind_blade.isorropia.common.IsorropiaAPI;
import thaumcraft.api.aspects.Aspect;

public class CompatIsorropia {

    public static final Aspect ENVY;
    public static final Aspect GLUTTONY;
    public static final Aspect LUST;
    public static final Aspect NETHER;
    public static final Aspect PRIDE;
    public static final Aspect SLOTH;
    public static final Aspect WRATH;

    static {
        WRATH = IsorropiaAPI.WRATH;
        ENVY = IsorropiaAPI.ENVY;
        GLUTTONY = IsorropiaAPI.GLUTTONY;
        LUST = IsorropiaAPI.LUST;
        NETHER = IsorropiaAPI.NETHER;
        PRIDE = IsorropiaAPI.PRIDE;
        SLOTH = IsorropiaAPI.SLOTH;
    }
}
