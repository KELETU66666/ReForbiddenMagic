package keletu.forbiddenmagicre.compat.botania;

import fox.spiteful.lostmagic.Lumberjack;
import keletu.forbiddenmagicre.compat.botania.flowers.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
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
        if(Loader.isModLoaded("botania"))
        for (SubTileFlower subTileFlower : subTiles) {
            BotaniaAPI.registerSubTile(subTileFlower.getKey(), subTileFlower.getSubTileClass());
            BotaniaAPI.addSubTileToCreativeMenu(subTileFlower.getKey());
            System.out.println("REGISTER FLOWER : " + subTileFlower.getKey());
        }
    }

    @SubscribeEvent
    public static void onModelRegister( ModelRegistryEvent event )
    {
        if(Loader.isModLoaded("botania"))
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
            if(Loader.isModLoaded("psi"))
            addSubTile("mindlotus", SubTileMindLotus.class);
            addSubTile("whisperweed", SubTileWhisperweed.class);
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

                if (Loader.isModLoaded("psi")) {
                ItemStack lotus = ItemBlockSpecialFlower.ofType("mindlotus");
                RecipeRuneAltar lotus_recipe = BotaniaAPI.registerRuneAltarRecipe(lotus, 600, "petalLightBlue", "petalBlue", new ItemStack(Items.WHEAT_SEEDS, 1), "gemPsi", "ingotPsi");

                    SubTileMindLotus.lexicon = new MagicLexicon("mindlotus", BotaniaAPI.categoryGenerationFlowers, "Psi");
                    SubTileMindLotus.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.mindlotus.0"));
                    SubTileMindLotus.lexicon.addPage(BotaniaAPI.internalHandler.runeRecipePage("forbidden.lexicon.mindlotus.1", lotus_recipe));
                    SubTileMindLotus.lexicon.setIcon(lotus);
                }

                SubTileWhisperweed.lexicon = new MagicLexicon("whisperweed", BotaniaAPI.categoryFunctionalFlowers, "Thaumcraft");
                SubTileWhisperweed.lexicon.addPage(BotaniaAPI.internalHandler.textPage("forbidden.lexicon.whisperweed.0"));
                ItemStack whisperweed = ItemBlockSpecialFlower.ofType("whisperweed");
                SubTileWhisperweed.lexicon.setIcon(whisperweed);
            } catch (Throwable e) {
                Lumberjack.log(Level.ERROR, e, "Schools of Magic decayed like a Daybloom.");
            }
        }
    }
}
