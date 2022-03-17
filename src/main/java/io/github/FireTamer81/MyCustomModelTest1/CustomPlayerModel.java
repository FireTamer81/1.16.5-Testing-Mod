package io.github.FireTamer81.MyCustomModelTest1;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class CustomPlayerModel extends BipedModel {

    ModelRenderer Head;
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


    public CustomPlayerModel() {
        this(0F); //Watch this, it might make everything unravel somehow
        texWidth = 96;
        texHeight = 96;

        this.buildModelPieces();
    }

    public CustomPlayerModel(float p_i1148_1_) {
        super(p_i1148_1_);
    }

    public void buildModelPieces() {
        Head = new ModelRenderer(this, 0, 25);
        Head.addBox(0F, 0F, 0F, 8, 8, 8);
        Head.setPos(-4F, -10F, -6F);
        Head.setTexSize(96, 96);
        Head.mirror = true;

    }

    public void render(LivingEntity entity, float f, float f1, float f2, float f3, float f4) {
        super.prepareMobModel(entity, f2, f3, f4);
        setupAnim(entity, f, f1, f2, f3, f4);
        //Head.render(f4);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }
}
