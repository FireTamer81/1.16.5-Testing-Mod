package io.github.FireTamer81.GeoPlayerModelTest.client.model.armour;

import com.google.common.collect.ImmutableList;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ModelRendererMatrix;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class TestElytraModel<T extends LivingEntity> extends ElytraModel<T> {
    public ModelRendererMatrix bipedBody;

    public TestElytraModel(ModelRenderer bipedBody) {
        this.bipedBody = new ModelRendererMatrix(bipedBody);
        this.bipedBody.cubes.clear();
        this.bipedBody.addChild(rightWing);
        this.bipedBody.addChild(leftWing);
        rightWing.z = 2;
        leftWing.z = 2;
    }

    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.bipedBody);
    }
}
