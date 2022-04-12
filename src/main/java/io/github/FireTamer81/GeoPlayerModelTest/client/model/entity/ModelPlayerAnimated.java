package io.github.FireTamer81.GeoPlayerModelTest.client.model.entity;

import com.google.common.collect.Lists;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.TestElytraModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ModelRendererMatrix;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.TestElytraModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.ModelBipedAnimated;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ModelPlayerAnimated<T extends LivingEntity> extends PlayerModel<T> {
    private List<ModelRenderer> modelRenderers = Lists.newArrayList();

    public ModelPlayerAnimated(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
        this.body = new ModelRendererMatrix(body);
        this.head = new ModelRendererMatrix(head);
        this.rightArm = new ModelRendererMatrix(rightArm);
        this.leftArm = new ModelRendererMatrix(leftArm);
        this.rightLeg = new ModelRendererMatrix(rightLeg);
        this.leftLeg = new ModelRendererMatrix(leftLeg);

        this.hat = new ModelRendererMatrix(hat);
        this.jacket = new ModelRendererMatrix(jacket);
        this.leftSleeve = new ModelRendererMatrix(leftSleeve);
        this.rightSleeve = new ModelRendererMatrix(rightSleeve);
        this.leftPants = new ModelRendererMatrix(leftPants);
        this.rightPants = new ModelRendererMatrix(rightPants);

        if (smallArmsIn) {
            modelRenderers.add(leftArm);
            modelRenderers.add(rightArm);
            modelRenderers.add(leftSleeve);
            modelRenderers.add(rightSleeve);
        }
        else {
            modelRenderers.add(leftArm);
            modelRenderers.add(leftSleeve);
            modelRenderers.add(rightSleeve);
        }
        modelRenderers.add(leftLeg);
        modelRenderers.add(leftPants);
        modelRenderers.add(rightPants);
        modelRenderers.add(jacket);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.jacket.copyFrom(this.body);
        this.hat.copyFrom(this.head);
    }

    @Override
    public ModelRenderer getRandomModelPart(Random randomIn) {
        return this.modelRenderers.get(randomIn.nextInt(this.modelRenderers.size()));
    }

    @Override
    public void copyPropertiesTo(EntityModel<T> p_217111_1_) {
        super.copyPropertiesTo(p_217111_1_);
        if (p_217111_1_ instanceof TestElytraModel) {
            TestElytraModel<?> elytraModel = (TestElytraModel<?>) p_217111_1_;
            elytraModel.bipedBody.copyFrom(this.body);
        }
    }

    @Override
    public void copyPropertiesTo(BipedModel<T> modelIn) {
        if (!(modelIn.body instanceof ModelRendererMatrix)) {
            modelIn.head = new ModelRendererMatrix(modelIn.head);
            modelIn.hat = new ModelRendererMatrix(modelIn.hat);
            modelIn.body = new ModelRendererMatrix(modelIn.body);
            modelIn.leftArm = new ModelRendererMatrix(modelIn.leftArm);
            modelIn.rightArm = new ModelRendererMatrix(modelIn.rightArm);
            modelIn.leftLeg = new ModelRendererMatrix(modelIn.leftLeg);
            modelIn.rightLeg = new ModelRendererMatrix(modelIn.rightLeg);
        }
        setUseMatrixMode(modelIn, true);
        super.copyPropertiesTo(modelIn);
    }

    public static void setUseMatrixMode(BipedModel<? extends LivingEntity> bipedModel, boolean useMatrixMode) {
        ModelBipedAnimated.setUseMatrixMode(bipedModel, useMatrixMode);
    }
}
