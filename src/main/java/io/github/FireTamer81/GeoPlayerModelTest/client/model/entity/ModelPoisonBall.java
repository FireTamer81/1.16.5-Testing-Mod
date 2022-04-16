package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelBase;
import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelRenderer;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityPoisonBall;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3d;

public class ModelPoisonBall<T extends EntityPoisonBall> extends AdvancedModelBase<T> {
    private final AdvancedModelRenderer inner;
    private final AdvancedModelRenderer outer;

    public ModelPoisonBall() {
        textureWidth = 32;
        textureHeight = 32;

        inner = new AdvancedModelRenderer(this, 0, 16);
        inner.setRotationPoint(0.0F, 3.5F, 0.0F);
        inner.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F, false);

        outer = new AdvancedModelRenderer(this, 0, 0);
        outer.setRotationPoint(0.0F, 3.5F, 0.0F);
        outer.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false);

        inner.setOpacity(1f);
        outer.setOpacity(0.6f);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        inner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        outer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        EntityPoisonBall poisonBall = entityIn;
        float delta = ageInTicks - entityIn.ticksExisted;
        Vector3d prevV = new Vector3d(poisonBall.prevMotionX, poisonBall.prevMotionY, poisonBall.prevMotionZ);
        Vector3d dv = prevV.add(poisonBall.getMotion().subtract(prevV).scale(delta));
        double d = Math.sqrt(dv.x * dv.x + dv.y * dv.y + dv.z * dv.z);
        if (d != 0) {
            double a = dv.y / d;
            a = Math.max(-1, Math.min(1, a));
            float pitch = -(float) Math.asin(a);
            inner.rotateAngleX = pitch + (float)Math.PI / 2f;
            outer.rotateAngleX = pitch + (float)Math.PI / 2f;
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}