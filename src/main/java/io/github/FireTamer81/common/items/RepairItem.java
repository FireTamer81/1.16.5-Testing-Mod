package io.github.FireTamer81.common.items;

import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class RepairItem extends Item
{
    public RepairItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        PlayerEntity playerEntity = context.getPlayer();


        if (!worldIn.isClientSide) {
            TileEntity te = worldIn.getBlockEntity(blockPos);

            if (te != null && te instanceof StrongBlockTileEntity) {
                StrongBlockTileEntity strongBlockTile = (StrongBlockTileEntity) te;

                if (playerEntity.isCrouching()) {
                    playerEntity.displayClientMessage(new StringTextComponent("Block Health: " + ((StrongBlockTileEntity) te).getHealth()), false);
                } else {
                    strongBlockTile.repairBlock(100);
                }

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else {
            return ActionResultType.FAIL;
        }
    }
}
