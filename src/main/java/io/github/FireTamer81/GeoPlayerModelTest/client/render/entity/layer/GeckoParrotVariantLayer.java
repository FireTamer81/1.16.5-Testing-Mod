package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer_Renderer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;

public class GeckoParrotVariantLayer extends ParrotVariantLayer<AbstractClientPlayerEntity> implements IGeckoRenderLayer {
    private final GeckoPlayer_Renderer renderPlayerAnimated;

    public GeckoParrotVariantLayer(GeckoPlayer_Renderer rendererIn) {
        super(rendererIn);
        renderPlayerAnimated = rendererIn;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int packedLightIn, AbstractClientPlayerEntity clientPlayerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        try {
            GeckoTestBone bone = renderPlayerAnimated.getAnimatedPlayerModel().getGeckoTestBone("body");
            MatrixStack newStack = new MatrixStack();
            newStack.last().normal().mul(bone.getWorldSpaceNormal());
            newStack.last().pose().multiply(bone.getWorldSpaceXForm());
            this.render(newStack, renderBuffer, packedLightIn, clientPlayerEntity, limbSwing, limbSwingAmount, netHeadYaw, headPitch, true);
            this.render(newStack, renderBuffer, packedLightIn, clientPlayerEntity, limbSwing, limbSwingAmount, netHeadYaw, headPitch, false);
        }

        catch (RuntimeException ignored) {}
    }
}
