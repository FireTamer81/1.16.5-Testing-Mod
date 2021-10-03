package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import io.github.FireTamer81.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class WarenaiBlock extends Block
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    //public static final IntegerProperty BLOCK_HEALTH_900 = CustomBlockstateProperties.BLOCK_HEALTH_900;

    public WarenaiBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                /**.setValue(BLOCK_HEALTH_900, Integer.valueOf(875))**/);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(CRACKED_DIRTY_CLEAN_POLISHED/**, BLOCK_HEALTH_900**/);
    }

    public void onPlace(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
        StrongBlockTileEntity.setInitialBlockHealth();
    }

    /**
     * Block Health Methods
     */
    /**
    public int getCurrentHealth() {
        int currentHealth = this.defaultBlockState().getValue(BLOCK_HEALTH_900);
        return currentHealth;
    }

    public BlockState repairBlock(int repairHealthAmount) {
        final int maximumHealthValue = 875;
        int currentMaximumRepairValue = maximumHealthValue - getCurrentHealth();

        if (repairHealthAmount > currentMaximumRepairValue) {
            return this.defaultBlockState().setValue(BLOCK_HEALTH_900, Integer.valueOf(875));
        } else {
            return this.defaultBlockState().setValue(BLOCK_HEALTH_900, getCurrentHealth() + repairHealthAmount);
        }
    }

    public BlockState damageBlock(int damageHealthAmount) {
        final int minimumHealthValue = 1;
        int currentMaximumDamageValue = getCurrentHealth() - 1;

        World worldIn = Minecraft.getInstance().level;
        if (worldIn.isClientSide) {
            System.out.println("Alright, the damageBlock method is called");
        }

        if (damageHealthAmount > currentMaximumDamageValue) {
            return this.defaultBlockState().setValue(BLOCK_HEALTH_900, Integer.valueOf(1));
        } else {
            return this.defaultBlockState().setValue(BLOCK_HEALTH_900, getCurrentHealth() - damageHealthAmount);
        }
    }

    public BlockState updateModel() {
        BlockState blockstateSetting = this.defaultBlockState().setValue(CRACKED_DIRTY_CLEAN_POLISHED, 3);

        if (getCurrentHealth() <= 150) {
            blockstateSetting = this.defaultBlockState().setValue(CRACKED_DIRTY_CLEAN_POLISHED, 1);
        }
        else if (getCurrentHealth() <= 450 && getCurrentHealth() > 150) {
            blockstateSetting = this.defaultBlockState().setValue(CRACKED_DIRTY_CLEAN_POLISHED, 2);
        }
        else if (getCurrentHealth() > 875) {
            blockstateSetting = this.defaultBlockState().setValue(CRACKED_DIRTY_CLEAN_POLISHED, 4);
        }

        return blockstateSetting;
    }

    public BlockState updateHealthAndModel(int damageAmount, int repairAmount) {
        //if (damageAmount != 0) { damageBlock(damageAmount); }
        //if (repairAmount != 0) { repairBlock(repairAmount); }
        damageBlock(damageAmount);
        repairBlock(repairAmount);

        World worldIn = Minecraft.getInstance().level;
        if (worldIn.isClientSide) { System.out.println("Alright, the updateHealthAndModel method is called"); }

        return updateModel();
    }


    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        //if (worldIn.isClientSide) { System.out.println("Hey, the use method works, but then that means something else doesn't"); }

        if (player.getItemInHand(handIn).getItem() == ItemInit.DAMAGE_ITEM.get()) {
            updateHealthAndModel(100, 0);

            //if (worldIn.isClientSide) { System.out.println("Alright, the item in hand check works"); }

            return ActionResultType.SUCCESS;
        }

        else if (player.getItemInHand(handIn).equals(ItemInit.REPAIR_ITEM)) {
            updateHealthAndModel(0, 100);
            return ActionResultType.SUCCESS;
        }

        else if (player.getItemInHand(handIn).equals(ItemInit.POLISHER_ITEM)) {
            if (getCurrentHealth() == 875) {
                updateHealthAndModel(0, 25);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
**/








    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StrongBlockTileEntity();
    }







}
