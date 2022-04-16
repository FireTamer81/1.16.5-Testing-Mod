package io.github.FireTamer81.GeoPlayerModelTest.client.model.armour;

import com.google.common.collect.ImmutableList;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ModelRendererMatrix;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MowzieElytraModel<T extends LivingEntity> extends ElytraModel<T> {
    public ModelRendererMatrix bipedBody;

    public MowzieElytraModel(ModelRenderer bipedBody) {
        this.bipedBody = new ModelRendererMatrix(bipedBody);
        this.bipedBody.cubeList.clear();
        this.bipedBody.addChild(rightWing);
        this.bipedBody.addChild(leftWing);
        rightWing.rotateAngleZ = 2;
        leftWing.rotateAngleZ = 2;
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.bipedBody);
    }
}
