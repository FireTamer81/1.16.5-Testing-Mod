package io.github.FireTamer81.MyCustomPlayerModelTest4;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * RevisedMalePlayerModel - FireTamer81
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TestPlayerModel<T extends Entity> extends EntityModel<T> {
    private final ModelRenderer bone;
    private final ModelRenderer bone2;

    public TestPlayerModel() {
        texWidth = 64;
        texHeight = 64;

        bone = new ModelRenderer(this);
        bone.setPos(0.0F, 16.0F, 0.0F);
        bone.texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setPos(0.0F, -8.0F, 0.0F);
        bone.addChild(bone2);
        bone2.texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.setRotationByDegrees(bone, 180, 180, 0);
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationByDegrees(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        this.setRotationAngle(modelRenderer, (float)Math.toRadians(x), (float)Math.toRadians(y), (float)Math.toRadians(z));
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}