package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WarenaiBlockSlab extends SlabBlock
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    public static final IntegerProperty CRACKED_LEVEL = CustomBlockstateProperties.LEVEL_OF_CRACKED;

    public WarenaiBlockSlab(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(TYPE, SlabType.BOTTOM)
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                .setValue(CRACKED_LEVEL, Integer.valueOf(0)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(TYPE, WATERLOGGED, CRACKED_DIRTY_CLEAN_POLISHED).add(CRACKED_LEVEL);
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
