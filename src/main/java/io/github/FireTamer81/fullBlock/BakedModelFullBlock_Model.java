package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.ModelHelper;
import io.github.FireTamer81.StrongBlockTextureHelper;
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

    //public static final ResourceLocation UNDERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/warenai_block_black");
    public static final ResourceLocation UNDERLAY_TEXTURE = BakedModelFullBlock_Tile.UNDERLAY_TEXTURE;
    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/cracked4_texture");

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        //TextureAtlasSprite underlayTexture = getUnderlayTexture();
        TextureAtlasSprite underlayTexture = StrongBlockTextureHelper.getSmoothWarenaiBlockAtlasSprites().get(extraData.getData(BakedModelFullBlock_Tile.TEXTURE));
        TextureAtlasSprite overlayTexture = getOverlayTexture();

        List<BakedQuad> allQuads = new ArrayList<>();

        double l = 0;
        double r = 1;

        boolean renderNorth = side == Direction.NORTH && extraData.getData(BakedModelFullBlock_Tile.NORTH_VISIBLE);
        boolean renderEast = side == Direction.EAST && extraData.getData(BakedModelFullBlock_Tile.EAST_VISIBLE);
        boolean renderSouth = side == Direction.SOUTH && extraData.getData(BakedModelFullBlock_Tile.SOUTH_VISIBLE);
        boolean renderWest = side == Direction.WEST && extraData.getData(BakedModelFullBlock_Tile.WEST_VISIBLE);
        boolean renderUp = side == Direction.UP && extraData.getData(BakedModelFullBlock_Tile.UP_VISIBLE);
        boolean renderDown = side == Direction.DOWN && extraData.getData(BakedModelFullBlock_Tile.DOWN_VISIBLE);

        allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, underlayTexture, renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));
        allQuads.addAll(ModelHelper.createCuboid(0f, 1f, 0f, 1f, 0f, 1f, overlayTexture, renderNorth, renderEast, renderSouth, renderWest, renderUp, renderDown));

        return allQuads;
    }




    private TextureAtlasSprite getUnderlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(UNDERLAY_TEXTURE);
    }

    private TextureAtlasSprite getOverlayTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(OVERLAY_TEXTURE);
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
