package io.github.FireTamer81.blocks;

import java.util.Random;

import io.github.FireTamer81.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

@SuppressWarnings("deprecation")
public class SpreadableNamekGrass extends Block implements IGrowable
{
	public SpreadableNamekGrass(AbstractBlock.Properties properties) 
	{
		super(properties);
	}

	
	private static boolean canStayNamekGrass(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockPos blockpos = pos.above();
		BlockState blockstate = reader.getBlockState(blockpos);
		
		if (reader.getBlockState(blockpos).is(BlockInit.TALL_NAMEK_GRASS) || reader.getBlockState(blockpos).is(BlockInit.SHORT_NAMEK_GRASS) || reader.getBlockState(blockpos).is(BlockInit.AJISA_BUSH) || reader.isEmptyBlock(blockpos)) 
		{
			return true;
		} else if (blockstate.getFluidState().getAmount() == 8) {
			return false;
		} else {
			int i = LightEngine.getLightBlockInto(reader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(reader, blockpos));
			return i < reader.getMaxLightLevel();
		}
	}
	
	private static boolean canPropagate(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockPos blockpos = pos.above();
		return canStayNamekGrass(state, reader, pos) && !reader.getFluidState(blockpos).is(FluidTags.WATER);
	}
	
	public void randomTick(BlockState state, ServerWorld server, BlockPos pos, Random random) 
	{
		if (!canStayNamekGrass(state, server, pos)) 
		{
			if (!server.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			server.setBlockAndUpdate(pos, BlockInit.NAMEK_DIRT.defaultBlockState());
		} else {
			if (server.getMaxLocalRawBrightness(pos.above()) >= 9) 
			{
				BlockState blockstate = this.defaultBlockState();
				
				for (int i = 0; i < 4; ++i) 
				{
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (server.getBlockState(blockpos).is(BlockInit.NAMEK_DIRT) && canPropagate(blockstate, server, blockpos)) 
					{
						server.setBlockAndUpdate(blockpos, blockstate);
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	//Determines if any given block can grow plants, in other words and for my pruposes, is the chosen block Namek Grass and the block above it air.
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return worldIn.getBlockState(pos.above()).isAir();
	}
	
	//Simply returns that instances of blocks that use this class can use bonemeal.
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
	
	//Uses the above booleans and a good bit of math to apply the growth effect (Plants growing on the block applied with bonemeal and it's neighbors).
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		BlockPos blockpos_above = pos.above();
		BlockState blockstate_namekgrassblock = BlockInit.NAMEK_GRASS_BLOCK.defaultBlockState();
		
		Task:
		for(int i = 0; i < 128; ++i) 
		{
			BlockPos blockpos1 = blockpos_above;
			
			for(int j = 0; j < i / 16; ++j) 
			{
	            blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
	            if (!worldIn.getBlockState(blockpos1.below()).is(this) || worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) //.hasOpaqueCollisionShape(worldIn, blockpos1)
	            {
	               continue Task;
	            }
	        }
			
			BlockState blockstate1 = worldIn.getBlockState(blockpos1);
			
			if (blockstate1.is(blockstate_namekgrassblock.getBlock()) && rand.nextInt(10) == 0) 
			{
				((IGrowable)blockstate_namekgrassblock.getBlock()).performBonemeal(worldIn, rand, blockpos1, blockstate1);; 
			}
			
			if (blockstate1.isAir()) 
			{
				BlockState blockstate2;
				
				/**
				//Basically, this is something that can be explored once I do biomes, which will probably be a while. Until then, this is useless.
				
				if (rand.nextInt(8) == 0) 
				{
					List<ConfiguredFeature<?, ?>> list = worldIn.getBiome(blockpos1).getGenerationSettings().getFlowerFeatures();
					if (list.isEmpty()) 
					{
						continue;
					}
					
					ConfiguredFeature<?, ?> configuredfeature = list.get(0);
					FlowersFeature flowersfeature = (FlowersFeature)configuredfeature.feature;
					blockstate1 = flowersfeature.getFlowerToPlace(rand, blockpos1, configuredfeature.getConfig());
				} else {
					blockstate1 = blockstate;
				}
				**/
				
				if (rand.nextInt(4) == 0) {
					blockstate2 = BlockInit.TALL_NAMEK_GRASS.defaultBlockState();
				} else {
					blockstate2 = BlockInit.SHORT_NAMEK_GRASS.defaultBlockState();
				}
				
				
				if (blockstate1.canSurvive(worldIn, blockpos1)) // .isValidPosition(worldIn, blockpos1)
				{
					worldIn.setBlockAndUpdate(blockpos1, blockstate2);
				}
				
			}
		}
	}
	
}
	

