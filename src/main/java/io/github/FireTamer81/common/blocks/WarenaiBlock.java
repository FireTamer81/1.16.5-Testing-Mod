package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81._testing.CustomBlockstateProperties;
import io.github.FireTamer81.init.ItemInit;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class WarenaiBlock extends Block
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;

    public WarenaiBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(CRACKED_DIRTY_CLEAN_POLISHED);
    }

}
