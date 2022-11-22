package keletu.forbiddenmagicre.util;


import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;

import java.util.function.BooleanSupplier;

public class Conditions implements IConditionFactory
{
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json)
    {
        String modId = json.get("mod").getAsString();
        boolean result = Loader.isModLoaded(modId);
        return () -> result;
    }

}