package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.blockColorignStuff.ColorsEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
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
    /**
    public static final EnumProperty<ColorsEnum> BLOCK_COLOR = EnumProperty.create("block_color", ColorsEnum.class,
            ColorsEnum.BLACK, ColorsEnum.BROWN, ColorsEnum.BLUE, ColorsEnum.CYAN,
            ColorsEnum.GRAY, ColorsEnum.GREEN, ColorsEnum.LIGHTBLUE, ColorsEnum.LIGHTGRAY,
            ColorsEnum.LIME, ColorsEnum.MAGENTA, ColorsEnum.ORANGE, ColorsEnum.PURPLE,
            ColorsEnum.PINK, ColorsEnum.RED, ColorsEnum.WHITE, ColorsEnum.YELLOW);
    **/

    public static final EnumProperty<ColorsEnum> BLOCK_COLOR = EnumProperty.create("block_color", ColorsEnum.class, ColorsEnum.values());
    public static final BooleanProperty NEEDS_OVERLAY = BooleanProperty.create("need_overlay");




    public BakedModelFullBlock(Properties properties) {
        super(properties);
        this.stateDefinition.any()
                .setValue(BLOCK_COLOR, ColorsEnum.WHITE)
                .setValue(NEEDS_OVERLAY, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_COLOR, NEEDS_OVERLAY);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BakedModelFullBlock_Tile();
    }


    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        /**
        if (player.isCrouching()) {
            player.displayClientMessage(new StringTextComponent(state.getValue(BLOCK_COLOR).toString()), true);
        } else {
            world.setBlockAndUpdate(pos, state.cycle(BLOCK_COLOR));
        }
        **/
        if (player.isCrouching()) {
            player.displayClientMessage(new StringTextComponent(state.getValue(NEEDS_OVERLAY).toString()), true);
        } else {
            world.setBlockAndUpdate(pos, state.cycle(NEEDS_OVERLAY));
        }


        return ActionResultType.SUCCESS;
    }
}
