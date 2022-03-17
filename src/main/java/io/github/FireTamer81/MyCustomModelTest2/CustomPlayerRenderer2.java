package io.github.FireTamer81.MyCustomModelTest2;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomPlayerRenderer2 extends LivingRenderer<AbstractClientPlayerEntity, CustomPlayerModel2<AbstractClientPlayerEntity>> {

    public CustomPlayerRenderer2(EntityRendererManager manager) {
        this(manager, false);
    }

    public CustomPlayerRenderer2(EntityRendererManager manager, boolean isSlim) {
        super(manager, new CustomPlayerModel2<>(0.0F), 0.5F);
    }

    public void render(AbstractClientPlayerEntity playerEntity, float f1, float f2, MatrixStack matrixStack, IRenderTypeBuffer buffer, int i) {
        this.setModelProperties(playerEntity);
        super.render(playerEntity, f1, f2, matrixStack, buffer, i);
    }

    public Vector3d getRenderOffset(AbstractClientPlayerEntity playerEntity, float f1) {
        return playerEntity.isCrouching() ? new Vector3d(0.0D, -0.125D, 0.0D) : super.getRenderOffset(playerEntity, f1);
    }

    private void setModelProperties(AbstractClientPlayerEntity playerEntity) {
        CustomPlayerModel2<AbstractClientPlayerEntity> customPlayerModel = this.getModel();

        //Some other stuff like poses
        //If I do this there would also be a getArmPose method below this
    }

    public ResourceLocation getTextureLocation(AbstractClientPlayerEntity playerEntity) {
        return new ResourceLocation("textures/player_entity/human_skin_average.png");
    }

    protected void renderNameTag(AbstractClientPlayerEntity playerEntity, ITextComponent nameText, MatrixStack matrixStack, IRenderTypeBuffer buffer, int i) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(playerEntity);
        matrixStack.pushPose();
        if (d0 < 100.0D) {
            Scoreboard scoreboard = playerEntity.getScoreboard();
            ScoreObjective scoreobjective = scoreboard.getDisplayObjective(2);
            if (scoreobjective != null) {
                Score score = scoreboard.getOrCreatePlayerScore(playerEntity.getScoreboardName(), scoreobjective);
                super.renderNameTag(playerEntity, (new StringTextComponent(Integer.toString(score.getScore()))).append(" ").append(scoreobjective.getDisplayName()), matrixStack, buffer, i);
                matrixStack.translate(0.0D, (double)(9.0F * 1.15F * 0.025F), 0.0D);
            }
        }

        super.renderNameTag(playerEntity, nameText, matrixStack, buffer, i);
        matrixStack.popPose();
    }


    /**
     * This is used in rendering the hands in first person. For now there is no use for me.
     */
    /*
    public void renderRightHand(MatrixStack p_229144_1_, IRenderTypeBuffer p_229144_2_, int p_229144_3_, AbstractClientPlayerEntity p_229144_4_) {
        this.renderHand(p_229144_1_, p_229144_2_, p_229144_3_, p_229144_4_, (this.model).rightArm, (this.model).rightSleeve);
    }

    public void renderLeftHand(MatrixStack p_229146_1_, IRenderTypeBuffer p_229146_2_, int p_229146_3_, AbstractClientPlayerEntity p_229146_4_) {
        this.renderHand(p_229146_1_, p_229146_2_, p_229146_3_, p_229146_4_, (this.model).leftArm, (this.model).leftSleeve);
    }

    private void renderHand(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, AbstractClientPlayerEntity p_229145_4_, ModelRenderer p_229145_5_, ModelRenderer p_229145_6_) {
        PlayerModel<AbstractClientPlayerEntity> playermodel = this.getModel();
        this.setModelProperties(p_229145_4_);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(p_229145_4_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        p_229145_5_.xRot = 0.0F;
        p_229145_5_.render(p_229145_1_, p_229145_2_.getBuffer(RenderType.entitySolid(p_229145_4_.getSkinTextureLocation())), p_229145_3_, OverlayTexture.NO_OVERLAY);
        p_229145_6_.xRot = 0.0F;
        p_229145_6_.render(p_229145_1_, p_229145_2_.getBuffer(RenderType.entityTranslucent(p_229145_4_.getSkinTextureLocation())), p_229145_3_, OverlayTexture.NO_OVERLAY);
    }
    */

    protected void setupRotations(AbstractClientPlayerEntity playerEntity, MatrixStack matrixStack, float f1, float f2, float f3) {
        float f = playerEntity.getSwimAmount(f3);
        if (playerEntity.isFallFlying()) {
            super.setupRotations(playerEntity, matrixStack, f1, f2, f3);
            float fa = (float)playerEntity.getFallFlyingTicks() + f3;
            float fb = MathHelper.clamp(fa * fa / 100.0F, 0.0F, 1.0F);
            if (!playerEntity.isAutoSpinAttack()) {
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(fb * (-90.0F - playerEntity.xRot)));
            }

            Vector3d vector3d = playerEntity.getViewVector(f3);
            Vector3d vector3d1 = playerEntity.getDeltaMovement();
            double d0 = Entity.getHorizontalDistanceSqr(vector3d1);
            double d1 = Entity.getHorizontalDistanceSqr(vector3d);
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                matrixStack.mulPose(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(playerEntity, matrixStack, f1, f2, f3);
            float fc = playerEntity.isInWater() ? -90.0F - playerEntity.xRot : -90.0F;
            float fd = MathHelper.lerp(f, 0.0F, fc);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(fd));
            if (playerEntity.isVisuallySwimming()) {
                matrixStack.translate(0.0D, -1.0D, (double)0.3F);
            }
        } else {
            super.setupRotations(playerEntity, matrixStack, f1, f2, f3);
        }

    }
}
