package io.github.FireTamer81.GeoPlayerModelTest.server.entity;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.*;
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


    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_SMALL = REG.register("boulder_small", () -> boulderBuilder().size(1, 1).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_small").toString()));;
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_MEDIUM = REG.register("boulder_medium", () -> boulderBuilder().size(2, 1.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_medium").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_LARGE = REG.register("boulder_large", () -> boulderBuilder().size(3, 2.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_large").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>> BOULDER_HUGE = REG.register("boulder_huge", () -> boulderBuilder().size(4, 3.5f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "boulder_huge").toString()));
    public static final RegistryObject<EntityType<EntityBoulder>>[] BOULDERS = new RegistryObject[] {BOULDER_SMALL, BOULDER_MEDIUM, BOULDER_LARGE, BOULDER_HUGE};
    public static final RegistryObject<EntityType<EntityAxeAttack>> AXE_ATTACK = REG.register("axe_attack", () -> axeAttackBuilder().size(1f, 1f).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "axe_attack").toString()));

    public static final RegistryObject<EntityType<EntitySunstrike>> SUNSTRIKE = REG.register("sunstrike", () -> sunstrikeBuilder().size(0.1F, 0.1F).build(new ResourceLocation(TestModMain.MODID, "sunstrike").toString()));
    public static final RegistryObject<EntityType<EntitySolarBeam>> SOLAR_BEAM = REG.register("solar_beam", () -> solarBeamBuilder().size(0.1F, 0.1F).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "solar_beam").toString()));
    public static final RegistryObject<EntityType<EntityIceBreath>> ICE_BREATH = REG.register("ice_breath", () -> iceBreathBuilder().size(0F, 0F).setUpdateInterval(1).build(new ResourceLocation(TestModMain.MODID, "ice_breath").toString()));
    public static final RegistryObject<EntityType<EntityIceBall>> ICE_BALL = REG.register("ice_ball", () -> iceBallBuilder().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "ice_ball").toString()));
    public static final RegistryObject<EntityType<EntityDart>> DART = REG.register("dart", () -> dartBuilder().disableSummoning().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "dart").toString()));
    public static final RegistryObject<EntityType<EntityPoisonBall>> POISON_BALL = REG.register("poison_ball", () -> poisonBallBuilder().size(0.5F, 0.5F).setUpdateInterval(20).build(new ResourceLocation(TestModMain.MODID, "poison_ball").toString()));
    public static final RegistryObject<EntityType<EntitySuperNova>> SUPER_NOVA = REG.register("super_nova", () -> superNovaBuilder().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "super_nova").toString()));
    public static final RegistryObject<EntityType<EntityFallingBlock>> FALLING_BLOCK = REG.register("falling_block", () -> fallingBlockBuilder().size(1, 1).build(new ResourceLocation(TestModMain.MODID, "falling_block").toString()));
    public static final RegistryObject<EntityType<EntityBlockSwapper>> BLOCK_SWAPPER = REG.register("block_swapper", () -> blockSwapperBuilder().disableSummoning().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "block_swapper").toString()));
    public static final RegistryObject<EntityType<EntityCameraShake>> CAMERA_SHAKE = REG.register("camera_shake", () -> cameraShakeBuilder().size(1, 1).setUpdateInterval(Integer.MAX_VALUE).build(new ResourceLocation(TestModMain.MODID, "camera_shake").toString()));





    /******************************************************************************************************************/
    //Builders
    /******************************************************************************************************************/

    private static EntityType.Builder<EntityCameraShake> cameraShakeBuilder() {
        return EntityType.Builder.create(EntityCameraShake::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityBlockSwapper> blockSwapperBuilder() {
        return EntityType.Builder.create(EntityBlockSwapper::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityFallingBlock> fallingBlockBuilder() {
        return EntityType.Builder.create(EntityFallingBlock::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntitySuperNova> superNovaBuilder() {
        return EntityType.Builder.create(EntitySuperNova::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityPoisonBall> poisonBallBuilder() {
        return EntityType.Builder.create(EntityPoisonBall::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityDart> dartBuilder() {
        return EntityType.Builder.create(EntityDart::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityIceBall> iceBallBuilder() {
        return EntityType.Builder.create(EntityIceBall::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityIceBreath> iceBreathBuilder() {
        return EntityType.Builder.create(EntityIceBreath::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityAxeAttack> axeAttackBuilder() {
        return EntityType.Builder.create(EntityAxeAttack::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntityBoulder> boulderBuilder() {
        return EntityType.Builder.create(EntityBoulder::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntitySolarBeam> solarBeamBuilder() {
        return EntityType.Builder.create(EntitySolarBeam::new, EntityClassification.MISC);
    }

    private static EntityType.Builder<EntitySunstrike> sunstrikeBuilder() {
        return EntityType.Builder.create(EntitySunstrike::new, EntityClassification.MISC);
    }
}
