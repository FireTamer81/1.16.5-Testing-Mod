package io.github.FireTamer81;

import io.github.FireTamer81.fullBlock.BakedModelFullBlock_Model;
import io.github.FireTamer81.fullBlock.BakedModelFullBlockLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(Registration.BAKED_MODEL_FULL_BLOCK_REGISTRY_OBJECT.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(TestModMain.MOD_ID, "baked_model_full_block_loader"), new BakedModelFullBlockLoader());
    }

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) { //getSmoothWarenaiBlockResourceLocations()
        if (event.getMap().location() == AtlasTexture.LOCATION_BLOCKS) {
            event.addSprite(BakedModelFullBlock_Model.UNDERLAY_TEXTURE);
            event.addSprite(BakedModelFullBlock_Model.OVERLAY_TEXTURE);


            event.addSprite(StrongBlockTextureHelper.getSmoothWarenaiBlockResourceLocations().get(0));
            event.addSprite(StrongBlockTextureHelper.getSmoothWarenaiBlockResourceLocations().get(1));
        }
    }
}
