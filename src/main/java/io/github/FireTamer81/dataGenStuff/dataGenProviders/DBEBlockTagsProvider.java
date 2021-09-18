package io.github.FireTamer81.dataGenStuff.dataGenProviders;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBEBlockTagsProvider extends BlockTagsProvider
{
    public DBEBlockTagsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, TestModMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(BlockTags.FENCES)
                .add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_FENCE.get());

        this.tag(BlockTags.WALLS)
                .add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_WALL.get());


        //I Think this is for making custom tags or something similar
        //But I deleted the TagsInit class since I don't plan on doing any of that stuff.
        //getOrCreateRawBuilder(TagsInit.Blocks.TEST_BLOCK);
        //getOrCreateRawBuilder(TagsInit.Items.TEST_BLOCK);

        //For adding custom tags and anything attached to them to another already existing tag.
        //(i.e. Anything in the "oak_logs" tag would also belong to the general "logs" tag)
        //getOrCreateRawBuilder(Tags.Blocks.ORES).addTag(TagsInit.Blocks.TEST_BLOCK_TAG);

        //For adding a block to an existing vanilla tag (often used for special functionality like leaves recognizing logs and stairs recognizing each other)
        //getOrCreateRawBuilder(BlockTags.STAIRS)
        //        .add(BlockInit.TEST_BLOCK);

    }



    protected Builder<Block> getBuilder(ITag.INamedTag<Block> tag) {
        return tag(tag);
    }


}
