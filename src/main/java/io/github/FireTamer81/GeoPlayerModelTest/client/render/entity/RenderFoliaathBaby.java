package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelFoliaathBaby;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath.EntityBabyFoliaath;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFoliaathBaby extends MobRenderer<EntityBabyFoliaath, ModelFoliaathBaby<EntityBabyFoliaath>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/foliaath_baby.png");

    public RenderFoliaathBaby(EntityRendererManager mgr) {
        super(mgr, new ModelFoliaathBaby<>(), 0);
    }

    @Override
    protected float getDeathMaxRotation(EntityBabyFoliaath entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityBabyFoliaath entity) {
        return RenderFoliaathBaby.TEXTURE;
    }
}
