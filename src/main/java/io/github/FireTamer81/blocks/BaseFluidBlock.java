package io.github.FireTamer81.blocks;

import java.util.function.Supplier;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;

public class BaseFluidBlock extends FlowingFluidBlock
{
    public BaseFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties.noCollission().strength(100F).noDrops());
    }

    /**
    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(this.getFluid() == FluidInit.NAMEK_FLUID_SOURCE.get() || this.getFluid() == FluidInit.NAMEK_FLUID_FLOWING.get()) {}
    }
    **/
}
