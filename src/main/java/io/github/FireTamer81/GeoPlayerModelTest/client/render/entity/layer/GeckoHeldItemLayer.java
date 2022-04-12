package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.GeckoTestBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer_Renderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class GeckoHeldItemLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> implements IGeckoRenderLayer {

    private GeckoPlayer_Renderer renderPlayerAnimated;

    public GeckoHeldItemLayer(GeckoPlayer_Renderer entityRenderIn) {
        super(entityRenderIn);
        renderPlayerAnimated = entityRenderIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!renderPlayerAnimated.getAnimatedPlayerModel().isInitialized()) return;
        boolean flag = entityLivingBaseIn.getMainArm() == HandSide.RIGHT;
        ItemStack mainHandStack = entityLivingBaseIn.getMainHandItem();
        ItemStack offHandStack = entityLivingBaseIn.getOffhandItem();

        AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(entitylivingbaseIn);
        if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
            Ability ability = abilityCapability.getActiveAbility();
            mainHandStack = ability.heldItemMainHandOverride() != null ? ability.heldItemMainHandOverride() : mainHandStack;
            offHandStack = ability.heldItemOffHandOverride() != null ? ability.heldItemOffHandOverride() : offHandStack;
        }

        ItemStack itemstack = flag ? offHandStack : mainHandStack;
        ItemStack itemstack1 = flag ? mainHandStack : offHandStack;
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            matrixStackIn.pushPose();

            if (this.getParentModel().young) {
                float f = 0.5F;
                matrixStackIn.translate(0.0D, 0.75D, 0.0D);
                matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            }

            this.func_229135_a_(entityLivingBaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, matrixStackIn, bufferIn, packedLightIn);
            this.func_229135_a_(entityLivingBaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, matrixStackIn, bufferIn, packedLightIn);

            matrixStackIn.popPose();
        }
    }


    private void func_229135_a_(LivingEntity entity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide side, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
        if (!itemStack.isEmpty()) {
            String boneName = side == HandSide.RIGHT ? "RightHeldItem" : "LeftHeldItem";
            GeckoTestBone bone = renderPlayerAnimated.getAnimatedPlayerModel().getGeckoTestBone(boneName);
            MatrixStack newMatrixStack = new MatrixStack();
            newMatrixStack.last().normal().mul(bone.getWorldSpaceNormal());
            newMatrixStack.last().pose().multiply(bone.getWorldSpaceXForm());
            newMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            boolean flag = side == HandSide.LEFT;
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemStack, transformType, flag, newMatrixStack, buffer, packedLightIn);
        }
    }
}
