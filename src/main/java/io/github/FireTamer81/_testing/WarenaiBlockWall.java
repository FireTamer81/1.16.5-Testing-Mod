package io.github.FireTamer81._testing;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.Map;
import java.util.stream.Stream;

public class WarenaiBlockWall extends Block
{
    /**
     * BlockState Properties and Values
     */
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallHeight> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallHeight> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallHeight> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallHeight> WEST_WALL = BlockStateProperties.WEST_WALL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;

    private static final VoxelShape POST_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape NORTH_SHAPE = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);


    /**
     * Main Constructor and Methods
     */
    public WarenaiBlockWall(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, Boolean.valueOf(true))
                .setValue(NORTH_WALL, WallHeight.NONE)
                .setValue(EAST_WALL, WallHeight.NONE)
                .setValue(SOUTH_WALL, WallHeight.NONE)
                .setValue(WEST_WALL, WallHeight.NONE)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));
    }


    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(UP, NORTH_WALL, EAST_WALL, SOUTH_WALL, WEST_WALL, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED);
    }


    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        IWorldReader reader = context.getLevel();
        BlockPos originPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        BlockPos northPos = originPos.north();
        BlockPos eastPos = originPos.east();
        BlockPos southPos = originPos.south();
        BlockPos westPos = originPos.west();
        BlockPos abovePos = originPos.above();

        BlockState northState = reader.getBlockState(northPos);
        BlockState eastState = reader.getBlockState(eastPos);
        BlockState southState = reader.getBlockState(southPos);
        BlockState westState = reader.getBlockState(westPos);
        BlockState aboveState = reader.getBlockState(abovePos);

        boolean flag1 = this.connectsTo(northState, northState.isFaceSturdy(reader, northPos, Direction.SOUTH), Direction.SOUTH);
        boolean flag2 = this.connectsTo(eastState, eastState.isFaceSturdy(reader, eastPos, Direction.WEST), Direction.WEST);
        boolean flag3 = this.connectsTo(southState, southState.isFaceSturdy(reader, southPos, Direction.NORTH), Direction.NORTH);
        boolean flag4 = this.connectsTo(westState, westState.isFaceSturdy(reader, westPos, Direction.EAST), Direction.EAST);
        BlockState isStateWaterlogged = this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));

        return this.updateShape(reader, isStateWaterlogged, abovePos, aboveState, flag1, flag2, flag3, flag4);
    }


    private WallHeight makeWallState(boolean bool, VoxelShape voxelShape1, VoxelShape voxelShape2) {
        if (bool) {
            return isCovered(voxelShape1, voxelShape2) ? WallHeight.TALL : WallHeight.LOW;
        } else {
            return WallHeight.NONE;
        }
    }


    /**
     * Shape Stuff
     */
