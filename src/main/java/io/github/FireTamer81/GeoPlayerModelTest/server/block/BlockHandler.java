package io.github.FireTamer81.GeoPlayerModelTest.server.block;

import io.github.FireTamer81.TestModMain;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class BlockHandler {
    private BlockHandler() {}

    public static final DeferredRegister<Block> REG = DeferredRegister.create(ForgeRegistries.BLOCKS, TestModMain.MODID);
    public static final RegistryObject<Block> PAINTED_ACACIA = REG.register("painted_acacia", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> PAINTED_ACACIA_SLAB = REG.register("painted_acacia_slab", () -> new SlabBlock(Block.Properties.from(PAINTED_ACACIA.get())));
    public static final RegistryObject<Block> THATCH = REG.register("thatch_block", () -> new HayBlock(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT)));
    //public static final RegistryObject<BlockGrottol> GROTTOL = REG.register("grottol", () -> new BlockGrottol(Block.Properties.create(Material.ROCK).noDrops()));

    public static void init() {
        FireBlock fireblock = (FireBlock)Blocks.FIRE;
        fireblock.setFireInfo(THATCH.get(), 60, 20);
        fireblock.setFireInfo(PAINTED_ACACIA.get(), 5, 20);
        fireblock.setFireInfo(PAINTED_ACACIA_SLAB.get(), 5, 20);
    }
}