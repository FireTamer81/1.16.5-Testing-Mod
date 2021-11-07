package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WarenaiBlockFence extends FenceBlock
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    public static final IntegerProperty CRACKED_LEVEL = CustomBlockstateProperties.LEVEL_OF_CRACKED;

    public WarenaiBlockFence(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, Boolean.valueOf(false))
                .setValue(EAST, Boolean.valueOf(false))
                .setValue(SOUTH, Boolean.valueOf(false))
                .setValue(WEST, Boolean.valueOf(false))
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                .setValue(CRACKED_LEVEL, Integer.valueOf(0)));

        //super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, p_i48399_1_);
        //this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
        //this.occlusionByIndex = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED).add(CRACKED_LEVEL);
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
