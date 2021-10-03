package io.github.FireTamer81.common.items;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.common.blocks.WarenaiBlockWall;
import io.github.FireTamer81.common.blocks.WarenaiBlockFence;
import io.github.FireTamer81.common.blocks.WarenaiBlockSlab;
import io.github.FireTamer81.common.blocks.WarenaiBlockStairs;
import io.github.FireTamer81.common.blocks.WarenaiBlock;
import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class DamageItem extends Item
{
    public DamageItem(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = worldIn.getBlockState(blockPos);

        Block warenaiClassBlock = blockState.getBlock();

        if (blockState.hasTileEntity() && warenaiClassBlock instanceof WarenaiBlock) {
            StrongBlockTileEntity strongBlockTile = new StrongBlockTileEntity();

            strongBlockTile.damageStrongBlock(50);
            //strongBlockTile.showBlockHealth();
            return ActionResultType.SUCCESS;

        } else {
            return ActionResultType.PASS;
        }
    }

/**
    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
        PlayerEntity playerEntity = context.getPlayer();

        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = worldIn.getBlockState(pos);

        Block warenaiClassBlock = state.getBlock();


        if (warenaiClassBlock instanceof WarenaiBlock ||
                warenaiClassBlock instanceof WarenaiBlockStairs ||
                warenaiClassBlock instanceof WarenaiBlockSlab ||
                warenaiClassBlock instanceof WarenaiBlockFence ||
                warenaiClassBlock instanceof WarenaiBlockWall)
        {
            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 1) {
                return ActionResultType.PASS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 2) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(1));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(1)));
                return ActionResultType.SUCCESS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 3) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(2));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(2)));

                return ActionResultType.SUCCESS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 4) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.SUCCESS;
    }
**/

}
