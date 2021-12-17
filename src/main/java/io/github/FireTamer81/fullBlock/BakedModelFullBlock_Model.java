package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.ModelHelper;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BakedModelFullBlock_Model implements IDynamicBakedModel {

    public BakedModelFullBlock_Model() {}

    public static final ResourceLocation UNDERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/warenai_block_white");

    public static final ResourceLocation CRACKED4_OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/cracked4_texture");
    public static final ResourceLocation CRACKED3_OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/cracked3_texture");
    public static final ResourceLocation CRACKED2_OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/cracked2_texture");
    public static final ResourceLocation CRACKED1_OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/cracked1_texture");
    public static final ResourceLocation SCUFFED_OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/scuffed_texture");

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        //int tintIndex = extraData.getData(BakedModelFullBlock_Tile.TEXTURE);
        TextureAtlasSprite underlayTexture = getUnderlayTexture();
        TextureAtlasSprite overlayTexture = get_C4_OverlayTexture();

        List<BakedQuad> allQuads = new ArrayList<>();

        double l = 0;
        double r = 1;

        boolean renderNorth = true;
        boolean renderEast = true;
        boolean renderSouth = true;
        boolean renderWest = true;
        boolean renderUp = true;
        boolean renderDown = true;

        allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, 0, renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));

        if (state.getValue(BakedModelFullBlock.NEEDS_OVERLAY)) {
            allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, overlayTexture, 0, renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
        }

        return allQuads;
    }




    private TextureAtlasSprite getUnderlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(UNDERLAY_TEXTURE);
    }

    private TextureAtlasSprite get_C4_OverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(CRACKED4_OVERLAY_TEXTURE);
    }

    private TextureAtlasSprite get_C3_OverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(CRACKED3_OVERLAY_TEXTURE);
    }

    private TextureAtlasSprite get_C2_OverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(CRACKED2_OVERLAY_TEXTURE);
    }

    private TextureAtlasSprite get_C1_OverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(CRACKED1_OVERLAY_TEXTURE);
    }

    private TextureAtlasSprite get_S_OverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(SCUFFED_OVERLAY_TEXTURE);
    }







    /**
     * Not much done with these methods
     */
    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getUnderlayTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }
}
