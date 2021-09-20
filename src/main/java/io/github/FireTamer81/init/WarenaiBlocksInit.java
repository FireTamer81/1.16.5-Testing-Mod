package io.github.FireTamer81.init;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81._testing.WarenaiBlockWall;
import io.github.FireTamer81.common.blocks.WarenaiBlockFence;
import io.github.FireTamer81.common.blocks.WarenaiBlockSlab;
import io.github.FireTamer81.common.blocks.WarenaiBlockStairs;
import io.github.FireTamer81.common.blocks.WarenaiBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class WarenaiBlocksInit
{
    /**
     * Register Stuff (Such as making BlockItems an easier way than an event class.)
     **/
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestModMain.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return WarenaiBlocksInit.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ItemInit.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }


    /**
     * Actual Registry Objects
     **/

    /**
     * Warenai Blocks
     **/

    //Full Blocks
    public static final RegistryObject<Block> WARENAI_BLOCK_BLACK = register("warenai_block_black", () ->
            new WarenaiBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(15f, 50f).harvestTool(ToolType.PICKAXE).harvestLevel(3)));



    //Stairs
    public static final RegistryObject<WarenaiBlockStairs> WARENAI_BLOCK_BLACK_STAIRS = register("warenai_block_black_stairs", () ->
            new WarenaiBlockStairs(WARENAI_BLOCK_BLACK.get().defaultBlockState(), AbstractBlock.Properties.copy(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get())));



    //Slabs
    public static final RegistryObject<SlabBlock> WARENAI_BLOCK_BLACK_SLAB = register("warenai_block_black_slab", () ->
            new WarenaiBlockSlab(AbstractBlock.Properties.copy(WARENAI_BLOCK_BLACK.get())));



    //Fences
    public static final RegistryObject<FenceBlock> WARENAI_BLOCK_BLACK_FENCE = register("warenai_block_black_fence", () ->
            new WarenaiBlockFence(AbstractBlock.Properties.copy(WARENAI_BLOCK_BLACK.get())));


    //Walls
    public static final RegistryObject<WarenaiBlockWall> WARENAI_BLOCK_BLACK_WALL = register("warenai_block_black_wall", () ->
            new WarenaiBlockWall(AbstractBlock.Properties.copy(WARENAI_BLOCK_BLACK.get())));


}
