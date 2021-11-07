package io.github.FireTamer81.dataGenStuff.dataGenProviders;

import io.github.FireTamer81.TestModMain;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBEItemModelsProvider extends ItemModelProvider implements IDataProvider
{
    public DBEItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, TestModMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        //Creates an Item Model that points to the parent Block Model
        //withExistingParent("test_block", modLoc("block/test_block"));

        //Uses the "itemGenerated" variable and "builder" method to create a standard non-special item model
        //ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        //builder(itemGenerated, "test_ingot");


        /**
         * Warenai Blocks
         **/
        //Full Blocks
        withExistingParent("warenai_block_black", modLoc("block/warenai_block_black"));

        //Stairs
        withExistingParent("warenai_block_black_stairs", modLoc("block/warenai_block_black_stairs"));

        //Slabs
        withExistingParent("warenai_block_black_slab", modLoc("block/warenai_block_black_slab"));

        //Fences
        withExistingParent("warenai_block_black_fence", modLoc("block/warenai_block_black_fence_inventory"));

        //Walls
        withExistingParent("warenai_block_black_wall", modLoc("block/warenai_block_black_wall_inventory"));

    }




    private ItemModelBuilder builder(ModelFile itemGenerated, String name)
    {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
