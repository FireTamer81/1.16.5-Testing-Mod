package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BakedModelFullBlock extends Block {

    public BakedModelFullBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return Registration.BAKED_MODEL_FULL_BLOCK_TILE.get().create();
    }
}
