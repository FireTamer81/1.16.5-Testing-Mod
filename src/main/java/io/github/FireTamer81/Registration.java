package io.github.FireTamer81;

import io.github.FireTamer81.fullBlock.BakedModelFullBlock;
import io.github.FireTamer81.fullBlock.BakedModelFullBlock_Tile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestModMain.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestModMain.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestModMain.MOD_ID);
    private static final Logger LOGGER = LogManager.getLogger();


    public static void init() {
        LOGGER.info("Registering Blocks");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering Items");
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering Tiles");
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<BakedModelFullBlock> BAKED_MODEL_FULL_BLOCK_REGISTRY_OBJECT = BLOCKS.register("baked_model_full_block", () ->
            new BakedModelFullBlock(1, AbstractBlock.Properties.of(Material.STONE)
                    .sound(SoundType.WOOD)
                    .strength(-1.0f)
                    .harvestTool(ToolType.AXE)
                    .harvestLevel(0)
                    .dynamicShape()
                    .noOcclusion()));

    public static final RegistryObject<Item> BAKED_MODEL_FULL_BLOCK_ITEM = ITEMS.register("baked_model_full_block_item", () ->
            new BlockItem(BAKED_MODEL_FULL_BLOCK_REGISTRY_OBJECT.get(), new Item.Properties().tab(TestModMain.TestModItemGroup.TEST_GROUP)));

    public static final RegistryObject<TileEntityType<BakedModelFullBlock_Tile>> BAKED_MODEL_FULL_BLOCK_TILE = TILES.register("baked_model_full_block_tile", () ->
            TileEntityType.Builder.of(BakedModelFullBlock_Tile::new, BAKED_MODEL_FULL_BLOCK_REGISTRY_OBJECT.get()).build(null));
}
