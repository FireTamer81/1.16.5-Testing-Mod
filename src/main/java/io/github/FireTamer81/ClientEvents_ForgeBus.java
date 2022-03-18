package io.github.FireTamer81;

import com.google.common.collect.Maps;
import io.github.FireTamer81.MyCustomModelTest1.CustomPlayerRenderer;
import io.github.FireTamer81.MyCustomModelTest2.CustomPlayerModel2;
import io.github.FireTamer81.MyCustomModelTest2.CustomPlayerRenderer2;
import io.github.FireTamer81.MyCustomModelTest3.CustomPlayerRenderer3;
import io.github.FireTamer81.MyCustomPlayerModelTest4.TestPlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents_ForgeBus {

    private static CustomPlayerRenderer customPlayerRenderer1;
    private static CustomPlayerRenderer2 customPlayerRenderer2;
    private static CustomPlayerRenderer3 customPlayerRenderer3;
    private static TestPlayerRenderer testPlayerRenderer;

    public static EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();


    @SubscribeEvent
    public static void renderCustomPlayer(final RenderPlayerEvent.Pre event) {
        event.setCanceled(true); /* This way cancels all rendering, even layers */

        //PlayerModel model = event.getRenderer().getModel(); /* This way cancels the base player model form rendering, but leaves layers active */
        //model.setAllVisible(false);                         /* I might be able to just make my player model stuff a layer, but I wonder about other layers interfering */

        //customPlayerRenderer1 = new CustomPlayerRenderer(manager);
        //customPlayerRenderer1.render((AbstractClientPlayerEntity) event.getEntity(), 0.0F, 0.0F, event.getMatrixStack(), event.getBuffers(), event.getLight());

        //customPlayerRenderer2 = new CustomPlayerRenderer2(manager);
        //customPlayerRenderer2.render((AbstractClientPlayerEntity) event.getEntity(), 0.0F, 0.0F, event.getMatrixStack(), event.getBuffers(), event.getLight());

        //customPlayerRenderer3 = new CustomPlayerRenderer3(manager);
        //customPlayerRenderer3.doRender((AbstractClientPlayerEntity) event.getEntity(), 0.0F, 0.0F, event.getMatrixStack(), event.getBuffers(), event.getLight());

        testPlayerRenderer = new TestPlayerRenderer(manager);
        testPlayerRenderer.doRender((AbstractClientPlayerEntity) event.getEntity(), 0.0F, 0.0F, event.getMatrixStack(), event.getBuffers(), event.getLight());
    }
}
