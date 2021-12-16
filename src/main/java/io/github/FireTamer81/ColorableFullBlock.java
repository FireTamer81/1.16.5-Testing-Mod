package io.github.FireTamer81;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ColorableFullBlock extends Block {

    public ColorableFullBlock(Properties properties) {
        super(properties);
    }












    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.byBlock(this), 0, new ModelResourceLocation(getRegistryName(), "hopping=false"));
        ModelLoader.setCustomModelResourceLocation(Item.byBlock(this), 1, new ModelResourceLocation(getRegistryName(), "hopping=true"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileBonsaiPot.class, new TESRBonsaiPot());
    }
}
