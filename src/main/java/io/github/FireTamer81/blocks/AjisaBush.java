package io.github.FireTamer81.blocks;

import java.util.Random;

import io.github.FireTamer81.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


/**
* This block will need some general tweaking when I get to that stage of development for it's drops and growth time.
* I would like to randomly choose if the player gets a "good yeild" or not of Ajisa Flowers when right clicked.
* I also want to lengthen the growth time to a good bit longer somehow. Perhaps something using randomized intergers again.
* Another thing I am look at is making a dead variant for the bush when the player attempts growing it outside of namek.
**/




@SuppressWarnings("deprecation")
public class AjisaBush extends BushBlock implements IGrowable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape SMALL_SHAPE = VoxelShapes.join(Block.box(7, 0, 7, 9, 2, 9), Block.box(5, 2, 5, 11, 8, 11), IBooleanFunction.OR);
	private static final VoxelShape MEDIUM_SHAPE = VoxelShapes.join(Block.box(7, 1, 7, 9, 3, 9), Block.box(5, 5.5, 5, 11, 11.5, 11), IBooleanFunction.OR);
	private static final VoxelShape LARGE_SHAPE = VoxelShapes.join(Block.box(7, 1.5, 7, 9, 3.5, 9), Block.box(5, 7, 5, 11, 13, 11), IBooleanFunction.OR);
			
	public AjisaBush(AbstractBlock.Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		if (state.getValue(AGE) == 0) {
			return SMALL_SHAPE;
		} else if (state.getValue(AGE) == 1) {
			return MEDIUM_SHAPE;
		} else {
			return LARGE_SHAPE;
		}
	}
	
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
	
	
	
	/**
	* Overriding Stuff From Extended Classes
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
				state.is(BlockInit.NAMEK_DIRT) ||
				state.is(BlockInit.TILLED_NAMEK_DIRT);
	}
	
	
	
	
	/**
	* Random Tick Stuff
	**/
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < 3;
	}
	
	protected boolean isNamekGrassBelow(IBlockReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		
		if(worldIn.getBlockState(blockpos) == BlockInit.NAMEK_GRASS_BLOCK.defaultBlockState()) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean isTilledNamekDirtBelow(IBlockReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		
		if(worldIn.getBlockState(blockpos) == BlockInit.TILLED_NAMEK_DIRT.defaultBlockState()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
		
		if (isNamekGrassBelow(worldIn, pos) == true || isTilledNamekDirtBelow(worldIn, pos) == true) 
		{
			int i = state.getValue(AGE);
			if (i < 3 && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(5) == 0)) 
			{ //.getLightSubtracted(pos.above()
				worldIn.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
			}
			
		//I want to make this turn into a dead variant that will just drop some sticks and maybe a namek log when broken. (Probably a different block from this one)
		} else if (isNamekGrassBelow(worldIn, pos) == false && isTilledNamekDirtBelow(worldIn, pos) == false) {
			worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}
	
	
	
	/**
	* If the Bush is fully matured and blooming, when rightclicked it will drop flowers, play a sound, and reverse the bushes age by 1.
	**/
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) 
	{
		int i = state.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) 
		{
			return ActionResultType.PASS;
		} else if (i == 3) {
			
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(2)));
			return ActionResultType.sidedSuccess(worldIn.isClientSide);
		} else {
			return super.use(state, worldIn, pos, player, handIn, hit);
		}
	}
	
	
	
	
	
	/**
	 * Whether this IGrowable can grow
	 **/
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return state.getValue(AGE) < 2;
	}

	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return true;
	}

	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		int i = state.getValue(AGE);
		worldIn.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
	}
}
