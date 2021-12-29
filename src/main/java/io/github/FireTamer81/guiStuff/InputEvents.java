package io.github.FireTamer81.guiStuff;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.guiStuff.objects.TestScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) { return; }

        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) { return; }

        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if (mc.screen == null && guiTestingModule.exampleKey.isDown()) {
            //PlayerEntity player = Minecraft.getInstance().player;
            //player.displayClientMessage(new StringTextComponent("Hey, the keybind works"), true);

            mc.setScreen(new TestScreen());
        }
    }

}
