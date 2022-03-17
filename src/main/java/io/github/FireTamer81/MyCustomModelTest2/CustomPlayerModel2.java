package io.github.FireTamer81.MyCustomModelTest2;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CustomPlayerModel2<T extends LivingEntity> extends BipedModel<T>{

    ModelRenderer head;
    ModelRenderer chest;
    ModelRenderer rightBicep;
    ModelRenderer rightForearm;
    ModelRenderer leftBicep;
    ModelRenderer leftForearm;
    ModelRenderer stomach;
    ModelRenderer hips;
    ModelRenderer rightThigh;
    ModelRenderer rightCalf;
    ModelRenderer rightFoot;
    ModelRenderer leftThigh;
    ModelRenderer leftCalf;
    ModelRenderer leftFoot;


    public CustomPlayerModel2(float f1) {
        super(RenderType::entityTranslucent, f1, 0.0F, 64, 64);
        this.buildModelPieces();
    }

    public void buildModelPieces() {
        this.head = new ModelRenderer(this, 0, 25);
        this.head.addBox(0F, 0F, 0F, 8, 8, 8);
        this.head.setPos(-4F, -10F, -6F);
        this.head.setTexSize(96, 96);
        this.head.mirror = true;

    }

    public void setupAnim(T livingEntity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(livingEntity, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
        /*
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        if (livingEntity.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
            if (livingEntity.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (livingEntity.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }
        */
    }

    /*
    public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
        ModelRenderer modelrenderer = this.getArm(p_225599_1_);
        if (this.slim) {
            float f = 0.5F * (float)(p_225599_1_ == HandSide.RIGHT ? 1 : -1);
            modelrenderer.x += f;
            modelrenderer.translateAndRotate(p_225599_2_);
            modelrenderer.x -= f;
        } else {
            modelrenderer.translateAndRotate(p_225599_2_);
        }

    }
    */
}
