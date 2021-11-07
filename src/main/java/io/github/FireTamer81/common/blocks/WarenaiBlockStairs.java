package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WarenaiBlockStairs extends StairsBlock
{
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    public static final IntegerProperty CRACKED_LEVEL = CustomBlockstateProperties.LEVEL_OF_CRACKED;

    public WarenaiBlockStairs(BlockState p_i48321_1_, Properties p_i48321_2_) {
        super(p_i48321_1_, p_i48321_2_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.BOTTOM)
                .setValue(SHAPE, StairsShape.STRAIGHT)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                .setValue(CRACKED_LEVEL, Integer.valueOf(0)));

    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, HALF, SHAPE, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED).add(CRACKED_LEVEL);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesInit.STRONGBLOCK_TILE.get().create();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponent, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockReader, textComponent, tooltipFlag);

        CompoundNBT stackNBT = itemStack.getTagElement("BlockEntityTag");
        if (stackNBT != null) {
            if (stackNBT.contains("BlockHealth")) {
                int blockHealthValue = stackNBT.getInt("BlockHealth");
                StringTextComponent mainTooltip = new StringTextComponent("Block Health is: " + blockHealthValue);
                textComponent.add(mainTooltip);
            }
        }
    }
}
