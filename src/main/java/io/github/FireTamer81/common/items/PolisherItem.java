package io.github.FireTamer81.common.items;

import io.github.FireTamer81._testing.CustomBlockstateProperties;
import io.github.FireTamer81._testing.WarenaiBlockSlab;
import io.github.FireTamer81.common.blocks.WarenaiBlockStairs;
import io.github.FireTamer81.common.blocks.WarenaiBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PolisherItem extends Item
{
    public PolisherItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = worldIn.getBlockState(pos);

        Block warenaiClassBlock = state.getBlock();


        if (warenaiClassBlock instanceof WarenaiBlock ||
                warenaiClassBlock instanceof WarenaiBlockStairs ||
                warenaiClassBlock instanceof WarenaiBlockSlab)
        {
            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 1) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(2));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(2)));

                return ActionResultType.SUCCESS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 2) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3)));

                return ActionResultType.SUCCESS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 3) {
                //state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(4));
                worldIn.setBlockAndUpdate(pos, state.setValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(4)));

                return ActionResultType.SUCCESS;
            }

            if (state.getValue(CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED) == 4) {
                return ActionResultType.PASS;
            }
        }

        return ActionResultType.SUCCESS;
    }

    /**
    if (!worldIn.isClientSide())
    {
        playerEntity.displayClientMessage(new StringTextComponent("Hey, it works... sort of"), false);
    }
     **/
}
