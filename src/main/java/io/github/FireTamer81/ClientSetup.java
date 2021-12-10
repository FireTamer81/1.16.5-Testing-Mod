package io.github.FireTamer81;

import io.github.FireTamer81.fullBlock.completedClasses.BakedModelFullBlockLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        //RenderTypeLookup.setRenderLayer(Registration.BAKED_MODEL_FULL_BLOCK_REGISTRY_OBJECT.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(TestModMain.MOD_ID, "baked_model_full_block_loader"), new BakedModelFullBlockLoader());
    }


}
