package io.github.FireTamer81.init;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TileEntityTypesInit
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TestModMain.MOD_ID);
/**
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return TILE_ENTITY_TYPE.register(name, () -> {
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
**/


    public static final RegistryObject<TileEntityType<StrongBlockTileEntity>> STRONGBLOCK_TILE = TILE_ENTITY_TYPE.register(
            "warenai_block_black_strong_block_tile", () -> TileEntityType.Builder.of(StrongBlockTileEntity::new,
                WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get(),
                WarenaiBlocksInit.WARENAI_BLOCK_BLACK_STAIRS.get()
        ).build(null));
}
