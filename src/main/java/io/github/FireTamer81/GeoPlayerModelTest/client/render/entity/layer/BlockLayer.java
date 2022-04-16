package io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.layer;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelRenderer;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.BlockModelRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class BlockLayer <T extends Entity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private final AdvancedModelRenderer root;

    public BlockLayer(IEntityRenderer<T, M> renderer, AdvancedModelRenderer root) {
        super(renderer);
        this.root = root;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.push();
        int packedOverlay = 0;
        if (entity instanceof LivingEntity) LivingRenderer.getPackedOverlay((LivingEntity) entity, 0.0F);
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        processModelRenderer(root, matrixStackIn, bufferIn, packedLightIn, packedOverlay, 1, 1, 1, 1, blockrendererdispatcher);
        matrixStackIn.pop();
    }

    public static void processModelRenderer(AdvancedModelRenderer modelRenderer, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, BlockRendererDispatcher dispatcher) {
        if (modelRenderer.showModel) {
            if (modelRenderer instanceof BlockModelRenderer || !modelRenderer.childModels.isEmpty()) {
                matrixStackIn.push();

                modelRenderer.translateRotate(matrixStackIn);
                if (!modelRenderer.isHidden() && modelRenderer instanceof BlockModelRenderer) {
                    BlockModelRenderer blockModelRenderer = (BlockModelRenderer) modelRenderer;
                    dispatcher.renderBlock(blockModelRenderer.getBlockState(), matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
                }

                // Render children
                for(ModelRenderer child : modelRenderer.childModels) {
                    if (child instanceof AdvancedModelRenderer) {
                        processModelRenderer((AdvancedModelRenderer) child, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha, dispatcher);
                    }
                }

                matrixStackIn.pop();
            }
        }
    }
}
