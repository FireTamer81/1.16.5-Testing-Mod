package io.github.FireTamer81.GeoPlayerModelTest.server.world.feature.structure;

import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public abstract class TestStructure extends Structure<NoFeatureConfig> {
    public TestStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public abstract ConfigHandler.GenerationConfig getGenerationConfig();

    public boolean checkHeightLimitAgainstSurface() {
        return true;
    }

    public boolean avoidStructures() {
        return false;
    }

    public boolean avoidWater() {
        return true;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
        if (checkHeightLimitAgainstSurface()) {
            int landHeight = chunkGenerator.getNoiseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
            double minHeight = getGenerationConfig().heightMin.get();
            double maxHeight = getGenerationConfig().heightMax.get();
            if (minHeight != -1 && landHeight < minHeight) return false;
            if (maxHeight != -1 && landHeight > maxHeight) return false;
        }
        if (avoidWater()) {
            int landHeight = chunkGenerator.getHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
            IBlockReader columnOfBlocks = chunkGenerator.func_230348_a_(centerOfChunk.getX(), centerOfChunk.getZ());
            BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.up(landHeight));
            if (!topBlock.getFluidState().isEmpty()) return false;
        }
        List<? extends String> avoidStructures = getGenerationConfig().avoidStructures.get();
        for (String structureName : avoidStructures) {
            Structure<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(structureName));
            if (structure == null) continue;
            if (structureNearby(structure, chunkGenerator, seed, chunkRandom, chunkX, chunkZ)) return false;
        }
        return super.func_230363_a_(chunkGenerator, biomeSource, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, featureConfig);
    }

    private boolean structureNearby(Structure structure, ChunkGenerator chunkGenerator, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkY) {
        StructureSeparationSettings structureseparationsettings = chunkGenerator.func_235957_b_().func_236197_a_(structure);
        if (structureseparationsettings != null) {
            for (int i = chunkX - 10; i <= chunkX + 10; ++i) {
                for (int j = chunkY - 10; j <= chunkY + 10; ++j) {
                    ChunkPos chunkpos = structure.getChunkPosForStructure(structureseparationsettings, seed, chunkRandom, i, j);
                    if (i == chunkpos.x && j == chunkpos.z) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
