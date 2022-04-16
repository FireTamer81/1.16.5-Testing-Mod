package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelRenderer;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.MowzieRenderUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private AdvancedModelRenderer modelRenderer;
    private ItemStack itemstack;
    private ItemCameraTransforms.TransformType transformType;

    public ItemLayer(IEntityRenderer<T, M> renderer, AdvancedModelRenderer modelRenderer, ItemStack itemstack, ItemCameraTransforms.TransformType transformType) {
        super(renderer);
        this.itemstack = itemstack;
        this.modelRenderer = modelRenderer;
        this.transformType = transformType;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!modelRenderer.showModel || modelRenderer.isHidden()) return;
        matrixStackIn.push();
        MowzieRenderUtils.matrixStackFromModel(matrixStackIn, getModelRenderer());
        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entitylivingbaseIn, getItemstack(), transformType, false, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }

    public ItemStack getItemstack() {
        return itemstack;
    }

    public void setItemstack(ItemStack itemstack) {
        this.itemstack = itemstack;
    }

    public AdvancedModelRenderer getModelRenderer() {
        return modelRenderer;
    }

    public void setModelRenderer(AdvancedModelRenderer modelRenderer) {
        this.modelRenderer = modelRenderer;
    }
}
