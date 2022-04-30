package io.github.FireTamer81.GeoPlayerModelTest.server.entity;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.*;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.*;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath.EntityBabyFoliaath;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath.EntityFoliaath;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.frostmaw.EntityFrostmaw;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.frostmaw.EntityFrozenController;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol.EntityGrottol;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.lantern.EntityLantern;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.naga.EntityNaga;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.wroughtnaut.EntityWroughtnaut;
import io.github.FireTamer81.TestModMain;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TestModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityHandler {
    public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITIES, TestModMain.MODID);

    public static final RegistryObject<EntityType<EntityFoliaath>> FOLIAATH = REG.register("foliaath", () -> EntityType.Builder.create(EntityFoliaath::new, EntityClassification.MONSTER).size(0.5f, 2.5f).build(new ResourceLocation(TestModMain.MODID, "foliaath").toString()));
    public static final RegistryObject<EntityType<EntityBabyFoliaath>> BABY_FOLIAATH = REG.register("baby_foliaath", () -> EntityType.Builder.create(EntityBabyFoliaath::new, EntityClassification.MONSTER).size(0.4f, 0.4f).build(new ResourceLocation(TestModMain.MODID, "baby_foliaath").toString()));
    public static final RegistryObject<EntityType<EntityWroughtnaut>> WROUGHTNAUT = REG.register("ferrous_wroughtnaut", () -> EntityType.Builder.create(EntityWroughtnaut::new, EntityClassification.MONSTER).size(2.5f, 3.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "ferrous_wroughtnaut").toString()));
    private static EntityType.Builder<EntityBarakoanToBarakoana> barakoanToBarakoanaBuilder() {
        return EntityType.Builder.create(EntityBarakoanToBarakoana::new, EntityClassification.MONSTER);
    }
    public static final RegistryObject<EntityType<EntityBarakoanToBarakoana>> BARAKOAN_TO_BARAKOANA = REG.register("barakoan_barakoana", () -> barakoanToBarakoanaBuilder().size(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoan_barakoana").toString()));
    private static EntityType.Builder<EntityBarakoanToPlayer> barakoanToPlayerBuilder() {
        return EntityType.Builder.create(EntityBarakoanToPlayer::new, EntityClassification.MONSTER);
    }
    public static final RegistryObject<EntityType<EntityBarakoanToPlayer>> BARAKOAN_TO_PLAYER = REG.register("barakoan_player", () -> barakoanToPlayerBuilder().size(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoan_player").toString()));
    private static EntityType.Builder<EntityBarakoayaToPlayer> barakoayaToPlayerBuilder() {
        return EntityType.Builder.create(EntityBarakoayaToPlayer::new, EntityClassification.MONSTER);
    }
    public static final RegistryObject<EntityType<EntityBarakoayaToPlayer>> BARAKOAYA_TO_PLAYER = REG.register("barakoa_sunblocker_player", () -> barakoayaToPlayerBuilder().size(MaskType.FAITH.entityWidth, MaskType.FAITH.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoa_sunblocker_player").toString()));
    public static final RegistryObject<EntityType<EntityBarakoaVillager>> BARAKOA_VILLAGER = REG.register("barakoaya", () -> EntityType.Builder.create(EntityBarakoaVillager::new, EntityClassification.MONSTER).size(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoaya").toString()));
    public static final RegistryObject<EntityType<EntityBarakoana>> BARAKOANA = REG.register("barakoana", () -> EntityType.Builder.create(EntityBarakoana::new, EntityClassification.MONSTER).size(MaskType.FURY.entityWidth, MaskType.FURY.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoana").toString()));
    public static final RegistryObject<EntityType<EntityBarakoaya>> BARAKOAYA = REG.register("barakoa_sunblocker", () -> EntityType.Builder.create(EntityBarakoaya::new, EntityClassification.MONSTER).size(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barakoa_sunblocker").toString()));
    public static final RegistryObject<EntityType<EntityBarako>> BARAKO = REG.register("barako", () -> EntityType.Builder.create(EntityBarako::new, EntityClassification.MONSTER).size(1.5f, 2.4f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "barako").toString()));
    public static final RegistryObject<EntityType<EntityFrostmaw>> FROSTMAW = REG.register("frostmaw", () -> EntityType.Builder.create(EntityFrostmaw::new, EntityClassification.MONSTER).size(4f, 4f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "frostmaw").toString()));
    public static final RegistryObject<EntityType<EntityGrottol>> GROTTOL = REG.register("grottol", () -> EntityType.Builder.create(EntityGrottol::new, EntityClassification.MONSTER).size(0.9F, 1.2F).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "grottol").toString()));
    public static final RegistryObject<EntityType<EntityLantern>> LANTERN = REG.register("lantern", () -> EntityType.Builder.create(EntityLantern::new, EntityClassification.AMBIENT).size(1.0f, 1.0f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "lantern").toString()));
    public static final RegistryObject<EntityType<EntityNaga>> NAGA = REG.register("naga", () -> EntityType.Builder.create(EntityNaga::new, EntityClassification.MONSTER).size(3.0f, 1.0f).setTrackingRange(128).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "naga").toString()));

    private static EntityType.Builder<EntitySunstrike> sunstrikeBuilder() {
        return EntityType.Builder.create(EntitySunstrike::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntitySunstrike>> SUNSTRIKE = REG.register("sunstrike", () -> sunstrikeBuilder().size(0.1F, 0.1F).build(new ResourceLocation(TestModMain.MODID, "sunstrike").toString()));
    private static EntityType.Builder<EntitySolarBeam> solarBeamBuilder() {
        return EntityType.Builder.create(EntitySolarBeam::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntitySolarBeam>> SOLAR_BEAM = REG.register("solar_beam", () -> solarBeamBuilder().size(0.1F, 0.1F).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "solar_beam").toString()));
    private static EntityType.Builder<EntityBoulder> boulderBuilder() {
        return EntityType.Builder.create(EntityBoulder::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_SMALL = REG.register("boulder_small", () -> boulderBuilder().size(1, 1).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_small").toString()));;
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_MEDIUM = REG.register("boulder_medium", () -> boulderBuilder().size(2, 1.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_medium").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_LARGE = REG.register("boulder_large", () -> boulderBuilder().size(3, 2.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_large").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_HUGE = REG.register("boulder_huge", () -> boulderBuilder().size(4, 3.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_huge").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>>[] BOULDERS = new RegistryObject[] {BOULDER_SMALL, BOULDER_MEDIUM, BOULDER_LARGE, BOULDER_HUGE};
    private static EntityType.Builder<EntityAxeAttack> axeAttackBuilder() {
        return EntityType.Builder.create(EntityAxeAttack::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityAxeAttack>> AXE_ATTACK = REG.register("axe_attack", () -> axeAttackBuilder().size(1f, 1f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "axe_attack").toString()));
    private static EntityType.Builder<EntityIceBreath> iceBreathBuilder() {
        return EntityType.Builder.create(EntityIceBreath::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityIceBreath>> ICE_BREATH = REG.register("ice_breath", () -> iceBreathBuilder().size(0F, 0F).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "ice_breath").toString()));
    private static EntityType.Builder<EntityIceBall> iceBallBuilder() {
        return EntityType.Builder.create(EntityIceBall::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityIceBall>> ICE_BALL = REG.register("ice_ball", () -> iceBallBuilder().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "ice_ball").toString()));
    private static EntityType.Builder<EntityFrozenController> frozenControllerBuilder() {
        return EntityType.Builder.create(EntityFrozenController::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityFrozenController>> FROZEN_CONTROLLER = REG.register("frozen_controller", () -> frozenControllerBuilder().disableSummoning().size(0, 0).build(new ResourceLocation(TestModMain.MODID, "frozen_controller").toString()));
    private static EntityType.Builder<EntityDart> dartBuilder() {
        return EntityType.Builder.create(EntityDart::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityDart>> DART = REG.register("dart", () -> dartBuilder().disableSummoning().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "dart").toString()));
    private static EntityType.Builder<EntityPoisonBall> poisonBallBuilder() {
        return EntityType.Builder.create(EntityPoisonBall::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityPoisonBall>> POISON_BALL = REG.register("poison_ball", () -> poisonBallBuilder().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "poison_ball").toString()));
    private static EntityType.Builder<EntitySuperNova> superNovaBuilder() {
        return EntityType.Builder.create(EntitySuperNova::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntitySuperNova>> SUPER_NOVA = REG.register("super_nova", () -> superNovaBuilder().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "super_nova").toString()));
    private static EntityType.Builder<EntityFallingBlock> fallingBlockBuilder() {
        return EntityType.Builder.create(EntityFallingBlock::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityFallingBlock>> FALLING_BLOCK = REG.register("falling_block", () -> fallingBlockBuilder().size(1, 1).build(new ResourceLocation(TestModMain.MODID, "falling_block").toString()));
    private static EntityType.Builder<EntityBlockSwapper> blockSwapperBuilder() {
        return EntityType.Builder.create(EntityBlockSwapper::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityBlockSwapper>> BLOCK_SWAPPER = REG.register("block_swapper", () -> blockSwapperBuilder().disableSummoning().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "block_swapper").toString()));
    private static EntityType.Builder<EntityCameraShake> cameraShakeBuilder() {
        return EntityType.Builder.create(EntityCameraShake::new, EntityClassification.MISC);
    }
    public static final RegistryObject<EntityType<EntityCameraShake>> CAMERA_SHAKE = REG.register("camera_shake", () -> cameraShakeBuilder().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "camera_shake").toString()));

    @SubscribeEvent
    public static void onCreateAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityHandler.FOLIAATH.get(), EntityFoliaath.createAttributes().create());
        event.put(EntityHandler.BABY_FOLIAATH.get(), EntityBabyFoliaath.createAttributes().create());
        event.put(EntityHandler.WROUGHTNAUT.get(), EntityWroughtnaut.createAttributes().create());
        event.put(EntityHandler.BARAKOANA.get(), EntityBarakoana.createAttributes().create());
        event.put(EntityHandler.BARAKOA_VILLAGER.get(), EntityBarakoa.createAttributes().create());
        event.put(EntityHandler.BARAKOAN_TO_PLAYER.get(), EntityBarakoanToPlayer.createAttributes().create());
        event.put(EntityHandler.BARAKOAYA_TO_PLAYER.get(), EntityBarakoanToPlayer.createAttributes().create());
        event.put(EntityHandler.BARAKOAN_TO_BARAKOANA.get(), EntityBarakoa.createAttributes().create());
        event.put(EntityHandler.BARAKOAYA.get(), EntityBarakoa.createAttributes().create());
        event.put(EntityHandler.BARAKO.get(), EntityBarako.createAttributes().create());
        event.put(EntityHandler.FROSTMAW.get(), EntityFrostmaw.createAttributes().create());
        event.put(EntityHandler.NAGA.get(), EntityNaga.createAttributes().create());
        event.put(EntityHandler.LANTERN.get(), EntityLantern.createAttributes().create());
        event.put(EntityHandler.GROTTOL.get(), EntityGrottol.createAttributes().create());
//        event.put(EntityHandler.SCULPTOR.get(), EntitySculptor.createAttributes().create());
    }
}
