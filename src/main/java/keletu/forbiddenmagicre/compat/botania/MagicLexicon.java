package keletu.forbiddenmagicre.compat.botania;


import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;

public class MagicLexicon extends LexiconEntry implements IAddonEntry {

    private String other;

    public MagicLexicon(String name, LexiconCategory category, String otherMod){
        super(name, category);
        BotaniaAPI.addEntry(this, category);
        other = otherMod;
    }

    @Override
    public String getSubtitle(){
        return "[ReForbidden Magic x " + other + " x Botania]";
    }

    @Override
    public String getUnlocalizedName() {
        return "forbidden.lexicon." + super.getUnlocalizedName();
    }
}