package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.DBB_BlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BakedModelFullBlock extends Block {
    public static final IntegerProperty TEXTURE_INDEX = DBB_BlockStateProperties.TEXTURE_INTEGER;

    public BakedModelFullBlock(int textureIndex, Properties properties) {
        super(properties);
        this.stateDefinition.any().setValue(TEXTURE_INDEX, textureIndex);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(TEXTURE_INDEX);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BakedModelFullBlock_Tile();
    }


    //Stuff for making some quads invisible based on whether a side is covered by a full block and such
    //Currently only works a little bit
    //Figure it out later
    public void determineVisibleSides(World world, BlockPos pos, BakedModelFullBlock_Tile tileEntity) {
        if (world.getBlockState(pos.below()).isCollisionShapeFullBlock(world, pos.below())) {
            tileEntity.setVisibleSides(Direction.DOWN, false);
        }
        if (world.getBlockState(pos.above()).isCollisionShapeFullBlock(world, pos.above())) {
            tileEntity.setVisibleSides(Direction.UP, false);
        }
        if (world.getBlockState(pos.north()).isCollisionShapeFullBlock(world, pos.north())) {
            tileEntity.setVisibleSides(Direction.NORTH, false);
        }
        if (world.getBlockState(pos.west()).isCollisionShapeFullBlock(world, pos.west())) {
            tileEntity.setVisibleSides(Direction.WEST, false);
        }
        if (world.getBlockState(pos.south()).isCollisionShapeFullBlock(world, pos.south())) {
            tileEntity.setVisibleSides(Direction.SOUTH, false);
        }
        if (world.getBlockState(pos.east()).isCollisionShapeFullBlock(world, pos.east())) {
            tileEntity.setVisibleSides(Direction.EAST, false);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = new BlockPos(0, 0, 0);
        World world = Minecraft.getInstance().level;

        TileEntity te = world.getBlockEntity(pos);
        BakedModelFullBlock_Tile bakedModelFullBlock_tile;

        if (te != null && te instanceof BakedModelFullBlock_Tile) {
            bakedModelFullBlock_tile = (BakedModelFullBlock_Tile) te;
            determineVisibleSides(world, pos, bakedModelFullBlock_tile);
        }

        return super.getStateForPlacement(context);
    }
}
