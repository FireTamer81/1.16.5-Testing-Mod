package io.github.FireTamer81.MyCustomPlayerModelTest4;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.swing.*;

/**
 * RevisedMalePlayerModel - FireTamer81
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TestPlayerModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer CenterMass;
    public ModelRenderer Chest;
    public ModelRenderer NoMove_Connectors;
    public ModelRenderer LeftShoulderConnector;
    public ModelRenderer RightShoulderConnector;
    public ModelRenderer Neck;
    public ModelRenderer RightNeckMuscle_r1;
    public ModelRenderer LeftNeckMuscle_r1;
    public ModelRenderer Head;
    public ModelRenderer Abs;
    public ModelRenderer Hips;
    public ModelRenderer LeftThigh;
    public ModelRenderer LeftCalf;
    public ModelRenderer LeftAnkle;
    public ModelRenderer LeftToe;
    public ModelRenderer RightThigh;
    public ModelRenderer RightCalf;
    public ModelRenderer RightAnkle;
    public ModelRenderer RightToe;
    public ModelRenderer LeftBicep;
    public ModelRenderer LeftForearm;
    public ModelRenderer LeftWrist;
    public ModelRenderer LeftPalm;
    public ModelRenderer LeftThumb_Seg1;
    public ModelRenderer LeftThumb_Seg2;
    public ModelRenderer LeftFinger1_Seg1;
    public ModelRenderer LeftFinger1_Seg2;
    public ModelRenderer LeftFinger1_Seg3;
    public ModelRenderer LeftFinger2_Seg1;
    public ModelRenderer LeftFinger2_Seg2;
    public ModelRenderer LeftFinger2_Seg3;
    public ModelRenderer LeftFinger3_Seg1;
    public ModelRenderer LeftFinger3_Seg2;
    public ModelRenderer LeftFinger3_Seg3;
    public ModelRenderer LeftFinger4_Seg1;
    public ModelRenderer LeftFinger4_Seg2;
    public ModelRenderer LeftFinger4_Seg3;
    public ModelRenderer RightBicep;
    public ModelRenderer RightForearm;
    public ModelRenderer RightWrist;
    public ModelRenderer RightPalm;
    public ModelRenderer RightThumb_Seg1;
    public ModelRenderer RightThumb_Seg2;
    public ModelRenderer RightFinger1_Seg1;
    public ModelRenderer RightFinger1_Seg2;
    public ModelRenderer RightFinger1_Seg3;
    public ModelRenderer RightFinger2_Seg1;
    public ModelRenderer RightFinger2_Seg2;
    public ModelRenderer RightFinger2_Seg3;
    public ModelRenderer RightFinger3_Seg1;
    public ModelRenderer RightFinger3_Seg2;
    public ModelRenderer RightFinger3_Seg3;
    public ModelRenderer RightFinger4_Seg1;
    public ModelRenderer RightFinger4_Seg2;
    public ModelRenderer RightFinger4_Seg3;

    public TestPlayerModel() {

        texWidth = 64;
        texHeight = 64;

        CenterMass = new ModelRenderer(this);
        CenterMass.setPos(0.0F, 9.0F, 0.0F);


        Chest = new ModelRenderer(this);
        Chest.setPos(0.0F, -5.5F, 0.0F);
        CenterMass.addChild(Chest);
        Chest.texOffs(0, 14).addBox(-4.0F, -2.7F, -2.0F, 8.0F, 6.0F, 4.0F, 0.25F, false);

        NoMove_Connectors = new ModelRenderer(this);
        NoMove_Connectors.setPos(0.0F, 0.0F, 0.0F);
        Chest.addChild(NoMove_Connectors);


        LeftShoulderConnector = new ModelRenderer(this);
        LeftShoulderConnector.setPos(0.0F, 0.0F, 0.0F);
        NoMove_Connectors.addChild(LeftShoulderConnector);
        LeftShoulderConnector.texOffs(42, 47).addBox(-5.0F, -2.2F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, false);

        RightShoulderConnector = new ModelRenderer(this);
        RightShoulderConnector.setPos(0.0F, 0.0F, 0.0F);
        NoMove_Connectors.addChild(RightShoulderConnector);
        RightShoulderConnector.texOffs(32, 46).addBox(3.0F, -2.2F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, false);

        Neck = new ModelRenderer(this);
        Neck.setPos(-1.35F, 9.25F, -3.275F);
        NoMove_Connectors.addChild(Neck);
        Neck.texOffs(12, 24).addBox(0.35F, -13.2F, 2.25F, 2.0F, 1.0F, 2.0F, 0.8F, false);

        RightNeckMuscle_r1 = new ModelRenderer(this);
        RightNeckMuscle_r1.setPos(-0.8F, -12.5F, 3.25F);
        Neck.addChild(RightNeckMuscle_r1);
        setRotationAngle(RightNeckMuscle_r1, 0.0F, 0.0F, -0.3927F);
        RightNeckMuscle_r1.texOffs(44, 21).addBox(-1.95F, -0.7F, -2.0F, 3.0F, 2.0F, 4.0F, -0.25F, false);

        LeftNeckMuscle_r1 = new ModelRenderer(this);
        LeftNeckMuscle_r1.setPos(3.5F, -12.5F, 3.25F);
        Neck.addChild(LeftNeckMuscle_r1);
        setRotationAngle(LeftNeckMuscle_r1, 0.0F, 0.0F, 0.3927F);
        LeftNeckMuscle_r1.texOffs(44, 33).addBox(-1.0F, -0.7F, -2.0F, 3.0F, 2.0F, 4.0F, -0.25F, false);

        Head = new ModelRenderer(this);
        Head.setPos(-1.35F, 9.25F, -2.775F);
        Chest.addChild(Head);
        Head.texOffs(0, 0).addBox(-2.15F, -20.15F, -0.75F, 7.0F, 7.0F, 7.0F, 0.0F, false);

        Abs = new ModelRenderer(this);
        Abs.setPos(0.0F, 0.0F, 0.0F);
        Chest.addChild(Abs);
        Abs.texOffs(20, 20).addBox(-4.0F, 3.2F, -2.0F, 8.0F, 4.0F, 4.0F, -0.1F, false);

        Hips = new ModelRenderer(this);
        Hips.setPos(0.0F, 6.75F, 0.0F);
        Abs.addChild(Hips);
        Hips.texOffs(21, 0).addBox(-4.0F, 0.3F, -2.0F, 8.0F, 2.0F, 4.0F, 0.0F, false);

        LeftThigh = new ModelRenderer(this);
        LeftThigh.setPos(2.0F, 2.0F, 0.0F);
        Hips.addChild(LeftThigh);
        LeftThigh.texOffs(40, 6).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        LeftCalf = new ModelRenderer(this);
        LeftCalf.setPos(0.0F, 5.0F, 0.0F);
        LeftThigh.addChild(LeftCalf);
        LeftCalf.texOffs(32, 37).addBox(-2.0F, 0.05F, -2.0F, 4.0F, 5.0F, 4.0F, -0.1F, false);

        LeftAnkle = new ModelRenderer(this);
        LeftAnkle.setPos(0.0F, 4.5F, 0.0F);
        LeftCalf.addChild(LeftAnkle);
        LeftAnkle.texOffs(0, 42).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);

        LeftToe = new ModelRenderer(this);
        LeftToe.setPos(0.0F, 1.0F, -2.0F);
        LeftAnkle.addChild(LeftToe);
        LeftToe.texOffs(20, 46).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        RightThigh = new ModelRenderer(this);
        RightThigh.setPos(-2.0F, 2.0F, 0.0F);
        Hips.addChild(RightThigh);
        RightThigh.texOffs(16, 37).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        RightCalf = new ModelRenderer(this);
        RightCalf.setPos(0.0F, 5.0F, 0.0F);
        RightThigh.addChild(RightCalf);
        RightCalf.texOffs(0, 33).addBox(-2.0F, 0.05F, -2.0F, 4.0F, 5.0F, 4.0F, -0.1F, false);

        RightAnkle = new ModelRenderer(this);
        RightAnkle.setPos(0.0F, 4.5F, 0.0F);
        RightCalf.addChild(RightAnkle);
        RightAnkle.texOffs(40, 15).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);

        RightToe = new ModelRenderer(this);
        RightToe.setPos(0.0F, 1.0F, -2.0F);
        RightAnkle.addChild(RightToe);
        RightToe.texOffs(28, 6).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        LeftBicep = new ModelRenderer(this);
        LeftBicep.setPos(6.0F, -1.0F, 0.0F);
        Chest.addChild(LeftBicep);
        LeftBicep.texOffs(32, 28).addBox(-1.7F, -1.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);

        LeftForearm = new ModelRenderer(this);
        LeftForearm.setPos(0.25F, 3.0F, 0.0F);
        LeftBicep.addChild(LeftForearm);
        LeftForearm.texOffs(16, 28).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 5.0F, 4.0F, -0.1F, false);

        LeftWrist = new ModelRenderer(this);
        LeftWrist.setPos(0.0F, 4.9F, 0.0F);
        LeftForearm.addChild(LeftWrist);
        LeftWrist.texOffs(45, 0).addBox(-1.0F, 0.15F, -2.0F, 2.0F, 1.0F, 4.0F, -0.15F, false);

        LeftPalm = new ModelRenderer(this);
        LeftPalm.setPos(-0.3F, 1.45F, 0.0F);
        LeftWrist.addChild(LeftPalm);
        LeftPalm.texOffs(12, 46).addBox(-0.4F, -0.8F, -2.0F, 2.0F, 1.0F, 4.0F, -0.08F, false);
        LeftPalm.texOffs(6, 49).addBox(-1.1F, -0.8F, -2.0F, 1.0F, 1.0F, 4.0F, -0.2F, false);

        LeftThumb_Seg1 = new ModelRenderer(this);
        LeftThumb_Seg1.setPos(0.1F, -0.6F, -1.525F);
        LeftPalm.addChild(LeftThumb_Seg1);
        setRotationAngle(LeftThumb_Seg1, 0.0F, 0.3927F, 0.0F);
        LeftThumb_Seg1.texOffs(23, 50).addBox(-0.5F, -0.2F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        LeftThumb_Seg2 = new ModelRenderer(this);
        LeftThumb_Seg2.setPos(1.05F, 0.0F, 0.0F);
        LeftThumb_Seg1.addChild(LeftThumb_Seg2);
        setRotationAngle(LeftThumb_Seg2, 0.0F, 0.0F, 0.0F);
        LeftThumb_Seg2.texOffs(49, 47).addBox(-0.2F, -0.2F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        LeftFinger1_Seg1 = new ModelRenderer(this);
        LeftFinger1_Seg1.setPos(1.35F, -0.4F, -1.275F);
        LeftPalm.addChild(LeftFinger1_Seg1);
        LeftFinger1_Seg1.texOffs(52, 42).addBox(-0.2F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        LeftFinger1_Seg2 = new ModelRenderer(this);
        LeftFinger1_Seg2.setPos(1.45F, 0.0F, 0.0F);
        LeftFinger1_Seg1.addChild(LeftFinger1_Seg2);
        LeftFinger1_Seg2.texOffs(35, 52).addBox(-0.3F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        LeftFinger1_Seg3 = new ModelRenderer(this);
        LeftFinger1_Seg3.setPos(1.2F, 0.2F, 0.0F);
        LeftFinger1_Seg2.addChild(LeftFinger1_Seg3);
        LeftFinger1_Seg3.texOffs(52, 29).addBox(-0.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        LeftFinger2_Seg1 = new ModelRenderer(this);
        LeftFinger2_Seg1.setPos(1.35F, -0.4F, -0.425F);
        LeftPalm.addChild(LeftFinger2_Seg1);
        LeftFinger2_Seg1.texOffs(29, 52).addBox(-0.2F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        LeftFinger2_Seg2 = new ModelRenderer(this);
        LeftFinger2_Seg2.setPos(1.45F, 0.0F, 0.0F);
        LeftFinger2_Seg1.addChild(LeftFinger2_Seg2);
        LeftFinger2_Seg2.texOffs(52, 27).addBox(-0.3F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        LeftFinger2_Seg3 = new ModelRenderer(this);
        LeftFinger2_Seg3.setPos(1.2F, 0.2F, 0.0F);
        LeftFinger2_Seg2.addChild(LeftFinger2_Seg3);
        LeftFinger2_Seg3.texOffs(23, 52).addBox(-0.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        LeftFinger3_Seg1 = new ModelRenderer(this);
        LeftFinger3_Seg1.setPos(1.35F, -0.4F, 0.425F);
        LeftPalm.addChild(LeftFinger3_Seg1);
        LeftFinger3_Seg1.texOffs(52, 17).addBox(-0.2F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        LeftFinger3_Seg2 = new ModelRenderer(this);
        LeftFinger3_Seg2.setPos(1.45F, 0.0F, 0.0F);
        LeftFinger3_Seg1.addChild(LeftFinger3_Seg2);
        LeftFinger3_Seg2.texOffs(52, 15).addBox(-0.3F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        LeftFinger3_Seg3 = new ModelRenderer(this);
        LeftFinger3_Seg3.setPos(1.2F, 0.2F, 0.0F);
        LeftFinger3_Seg2.addChild(LeftFinger3_Seg3);
        LeftFinger3_Seg3.texOffs(52, 7).addBox(-0.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        LeftFinger4_Seg1 = new ModelRenderer(this);
        LeftFinger4_Seg1.setPos(1.35F, -0.4F, 1.275F);
        LeftPalm.addChild(LeftFinger4_Seg1);
        LeftFinger4_Seg1.texOffs(52, 5).addBox(-0.2F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        LeftFinger4_Seg2 = new ModelRenderer(this);
        LeftFinger4_Seg2.setPos(1.45F, 0.0F, 0.0F);
        LeftFinger4_Seg1.addChild(LeftFinger4_Seg2);
        LeftFinger4_Seg2.texOffs(18, 51).addBox(-0.3F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        LeftFinger4_Seg3 = new ModelRenderer(this);
        LeftFinger4_Seg3.setPos(1.2F, 0.2F, 0.0F);
        LeftFinger4_Seg2.addChild(LeftFinger4_Seg3);
        LeftFinger4_Seg3.texOffs(12, 51).addBox(-0.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        RightBicep = new ModelRenderer(this);
        RightBicep.setPos(-6.0F, -1.0F, 0.0F);
        Chest.addChild(RightBicep);
        RightBicep.texOffs(24, 10).addBox(-2.3F, -1.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);

        RightForearm = new ModelRenderer(this);
        RightForearm.setPos(-0.25F, 3.0F, 0.0F);
        RightBicep.addChild(RightForearm);
        RightForearm.texOffs(0, 24).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 5.0F, 4.0F, -0.1F, false);

        RightWrist = new ModelRenderer(this);
        RightWrist.setPos(0.0F, 4.9F, 0.0F);
        RightForearm.addChild(RightWrist);
        RightWrist.texOffs(44, 42).addBox(-1.0F, 0.15F, -2.0F, 2.0F, 1.0F, 4.0F, -0.15F, false);

        RightPalm = new ModelRenderer(this);
        RightPalm.setPos(0.3F, 1.45F, 0.0F);
        RightWrist.addChild(RightPalm);
        RightPalm.texOffs(44, 27).addBox(-1.6F, -0.8F, -2.0F, 2.0F, 1.0F, 4.0F, -0.08F, false);
        RightPalm.texOffs(0, 48).addBox(0.1F, -0.8F, -2.0F, 1.0F, 1.0F, 4.0F, -0.2F, false);

        RightThumb_Seg1 = new ModelRenderer(this);
        RightThumb_Seg1.setPos(-0.15F, -0.6F, -1.525F);
        RightPalm.addChild(RightThumb_Seg1);
        setRotationAngle(RightThumb_Seg1, 0.0F, -0.3927F, 0.0F);
        RightThumb_Seg1.texOffs(49, 40).addBox(-1.45F, -0.2F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        RightThumb_Seg2 = new ModelRenderer(this);
        RightThumb_Seg2.setPos(-1.05F, 0.0F, 0.0F);
        RightThumb_Seg1.addChild(RightThumb_Seg2);
        RightThumb_Seg2.texOffs(39, 46).addBox(-1.75F, -0.2F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        RightFinger1_Seg1 = new ModelRenderer(this);
        RightFinger1_Seg1.setPos(-1.35F, -0.4F, -1.275F);
        RightPalm.addChild(RightFinger1_Seg1);
        RightFinger1_Seg1.texOffs(44, 39).addBox(-1.8F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        RightFinger1_Seg2 = new ModelRenderer(this);
        RightFinger1_Seg2.setPos(-1.45F, 0.0F, 0.0F);
        RightFinger1_Seg1.addChild(RightFinger1_Seg2);
        RightFinger1_Seg2.texOffs(41, 2).addBox(-1.7F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        RightFinger1_Seg3 = new ModelRenderer(this);
        RightFinger1_Seg3.setPos(-1.2F, 0.2F, 0.0F);
        RightFinger1_Seg2.addChild(RightFinger1_Seg3);
        RightFinger1_Seg3.texOffs(41, 0).addBox(-1.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        RightFinger2_Seg1 = new ModelRenderer(this);
        RightFinger2_Seg1.setPos(-1.35F, -0.4F, -0.425F);
        RightPalm.addChild(RightFinger2_Seg1);
        RightFinger2_Seg1.texOffs(40, 21).addBox(-1.8F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        RightFinger2_Seg2 = new ModelRenderer(this);
        RightFinger2_Seg2.setPos(-1.45F, 0.0F, 0.0F);
        RightFinger2_Seg1.addChild(RightFinger2_Seg2);
        RightFinger2_Seg2.texOffs(28, 39).addBox(-1.7F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        RightFinger2_Seg3 = new ModelRenderer(this);
        RightFinger2_Seg3.setPos(-1.2F, 0.2F, 0.0F);
        RightFinger2_Seg2.addChild(RightFinger2_Seg3);
        RightFinger2_Seg3.texOffs(38, 6).addBox(-1.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        RightFinger3_Seg1 = new ModelRenderer(this);
        RightFinger3_Seg1.setPos(-1.35F, -0.4F, 0.425F);
        RightPalm.addChild(RightFinger3_Seg1);
        RightFinger3_Seg1.texOffs(28, 37).addBox(-1.8F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        RightFinger3_Seg2 = new ModelRenderer(this);
        RightFinger3_Seg2.setPos(-1.45F, 0.0F, 0.0F);
        RightFinger3_Seg1.addChild(RightFinger3_Seg2);
        RightFinger3_Seg2.texOffs(28, 30).addBox(-1.7F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        RightFinger3_Seg3 = new ModelRenderer(this);
        RightFinger3_Seg3.setPos(-1.2F, 0.2F, 0.0F);
        RightFinger3_Seg2.addChild(RightFinger3_Seg3);
        RightFinger3_Seg3.texOffs(28, 28).addBox(-1.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);

        RightFinger4_Seg1 = new ModelRenderer(this);
        RightFinger4_Seg1.setPos(-1.35F, -0.4F, 1.275F);
        RightPalm.addChild(RightFinger4_Seg1);
        RightFinger4_Seg1.texOffs(0, 4).addBox(-1.8F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.09F, false);

        RightFinger4_Seg2 = new ModelRenderer(this);
        RightFinger4_Seg2.setPos(-1.45F, 0.0F, 0.0F);
        RightFinger4_Seg1.addChild(RightFinger4_Seg2);
        RightFinger4_Seg2.texOffs(0, 2).addBox(-1.7F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, -0.1F, false);

        RightFinger4_Seg3 = new ModelRenderer(this);
        RightFinger4_Seg3.setPos(-1.2F, 0.2F, 0.0F);
        RightFinger4_Seg2.addChild(RightFinger4_Seg3);
        RightFinger4_Seg3.texOffs(0, 0).addBox(-1.5F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, -0.11F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.setRotationByDegrees(CenterMass, 180, 180, 0); //Flips the model rightside up (Remove the y rotation once movement is done properly)
        this.setPosition(CenterMass, 0.0f, 15.0f, 0.0f); //Makes the model flush with the ground

        AbstractClientPlayerEntity entity = Minecraft.getInstance().player;
        this.playerCrouchPose(entity);

        CenterMass.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationByDegrees(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        this.setRotationAngle(modelRenderer, (float)Math.toRadians(x), (float)Math.toRadians(y), (float)Math.toRadians(z));
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public void setPosition(ModelRenderer renderer, float x, float y, float z) {
        renderer.x = x;
        renderer.y = y;
        renderer.z = z;
    }

    public void playerCrouchPose(AbstractClientPlayerEntity entity) {
        if(entity.isCrouching()) {
            this.setRotationByDegrees(CenterMass, 177.5f, 0, 0); //180 to rotate the model right side up - 2.5 from the initial rotation.
            this.setPosition(CenterMass, 0.0f, 16.75f, 0.5f); //y = 15 + 1.75, z = 0 + 0.5

            this.setRotationByDegrees(Chest, 12.5f, 0, 0);
            this.setRotationByDegrees(Abs, -7.5f, 0, 0);
            this.setRotationByDegrees(Hips, -5, 0, 0);
            this.setRotationByDegrees(LeftThigh, -7.43656f, 0.9762f, -15.06344f);
            this.setRotationByDegrees(LeftCalf, 9.91615f, -1.29876f, 7.38733f);
            this.setRotationByDegrees(LeftAnkle, 0, 0, 7.5f);
            this.setRotationByDegrees(RightThigh, -7.43656f, -0.9762f, 15.06344f);
            this.setRotationByDegrees(RightCalf, 9.91615f, 1.29876f, -7.38733f);
            this.setRotationByDegrees(RightAnkle, 0, 0, -7.5f);

            this.setRotationByDegrees(LeftBicep, -7.5f, 0, -5);
            this.setRotationByDegrees(LeftForearm, -90, -60, 90);
            this.setRotationByDegrees(LeftPalm, 0, 0, 90);
            this.setRotationByDegrees(LeftThumb_Seg1, 21.80633f, -16.81578f, 76.0958f);
            this.setRotationByDegrees(LeftThumb_Seg2, 0, -32.5f, 0);
            this.setRotationByDegrees(LeftFinger1_Seg1, 0, 0, 90);
            this.setRotationByDegrees(LeftFinger1_Seg2, 0, 0, 100);
            this.setRotationByDegrees(LeftFinger1_Seg3, 0, 0, 50);
            this.setRotationByDegrees(LeftFinger2_Seg1, 0, 0, 90);
            this.setRotationByDegrees(LeftFinger2_Seg2, 0, 0, 100);
            this.setRotationByDegrees(LeftFinger2_Seg3, 0, 0, 50);
            this.setRotationByDegrees(LeftFinger3_Seg1, 0, 0, 90);
            this.setRotationByDegrees(LeftFinger3_Seg2, 0, 0, 100);
            this.setRotationByDegrees(LeftFinger3_Seg3, 0, 0, 50);
            this.setRotationByDegrees(LeftFinger4_Seg1, 0, 0, 90);
            this.setRotationByDegrees(LeftFinger4_Seg2, 0, 0, 100);
            this.setRotationByDegrees(LeftFinger4_Seg3, 0, 0, 50);

            this.setRotationByDegrees(RightBicep, -7.5f, 0, 5);
            this.setRotationByDegrees(RightForearm, -90, 60, -90);
            this.setRotationByDegrees(RightPalm, 0, 0, -90);
            this.setRotationByDegrees(RightThumb_Seg1, 21.80633f, 16.81578f, -76.0958f);
            this.setRotationByDegrees(RightThumb_Seg2, 0, 32.5f, 0);
            this.setRotationByDegrees(RightFinger1_Seg1, 0, 0, -90);
            this.setRotationByDegrees(RightFinger1_Seg2, 0, 0, -100);
            this.setRotationByDegrees(RightFinger1_Seg3, 0, 0, -50);
            this.setRotationByDegrees(RightFinger2_Seg1, 0, 0, -90);
            this.setRotationByDegrees(RightFinger2_Seg2, 0, 0, -100);
            this.setRotationByDegrees(RightFinger2_Seg3, 0, 0, -50);
            this.setRotationByDegrees(RightFinger3_Seg1, 0, 0, -90);
            this.setRotationByDegrees(RightFinger3_Seg2, 0, 0, -100);
            this.setRotationByDegrees(RightFinger3_Seg3, 0, 0, -50);
            this.setRotationByDegrees(RightFinger4_Seg1, 0, 0, -90);
            this.setRotationByDegrees(RightFinger4_Seg2, 0, 0, -100);
            this.setRotationByDegrees(RightFinger4_Seg3, 0, 0, -50);


            //Timer timer1 = new Timer(0, action -> this.setRotationByDegrees(CenterMass, 90, 0, 0));
            //timer1.setRepeats(false);
            //timer1.setInitialDelay(5000);
            //timer1.start();
        }
    }





    /******************************************************************************************************************/
    // Animation Stuff
    /******************************************************************************************************************/

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        //ModelHelper.bobArms(this.RightBicep, this.LeftBicep, limbSwingAmount);
        //this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        //this.Head.xRot = (-(float)Math.PI / 4F);

        /*
        this.setRotationByDegrees(LeftBicep, -7.5f, 0, 5);
        this.setRotationByDegrees(LeftForearm, -90, -60, 90);
        this.setRotationByDegrees(RightBicep, -7.5f, 0, 5);
        this.setRotationByDegrees(RightForearm, -90, 60, -90);
        this.setRotationByDegrees(RightAnkle, 0, 0, -7.5f);
        this.setRotationByDegrees(RightCalf, 9.9162f, 1.2988f, -7.3873f);
        this.setRotationByDegrees(LeftAnkle, 0, 0, 7.5f);
        this.setRotationByDegrees(RightThigh, -7.4366f, -0.9762f, 15.0634f);
        this.setRotationByDegrees(Hips, -5, 0, 0);
        this.setRotationByDegrees(Chest, 12.5f, 0, 0);
        this.setRotationByDegrees(Abs, -7.5f, 0, 0);
        this.setRotationByDegrees(LeftCalf, 9.9162f, -1.2988f, 7.3873f);
        this.setRotationByDegrees(LeftThigh, -7.4366f, 0.9762f, -15.0634f);

        //Hand Pieces Rotations
        this.setRotationByDegrees(RightThumb_Seg1, 21.8063f, 16.8158f, -76.0958f);
        this.setRotationByDegrees(RightThumb_Seg2, 0, 32.5f, 0);
        this.setRotationByDegrees(LeftThumb_Seg2, 0, -32.5f, 0);
        this.setRotationByDegrees(LeftThumb_Seg1, 21.8063f, -16.8158f, 76.0958f);
        this.setRotationByDegrees(RightFinger1_Seg1, 0, 0, -90);
        this.setRotationByDegrees(RightFinger2_Seg1, 0, 0, -90);
        this.setRotationByDegrees(RightFinger3_Seg1, 0, 0, -90);
        this.setRotationByDegrees(RightFinger4_Seg1, 0, 0, -90);
        this.setRotationByDegrees(RightFinger1_Seg2, 0, 0, -100);
        this.setRotationByDegrees(RightFinger2_Seg2, 0, 0, -100);
        this.setRotationByDegrees(RightFinger3_Seg2, 0, 0, -100);
        this.setRotationByDegrees(RightFinger4_Seg2, 0, 0, -100);
        this.setRotationByDegrees(RightFinger1_Seg3, 0, 0, -50);
        this.setRotationByDegrees(RightFinger2_Seg3, 0, 0, -50);
        this.setRotationByDegrees(RightFinger3_Seg3, 0, 0, -50);
        this.setRotationByDegrees(RightFinger4_Seg3, 0, 0, -50);

        this.setRotationByDegrees(LeftFinger1_Seg1, 0, 0, -90);
        this.setRotationByDegrees(LeftFinger2_Seg1, 0, 0, -90);
        this.setRotationByDegrees(LeftFinger3_Seg1, 0, 0, -90);
        this.setRotationByDegrees(LeftFinger4_Seg1, 0, 0, -90);
        this.setRotationByDegrees(LeftFinger1_Seg2, 0, 0, -100);
        this.setRotationByDegrees(LeftFinger2_Seg2, 0, 0, -100);
        this.setRotationByDegrees(LeftFinger3_Seg2, 0, 0, -100);
        this.setRotationByDegrees(LeftFinger4_Seg2, 0, 0, -100);
        this.setRotationByDegrees(LeftFinger1_Seg3, 0, 0, -50);
        this.setRotationByDegrees(LeftFinger2_Seg3, 0, 0, -50);
        this.setRotationByDegrees(LeftFinger3_Seg3, 0, 0, -50);
        this.setRotationByDegrees(LeftFinger4_Seg3, 0, 0, -50);
        this.setRotationByDegrees(RightPalm, 0, 0, -90);
        this.setRotationByDegrees(LeftPalm, 0, 0, 90);
        */
    }
}