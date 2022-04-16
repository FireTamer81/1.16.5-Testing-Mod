package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelSuperNova;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.MMRenderType;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySuperNova;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class RenderSuperNova extends EntityRenderer<EntitySuperNova> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova.png");
    public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_1.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_2.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_3.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_4.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_5.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_6.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_7.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_8.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_9.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_10.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_11.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_12.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_13.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_14.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_15.png"),
            new ResourceLocation(TestModMain.MODID, "textures/effects/super_nova_16.png")
    };
    public ModelSuperNova model;

    public RenderSuperNova(EntityRendererManager mgr) {
        super(mgr);
        model = new ModelSuperNova();
    }

    @Override
    public ResourceLocation getEntityTexture(EntitySuperNova entity) {
        int index = entity.ticksExisted % TEXTURES.length;
        return TEXTURES[index];
    }

    @Override
    public void render(EntitySuperNova entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(MMRenderType.getGlowingEffect(this.getEntityTexture(entityIn)));
        model.setRotationAngles(entityIn, 0, 0, entityIn.ticksExisted + partialTicks, 0, 0);
        model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStackIn.pop();
    }
}
