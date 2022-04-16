package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelLantern;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer.LanternGelLayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.lantern.EntityLantern;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by BobMowzie on 5/8/2017.
 */
public class RenderLantern extends MobRenderer<EntityLantern, ModelLantern<EntityLantern>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/mmlantern.png");

    public RenderLantern(EntityRendererManager mgr) {
        super(mgr, new ModelLantern<>(), 0.6f);
        this.addLayer(new LanternGelLayer<>(this));
    }

    @Override
    protected float getDeathMaxRotation(EntityLantern entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityLantern entity) {
        return RenderLantern.TEXTURE;
    }
}
