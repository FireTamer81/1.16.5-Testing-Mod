package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81._testing.CustomBlockstateProperties;
import io.github.FireTamer81.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class WarenaiBlockStairs extends StairsBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;



    public WarenaiBlockStairs(BlockState p_i48321_1_, Properties p_i48321_2_) {
        super(p_i48321_1_, p_i48321_2_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.BOTTOM)
                .setValue(SHAPE, StairsShape.STRAIGHT)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));

    }



    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, HALF, SHAPE, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED);
    }


}
