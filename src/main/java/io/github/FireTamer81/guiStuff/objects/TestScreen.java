package io.github.FireTamer81.guiStuff.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.swing.*;

public class TestScreen extends Screen {
    public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(TestModMain.MOD_ID, "textures/screens/dragonball_gear.png");

    private final TestChildScreen childScreenComponent = new TestChildScreen();
    private float xMouse;
    private float yMouse;

    public TestScreen() {
        //super(new TranslationTextComponent("menu.player_screen"));
        super(new StringTextComponent(""));
    }

    protected void init() {
        super.init();
        this.childScreenComponent.init(this.width, this.height, this.minecraft);
        this.children.add(this.childScreenComponent);
    }



    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
    }

    public void render(MatrixStack stack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(stack);
        //drawCenteredString(stack, this.font, this.title, this.width / 2, 40, 16777215);

        this.childScreenComponent.render(stack, p_230430_2_, p_230430_3_, p_230430_4_); //Adds the Menu Selector GUI

        super.render(stack, p_230430_2_, p_230430_3_, p_230430_4_);

        this.xMouse = (float)p_230430_2_;
        this.yMouse = (float)p_230430_3_;
    }

    @Override
    public void renderBackground(MatrixStack stack) {
        //the first int in the blit method is x position on screen
        //the second is y position on screen
        //3rd is start x position on texture
        //4th is start y position on texture
        //5th is texture width (or a section of that texture)       [Max size of 256]
        //6th is texture height (or a section of that texture)      [Max size of 256]
        super.renderBackground(stack);
        GlStateManager._color4f(1, 1, 1, 1);
        this.minecraft.getTextureManager().bind(WIDGETS_LOCATION);
        //int i = (this.width - 97);
        //int j = (this.height - 94);
        this.blit(stack, 0, 0, 0, 0, 97, 94);      //Top Piece
        renderEntityInInventory(this.width - 51, this.height - 75, 30, (float)(this.width - 51) - this.xMouse, (float)(this.height - 75 - 50) - this.yMouse, this.minecraft.player);
    }







    public static void renderEntityInInventory(int p_228187_0_, int p_228187_1_, int p_228187_2_, float p_228187_3_, float p_228187_4_, LivingEntity p_228187_5_) {
        float f = (float)Math.atan((double)(p_228187_3_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_228187_4_ / 40.0F));
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)p_228187_0_, (float)p_228187_1_, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        matrixstack.mulPose(quaternion);
        float f2 = p_228187_5_.yBodyRot;
        float f3 = p_228187_5_.yRot;
        float f4 = p_228187_5_.xRot;
        float f5 = p_228187_5_.yHeadRotO;
        float f6 = p_228187_5_.yHeadRot;
        p_228187_5_.yBodyRot = 180.0F + f * 20.0F;
        p_228187_5_.yRot = 180.0F + f * 40.0F;
        p_228187_5_.xRot = -f1 * 20.0F;
        p_228187_5_.yHeadRot = p_228187_5_.yRot;
        p_228187_5_.yHeadRotO = p_228187_5_.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.render(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        p_228187_5_.yBodyRot = f2;
        p_228187_5_.yRot = f3;
        p_228187_5_.xRot = f4;
        p_228187_5_.yHeadRotO = f5;
        p_228187_5_.yHeadRot = f6;
        RenderSystem.popMatrix();
    }
}
