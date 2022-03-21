package io.github.FireTamer81;

import io.github.FireTamer81.MyCustomPlayerModelTest4.TestPlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents_ForgeBus {

    private static TestPlayerRenderer testPlayerRenderer;

    public static EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();


    @SubscribeEvent
    public static void renderCustomPlayer(final RenderPlayerEvent.Pre event) {
        event.setCanceled(true); /* This way cancels all rendering, even layers */

        //PlayerModel model = event.getRenderer().getModel(); /* This way cancels the base player model form rendering, but leaves layers active */
        //model.setAllVisible(false);                         /* I might be able to just make my player model stuff a layer, but I wonder about other layers interfering */

        testPlayerRenderer = new TestPlayerRenderer(manager);
        testPlayerRenderer.doRender(event.getMatrixStack(), event.getBuffers(), event.getLight());
    }
}
