package io.github.FireTamer81.guiStuff.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class TestChildScreen extends AbstractGui implements IRenderable, IGuiEventListener {
    public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation(TestModMain.MOD_ID, "textures/screens/menu_selector.png");

    private int width;
    private int height;
    protected Minecraft minecraft;

    public TestChildScreen() {}

    public void init(int p_201520_1_, int p_201520_2_, Minecraft p_201520_3_) {
        this.minecraft = p_201520_3_;
        this.width = p_201520_1_;
        this.height = p_201520_2_;
    }

    //public void tick() {super.tick();}

    public void render(MatrixStack stack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 100.0f);
        this.minecraft.getTextureManager().bind(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //int i = (this.width - 146) / 2;
        //int j = (this.height - 165) - 90;
        this.blit(stack, 0, 100, 0, 0, 146, 165);      //Main Background
    }


}
