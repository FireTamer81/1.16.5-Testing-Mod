package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelFoliaath;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath.EntityFoliaath;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFoliaath extends MobRenderer<EntityFoliaath, ModelFoliaath<EntityFoliaath>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/entity/foliaath.png");

    public RenderFoliaath(EntityRendererManager mgr) {
        super(mgr, new ModelFoliaath<>(), 0);
    }

    @Override
    protected float getDeathMaxRotation(EntityFoliaath entity) {
        return 0;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityFoliaath entity) {
        return RenderFoliaath.TEXTURE;
    }

    @Override
    public void render(EntityFoliaath entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
