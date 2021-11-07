package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class WarenaiBlockWall extends Block implements IWaterLoggable
{
    public static final BooleanProperty WALL_POST = BlockStateProperties.UP;
    public static final EnumProperty<WallHeight> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallHeight> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallHeight> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallHeight> WEST_WALL = BlockStateProperties.WEST_WALL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    public static final IntegerProperty CRACKED_LEVEL = CustomBlockstateProperties.LEVEL_OF_CRACKED;

    private static final VoxelShape POST_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape NORTH_SHAPE = Block.box(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);



    public WarenaiBlockWall(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WALL_POST, Boolean.valueOf(true)) //Determines if the Wall Post is in the block model
                .setValue(NORTH_WALL, WallHeight.NONE)
                .setValue(EAST_WALL, WallHeight.NONE)
                .setValue(SOUTH_WALL, WallHeight.NONE)
                .setValue(WEST_WALL, WallHeight.NONE)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                .setValue(CRACKED_LEVEL, Integer.valueOf(0)));
    }

    private boolean connectsTo(BlockState state, boolean isFaceSturdyBool, Direction connectionDirection)
    {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, connectionDirection);

        return state.is(BlockTags.WALLS) || !isExceptionForConnection(block) && isFaceSturdyBool || block instanceof PaneBlock || flag;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        IWorldReader reader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        BlockPos northBlockPos = blockPos.north();
        BlockPos eastBlockPos = blockPos.east();
        BlockPos southBlockPos = blockPos.south();
        BlockPos westBlockPos = blockPos.west();
        BlockPos aboveBlockPos = blockPos.above();

        BlockState northBlockState = reader.getBlockState(northBlockPos);
        BlockState eastBlockState = reader.getBlockState(eastBlockPos);
        BlockState southBlockState = reader.getBlockState(southBlockPos);
        BlockState westBlockState = reader.getBlockState(westBlockPos);
        BlockState aboveBlockState = reader.getBlockState(aboveBlockPos);

        boolean flag = this.connectsTo(northBlockState, northBlockState.isFaceSturdy(reader, northBlockPos, Direction.SOUTH), Direction.SOUTH);
        boolean flag1 = this.connectsTo(eastBlockState, eastBlockState.isFaceSturdy(reader, eastBlockPos, Direction.WEST), Direction.WEST);
        boolean flag2 = this.connectsTo(southBlockState, southBlockState.isFaceSturdy(reader, southBlockPos, Direction.NORTH), Direction.NORTH);
        boolean flag3 = this.connectsTo(westBlockState, westBlockState.isFaceSturdy(reader, westBlockPos, Direction.EAST), Direction.EAST);
        BlockState blockstate5 = this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));

        return this.updateShape(reader, blockstate5, aboveBlockPos, aboveBlockState, flag, flag1, flag2, flag3);
    }



    private BlockState updateShape(IWorldReader reader, BlockState isWaterLogged, BlockPos aboveBlockPos, BlockState aboveBlockState, boolean northConnectionBool, boolean eastConnectionBool, boolean southConnectionBool, boolean westConnectionBool)
    {
        VoxelShape voxelshape = aboveBlockState.getCollisionShape(reader, aboveBlockPos).getFaceShape(Direction.DOWN);
        BlockState blockState = this.updateSides(isWaterLogged, northConnectionBool, eastConnectionBool, southConnectionBool, westConnectionBool, voxelshape);
        return blockState.setValue(WALL_POST, Boolean.valueOf(this.shouldRaisePost(blockState, aboveBlockState, voxelshape)));
    }

    private BlockState updateSides(BlockState blockState, boolean northSideConnectionBool, boolean eastSideConnectionBool, boolean southSideConnectionBool, boolean westSideConnectionBool, VoxelShape voxelShape)
    {
        return blockState
                .setValue(NORTH_WALL, this.makeWallState(northSideConnectionBool, voxelShape, NORTH_SHAPE))
                .setValue(EAST_WALL, this.makeWallState(eastSideConnectionBool, voxelShape, EAST_SHAPE))
                .setValue(SOUTH_WALL, this.makeWallState(southSideConnectionBool, voxelShape, SOUTH_SHAPE))
                .setValue(WEST_WALL, this.makeWallState(westSideConnectionBool, voxelShape, WEST_SHAPE));
    }

    private static boolean isWallBlockCovered()
    {
        IWorldReader reader = Minecraft.getInstance().level;

        BlockPos thisBlockPos = new BlockPos(0, 0, 0);
        BlockPos aboveThisBlockPos = thisBlockPos.above();

        BlockState thisBlockState = reader.getBlockState(thisBlockPos);
        BlockState aboveThisBlockState = reader.getBlockState(aboveThisBlockPos);

        boolean isAboveBlockStateAFullBlock = aboveThisBlockState.isFaceSturdy(reader, aboveThisBlockPos, Direction.DOWN);
        boolean isAboveBlockStateWallBlock = aboveThisBlockState.getBlock() instanceof WallBlock || aboveThisBlockState.getBlock() instanceof WarenaiBlockWall;
        //boolean aboveBlockIsAir = aboveThisBlockState.getBlock() instanceof AirBlock;

        boolean isCovered = isAboveBlockStateAFullBlock || isAboveBlockStateWallBlock;
        //boolean aboveBlockStateIsNotAir = !aboveThisBlockState.isAir();

        if (isCovered) {
            return true;
        } else {
            return false;
        }
    }

    private WallHeight makeWallState(boolean sideConnectionBool, VoxelShape voxelShape, VoxelShape actualShape)
    {
        if (sideConnectionBool) {
            if (isWallBlockCovered() == Boolean.valueOf(Boolean.TRUE)) {
                return WallHeight.TALL;
            } else {
                return WallHeight.LOW;
            }
        } else {
            return WallHeight.NONE;
        }
    }


    private boolean shouldRaisePost(BlockState state1, BlockState state2, VoxelShape voxelShape)
    {
        boolean isWallBlockInstance = state2.getBlock() instanceof WallBlock && state2.getValue(WALL_POST);
        boolean isWarenaiWallBlockInstance = state2.getBlock() instanceof WarenaiBlockWall && state2.getValue(WALL_POST);

        if (isWallBlockInstance || isWarenaiWallBlockInstance) {
            return true;
        } else {
            WallHeight northWallHeightValue = state1.getValue(NORTH_WALL);
            WallHeight southWallHeightValue = state1.getValue(SOUTH_WALL);
            WallHeight eastWallHeightValue = state1.getValue(EAST_WALL);
            WallHeight westWallHeightValue = state1.getValue(WEST_WALL);

            boolean isSouthWallHeightNone = southWallHeightValue.equals(WallHeight.NONE);
            boolean isWestWallHeightNone = westWallHeightValue.equals(WallHeight.NONE);
            boolean isEastWallHeightNone = eastWallHeightValue.equals(WallHeight.NONE);
            boolean isNorthWallHeightNone = northWallHeightValue.equals(WallHeight.NONE);

            boolean iDontKnowBool = isNorthWallHeightNone && isSouthWallHeightNone && isWestWallHeightNone && isEastWallHeightNone || isNorthWallHeightNone != isSouthWallHeightNone || isWestWallHeightNone != isEastWallHeightNone;

            if (iDontKnowBool) {
                return true;
            } else {
                boolean flag6 = northWallHeightValue == WallHeight.TALL && southWallHeightValue == WallHeight.TALL || eastWallHeightValue == WallHeight.TALL && westWallHeightValue == WallHeight.TALL;

                if (flag6) {
                    return false;
                } else {
                    return state2.getBlock().is(BlockTags.WALL_POST_OVERRIDE) || isWallBlockCovered();
                }
            }
        }
    }



    public BlockState updateShape(BlockState blockState1, Direction direction, BlockState blockState2, IWorld world, BlockPos blockPos1, BlockPos blockPos2) //This is a method from AbstractBlock
    {
        if (blockState1.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(blockPos1, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction.equals(Direction.DOWN)) {
            return super.updateShape(blockState1, direction, blockState2, world, blockPos1, blockPos2);
        } else {
            return direction == Direction.UP ? this.topUpdate(world, blockState1, blockPos2, blockState2) : this.sideUpdate(world, blockPos1, blockState1, blockPos2, blockState2, direction);
        }
    }

    private BlockState topUpdate(IWorldReader reader, BlockState blockState1, BlockPos blockPos, BlockState blockState2)
    {
        boolean flag1 = isConnected(blockState1, NORTH_WALL);
        boolean flag2 = isConnected(blockState1, EAST_WALL);
        boolean flag3 = isConnected(blockState1, SOUTH_WALL);
        boolean flag4 = isConnected(blockState1, WEST_WALL);

        return this.updateShape(reader, blockState1, blockPos, blockState2, flag1, flag2, flag3, flag4);
    }

    private BlockState sideUpdate(IWorldReader reader, BlockPos blockPos1, BlockState blockState1, BlockPos blockPos2, BlockState blockState2, Direction direction)
    {
        //Direction directionOpposite = direction;
        boolean flag1 = direction == Direction.NORTH ? this.connectsTo(blockState2, blockState2.isFaceSturdy(reader, blockPos2, direction), direction) : isConnected(blockState1, NORTH_WALL);
        boolean flag2 = direction == Direction.EAST ? this.connectsTo(blockState2, blockState2.isFaceSturdy(reader, blockPos2, direction), direction) : isConnected(blockState1, EAST_WALL);;
        boolean flag3 = direction == Direction.SOUTH ? this.connectsTo(blockState2, blockState2.isFaceSturdy(reader, blockPos2, direction), direction) : isConnected(blockState1, SOUTH_WALL);;
        boolean flag4 = direction == Direction.WEST ? this.connectsTo(blockState2, blockState2.isFaceSturdy(reader, blockPos2, direction), direction) : isConnected(blockState1, WEST_WALL);;
        BlockPos aboveBlockPos = blockPos1.above();
        BlockState aboveBlockState = reader.getBlockState(aboveBlockPos);

        return this.updateShape(reader, blockState1, aboveBlockPos, aboveBlockState, flag1, flag2, flag3, flag4);
    }

    private static boolean isConnected(BlockState blockState, Property<WallHeight> wallHeightProperty) {
        return blockState.getValue(wallHeightProperty) != WallHeight.NONE;
    }




    public BlockState rotate(BlockState blockState, Rotation rotation)
    {
        switch(rotation) {
            case CLOCKWISE_180:
                return blockState
                        .setValue(NORTH_WALL, blockState.getValue(SOUTH_WALL))
                        .setValue(EAST_WALL, blockState.getValue(WEST_WALL))
                        .setValue(SOUTH_WALL, blockState.getValue(NORTH_WALL))
                        .setValue(WEST_WALL, blockState.getValue(EAST_WALL));
            case COUNTERCLOCKWISE_90:
                return blockState
                        .setValue(NORTH_WALL, blockState.getValue(EAST_WALL))
                        .setValue(EAST_WALL, blockState.getValue(SOUTH_WALL))
                        .setValue(SOUTH_WALL, blockState.getValue(WEST_WALL))
                        .setValue(WEST_WALL, blockState.getValue(NORTH_WALL));
            case CLOCKWISE_90:
                return blockState
                        .setValue(NORTH_WALL, blockState.getValue(WEST_WALL))
                        .setValue(EAST_WALL, blockState.getValue(NORTH_WALL))
                        .setValue(SOUTH_WALL, blockState.getValue(EAST_WALL))
                        .setValue(WEST_WALL, blockState.getValue(SOUTH_WALL));
            default:
                return blockState;
        }
    }

    public BlockState mirror(BlockState blockState, Mirror mirror)
    {
        switch(mirror) {
            case LEFT_RIGHT:
                return blockState.setValue(NORTH_WALL, blockState.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, blockState.getValue(NORTH_WALL));
            case FRONT_BACK:
                return blockState.setValue(EAST_WALL, blockState.getValue(WEST_WALL)).setValue(WEST_WALL, blockState.getValue(EAST_WALL));
            default:
                return super.mirror(blockState, mirror);
        }
    }














    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos blockPos, ISelectionContext selectionContext)
    {
        VoxelShape POST = Block.box(4, 0, 4, 12, 16, 12);

        VoxelShape SHORT_NORTH = Block.box(5, 0, 0, 11, 14, 4);
        VoxelShape SHORT_EAST = Block.box(12, 0, 5, 16, 14, 11);
        VoxelShape SHORT_SOUTH = Block.box(5, 0, 12, 11, 14, 16);
        VoxelShape SHORT_WEST = Block.box(0, 0, 5, 4, 14, 11);

        VoxelShape SHORT_STRAIGHT_EAST_TO_WEST = Block.box(0, 0, 5, 16, 14, 11);
        VoxelShape SHORT_STRAIGHT_NORTH_TO_SOUTH = Block.box(5, 0, 0, 11, 14, 16);


        VoxelShape TALL_NORTH = Block.box(5, 0, 0, 11, 16, 4);
        VoxelShape TALL_EAST = Block.box(12, 0, 5, 16, 16, 11);
        VoxelShape TALL_SOUTH = Block.box(5, 0, 12, 11, 16, 16);
        VoxelShape TALL_WEST = Block.box(0, 0, 5, 4, 16, 11);

        VoxelShape TALL_STRAIGHT_EAST_TO_WEST = Block.box(0, 0, 5, 16, 16, 11);
        VoxelShape TALL_STRAIGHT_NORTH_TO_SOUTH = Block.box(5, 0, 0, 11, 16, 16);


        //Just North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, false, false, false)) { return VoxelShapes.join(POST, SHORT_NORTH, IBooleanFunction.OR); }
        //Just East
        if (surroundingBlockConfiguration(reader, blockPos, false, true, false, false, false)) { return VoxelShapes.join(POST, SHORT_EAST, IBooleanFunction.OR); }
        //Just South
        if (surroundingBlockConfiguration(reader, blockPos, false, false, true, false, false)) { return VoxelShapes.join(POST, SHORT_SOUTH, IBooleanFunction.OR); }
        //Just West
        if (surroundingBlockConfiguration(reader, blockPos, false, false, false, true, false)) { return VoxelShapes.join(POST, SHORT_WEST, IBooleanFunction.OR); }

        //North + East
        if (surroundingBlockConfiguration(reader, blockPos, true, true, false, false, false)) { return Stream.of(POST, SHORT_NORTH, SHORT_EAST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //East + South
        if (surroundingBlockConfiguration(reader, blockPos, false, true, true, false, false)) { return Stream.of(POST, SHORT_EAST, SHORT_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //South + West
        if (surroundingBlockConfiguration(reader, blockPos, false, false, true, true, false)) { return Stream.of(POST, SHORT_SOUTH, SHORT_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //West + North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, false, true, false)) { return Stream.of(POST, SHORT_WEST, SHORT_NORTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }

        //North + South
        if (surroundingBlockConfiguration(reader, blockPos, true, false, true, false, false)) { return SHORT_STRAIGHT_NORTH_TO_SOUTH; }
        //East + West
        if (surroundingBlockConfiguration(reader, blockPos, false, true, false, true, false)) { return SHORT_STRAIGHT_EAST_TO_WEST; }

        //North + East + West
        if (surroundingBlockConfiguration(reader, blockPos, true, true, false, true, false)) { return Stream.of(POST, SHORT_NORTH, SHORT_EAST, SHORT_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //East + North + South
        if (surroundingBlockConfiguration(reader, blockPos, true, true, true, false, false)) { return Stream.of(POST, SHORT_EAST, SHORT_NORTH, SHORT_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //South + East + West
        if (surroundingBlockConfiguration(reader, blockPos, false, true, true, true, false)) { return Stream.of(POST, SHORT_SOUTH, SHORT_EAST, SHORT_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //West + South + North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, true, true, false)) { return Stream.of(POST, SHORT_WEST, SHORT_SOUTH, SHORT_NORTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }

        //North + East + South + West
        if (surroundingBlockConfiguration(reader, blockPos, true, true, true, true, false)) { return Stream.of(SHORT_STRAIGHT_EAST_TO_WEST, SHORT_STRAIGHT_NORTH_TO_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }



        //Just North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, false, false, true)) { return VoxelShapes.join(POST, TALL_NORTH, IBooleanFunction.OR); }
        //Just East
        if (surroundingBlockConfiguration(reader, blockPos, false, true, false, false, true)) { return VoxelShapes.join(POST, TALL_EAST, IBooleanFunction.OR); }
        //Just South
        if (surroundingBlockConfiguration(reader, blockPos, false, false, true, false, true)) { return VoxelShapes.join(POST, TALL_SOUTH, IBooleanFunction.OR); }
        //Just West
        if (surroundingBlockConfiguration(reader, blockPos, false, false, false, true, true)) { return VoxelShapes.join(POST, TALL_WEST, IBooleanFunction.OR); }

        //North + East
        if (surroundingBlockConfiguration(reader, blockPos, true, true, false, false, true)) { return Stream.of(POST, TALL_NORTH, TALL_EAST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //East + South
        if (surroundingBlockConfiguration(reader, blockPos, false, true, true, false, true)) { return Stream.of(POST, TALL_EAST, TALL_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //South + West
        if (surroundingBlockConfiguration(reader, blockPos, false, false, true, true, true)) { return Stream.of(POST, TALL_SOUTH, TALL_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //West + North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, false, true, true)) { return Stream.of(POST, TALL_WEST, TALL_NORTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }

        //North + South
        if (surroundingBlockConfiguration(reader, blockPos, true, false, true, false, true)) { return TALL_STRAIGHT_NORTH_TO_SOUTH ; }
        //East + West
        if (surroundingBlockConfiguration(reader, blockPos, false, true, false, true, true)) { return TALL_STRAIGHT_EAST_TO_WEST ; }

        //North + East + West
        if (surroundingBlockConfiguration(reader, blockPos, true, true, false, true, true)) { return Stream.of(POST, TALL_NORTH, TALL_EAST, TALL_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //East + North + South
        if (surroundingBlockConfiguration(reader, blockPos, true, true, true, false, true)) { return Stream.of(POST, TALL_EAST, TALL_NORTH, TALL_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //South + East + West
        if (surroundingBlockConfiguration(reader, blockPos, false, true, true, true, true)) { return Stream.of(POST, TALL_SOUTH, TALL_EAST, TALL_WEST).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }
        //West + South + North
        if (surroundingBlockConfiguration(reader, blockPos, true, false, true, true, true)) { return Stream.of(POST, TALL_WEST, TALL_SOUTH, TALL_NORTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }

        //North + East + South + West
        if (surroundingBlockConfiguration(reader, blockPos, true, true, true, true, true)) { return Stream.of(TALL_STRAIGHT_EAST_TO_WEST, TALL_STRAIGHT_NORTH_TO_SOUTH).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get(); }



        else {
            return POST;
            //return VoxelShapes.block();
        }
    }


    private boolean surroundingBlockConfiguration(IBlockReader reader, BlockPos blockPos, boolean northBlockBool, boolean eastBlockBool,
                                                  boolean southBlockBool, boolean westBlockBool, boolean aboveFullBlockOrWallBool)
    {
        BlockPos northPos = blockPos.north();
        BlockPos eastPos = blockPos.east();
        BlockPos southPos = blockPos.south();
        BlockPos westPos = blockPos.west();
        BlockPos abovePos = blockPos.above();

        BlockState northState = reader.getBlockState(northPos);
        BlockState eastState = reader.getBlockState(eastPos);
        BlockState southState = reader.getBlockState(southPos);
        BlockState westState = reader.getBlockState(westPos);
        BlockState aboveState = reader.getBlockState(abovePos);

        boolean fullBlockOrWallNorth = northState.isCollisionShapeFullBlock(reader, northPos) ||
                northState.getBlock() instanceof WallBlock ||
                northState.getBlock() instanceof WarenaiBlockWall;
        boolean fullBlockOrWallEast = eastState.isCollisionShapeFullBlock(reader, eastPos) ||
                eastState.getBlock() instanceof WallBlock ||
                eastState.getBlock() instanceof WarenaiBlockWall;
        boolean fullBlockOrWallSouth = southState.isCollisionShapeFullBlock(reader, southPos) ||
                southState.getBlock() instanceof WallBlock ||
                southState.getBlock() instanceof WarenaiBlockWall;
        boolean fullBlockOrWallWest = westState.isCollisionShapeFullBlock(reader, westPos) ||
                westState.getBlock() instanceof WallBlock ||
                westState.getBlock() instanceof WarenaiBlockWall;

        boolean aboveFullBlockOrWall = aboveState.isCollisionShapeFullBlock(reader, abovePos) || aboveState.getBlock() instanceof WallBlock || aboveState.getBlock() instanceof WarenaiBlockWall;


        if (fullBlockOrWallNorth==northBlockBool && fullBlockOrWallEast==eastBlockBool && fullBlockOrWallSouth==southBlockBool &&
                fullBlockOrWallWest==westBlockBool && aboveFullBlockOrWall==aboveFullBlockOrWallBool) {
            return true;
        } else {
            return false;
        }
    }













    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader reader, BlockPos blockPos) { return !blockState.getValue(WATERLOGGED); }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) { return false; }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WALL_POST, NORTH_WALL, EAST_WALL, SOUTH_WALL, WEST_WALL, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED).add(CRACKED_LEVEL); }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesInit.STRONGBLOCK_TILE.get().create();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponent, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockReader, textComponent, tooltipFlag);

        CompoundNBT stackNBT = itemStack.getTagElement("BlockEntityTag");
        if (stackNBT != null) {
            if (stackNBT.contains("BlockHealth")) {
                int blockHealthValue = stackNBT.getInt("BlockHealth");
                StringTextComponent mainTooltip = new StringTextComponent("Block Health is: " + blockHealthValue);
                textComponent.add(mainTooltip);
            }
        }
    }
}
