package io.github.FireTamer81.MyCustomPlayerModelTest4;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, TestPlayerModel<AbstractClientPlayerEntity>> {

    public TestPlayerModel model;
    protected ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "textures/entity/basic_male_2.png");

    public TestPlayerRenderer(EntityRendererManager manager) {
        super(manager, new TestPlayerModel<>(), 0.5F); //The 0.5F is meant to be the shadow size. So, if a model is larger I might want this to be a larger value

        model = new TestPlayerModel();
    }

    public void doRender(MatrixStack stack, IRenderTypeBuffer buffer, int light) {
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        model.renderToBuffer(stack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 0.0f, 0.0f, 0.0f, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractClientPlayerEntity playerEntity) {
        return TEXTURE;
    }
}
