package io.github.FireTamer81.GeoPlayerModelTest.client.model.item;

import io.github.FireTamer81.GeoPlayerModelTest.server.item.objects.ItemEarthboreGauntlet;
import io.github.FireTamer81.TestModMain;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelEarthboreGauntlet extends AnimatedGeoModel<ItemEarthboreGauntlet> {

    @Override
    public ResourceLocation getModelLocation(ItemEarthboreGauntlet object) {
        return new ResourceLocation(TestModMain.MODID, "geo/earthbore_gauntlet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ItemEarthboreGauntlet object) {
        return new ResourceLocation(TestModMain.MODID, "textures/item/earthbore_gauntlet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemEarthboreGauntlet animatable) {
        return new ResourceLocation(TestModMain.MODID, "animations/earthbore_gauntlet.animation.json");
    }
}