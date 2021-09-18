package io.github.FireTamer81._testing;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;

public class WarenaiBlockSlab extends SlabBlock
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;


    public WarenaiBlockSlab(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));
    }


    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(TYPE, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED);
    }
}
