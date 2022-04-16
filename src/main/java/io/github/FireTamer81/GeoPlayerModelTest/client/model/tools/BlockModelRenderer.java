package io.github.FireTamer81.GeoPlayerModelTest.client.model.tools;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelBase;
import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.AdvancedModelRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * Created by BobMowzie on 5/1/2017.
 */
public class BlockModelRenderer extends AdvancedModelRenderer {
    private BlockState blockState;

    public BlockModelRenderer(AdvancedModelBase model) {
        super(model);
        setBlockState(Blocks.DIRT.getDefaultState());
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockState getBlockState() {
        return blockState;
    }
}