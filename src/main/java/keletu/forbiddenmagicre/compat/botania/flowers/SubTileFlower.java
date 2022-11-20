package keletu.forbiddenmagicre.compat.botania.flowers;


import vazkii.botania.api.subtile.SubTileEntity;

public class SubTileFlower {
    private int manaPerProduction;

    private String key;

    private String production;

    private Class<? extends SubTileEntity> subTileClass;

    public SubTileFlower(String key, Class<? extends SubTileEntity> subTileClass) {
        this.key = key;
        this.subTileClass = subTileClass;
    }

    public SubTileFlower(String key, String production, int manaPerProduction, Class<? extends SubTileEntity> subTileClass) {
        this.key = key;
        this.production = production;
        this.manaPerProduction = manaPerProduction;
        this.subTileClass = subTileClass;
    }

    public boolean hasProduction() {
        return (this.production != null);
    }

    public String getKey() {
        return this.key;
    }

    public String getProduction() {
        return this.production;
    }

    public Class<? extends SubTileEntity> getSubTileClass() {
        return this.subTileClass;
    }

    public int getManaPerProduction() {
        return this.manaPerProduction;
    }
}

