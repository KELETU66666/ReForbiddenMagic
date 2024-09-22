package keletu.forbiddenmagicre.compat.botania;

import keletu.forbiddenmagicre.Lumberjack;
import keletu.forbiddenmagicre.compat.botania.flowers.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.ArrayList;
import java.util.List;

public class RegisterHandlerBota {

    public static List<SubTileFlower> subTiles = new ArrayList<>();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        if (Loader.isModLoaded("botania"))
            for (SubTileFlower subTileFlower : subTiles) {
                BotaniaAPI.registerSubTile(subTileFlower.getKey(), subTileFlower.getSubTileClass());
                BotaniaAPI.addSubTileToCreativeMenu(subTileFlower.getKey());
                System.out.println("REGISTER FLOWER : " + subTileFlower.getKey());
            }
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        if (Loader.isModLoaded("botania"))
            for (SubTileFlower subTileFlower : subTiles) {
                BotaniaAPIClient.registerSubtileModel(subTileFlower.getKey(), new ModelResourceLocation("botania:" + subTileFlower.getKey()));
            }

    }

    private static void addSubTile(String key, Class<? extends SubTileEntity> subTileClass) {
        subTiles.add(new SubTileFlower(key, subTileClass));
    }

    public static void registerFlowers() {
        addSubTile("astralbloom", SubTileAstralBloom.class);
        addSubTile("euclidaisy", SubTileEuclidaisy.class);
        addSubTile("tainthistle", SubTileTainthistle.class);
        addSubTile("whisperweed", SubTileWhisperweed.class);
        if (Loader.isModLoaded("bloodmagic"))
            addSubTile("bloodthorn", SubTileBloodthorn.class);
        if (Loader.isModLoaded("psi"))
            addSubTile("mindlotus", SubTileMindLotus.class);
    }

    public static void lexify() {
        if (Loader.isModLoaded("botania")) {
            try {
                SubTileEuclidaisy.lexicon = new MagicLexicon("euclidaisy", BotaniaAPI.categoryFunctionalFlowers, "Thaumcraft");
                SubTileEuclidaisy.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.euclidaisy.0"));
                ItemStack euclidaisy = ItemBlockSpecialFlower.ofType("euclidaisy");
                SubTileEuclidaisy.lexicon.setIcon(euclidaisy);

                SubTileAstralBloom.lexicon = new MagicLexicon("astralbloom", BotaniaAPI.categoryFunctionalFlowers, "Thaumcraft");
                SubTileAstralBloom.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.astralbloom.0"));
                ItemStack astralbloom = ItemBlockSpecialFlower.ofType("astralbloom");
                SubTileAstralBloom.lexicon.setIcon(astralbloom);

                SubTileTainthistle.lexicon = new MagicLexicon("tainthistle", BotaniaAPI.categoryFunctionalFlowers, "Thaumcraft");
                SubTileTainthistle.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.tainthistle.0"));
                ItemStack tainthistle = ItemBlockSpecialFlower.ofType("tainthistle");
                SubTileTainthistle.lexicon.setIcon(tainthistle);

                SubTileWhisperweed.lexicon = new MagicLexicon("whisperweed", BotaniaAPI.categoryFunctionalFlowers, "Thaumcraft");
                SubTileWhisperweed.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.whisperweed.0"));
                ItemStack whisperweed = ItemBlockSpecialFlower.ofType("whisperweed");
                SubTileWhisperweed.lexicon.setIcon(whisperweed);

                if (Loader.isModLoaded("bloodmagic")) {
                    SubTileBloodthorn.lexicon = new MagicLexicon("bloodthorn", BotaniaAPI.categoryFunctionalFlowers, "BloodMagic");
                    SubTileBloodthorn.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.bloodthorn.0"));
                    ItemStack bloodThorn = ItemBlockSpecialFlower.ofType("bloodthorn");
                    SubTileBloodthorn.lexicon.setIcon(bloodThorn);
                }

                if (Loader.isModLoaded("psi")) {
                    ItemStack lotus = ItemBlockSpecialFlower.ofType("mindlotus");
                    SubTileMindLotus.lexicon = new MagicLexicon("mindlotus", BotaniaAPI.categoryGenerationFlowers, "Psi");
                    SubTileMindLotus.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.mindlotus.0"));
                    SubTileMindLotus.lexicon.addPage(BotaniaAPI.internalHandler.runeRecipePage("forbidden.lexicon.mindlotus.1", new RecipeRuneAltar(lotus, 600, "petalLightBlue", "petalBlue", new ItemStack(Items.WHEAT_SEEDS, 1), "gemPsi", "ingotPsi")));
                    SubTileMindLotus.lexicon.setIcon(lotus);
                }

            } catch (Throwable e) {
                Lumberjack.log(Level.ERROR, e, "Schools of Magic decayed like a Daybloom.");
            }
        }
    }

    @SubscribeEvent
    @Optional.Method(modid = "psi")
    public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
        ItemStack lotus = ItemBlockSpecialFlower.ofType("mindlotus");
        BotaniaAPI.registerRuneAltarRecipe(lotus, 600, "petalLightBlue", "petalBlue", new ItemStack(Items.WHEAT_SEEDS), "gemPsi", "ingotPsi");

    }
}
