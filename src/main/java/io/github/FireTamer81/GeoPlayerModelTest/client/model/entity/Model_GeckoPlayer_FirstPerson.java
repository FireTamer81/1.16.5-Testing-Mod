package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.TestAnimatedGeoModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class Model_GeckoPlayer_FirstPerson extends TestAnimatedGeoModel<GeckoPlayer> {
    private ResourceLocation animationFileLocation;
    private ResourceLocation modelLocation;
    private ResourceLocation textureLocation;

    public BipedModel.ArmPose leftArmPose = BipedModel.ArmPose.EMPTY;
    public BipedModel.ArmPose rightArmPose = BipedModel.ArmPose.EMPTY;

    protected boolean useSmallArms;

    @Override
    public ResourceLocation getAnimationFileLocation(GeckoPlayer animatable) {
        return animationFileLocation;
    }

    @Override
    public ResourceLocation getModelLocation(GeckoPlayer animatable) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(GeckoPlayer animatable) {
        return textureLocation;
    }

    public void setUseSmallArms(boolean useSmallArms) {
        this.useSmallArms = useSmallArms;
    }

    public boolean isUsingSmallArms() {
        return useSmallArms;
    }



    @Override
    public void setLivingAnimations(GeckoPlayer entity, Integer uniqueID) {
        super.setLivingAnimations(entity, uniqueID);
        if (isInitialized()) {
            GeckoTestBone rightArmLayerClassic = getGeckoTestBone("RightArmLayerClassic");
            GeckoTestBone leftArmLayerClassic = getGeckoTestBone("LeftArmLayerClassic");
            GeckoTestBone rightArmLayerSlim = getGeckoTestBone("RightArmLayerSlim");
            GeckoTestBone leftArmLayerSlim = getGeckoTestBone("LeftArmLayerSlim");
            GeckoTestBone rightArmClassic = getGeckoTestBone("RightArmClassic");
            GeckoTestBone leftArmClassic = getGeckoTestBone("LeftArmClassic");
            GeckoTestBone rightArmSlim = getGeckoTestBone("RightArmSlim");
            GeckoTestBone leftArmSlim = getGeckoTestBone("LeftArmSlim");
            getGeckoTestBone("LeftHeldItem").setHidden(true);
            getGeckoTestBone("RightHeldItem").setHidden(true);
            rightArmClassic.setHidden(true);
            leftArmClassic.setHidden(true);
            rightArmLayerClassic.setHidden(true);
            leftArmLayerClassic.setHidden(true);
            rightArmSlim.setHidden(true);
            leftArmSlim.setHidden(true);
            rightArmLayerSlim.setHidden(true);
            leftArmLayerSlim.setHidden(true);
        }
    }

    /** Check if the modelId has some ResourceLocation **/
    @Override
    public boolean resourceForModelId(AbstractClientPlayerEntity player) {
        this.animationFileLocation = new ResourceLocation(TestModMain.MOD_ID, "animations/animated_player_first_person.animation.json");
        this.modelLocation = new ResourceLocation(TestModMain.MOD_ID, "geo/animated_player_first_person.geo.json");
        this.textureLocation = player.getSkinTextureLocation();
        return true;
    }
}
