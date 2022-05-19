package io.github.FireTamer81.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class FacedPortalBlock extends HorizontalBlock
{
    public FacedPortalBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        Direction direction = context.getHorizontalDirection().getOpposite();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);

        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainer)
    {
        stateContainer.add(FACING);
    }

    /**
    //Voxel Shapes
    //private static final VoxelShape WEST_SHAPE = Block.box(0, 0, 0, 16, 13, 16);
    //private static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 0, 16, 13, 16);
    //private static final VoxelShape EAST_SHAPE = Block.box(16, 0, 0, 0, 13, 16);
    //private static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 16, 16, 13, 0);



    public ManufacturerCore(AbstractBlock.Properties properties)
    {
        super(properties);

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        //return SHAPES.get(this).get(state.getValue(HORIZONTAL_FACING));

        switch((Direction)state.getValue(FACING))
        {
            case SOUTH:
                return SOUTH_SHAPE;
            default:
                return NORTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case EAST:
                return EAST_SHAPE;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        Direction direction = context.getHorizontalDirection().getOpposite();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);

        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainer)
    {
        stateContainer.add(FACING);
    }
    **/
}
