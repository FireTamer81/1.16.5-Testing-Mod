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

        //Block warenaiClassBlock = blockState.getBlock();
        boolean warenaiClassBlock = blockState.getBlock() instanceof WarenaiBlock;

        if (blockState.hasTileEntity() && warenaiClassBlock) {
            StrongBlockTileEntity strongBlockTile = new StrongBlockTileEntity();

            strongBlockTile.damageStrongBlock(50);
            //strongBlockTile.showBlockHealth();
            return ActionResultType.SUCCESS;

        } else {
            return ActionResultType.PASS;
        }
    }


}
