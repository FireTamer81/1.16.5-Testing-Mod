package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.TestModMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
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

    public static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "block/warenai_block_black");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(TEXTURE);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        TextureAtlasSprite texture = getTexture();
        List<BakedQuad> allQuads = new ArrayList<>();
        double l = 1;
        double r = 1;

        allQuads.add(ModelHelper.createQuad(ModelHelper.v(l, r, l), ModelHelper.v(l, r, r), ModelHelper.v(r, r, r), ModelHelper.v(r, r, l), texture));
        allQuads.add(ModelHelper.createQuad(ModelHelper.v(l, l, l), ModelHelper.v(r, l, l), ModelHelper.v(r, l, r), ModelHelper.v(l, l, r), texture));
        allQuads.add(ModelHelper.createQuad(ModelHelper.v(r, r, r), ModelHelper.v(r, l, r), ModelHelper.v(r, l, l), ModelHelper.v(r, r, l), texture));
        allQuads.add(ModelHelper.createQuad(ModelHelper.v(l, r, l), ModelHelper.v(l, l, l), ModelHelper.v(l, l, r), ModelHelper.v(l, r, r), texture));
        allQuads.add(ModelHelper.createQuad(ModelHelper.v(r, r, l), ModelHelper.v(r, l, l), ModelHelper.v(l, l, l), ModelHelper.v(l, r, l), texture));
        allQuads.add(ModelHelper.createQuad(ModelHelper.v(l, r, r), ModelHelper.v(l, l, r), ModelHelper.v(r, l, r), ModelHelper.v(r, r, r), texture));

        return allQuads;
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
        return getTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }
}
