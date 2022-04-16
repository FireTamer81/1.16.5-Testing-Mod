package io.github.FireTamer81.GeoPlayerModelTest.server.world.spawn;

import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.world.BiomeChecker;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.HashMap;
import java.util.Map;

public class SpawnHandler {
    public static final Map<EntityType<?>, ConfigHandler.SpawnConfig> spawnConfigs = new HashMap<>();
    static {
        spawnConfigs.put(EntityHandler.FOLIAATH.get(), ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig);
        spawnConfigs.put(EntityHandler.BARAKOANA.get(), ConfigHandler.COMMON.MOBS.BARAKOA.spawnConfig);
        spawnConfigs.put(EntityHandler.LANTERN.get(), ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig);
        spawnConfigs.put(EntityHandler.NAGA.get(), ConfigHandler.COMMON.MOBS.NAGA.spawnConfig);
        spawnConfigs.put(EntityHandler.GROTTOL.get(), ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig);
    }

    public static void registerSpawnPlacementTypes() {
        EntitySpawnPlacementRegistry.PlacementType.create("MMSPAWN", new TriPredicate<IWorldReader, BlockPos, EntityType<? extends MobEntity>>() {
            @Override
            public boolean test(IWorldReader t, BlockPos pos, EntityType<? extends MobEntity> entityType) {
                BlockState block = t.getBlockState(pos.down());
                if (block.getBlock() == Blocks.BEDROCK || block.getBlock() == Blocks.BARRIER || !block.getMaterial().blocksMovement())
                    return false;
                BlockState iblockstateUp = t.getBlockState(pos);
                BlockState iblockstateUp2 = t.getBlockState(pos.up());
                return WorldEntitySpawner.func_234968_a_(t, pos, iblockstateUp, iblockstateUp.getFluidState(), entityType) && WorldEntitySpawner.func_234968_a_(t, pos.up(), iblockstateUp2, iblockstateUp2.getFluidState(), entityType);
            }
        });

        EntitySpawnPlacementRegistry.PlacementType mmSpawn = EntitySpawnPlacementRegistry.PlacementType.valueOf("MMSPAWN");
        if (mmSpawn != null) {
            EntitySpawnPlacementRegistry.register(EntityHandler.FOLIAATH.get(), mmSpawn, Heightmap.Type.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
            EntitySpawnPlacementRegistry.register(EntityHandler.LANTERN.get(), mmSpawn, Heightmap.Type.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
            EntitySpawnPlacementRegistry.register(EntityHandler.BARAKOANA.get(), mmSpawn, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
            EntitySpawnPlacementRegistry.register(EntityHandler.NAGA.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, MowzieEntity::spawnPredicate);
            EntitySpawnPlacementRegistry.register(EntityHandler.GROTTOL.get(), mmSpawn, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
            EntitySpawnPlacementRegistry.register(EntityHandler.BARAKOAYA.get(), mmSpawn, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MowzieEntity::spawnPredicate);
        }
    }

    public static void onBiomeLoading(BiomeLoadingEvent event) {
        ResourceLocation biomeName = event.getName();
        if (biomeName == null) return;
        if (ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig.spawnRate.get() > 0 && BiomeChecker.isBiomeInConfig(ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig.biomeConfig, biomeName)) {
//            System.out.println("Added foliaath biome: " + biomeName.toString());
            registerEntityWorldSpawn(EntityHandler.FOLIAATH.get(), ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig, EntityClassification.MONSTER, event);
        }
        if (ConfigHandler.COMMON.MOBS.BARAKOA.spawnConfig.spawnRate.get() > 0 && BiomeChecker.isBiomeInConfig(ConfigHandler.COMMON.MOBS.BARAKOA.spawnConfig.biomeConfig, biomeName)) {
//            System.out.println("Added Barakoa biome: " + biomeName.toString());
            registerEntityWorldSpawn(EntityHandler.BARAKOANA.get(), ConfigHandler.COMMON.MOBS.BARAKOA.spawnConfig, EntityClassification.MONSTER, event);
        }
        if (ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig.spawnRate.get() > 0 && BiomeChecker.isBiomeInConfig(ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig.biomeConfig, biomeName)) {
//            System.out.println("Added grottol biome: " + biomeName.toString());
            registerEntityWorldSpawn(EntityHandler.GROTTOL.get(), ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig, EntityClassification.MONSTER, event);
        }
        if (ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig.spawnRate.get() > 0 && BiomeChecker.isBiomeInConfig(ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig.biomeConfig, biomeName)) {
//            System.out.println("Added lantern biome: " + biomeName.toString());
            registerEntityWorldSpawn(EntityHandler.LANTERN.get(), ConfigHandler.COMMON.MOBS.LANTERN.spawnConfig, EntityClassification.AMBIENT, event);
        }
        if (ConfigHandler.COMMON.MOBS.NAGA.spawnConfig.spawnRate.get() > 0 && BiomeChecker.isBiomeInConfig(ConfigHandler.COMMON.MOBS.NAGA.spawnConfig.biomeConfig, biomeName)) {
//            System.out.println("Added naga biome: " + biomeName.toString());
            registerEntityWorldSpawn(EntityHandler.NAGA.get(), ConfigHandler.COMMON.MOBS.NAGA.spawnConfig, EntityClassification.MONSTER, event);
        }
    }

    private static void registerEntityWorldSpawn(EntityType<?> entity, ConfigHandler.SpawnConfig spawnConfig, EntityClassification classification, BiomeLoadingEvent event) {
        event.getSpawns().getSpawner(classification).add(new MobSpawnInfo.Spawners(entity, spawnConfig.spawnRate.get(), spawnConfig.minGroupSize.get(), spawnConfig.maxGroupSize.get()));
    }
}
