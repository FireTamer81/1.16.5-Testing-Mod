package io.github.FireTamer81.MyCustomPlayerModelTest4;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.FireTamer81.MyCustomModelTest3.CustomPlayerModel;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, TestPlayerModel<AbstractClientPlayerEntity>> {

    public TestPlayerModel model;
    public static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MOD_ID, "textures/entity/revised_male_player_model.png");

    public TestPlayerRenderer(EntityRendererManager manager) {
        super(manager, new TestPlayerModel<>(), 0.5F); //The 0.5F is meant to be the shadow size. So, if a model is larger I might want this to be a larger value

        model = new TestPlayerModel();
    }

    public void doRender(AbstractClientPlayerEntity e, float f1, float f2, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
        model.renderToBuffer(stack, buffer.getBuffer(RenderType.cutout()), light, 1, 1, 1, 1, 1);
    }

    //Might not need this since I am not using the vanilla player model that is being used by the super class PlayerRenderer
    @Override
    public ResourceLocation getTextureLocation(AbstractClientPlayerEntity playerEntity) {
        return TEXTURE;
    }
}
