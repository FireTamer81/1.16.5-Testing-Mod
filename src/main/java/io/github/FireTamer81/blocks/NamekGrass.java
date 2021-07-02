package io.github.FireTamer81.blocks;

import java.util.Random;

import io.github.FireTamer81.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class NamekGrass extends BushBlock
{
	protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 13, 16);



	public NamekGrass(AbstractBlock.Properties properties) 
	{
		super(properties);
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vector3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vector3d.x, vector3d.y, vector3d.z); //.withOffset(vector3d.x, vector3d.y, vector3d.z);
	}
	
	
	
	//Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
	public AbstractBlock.OffsetType getOffsetType() 
	{
		return AbstractBlock.OffsetType.XZ;
	}
	
	
	
	/**
	* What's below this comment is being used to determine whether the grass can be "sustained" where it is placed.
	* Basically, I don't want these plants to be able to be placed in any other dimension than namek.
	* But since I don't even have a biome for it yet the check will be for whether the block below is the Namek_Grass_Block.
	**/
	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) 
	{
		return state.is(Blocks.GRASS_BLOCK) || 
				state.is(Blocks.DIRT) ||
				state.is(Blocks.COARSE_DIRT) || 
				state.is(Blocks.PODZOL) || 
				state.is(Blocks.FARMLAND) || 
				state.is(BlockInit.NAMEK_GRASS_BLOCK) || 
				state.is(BlockInit.NAMEK_DIRT);
	}

	protected boolean isNamekGrassBelow(IBlockReader worldIn, BlockPos pos) 
	{
		BlockPos blockpos = pos.below();
		
		if(worldIn.getBlockState(blockpos) == BlockInit.NAMEK_GRASS_BLOCK.defaultBlockState()) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{	
		if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
		
		if (isNamekGrassBelow(worldIn, pos)) {
			return;
		} else if (!isNamekGrassBelow(worldIn, pos)) {
			worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}
}
