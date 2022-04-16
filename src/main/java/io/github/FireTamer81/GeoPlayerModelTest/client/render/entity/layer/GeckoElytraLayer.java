package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.MowzieElytraModel;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class GeckoElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {
    public GeckoElytraLayer(IEntityRenderer<T, M> rendererIn, ModelRenderer bipedBody) {
        super(rendererIn);
        modelElytra = new MowzieElytraModel(bipedBody);
    }
}
