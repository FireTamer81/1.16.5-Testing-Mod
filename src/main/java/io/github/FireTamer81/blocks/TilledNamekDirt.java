package io.github.FireTamer81.blocks;

import java.util.Random;

import io.github.FireTamer81.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("deprecation")
public class TilledNamekDirt extends Block //extends FarmlandBlock
{
	/**
	* General Things
	**/
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	
	public TilledNamekDirt(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	
	
	/**
	* Special Functions
	**/
	
	public void tick(BlockState state, ServerWorld server, BlockPos pos, Random random) 
	{
		if (!state.canSurvive(server, pos)) 
		{
			turnToNamekDirt(state, server, pos);
		}
	}
	
	public void randomTick(BlockState state, ServerWorld server, BlockPos pos, Random random) 
	{
		if (!isUnderAjisaBush(server, pos)) 
		{
			turnToNamekDirt(state, server, pos);
		}
	}
	
	public void fallOn(World world, BlockPos pos, Entity entity, float fallDist) 
	{
		if (!world.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(world, pos, BlockInit.NAMEK_DIRT.defaultBlockState(), fallDist, entity)) 
		{
			turnToNamekDirt(world.getBlockState(pos), world, pos);
		}
	}
	
	
	
	/**
	* Compaction Methods (Name I came up for the things I make that make the code easier to read)
	**/
	
	private boolean isUnderAjisaBush(IBlockReader reader, BlockPos pos) 
	{
		BlockState plant = reader.getBlockState(pos.above());
		BlockState state = reader.getBlockState(pos);
		
		return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(reader, pos, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
	}
	
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockState blockstate = reader.getBlockState(pos.above());
		return !blockstate.getMaterial().isSolid() || 
				blockstate.getBlock() instanceof FenceGateBlock || 
				blockstate.getBlock() instanceof MovingPistonBlock || 
				blockstate.getBlock() instanceof AjisaBush;
	}
	
	public static void turnToNamekDirt(BlockState state, World world, BlockPos pos) 
	{
		world.setBlockAndUpdate(pos, pushEntitiesUp(state, BlockInit.NAMEK_DIRT.defaultBlockState(), world, pos));
	}
	
	
	
	/**
	* Things I don't Quite Understandw
	**/
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) 
	{
		if (direction == Direction.UP && !state1.canSurvive(world, pos1)) 
		{
			world.getBlockTicks().scheduleTick(pos1, this, 1);
		}
		
		return super.updateShape(state1, direction, state2, world, pos1, pos2);
	}
	
	public boolean useShapeForLightOcclusion(BlockState state) 
	{
		return true;
	}
	
	//These two might not be needed
	//public BlockState getStateForPlacement() {}
	
	//public boolean isPathfindable() {}
	
	
	
}
