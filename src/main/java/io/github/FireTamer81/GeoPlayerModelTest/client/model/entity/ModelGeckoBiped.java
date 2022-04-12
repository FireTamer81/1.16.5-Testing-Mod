package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.TestAnimatedGeoModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class ModelGeckoBiped extends TestAnimatedGeoModel<GeckoPlayer> {
    private ResourceLocation animationFileLocation;
    private ResourceLocation modelLocation;
    private ResourceLocation textureLocation;

    public boolean isSitting = false;
    public boolean isChild = true;
    public float swingProgress;
    public boolean isSneak;
    public float swimAnimation;

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


    /** Check if the modelId has some ResourceLocation **/
    public boolean resourceForModelId(AbstractClientPlayerEntity player) {
        this.animationFileLocation = new ResourceLocation(TestModMain.MOD_ID, "animations/animated_player.animation.json");
        this.modelLocation = new ResourceLocation(TestModMain.MOD_ID, "geo/animated_player.geo.json");
        this.textureLocation = player.getSkinTextureLocation();
        return true;
    }

    public void setUseSmallArms(boolean useSmallArms) {
        this.useSmallArms = useSmallArms;
    }

    public boolean isUsingSmallArms() {
        return useSmallArms;
    }

    public GeckoTestBone bipedHead() {
        return getGeckoTestBone("Head");
    }

    public GeckoTestBone bipedHeadwear() {
        return getGeckoTestBone("HatLayer");
    }

    public GeckoTestBone bipedBody() {
        return getGeckoTestBone("Body");
    }

    public GeckoTestBone bipedRightArm() {
        return getGeckoTestBone("RightArm");
    }

    public GeckoTestBone bipedLeftArm() {
        return getGeckoTestBone("LeftArm");
    }

    public GeckoTestBone bipedRightLeg() {
        return getGeckoTestBone("RightLeg");
    }

    public GeckoTestBone bipedLeftLeg() {
        return getGeckoTestBone("LeftLeg");
    }







    public void setVisible(boolean visible) {
        this.bipedHead().setHidden(!visible);
        this.bipedHeadwear().setHidden(!visible);
        this.bipedBody().setHidden(!visible);
        this.bipedRightArm().setHidden(!visible);
        this.bipedLeftArm().setHidden(!visible);
        this.bipedRightLeg().setHidden(!visible);
        this.bipedLeftLeg().setHidden(!visible);
    }

    public void setRotationAngles() {
        GeckoTestBone head = getGeckoTestBone("Head");
        GeckoTestBone neck = getGeckoTestBone("Neck");
        float yaw = 0;
        float pitch = 0;
        float roll = 0;
        GeoBone parent = neck.parent;
        while (parent != null) {
            pitch += parent.getRotationX();
            yaw += parent.getRotationY();
            roll += parent.getRotationZ();
            parent = parent.parent;
        }
        neck.addRotation(-yaw, -pitch, -roll);
    }

    public void setRotationAngles(PlayerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if (!isInitialized()) return;
        if (Minecraft.getInstance().isPaused()) return;

        GeckoTestBone rightArmClassic = getGeckoTestBone("RightArmClassic");
        GeckoTestBone leftArmClassic = getGeckoTestBone("LeftArmClassic");
        GeckoTestBone rightArmSlim = getGeckoTestBone("RightArmSlim");
        GeckoTestBone leftArmSlim = getGeckoTestBone("LeftArmSlim");
        if (useSmallArms) {
            rightArmClassic.setHidden(true);
            leftArmClassic.setHidden(true);
            rightArmSlim.setHidden(false);
            leftArmSlim.setHidden(false);
        }
        else {
            rightArmSlim.setHidden(true);
            leftArmSlim.setHidden(true);
            rightArmClassic.setHidden(false);
            leftArmClassic.setHidden(false);
        }

        //.getSwimAmount() was originally .getSwimAnimation()
        //If this doesn't work as intended, revert and find the right method
        this.swimAnimation = entityIn.getSwimAmount(partialTick);

        float headLookAmount = getControllerValue("HeadLookController");
        float armLookAmount = 1f - getControllerValue("ArmPitchController");
        float armLookAmountRight = getBone("ArmPitchController").getPositionY();
        float armLookAmountLeft = getBone("ArmPitchController").getPositionZ();
        //.getFallFlyingTicks() was originally .getTicksElytraFlying()
        //If this doesn't work as intended, revert and find the right method
        boolean flag = entityIn.getFallFlyingTicks() > 4;
        boolean flag1 = entityIn.isSwimming();
        this.bipedHead().addRotationY(headLookAmount * -netHeadYaw * ((float)Math.PI / 180F));
        this.getGeckoTestBone("LeftClavicle").addRotationY(Math.min(armLookAmount + armLookAmountLeft, 1) * -netHeadYaw * ((float)Math.PI / 180F));
        this.getGeckoTestBone("RightClavicle").addRotationY(Math.min(armLookAmount + armLookAmountRight, 1) * -netHeadYaw * ((float)Math.PI / 180F));
        if (flag) {
            this.bipedHead().addRotationX((-(float)Math.PI / 4F));
        } else if (this.swimAnimation > 0.0F) {
            if (flag1) {
                this.bipedHead().addRotationX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotationX(), (-(float)Math.PI / 4F)));
            } else {
                this.bipedHead().addRotationX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotationX(), headPitch * ((float)Math.PI / 180F)));
            }
        } else {
            this.bipedHead().addRotationX(headLookAmount * -headPitch * ((float)Math.PI / 180F));
            this.getGeckoTestBone("LeftClavicle").addRotationX(Math.min(armLookAmount + armLookAmountLeft, 1) * -headPitch * ((float)Math.PI / 180F));
            this.getGeckoTestBone("RightClavicle").addRotationX(Math.min(armLookAmount + armLookAmountRight, 1) * -headPitch * ((float)Math.PI / 180F));
        }

        float f = 1.0F;
        if (flag) {
            f = (float)entityIn.getMotion().lengthSquared();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        float legWalkAmount = getControllerValue("LegWalkController");
        float armSwingAmount = getControllerValue("ArmSwingController");
        float armSwingAmountRight = 1.0f - getBone("ArmSwingController").getPositionY();
        float armSwingAmountLeft = 1.0f - getBone("ArmSwingController").getPositionZ();
        this.bipedRightArm().addRotationX(armSwingAmount * armSwingAmountRight * MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f);
        this.bipedLeftArm().addRotationX(armSwingAmount * armSwingAmountLeft * MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f);
        this.bipedRightLeg().addRotationX(legWalkAmount * MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f);
        this.bipedLeftLeg().addRotationX(legWalkAmount * MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f);

        if (this.isSitting) {
            this.bipedRightArm().setRotationX(bipedRightArm().getRotationX() + (-(float)Math.PI / 5F));
            this.bipedLeftArm().setRotationX(bipedRightArm().getRotationX() + (-(float)Math.PI / 5F));
            this.bipedRightLeg().setRotationX(-1.4137167F);
            this.bipedRightLeg().setRotationY(((float)Math.PI / 10F));
            this.bipedRightLeg().setRotationZ(0.07853982F);
            this.bipedLeftLeg().setRotationX(-1.4137167F);
            this.bipedLeftLeg().setRotationY((-(float)Math.PI / 10F));
            this.bipedLeftLeg().setRotationZ(-0.07853982F);
            getGeckoTestBone("Waist").setRotation(0, 0, 0);
            getGeckoTestBone("Root").setRotation(0, 0, 0);
        }

        boolean flag2 = entityIn.getPrimaryHand() == HandSide.RIGHT;
        boolean flag3 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
        if (flag2 != flag3) {
            this.func_241655_c_(entityIn);
            this.func_241654_b_(entityIn);
        } else {
            this.func_241654_b_(entityIn);
            this.func_241655_c_(entityIn);
        }

//		this.swingAnim(entityIn, ageInTicks);

        float sneakController = getControllerValue("CrouchController");
        if (this.isSneak) {
            this.bipedBody().addRotationX(-0.5F * sneakController);
            this.getGeckoTestBone("Neck").addRotationX(0.5F * sneakController);
            this.bipedRightArm().addRotation(0.4F * sneakController, 0, 0);
            this.bipedLeftArm().addRotation(0.4F * sneakController, 0, 0);
            this.bipedHead().addPositionY(-1F * sneakController);
            this.bipedBody().addPosition(0, -1.5F * sneakController, 1.7f * sneakController);
            this.getGeckoTestBone("Waist").addPosition(0, -0.2f * sneakController, 4F * sneakController);
            this.bipedLeftArm().addRotationX(-0.4f * sneakController);
            this.bipedLeftArm().addPosition(0, 0.2f * sneakController, -1f * sneakController);
            this.bipedRightArm().addRotationX(-0.4f * sneakController);
            this.bipedRightArm().addPosition(0, 0.2f * sneakController, -1f * sneakController);

            this.getGeckoTestBone("Waist").addPositionY(2f * (1f - sneakController));
        }

        float armBreathAmount = getControllerValue("ArmBreathController");
        breathAnim(this.bipedRightArm(), this.bipedLeftArm(), ageInTicks, armBreathAmount);


        AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(entityIn);
        if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
            abilityCapability.codeAnimations(this, partialTick);
        }
    }

    protected GeckoTestBone getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm() : this.bipedRightArm();
    }

    protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
        float f = (mulIn - maxAngleIn) % ((float)Math.PI * 2F);
        if (f < -(float)Math.PI) {
            f += ((float)Math.PI * 2F);
        }

        if (f >= (float)Math.PI) {
            f -= ((float)Math.PI * 2F);
        }

        return maxAngleIn + angleIn * f;
    }

    private float getArmAngleSq(float limbSwing) {
        return -65.0F * limbSwing + limbSwing * limbSwing;
    }

    protected HandSide getMainHand(PlayerEntity entityIn) {
        HandSide handside = entityIn.getPrimaryHand();
        return entityIn.swingingArm == Hand.MAIN_HAND ? handside : handside.getOpposite();
    }

    public static void breathAnim(GeckoTestBone rightArm, GeckoTestBone leftArm, float ageInTicks, float armBreathAmount) {
        rightArm.addRotationZ(armBreathAmount * MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        leftArm.addRotationZ(armBreathAmount * -MathHelper.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);
        rightArm.addRotationX(armBreathAmount * MathHelper.sin(ageInTicks * 0.067F) * 0.05F);
        leftArm.addRotationX(armBreathAmount * -MathHelper.sin(ageInTicks * 0.067F) * 0.05F);
    }

    private void func_241654_b_(PlayerEntity p_241654_1_) {
        float armSwingAmount = getControllerValue("ArmSwingController");
        switch(this.rightArmPose) {
            case EMPTY:
                break;
            case BLOCK:
                this.bipedRightArm().addRotationX(0.9424779F * armSwingAmount);
                break;
            case ITEM:
                this.bipedRightArm().addRotationX( ((float)Math.PI / 10F) * armSwingAmount);
                break;
        }

    }

    private void func_241655_c_(PlayerEntity p_241655_1_) {
        float armSwingAmount = getControllerValue("ArmSwingController");
        switch(this.leftArmPose) {
            case EMPTY:
                break;
            case BLOCK:
                this.bipedLeftArm().addRotationX(0.9424779F * armSwingAmount);
                break;
            case ITEM:
                this.bipedLeftArm().addRotationX(((float)Math.PI / 10F) * armSwingAmount);
                break;
        }
    }
}
