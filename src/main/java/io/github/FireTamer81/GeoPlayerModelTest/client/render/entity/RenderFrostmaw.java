package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelFrostmaw;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.MowzieRenderUtils;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer.ItemLayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.frostmaw.EntityFrostmaw;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Created by BobMowzie on 5/8/2017.
 */
public class RenderFrostmaw extends MobRenderer<EntityFrostmaw, ModelFrostmaw<EntityFrostmaw>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/frostmaw.png");

    public RenderFrostmaw(EntityRendererManager mgr) {
        super(mgr, new ModelFrostmaw<>(), 3.5f);
        addLayer(new ItemLayer<>(this, getEntityModel().iceCrystalHand, ItemHandler.ICE_CRYSTAL.getDefaultInstance(), ItemCameraTransforms.TransformType.GROUND));
        addLayer(new ItemLayer<>(this, getEntityModel().iceCrystal, ItemHandler.ICE_CRYSTAL.getDefaultInstance(), ItemCameraTransforms.TransformType.GROUND));
    }

    @Override
    protected float getDeathMaxRotation(EntityFrostmaw entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityFrostmaw entity) {
        return RenderFrostmaw.TEXTURE;
    }

    @Override
    public void render(EntityFrostmaw entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (entity.getAnimation() == EntityFrostmaw.SWIPE_ANIMATION || entity.getAnimation() == EntityFrostmaw.SWIPE_TWICE_ANIMATION || entity.getAnimation() == EntityFrostmaw.ICE_BREATH_ANIMATION || entity.getAnimation() == EntityFrostmaw.ICE_BALL_ANIMATION || !entity.getActive()) {
            Vector3d rightHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, entityModel.rightHandSocket);
            Vector3d leftHandPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, entityModel.leftHandSocket);
            Vector3d mouthPos = MowzieRenderUtils.getWorldPosFromModel(entity, entityYaw, entityModel.mouthSocket);
            entity.setSocketPosArray(0, rightHandPos);
            entity.setSocketPosArray(1, leftHandPos);
            entity.setSocketPosArray(2, mouthPos);
        }
    }
}
