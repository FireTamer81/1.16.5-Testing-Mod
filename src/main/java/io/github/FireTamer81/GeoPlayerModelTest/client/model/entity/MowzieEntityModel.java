package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelBase;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.MMModelAnimator;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public abstract class MowzieEntityModel<T extends MowzieEntity> extends AdvancedModelBase<T> {
    protected final MMModelAnimator animator = MMModelAnimator.create();

    protected MowzieEntityModel() {
        super();
    }

    protected MowzieEntityModel(Function<ResourceLocation, RenderType> renderTypeFunction) {
        super(renderTypeFunction);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float delta = ageInTicks - entityIn.ticksExisted;
        this.animator.update(entityIn, delta);
        this.animate(entityIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, delta);
    }

    @SuppressWarnings("unchecked")
    private T cast(Entity entity) {
        return (T) entity;
    }

    protected abstract void animate(T entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta);

    protected static void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
