package keletu.forbiddenmagicre.enchantments;

import com.google.common.collect.ImmutableSet;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;

import java.util.Set;

public class FMEnchantments {
    public static EnumInfusionEnchantment GREEDY = EnumHelper.addEnum(EnumInfusionEnchantment.class, "GREEDY", new Class<?>[]{Set.class, int.class, String.class}, ImmutableSet.of("weapon"), 1, "INFUSIONENCHANTMENTFM");
    public static EnumInfusionEnchantment EDUCATIONAL = EnumHelper.addEnum(EnumInfusionEnchantment.class, "EDUCATIONAL", new Class<?>[]{Set.class, int.class, String.class}, ImmutableSet.of("weapon"), 3, "INFUSIONENCHANTMENTFM");
    public static EnumInfusionEnchantment CONSUMING = EnumHelper.addEnum(EnumInfusionEnchantment.class, "CONSUMING", new Class<?>[]{Set.class, int.class, String.class}, ImmutableSet.of("axe", "pickaxe", "shovel"), 1, "INFUSIONENCHANTMENTFM");
    public static EnumInfusionEnchantment VOID_TOUCHED = EnumHelper.addEnum(EnumInfusionEnchantment.class, "VOID_TOUCHED", new Class<?>[]{Set.class, int.class, String.class}, ImmutableSet.of("morph_tools"), 1, "INFUSIONENCHANTMENTFM");

}
