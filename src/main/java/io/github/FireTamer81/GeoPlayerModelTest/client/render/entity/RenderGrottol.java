package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelGrottol;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol.EntityGrottol;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by BobMowzie on 5/8/2017.
 */
public class RenderGrottol extends MobRenderer<EntityGrottol, ModelGrottol<EntityGrottol>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/grottol.png");

    public RenderGrottol(EntityRendererManager mgr) {
        super(mgr, new ModelGrottol<>(), 0.6f);
    }

    @Override
    protected float getDeathMaxRotation(EntityGrottol entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityGrottol entity) {
        return RenderGrottol.TEXTURE;
    }

    /*@Override
    public void doRender(EntityGrottol entity, double x, double y, double z, float yaw, float delta) {
        if (entity.hasMinecartBlockDisplay()) {
            if (!renderOutlines) {
                renderName(entity, x, y, z);
            }
        } else {
            super.doRender(entity, x, y, z, yaw, delta);
        }
    }
    @Override
    public void doRenderShadowAndFire(Entity entity, double x, double y, double z, float yaw, float delta) {
        if (!(entity instanceof EntityGrottol) || !((EntityGrottol) entity).hasMinecartBlockDisplay()) {
            super.doRenderShadowAndFire(entity, x, y, z, yaw, delta);
        }
    }*/
}
