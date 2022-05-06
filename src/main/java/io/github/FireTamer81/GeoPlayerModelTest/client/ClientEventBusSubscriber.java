package io.github.FireTamer81.GeoPlayerModelTest.client;

import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.*;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityBoulder;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TestModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.DART.get(), RenderDart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.SUNSTRIKE.get(), RenderSunstrike::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.SOLAR_BEAM.get(), RenderSolarBeam::new);

        for (RegistryObject<EntityType<EntityBoulder>> boulderType : EntityHandler.BOULDERS) {
            RenderingRegistry.registerEntityRenderingHandler(boulderType.get(), RenderBoulder::new);
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.AXE_ATTACK.get(), RenderAxeAttack::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.POISON_BALL.get(), RenderPoisonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.ICE_BALL.get(), RenderIceBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.ICE_BREATH.get(), RenderNothing::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.SUPER_NOVA.get(), RenderSuperNova::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.FALLING_BLOCK.get(), RenderFallingBlock::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.BLOCK_SWAPPER.get(), RenderNothing::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHandler.CAMERA_SHAKE.get(), RenderNothing::new);
    }

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent modelRegistryEvent) {
        for (String item : MMModels.HAND_MODEL_ITEMS) {
            ModelLoader.addSpecialModel(new ModelResourceLocation(TestModMain.MODID + ":" + item + "_in_hand", "inventory"));
        }
    }

}
