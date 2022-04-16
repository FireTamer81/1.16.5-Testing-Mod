package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelWroughtnaut;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer.ItemLayer;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer.WroughtnautEyesLayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.wroughtnaut.EntityWroughtnaut;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderWroughtnaut extends MobRenderer<EntityWroughtnaut, ModelWroughtnaut<EntityWroughtnaut>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/wroughtnaut.png");

    public RenderWroughtnaut(EntityRendererManager mgr) {
        super(mgr, new ModelWroughtnaut<>(), 1.0F);
        addLayer(new WroughtnautEyesLayer<>(this));
        addLayer(new ItemLayer<>(this, getEntityModel().sword, Items.DIAMOND_SWORD.getDefaultInstance(), ItemCameraTransforms.TransformType.GROUND));
    }

    @Override
    protected float getDeathMaxRotation(EntityWroughtnaut entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityWroughtnaut entity) {
        return RenderWroughtnaut.TEXTURE;
    }
}
