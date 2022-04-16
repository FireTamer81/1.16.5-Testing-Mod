package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelSculptor;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.sculptor.EntitySculptor;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderSculptor extends MowzieGeoEntityRenderer<EntitySculptor> {
    public RenderSculptor(EntityRendererManager renderManager) {
        super(renderManager, new ModelSculptor());
        this.shadowSize = 0.7f;
    }

    @Override
    public ResourceLocation getEntityTexture(EntitySculptor entity) {
        return this.getGeoModelProvider().getTextureLocation(entity);
    }
}
