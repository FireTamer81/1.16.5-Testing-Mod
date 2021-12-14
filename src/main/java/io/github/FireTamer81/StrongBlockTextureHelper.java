package io.github.FireTamer81;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class StrongBlockTextureHelper {

    private static ResourceLocation loc(String nameSpace, String path) {
        return new ResourceLocation(nameSpace, path);
    }

    public static List<TextureAtlasSprite> getSmoothWarenaiBlockAtlasSprites() {
        List<TextureAtlasSprite> smoothWarenaiBlockTextures = new ArrayList<>();

        smoothWarenaiBlockTextures.add(Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(loc(TestModMain.MOD_ID, "block/warenai_block_black")));
        smoothWarenaiBlockTextures.add(Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(loc(TestModMain.MOD_ID, "block/warenai_block_white")));

        return smoothWarenaiBlockTextures;
    }

    public static List<ResourceLocation> getSmoothWarenaiBlockResourceLocations() {
        List<ResourceLocation> smoothWarenaiBlockTextures = new ArrayList<>();

        smoothWarenaiBlockTextures.add(loc(TestModMain.MOD_ID, "block/warenai_block_black"));
        smoothWarenaiBlockTextures.add(loc(TestModMain.MOD_ID, "block/warenai_block_white"));

        return smoothWarenaiBlockTextures;
    }
}
