package keletu.forbiddenmagicre.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class InitVanillaRecipes {

    public static void init(){
        GameRegistry.addSmelting(ModBlocks.BLOCK_LOG_TAINTED,new ItemStack(ModItems.TAINTCHARCOAL,1),0.15f);
        GameRegistry.registerFuelHandler(new IFuelHandler() {
            Random randy = new Random();
            @Override
            public int getBurnTime(ItemStack fuel) {

                if(fuel.getItem() == ModItems.TAINTCHARCOAL) {
                    return 1 + randy.nextInt(2400);
                }
                return 0;
            }
        });
    }
}
