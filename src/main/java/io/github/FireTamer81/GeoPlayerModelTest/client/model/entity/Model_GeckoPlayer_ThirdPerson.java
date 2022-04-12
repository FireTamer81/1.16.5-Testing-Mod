package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import net.minecraft.entity.player.PlayerEntity;

public class Model_GeckoPlayer_ThirdPerson extends ModelGeckoBiped {

    public GeckoTestBone bipedLeftArmwear() {
        return getGeckoTestBone("LeftArmLayer");
    }
    public GeckoTestBone bipedRightArmwear() {
        return getGeckoTestBone("LeftArmLayer");
    }
    public GeckoTestBone bipedLeftLegwear() {
        return getGeckoTestBone("LeftArmLayer");
    }
    public GeckoTestBone bipedRightLegwear() {
        return getGeckoTestBone("LeftArmLayer");
    }
    public GeckoTestBone bipedBodywear() {
        return getGeckoTestBone("LeftArmLayer");
    }

    public void setVisibility(boolean visible) {
        super.setVisible(visible);
        this.bipedLeftArmwear().setHidden(!visible);
        this.bipedRightArmwear().setHidden(!visible);
        this.bipedLeftLegwear().setHidden(!visible);
        this.bipedRightLegwear().setHidden(!visible);
        this.bipedBodywear().setHidden(!visible);
    }

    @Override
    public void setRotationAngles(PlayerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick);
        GeckoTestBone rightArmLayerClassic = getGeckoTestBone("RightArmLayerClassic");
        GeckoTestBone leftArmLayerClassic = getGeckoTestBone("LeftArmLayerClassic");
        GeckoTestBone rightArmLayerSlim = getGeckoTestBone("RightArmLayerSlim");
        GeckoTestBone leftArmLayerSlim = getGeckoTestBone("LeftArmLayerSlim");
        if (useSmallArms) {
            rightArmLayerClassic.setHidden(true);
            leftArmLayerClassic.setHidden(true);
            rightArmLayerSlim.setHidden(false);
            leftArmLayerSlim.setHidden(false);
        }
        else {
            rightArmLayerSlim.setHidden(true);
            leftArmLayerSlim.setHidden(true);
            rightArmLayerClassic.setHidden(false);
            leftArmLayerClassic.setHidden(false);
        }
    }
}
