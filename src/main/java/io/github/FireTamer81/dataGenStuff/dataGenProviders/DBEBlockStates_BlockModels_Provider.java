package io.github.FireTamer81.dataGenStuff.dataGenProviders;

import io.github.FireTamer81.common.blocks.WarenaiBlock;
import io.github.FireTamer81.common.blocks.properties.WarenaiBlockCondition;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBEBlockStates_BlockModels_Provider extends BlockStateProvider implements IDataProvider
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
        //warenaiFullBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get());

        //Stairs
        //stairsBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_STAIRS.get(), modLoc("block/warenai_block_black"));

        //Slabs
        //slabBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_SLAB.get(), modLoc("block/warenai_block_black"), modLoc("block/warenai_block_black"));

        //Fences
        //fenceBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_FENCE.get(), modLoc("block/warenai_block_black"));

        //Walls
        //wallBlock(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_WALL.get(), modLoc("block/warenai_block_black"));
    }

    public String name(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation crackedTexture(int levelOfCracked) {
        return new ResourceLocation(TestModMain.MOD_ID, "block/break_texture_" + levelOfCracked);
    }

    public ResourceLocation scuffedTexture() {
        return new ResourceLocation(TestModMain.MOD_ID, "block/scuffed_overlay_texture");
    }

    public ResourceLocation warenaiBlockTexture(Block block) {
        return new ResourceLocation(TestModMain.MOD_ID, "block/" + name(block));
    }

    public ResourceLocation polishedWarenaiBlockTexture(Block block) {
        return new ResourceLocation(TestModMain.MOD_ID, "block/polished_" + name(block));
    }

    private ModelFile existingMcModel(final String name) {
        return models().getExistingFile(new ResourceLocation("minecraft", name));
    }




    /*********************************************************************************************************************************************************
     *
    **********************************************************************************************************************************************************/


    public ModelFile scuffedWarenaiBlockModel(Block block) {
        return models().getBuilder("scuffed_" + name(block))
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .texture("overlay", scuffedTexture())
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#overlay").cullface(direction)).end();
    }

    public ModelFile crackedWarenaiBlockModel(Block block, int crackedLevel) {
        return models().getBuilder("cracked" + crackedLevel + "_" + name(block))
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .texture("overlay1", scuffedTexture())
                .texture("overlay2", crackedTexture(crackedLevel))
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#overlay1").cullface(direction)).end()
                .element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#overlay2").cullface(direction)).end();
    }
/**
    public void warenaiFullBlock(Block block) {
        EnumProperty<WarenaiBlockCondition> blockCondition = WarenaiBlock.BLOCK_CONDITION;

        getVariantBuilder(block)
                .partialState().with(blockCondition, WarenaiBlockCondition.CRACKED4).modelForState().modelFile(crackedWarenaiBlockModel(block, 4)).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.CRACKED3).modelForState().modelFile(crackedWarenaiBlockModel(block, 3)).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.CRACKED2).modelForState().modelFile(crackedWarenaiBlockModel(block, 2)).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.CRACKED1).modelForState().modelFile(crackedWarenaiBlockModel(block, 1)).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.SCUFFED).modelForState().modelFile(scuffedWarenaiBlockModel(block)).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.NORMAL).modelForState().modelFile(models().cubeAll(name(block), warenaiBlockTexture(block))).addModel()
                .partialState().with(blockCondition, WarenaiBlockCondition.POLISHED).modelForState().modelFile(models().cubeAll("polished_" + name(block), polishedWarenaiBlockTexture(block))).addModel();
    }
**/



    /*********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/

/**
    public ModelFile polishedWarenaiStairModel(Block block) {
        final ModelFile scuffedModel = models().getBuilder("polished_" + name(block) + "_stairs")
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .element().from(0, 0, 0).to(8, 8, 16).faces((direction, faceBuilder) -> faceBuilder).end();
        return scuffedModel;
    }

    public ModelFile normalWarenaiStairModel(Block block) {
        final ModelFile scuffedModel = models().getBuilder(name(block) + "_stairs")
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .element().from(0, 0, 0).to(8, 8, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end();
        return scuffedModel;
    }

    public ModelFile scuffedWarenaiStairModel(Block block) {
        final ModelFile scuffedModel = models().getBuilder("scuffed_" + name(block) + "_stairs")
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .texture("overlay", scuffedTexture())
                .element().from(0, 0, 0).to(8, 8, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end();
        return scuffedModel;
    }

    public ModelFile crackedWarenaiStairModel(Block block, int crackedLevel) {
        final ModelFile crackedModel = models().getBuilder("cracked" + crackedLevel + "_" + name(block) + "_stairs")
                .parent(existingMcModel("block"))
                .texture("particle", warenaiBlockTexture(block))
                .texture("underlay", warenaiBlockTexture(block))
                .texture("overlay1", scuffedTexture())
                .texture("overlay2", crackedTexture(crackedLevel))
                .element().from(0, 0, 0).to(8, 8, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end()
                .element().from(0, 0, 0).to(8, 8, 16).allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16).texture("#underlay").cullface(direction)).end();

        return crackedModel;
    }

    public void warenaiStairBlock(Block block) {
        IntegerProperty CDCP = WarenaiBlockStairs.CRACKED_DIRTY_CLEAN_POLISHED;
        IntegerProperty CL = WarenaiBlockStairs.CRACKED_LEVEL;
        DirectionProperty F = WarenaiBlockStairs.FACING;
        EnumProperty<Half> H = WarenaiBlockStairs.HALF;
        EnumProperty<StairsShape> S = WarenaiBlockStairs.SHAPE;
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile model = ;
        });
    }
**/



    /*********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/


//Yea




    /*********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/


//Oh Yea




    /*********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/


    //Even more yea
}
