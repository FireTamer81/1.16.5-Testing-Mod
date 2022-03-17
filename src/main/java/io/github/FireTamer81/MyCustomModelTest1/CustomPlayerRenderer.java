package io.github.FireTamer81.MyCustomModelTest1;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;

public class CustomPlayerRenderer extends PlayerRenderer {

    public static CustomPlayerModel model;
    //public static CustomPlayerModel model = new CustomPlayerModel();

    public CustomPlayerRenderer(EntityRendererManager p_i46102_1_) {
        super(p_i46102_1_);
        model = new CustomPlayerModel();
    }

    public static void doRender(AbstractClientPlayerEntity e) {
        ActiveRenderInfo ari = Minecraft.getInstance().gameRenderer.getMainCamera();
        float cameraX = ari.getXRot();
        float cameraY = ari.getYRot();

        model.render(e, 1, 1, cameraX, cameraY, 0.1f);
    }
}
