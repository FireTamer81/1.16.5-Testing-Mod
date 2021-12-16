package io.github.FireTamer81;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestModMain.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestModMain.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestModMain.MOD_ID);



    public static final RegistryObject<Block> COLORABLE_FULL_BLOCK = BLOCKS.register("colorable_full_block",
            () -> new ColorableFullBlock(AbstractBlock.Properties.of(Material.STONE)));

    public static final RegistryObject<TileEntityType<ColorableFullBlockTile>> COLORABLE_FULL_BLOCK_TILE = TILES.register("colorable_full_block_tile",
            () -> TileEntityType.Builder.of(ColorableFullBlockTile::new, COLORABLE_FULL_BLOCK.get()).build(null));

    public static final RegistryObject<Item> COLORABLE_FULL_BLOCK_ITEM = ITEMS.register("colorable_full_block_item",
            () -> new BlockItem(COLORABLE_FULL_BLOCK.get(), new Item.Properties().tab(TestModMain.TestModItemGroup.TEST_GROUP)));



    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BlockModels.initModels();
    }
}
