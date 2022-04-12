package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.ModelPlayerAnimated;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.entity.Model_GeckoPlayer_ThirdPerson;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.text.TextFormatting;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.HashMap;
import java.util.Iterator;

public class GeckoPlayer_Renderer extends PlayerRenderer implements IGeoRenderer<GeckoPlayer> {
    private static HashMap<Class<? extends GeckoPlayer>, GeckoPlayer_Renderer> modelsToLoad = new HashMap<>();
    private Model_GeckoPlayer_ThirdPerson modelProvider;

    private Matrix4f worldRenderMat;

    public Vector3d betweenHandsPos;

    public GeckoPlayer_Renderer(EntityRendererManager renderManager, Model_GeckoPlayer_ThirdPerson modelProvider) {
        super(renderManager, false);

        this.model = new ModelPlayerAnimated<>(0.0f, false);

        this.layers.clear();
        this.addLayer(new BipedArmorLayer<>(this, new ModelBipedAnimated<>(0.5F), new ModelBipedAnimated<>(1.0F)));
        this.addLayer(new GeckoHeldItemLayer(this));
        this.addLayer(new ArrowLayer<>(this));
        this.addLayer(new Deadmau5HeadLayer(this));
        this.addLayer(new GeckoCapeLayer(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new GeckoElytraLayer<>(this, this.model.body));
        this.addLayer(new GeckoParrotVariantLayer(this));
        this.addLayer(new SpinAttackEffectLayer<>(this));
        this.addLayer(new BeeStingerLayer<>(this));
        //this.addLayer(new FrozenRenderHandler.LayerFrozen<>(this));

        this.modelProvider = modelProvider;

        worldRenderMat = new Matrix4f();
        worldRenderMat.setIdentity();
    }

    static {
        AnimationController.addModelFetcher((IAnimatable object) -> {
            if (object instanceof GeckoPlayer.GeckoPlayerThirdPerson) {
                GeckoPlayer_Renderer render = modelsToLoad.get(object.getClass());
                return (IAnimatableModel<Object>) render.getGeoModelProvider();
            } else {
                return null;
            }
        });
    }

    public GeckoPlayer_Renderer getModelProvider(Class<? extends GeckoPlayer> animatable) {
        return modelsToLoad.get(animatable);
    }

    public HashMap<Class<? extends GeckoPlayer>, GeckoPlayer_Renderer> getModelsToLoad() {
        return modelsToLoad;
    }

    public void setSmallArms() {
        this.model = new ModelPlayerAnimated<>(0.0f, true);
        this.modelProvider.setUseSmallArms(true);
    }

    public void render(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, GeckoPlayer geckoPlayer) {
        this.setModelVisibilities(entityIn);
        renderLiving(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, geckoPlayer);
    }

    private void setModelVisibilities(AbstractClientPlayerEntity clientPlayer) {
        Model_GeckoPlayer_ThirdPerson playermodel = (Model_GeckoPlayer_ThirdPerson) getGeoModelProvider();
        if (playermodel.isInitialized()) {
            if (clientPlayer.isSpectator()) {
                playermodel.setVisible(false);
                playermodel.bipedHead().setHidden(false);
                playermodel.bipedHeadwear().setHidden(false);
            } else {
                playermodel.setVisible(true);
                playermodel.bipedHeadwear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.HAT));
                playermodel.bipedBodywear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.JACKET));
                playermodel.bipedLeftLegwear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG));
                playermodel.bipedRightLegwear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG));
                playermodel.bipedLeftArmwear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE));
                playermodel.bipedRightArmwear().setHidden(!clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE));
                playermodel.isSneak = clientPlayer.isCrouching();
                BipedModel.ArmPose bipedmodel$armpose = getArmPose(clientPlayer, Hand.MAIN_HAND);
                BipedModel.ArmPose bipedmodel$armpose1 = getArmPose(clientPlayer, Hand.OFF_HAND);
                if (bipedmodel$armpose.isTwoHanded()) {
                    bipedmodel$armpose1 = clientPlayer.getOffhandItem().isEmpty() ? BipedModel.ArmPose.EMPTY : BipedModel.ArmPose.ITEM;
                }

                if (clientPlayer.getMainArm() == HandSide.RIGHT) {
                    modelProvider.rightArmPose = bipedmodel$armpose;
                    modelProvider.leftArmPose = bipedmodel$armpose1;
                } else {
                    modelProvider.rightArmPose = bipedmodel$armpose1;
                    modelProvider.leftArmPose = bipedmodel$armpose;
                }
            }
        }
    }

    public void renderLiving(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, GeckoPlayer geckoPlayer) {
//        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;
        matrixStackIn.pushPose();
        this.model.attackTime = this.getAttackAnim(entityIn, partialTicks);

        boolean shouldSit = entityIn.isPassenger() && (entityIn.getVehicle() != null && entityIn.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = entityIn.isBaby();
        float f = MathHelper.rotLerp(partialTicks, entityIn.yBodyRotO, entityIn.yBodyRot);
        float f1 = MathHelper.rotLerp(partialTicks, entityIn.yHeadRotO, entityIn.yHeadRot);
        float f2 = f1 - f;
        if (shouldSit && entityIn.getVehicle() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn.getVehicle();
            f = MathHelper.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f3 = MathHelper.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot);
        if (entityIn.getPose() == Pose.SLEEPING) {
            Direction direction = entityIn.getBedOrientation();
            if (direction != null) {
                float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
                matrixStackIn.translate((double)((float)(-direction.getStepX()) * f4), 0.0D, (double)((float)(-direction.getStepZ()) * f4));
            }
        }

        float f7 = this.handleRotationFloat(entityIn, partialTicks);
        this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entityIn.isAlive()) {
            f8 = MathHelper.lerp(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
            f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
            if (entityIn.isBaby()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.modelProvider.setLivingAnimations(geckoPlayer, entityIn.getUUID().hashCode());
        if (this.modelProvider.isInitialized()) {
            this.applyRotationsPlayerRenderer(entityIn, matrixStackIn, f7, f, partialTicks, f1);
            float bodyRotateAmount = this.modelProvider.getControllerValue("BodyRotateController");
            this.modelProvider.setRotationAngles(entityIn, f5, f8, f7, MathHelper.rotLerp(bodyRotateAmount, 0, f2), f6, partialTicks);

            GeckoTestBone leftHeldItem = modelProvider.getGeckoTestBone("LeftHeldItem");
            GeckoTestBone rightHeldItem = modelProvider.getGeckoTestBone("RightHeldItem");

            Matrix4f worldMatInverted = matrixStackIn.last().pose().copy();
            worldMatInverted.invert();
            Matrix3f worldNormInverted = matrixStackIn.last().normal().copy();
            worldNormInverted.invert();
            MatrixStack toWorldSpace = new MatrixStack();
            toWorldSpace.mulPose(new Quaternion(0, -entityYaw + 180, 0, true));
            toWorldSpace.translate(0, -1.5f, 0);
            toWorldSpace.last().normal().mul(worldNormInverted);
            toWorldSpace.last().pose().multiply(worldMatInverted);

            Vector4f leftHeldItemPos = new Vector4f(0, 0, 0, 1);
            leftHeldItemPos.transform(leftHeldItem.getWorldSpaceXForm());
            leftHeldItemPos.transform(toWorldSpace.last().pose());
            Vector3d leftHeldItemPos3 = new Vector3d(leftHeldItemPos.x(), leftHeldItemPos.y(), leftHeldItemPos.z());

            Vector4f rightHeldItemPos = new Vector4f(0, 0, 0, 1);
            rightHeldItemPos.transform(rightHeldItem.getWorldSpaceXForm());
            rightHeldItemPos.transform(toWorldSpace.last().pose());
            Vector3d rightHeldItemPos3 = new Vector3d(rightHeldItemPos.x(), rightHeldItemPos.y(), rightHeldItemPos.z());

            betweenHandsPos = rightHeldItemPos3.add(leftHeldItemPos3.subtract(rightHeldItemPos3).scale(0.5));
        }
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isVisible(entityIn);
        boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(minecraft.player);
        boolean flag2 = minecraft.isEntityGlowing(entityIn);
        RenderType rendertype = this.func_230496_a_(entityIn, flag, flag1, flag2);
        if (rendertype != null) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
            int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
            matrixStackIn.pushPose();
            worldRenderMat.set(matrixStackIn.last().pose());
            render(
                    getGeoModelProvider().getModel(getGeoModelProvider().getModelLocation(geckoPlayer)),
                    geckoPlayer, partialTicks, rendertype, matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F
            );
            matrixStackIn.popPose();
            this.model.setRotationAngles(entityIn, f5, f8, f7, f2, f6);
            ModelBipedAnimated.copyFromGeckoModel(this.model, this.modelProvider);
        }

        if (!entityIn.isSpectator()) {
            for(LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> layerrenderer : this.layers) {
                layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
            }
        }

        matrixStackIn.popPose();
        renderEntity(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
    }

    public void renderEntity(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(entityIn))) {
            this.renderName(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
    }

    protected void applyRotationsPlayerRenderer(AbstractClientPlayerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float headYaw) {
        float f = entityLiving.getSwimAnimation(partialTicks);
        if (entityLiving.isElytraFlying()) {
            this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
            float f1 = (float)entityLiving.getTicksElytraFlying() + partialTicks;
            float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
            if (!entityLiving.isSpinAttacking()) {
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entityLiving.xRot)));
            }

            Vector3d vector3d = entityLiving.getLook(partialTicks);
            Vector3d vector3d1 = entityLiving.getMotion();
            double d0 = Entity.horizontalMag(vector3d1);
            double d1 = Entity.horizontalMag(vector3d);
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                matrixStackIn.mulPose(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            float swimController = this.modelProvider.getControllerValue("SwimController");
            this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
            float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.xRot : -90.0F;
            float f4 = MathHelper.lerp(f, 0.0F, f3) * swimController;
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f4));
            if (entityLiving.isActualySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, (double)0.3F);
            }
        } else {
            this.applyRotationsLivingRenderer(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks, headYaw);
        }
    }

    protected void applyRotationsLivingRenderer(AbstractClientPlayerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float headYaw) {
        if (this.func_230495_a_(entityLiving)) {
            rotationYaw += (float)(Math.cos((double)entityLiving.tickCount * 3.25D) * Math.PI * (double)0.4F);
        }

        Pose pose = entityLiving.getPose();
        if (pose != Pose.SLEEPING) {
            float bodyRotateAmount = this.modelProvider.getControllerValue("BodyRotateController");
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - MathHelper.rotLerp(bodyRotateAmount, headYaw, rotationYaw)));
        }

        if (entityLiving.deathTime > 0) {
            float f = ((float)entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * this.getDeathMaxRotation(entityLiving)));
        } else if (entityLiving.isSpinAttacking()) {
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F - entityLiving.xRot));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(((float)entityLiving.tickCount + partialTicks) * -75.0F));
        } else if (pose == Pose.SLEEPING) {
            Direction direction = entityLiving.getBedOrientation();
            float f1 = direction != null ? getFacingAngle(direction) : rotationYaw;
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(this.getDeathMaxRotation(entityLiving)));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270.0F));
        } else if (entityLiving.hasCustomName() || entityLiving instanceof PlayerEntity) {
            String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName().getString());
            if (("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof PlayerEntity) || ((PlayerEntity)entityLiving).isWearing(PlayerModelPart.CAPE))) {
                matrixStackIn.translate(0.0D, (double)(entityLiving.getHeight() + 0.1F), 0.0D);
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
        }
    }

    private static float getFacingAngle(Direction facingIn) {
        switch(facingIn) {
            case SOUTH:
                return 90.0F;
            case WEST:
                return 0.0F;
            case NORTH:
                return 270.0F;
            case EAST:
                return 180.0F;
            default:
                return 0.0F;
        }
    }

    @Override
    public GeoModelProvider<GeckoPlayer> getGeoModelProvider() {
        return this.modelProvider;
    }

    public Model_GeckoPlayer_ThirdPerson getAnimatedPlayerModel() {
        return this.modelProvider;
    }

    @Override
    public ResourceLocation getTextureLocation(GeckoPlayer geckoPlayer) {
        //Originally "getEntityTexture"
        //If it doesn't work properly, revert and try again
        return getTextureLocation((AbstractClientPlayerEntity) geckoPlayer.getPlayer());
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        RenderUtils.translate(bone, matrixStack);
        RenderUtils.moveToPivot(bone, matrixStack);
        RenderUtils.rotate(bone, matrixStack);
        RenderUtils.scale(bone, matrixStack);
        // Record xform matrices for relevant bones
        if (bone instanceof GeckoTestBone) {
            GeckoTestBone testBone = (GeckoTestBone)bone;
            if (
                    testBone.name.equals("LeftHeldItem") || testBone.name.equals("RightHeldItem") ||
                            testBone.name.equals("Head") ||
                            testBone.name.equals("Body") ||
                            testBone.name.equals("LeftArm") ||
                            testBone.name.equals("RightArm") ||
                            testBone.name.equals("RightLeg") ||
                            testBone.name.equals("LeftLeg")
            ) {
                matrixStack.pushPose();
                if (!testBone.name.equals("LeftHeldItem") && !testBone.name.equals("RightHeldItem")) {
                    matrixStack.scale(-1.0F, -1.0F, 1.0F);
                }
                if (testBone.name.equals("Body")) {
                    matrixStack.translate(0, -0.75, 0);
                }
                if (testBone.name.equals("LeftArm")) {
                    matrixStack.translate(-0.075, 0, 0);
                }
                if (testBone.name.equals("RightArm")) {
                    matrixStack.translate(0.075, 0, 0);
                }
                MatrixStack.Entry entry = matrixStack.last();
                testBone.setWorldSpaceNormal(entry.normal().copy());
                testBone.setWorldSpaceXForm(entry.pose().copy());
                matrixStack.popPose();
            }
        }
        RenderUtils.moveBackFromPivot(bone, matrixStack);
        if (!bone.isHidden) {
            Iterator var10 = bone.childCubes.iterator();

            while(var10.hasNext()) {
                GeoCube cube = (GeoCube)var10.next();
                matrixStack.pushPose();
                this.renderCube(cube, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                matrixStack.popPose();
            }

            var10 = bone.childBones.iterator();

            while(var10.hasNext()) {
                GeoBone childBone = (GeoBone)var10.next();
                this.renderRecursively(childBone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }

        matrixStack.popPose();

        for(LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> layerrenderer : this.layers) {
            if (layerrenderer instanceof IGeckoRenderLayer) ((IGeckoRenderLayer)layerrenderer).renderRecursively(bone, matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}