/**
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext selectionContext) {
        return this.SEEN_SHAPE.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext selectionContext) {
        return this.COLLISION_SHAPE.get(state);
    }
**/
    //private final VoxelShape SEEN_SHAPE = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
    //private final VoxelShape COLLISION_SHAPE = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);

    private VoxelShape makeShape()
    {
        float origin1 = 4.0F;
        float origin2 = 3.0F;
        float origin3 = 16.0F;
        float origin4 = 0.0F;
        float origin5 = 14.0F;
        float origin6 = 16.0F;

        float f = 8.0F - origin1;
        float f1 = 8.0F + origin1;
        float f2 = 8.0F - origin2;
        float f3 = 8.0F + origin2;

        VoxelShape voxelshape = Block.box((double)f, 0.0D, (double)f, (double)f1, (double)origin3, (double)f1);
        VoxelShape voxelshape1 = Block.box((double)f2, (double)origin4, 0.0D, (double)f3, (double)origin5, (double)f3);
        VoxelShape voxelshape2 = Block.box((double)f2, (double)origin4, (double)f2, (double)f3, (double)origin5, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, (double)origin4, (double)f2, (double)f3, (double)origin5, (double)f3);
        VoxelShape voxelshape4 = Block.box((double)f2, (double)origin4, (double)f2, 16.0D, (double)origin5, (double)f3);
        VoxelShape voxelshape5 = Block.box((double)f2, (double)origin4, 0.0D, (double)f3, (double)origin6, (double)f3);
        VoxelShape voxelshape6 = Block.box((double)f2, (double)origin4, (double)f2, (double)f3, (double)origin6, 16.0D);
        VoxelShape voxelshape7 = Block.box(0.0D, (double)origin4, (double)f2, (double)f3, (double)origin6, (double)f3);
        VoxelShape voxelshape8 = Block.box((double)f2, (double)origin4, (double)f2, 16.0D, (double)origin6, (double)f3);


        for(Boolean obool : UP.getPossibleValues()) {
            for(WallHeight wallheight : EAST_WALL.getPossibleValues()) {
                for(WallHeight wallheight1 : NORTH_WALL.getPossibleValues()) {
                    for(WallHeight wallheight2 : WEST_WALL.getPossibleValues()) {
                        for(WallHeight wallheight3 : SOUTH_WALL.getPossibleValues()) {
                            VoxelShape voxelshape9 = VoxelShapes.empty();
                            voxelshape9 = applyWallShape(voxelshape9, wallheight, voxelshape4, voxelshape8);
                            voxelshape9 = applyWallShape(voxelshape9, wallheight2, voxelshape3, voxelshape7);
                            voxelshape9 = applyWallShape(voxelshape9, wallheight1, voxelshape1, voxelshape5);
                            voxelshape9 = applyWallShape(voxelshape9, wallheight3, voxelshape2, voxelshape6);
                            if (obool) {
                                voxelshape9 = VoxelShapes.or(voxelshape9, voxelshape);
                            }

                            BlockState blockstate = this.defaultBlockState()
                                    .setValue(UP, obool)
                                    .setValue(EAST_WALL, wallheight)
                                    .setValue(WEST_WALL, wallheight2)
                                    .setValue(NORTH_WALL, wallheight1)
                                    .setValue(SOUTH_WALL, wallheight3);
                            //builder.put(blockstate.setValue(WATERLOGGED, Boolean.valueOf(false)), voxelshape9);
                            //builder.put(blockstate.setValue(WATERLOGGED, Boolean.valueOf(true)), voxelshape9);
                        }
                    }
                }
            }
        }

        //return builder.build();
    }

    //private VoxelShape makeCollisionShape(float p_235624_1_, float p_235624_2_, float p_235624_3_, float p_235624_4_, float p_235624_5_, float p_235624_6_) {}


    private static VoxelShape applyWallShape(VoxelShape shape1, WallHeight wallHeight, VoxelShape shape2, VoxelShape shape3) {
        if (wallHeight == WallHeight.TALL) {
            return VoxelShapes.or(shape1, shape3);
        } else {
            return wallHeight == WallHeight.LOW ? VoxelShapes.or(shape1, shape2) : shape1;
        }
    }



    /**
     * Logical Methods
     */

    private boolean connectsTo(BlockState state, boolean bool, Direction direction)
    {
        Block block = state.getBlock();
        boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        //boolean flag2 = block instanceof WarenaiFenceGateBlock && WarenaiFenceGateBlock.connectsToDirection(state, direction); Use later

        return state.is(BlockTags.WALLS) || !isExceptionForConnection(block) && bool || block instanceof PaneBlock || flag1;
    }


    private static boolean isConnected(BlockState state, Property<WallHeight> wallHeightProperty) {
        return state.getValue(wallHeightProperty) != WallHeight.NONE;
    }


    private boolean shouldRaisePost(BlockState state1, BlockState state2, VoxelShape voxelShape) {
        boolean wallBlockNeighbor = state2.getBlock() instanceof WallBlock && state2.getValue(UP);
        boolean warenaiWallBlockNeighbor = state2.getBlock() instanceof WarenaiBlockWall && state2.getValue(UP);

        if (wallBlockNeighbor || warenaiWallBlockNeighbor) {
            return true;
        } else {
            WallHeight wallheight = state1.getValue(NORTH_WALL);
            WallHeight wallheight1 = state1.getValue(SOUTH_WALL);
            WallHeight wallheight2 = state1.getValue(EAST_WALL);
            WallHeight wallheight3 = state1.getValue(WEST_WALL);

            boolean flag1 = wallheight1 == WallHeight.NONE;
            boolean flag2 = wallheight3 == WallHeight.NONE;
            boolean flag3 = wallheight2 == WallHeight.NONE;
            boolean flag4 = wallheight == WallHeight.NONE;

            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;

            if (flag5) {
                return true;
            } else {
                boolean flag6 = wallheight == WallHeight.TALL && wallheight1 == WallHeight.TALL || wallheight2 == WallHeight.TALL && wallheight3 == WallHeight.TALL;
                if (flag6) {
                    return false;
                } else {
                    return state2.getBlock().is(BlockTags.WALL_POST_OVERRIDE) || isCovered(voxelShape, POST_SHAPE);
                }
            }
        }
    }


    private static boolean isCovered(VoxelShape voxelShape1, VoxelShape voxelShape2) {
        return !VoxelShapes.joinIsNotEmpty(voxelShape2, voxelShape1, IBooleanFunction.ONLY_FIRST);
    }



    /**
     * Updating Methods
     */

    private BlockState updateSides(BlockState state, boolean bool1, boolean bool2, boolean bool3, boolean bool4, VoxelShape voxelShape) {
        return state
                .setValue(NORTH_WALL, this.makeWallState(bool1, voxelShape, NORTH_SHAPE))
                .setValue(EAST_WALL, this.makeWallState(bool2, voxelShape, EAST_SHAPE))
                .setValue(SOUTH_WALL, this.makeWallState(bool3, voxelShape, SOUTH_SHAPE))
                .setValue(WEST_WALL, this.makeWallState(bool4, voxelShape, WEST_SHAPE));
    }

    private BlockState updateShape(IWorldReader reader, BlockState state1, BlockPos blockPos, BlockState state2,
                                   boolean bool1, boolean bool2, boolean bool3, boolean bool4)
    {
        VoxelShape voxelshape = state2.getCollisionShape(reader, blockPos).getFaceShape(Direction.DOWN);
        BlockState blockstate = this.updateSides(state1, bool1, bool2, bool3, bool4, voxelshape);

        return blockstate.setValue(UP, Boolean.valueOf(this.shouldRaisePost(blockstate, state2, voxelshape)));
    }

    private BlockState topUpdate(IWorldReader reader, BlockState state1, BlockPos blockPos, BlockState state2) {
        boolean flag = isConnected(state1, NORTH_WALL);
        boolean flag1 = isConnected(state1, EAST_WALL);
        boolean flag2 = isConnected(state1, SOUTH_WALL);
        boolean flag3 = isConnected(state1, WEST_WALL);

        return this.updateShape(reader, state1, blockPos, state2, flag, flag1, flag2, flag3);
    }

    private BlockState sideUpdate(IWorldReader reader, BlockPos pos1, BlockState state1, BlockPos pos2, BlockState state2, Direction direction) {
        Direction direction1 = direction.getOpposite();
        boolean flag = direction == Direction.NORTH ? this.connectsTo(state2, state2.isFaceSturdy(reader, pos2, direction1), direction1) :
                isConnected(state1, NORTH_WALL);
        boolean flag1 = direction == Direction.EAST ? this.connectsTo(state2, state2.isFaceSturdy(reader, pos2, direction1), direction1) :
                isConnected(state1, EAST_WALL);
        boolean flag2 = direction == Direction.SOUTH ? this.connectsTo(state2, state2.isFaceSturdy(reader, pos2, direction1), direction1) :
                isConnected(state1, SOUTH_WALL);
        boolean flag3 = direction == Direction.WEST ? this.connectsTo(state2, state2.isFaceSturdy(reader, pos2, direction1), direction1) :
                isConnected(state1, WEST_WALL);

        BlockPos blockpos = pos1.above();
        BlockState blockstate = reader.getBlockState(blockpos);
        return this.updateShape(reader, state1, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld iWorld, BlockPos pos1, BlockPos pos2) {
        if (state1.getValue(WATERLOGGED)) {
            iWorld.getLiquidTicks().scheduleTick(pos1, Fluids.WATER, Fluids.WATER.getTickDelay(iWorld));
        }

        if (direction == Direction.DOWN) {
            return super.updateShape(state1, direction, state2, iWorld, pos1, pos2);
        } else {
            return direction == Direction.UP ? this.topUpdate(iWorld, state1, pos2, state2) :
                    this.sideUpdate(iWorld, pos1, state1, pos2, state2, direction);
        }
    }
}
