package io.github.FireTamer81.dataGenStuff.dataGenProviders;

import io.github.FireTamer81.init.WarenaiBlocksInit;
import io.github.FireTamer81.TestModMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBEBlockStates_BlockModels_Provider extends BlockStateProvider //implements IDataProvider
{
    public DBEBlockStates_BlockModels_Provider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, TestModMain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        //Gets the actual instance of the block object, and then sets up the texture pattern where it gets the registry name to look for the texture
        //simpleBlock(BlockInit.TEST_BLOCK.get(), cubeAll(BlockInit.TEST_BLOCK.get()));

        /**
         * Warenai Blocks
         **/
        //Full Blocks
        //simpleBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get(), cubeAll(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get()));
        //getVariantBuilder(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get()).forAllStates( blockState ->
        //        ConfiguredModel.builder().modelFile());

        //Stairs
        //stairsBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_STAIRS.get(), modLoc("block/warenai_block_black"));

        //Slabs
        //slabBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_SLAB.get(), modLoc("block/warenai_block_black"), modLoc("block/warenai_block_black"));

        //Fences
        //fenceBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_FENCE.get(), modLoc("block/warenai_block_black"));

        //Walls
        wallBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_WALL.get(), modLoc("block/warenai_block_black"));

    }
}
