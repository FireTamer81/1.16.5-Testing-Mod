package io.github.FireTamer81.common.blocks;

import io.github.FireTamer81.common.CustomBlockstateProperties;
import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import io.github.FireTamer81.init.ItemInit;
import io.github.FireTamer81.init.TileEntityTypesInit;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;

public class WarenaiBlock extends Block
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = CustomBlockstateProperties.CRACKED_DIRTY_CLEAN_POLISHED;
    public static final IntegerProperty CRACKED_LEVEL = CustomBlockstateProperties.LEVEL_OF_CRACKED;

    public WarenaiBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CRACKED_DIRTY_CLEAN_POLISHED, Integer.valueOf(3))
                .setValue(CRACKED_LEVEL, Integer.valueOf(0)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(CRACKED_DIRTY_CLEAN_POLISHED).add(CRACKED_LEVEL);
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
