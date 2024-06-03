package keletu.forbiddenmagicre.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBloodSeal extends Potion {
    public PotionBloodSeal() {
        super(true, 0xAC1919);
        this.setPotionName("potion.blood_seal");
        this.setRegistryName("blood_seal");
        this.setIconIndex(4, 0);
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("forbiddenmagicre", "textures/misc/potions.png"));
        return super.getStatusIconIndex();
    }
}
