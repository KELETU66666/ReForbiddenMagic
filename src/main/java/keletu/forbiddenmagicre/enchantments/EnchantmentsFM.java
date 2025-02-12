package keletu.forbiddenmagicre.enchantments;

import keletu.forbiddenmagicre.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EnchantmentsFM {

    public static final List<Enchantment> ENCHANTNENTS = new ArrayList<Enchantment>();
    public static final Enchantment wrath = new EnchantmentWrath();
    public static final Enchantment educational = new EnchantmentEducational();
    public static final Enchantment greedy = new EnchantmentGreedy();
    public static final Enchantment consuming = new EnchantmentConsuming();
    public static final Enchantment void_touched = new EnchantmentVoid();
}
