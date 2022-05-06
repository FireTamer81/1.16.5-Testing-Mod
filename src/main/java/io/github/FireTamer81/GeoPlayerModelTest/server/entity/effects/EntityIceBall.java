package io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects;

import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleCloud;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleRing;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleSnowFlake;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.TestModMain;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by BobMowzie on 9/2/2018.
 */
public class EntityIceBall extends EntityMagicEffect {
    public EntityIceBall(World world) {
        super(EntityHandler.ICE_BALL.get(), world);
    }

    public EntityIceBall(EntityType<? extends EntityIceBall> type, World worldIn) {
        super(type, worldIn);
    }

    public EntityIceBall(EntityType<? extends EntityIceBall> type, World worldIn, LivingEntity caster) {
        this(type, worldIn);
        if (!world.isRemote) {
            this.setCasterID(caster.getEntityId());
        }
    }

    @Override
    public void tick() {
        super.tick();
        move(MoverType.SELF, getMotion());

        if (ticksExisted == 1) {
            if (world.isRemote) {
                TestModMain.PROXY.playIceBreathSound(this);
            }
        }

        List<LivingEntity> entitiesHit = getEntityLivingBaseNearby(2);
        if (!entitiesHit.isEmpty()) {
            for (LivingEntity entity : entitiesHit) {
                if (entity == caster) continue;
                List<? extends String> freezeImmune = ConfigHandler.COMMON.GENERAL.freeze_blacklist.get();
                ResourceLocation mobName = EntityType.getKey(entity.getType());
                if (freezeImmune.contains(mobName.toString())) continue;
            }
        }

        if (!world.hasNoCollisions(this, getBoundingBox().grow(0.15))) {
            explode();
        }

        if (world.isRemote) {
            float scale = 2f;
            double x = getPosX();
            double y = getPosY() + getHeight() / 2;
            double z = getPosZ();
            double motionX = getMotion().x;
            double motionY = getMotion().y;
            double motionZ = getMotion().z;
            for (int i = 0; i < 4; i++) {
                double xSpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                double ySpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                double zSpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                float value = rand.nextFloat() * 0.15f;
                world.addParticle(new ParticleCloud.CloudData(ParticleHandler.CLOUD.get(), 0.75f + value, 0.75f + value,1f, scale * (10f + rand.nextFloat() * 20f), 20, ParticleCloud.EnumCloudBehavior.SHRINK, 1f), x + xSpeed, y + ySpeed, z + zSpeed, xSpeed, ySpeed, zSpeed);
            }
            for (int i = 0; i < 1; i++) {
                double xSpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                double ySpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                double zSpeed = scale * 0.01 * (rand.nextFloat() * 2 - 1);
                world.addParticle(new ParticleCloud.CloudData(ParticleHandler.CLOUD.get(), 1f, 1f, 1f, scale * (5f + rand.nextFloat() * 10f), 40, ParticleCloud.EnumCloudBehavior.SHRINK, 1f), x, y, z, xSpeed, ySpeed, zSpeed);
            }

            for (int i = 0; i < 5; i++) {
                double xSpeed = scale * 0.05 * (rand.nextFloat() * 2 - 1);
                double ySpeed = scale * 0.05 * (rand.nextFloat() * 2 - 1);
                double zSpeed = scale * 0.05 * (rand.nextFloat() * 2 - 1);
                world.addParticle(new ParticleSnowFlake.SnowflakeData(40, false), x - 20 * (xSpeed) + motionX, y - 20 * ySpeed + motionY, z - 20 * zSpeed + motionZ, xSpeed, ySpeed, zSpeed);
            }

            float yaw = (float) Math.atan2(motionX, motionZ);
            float pitch = (float) (Math.acos(motionY / Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ)) + Math.PI / 2);
            if (ticksExisted % 3 == 0) {
                world.addParticle(new ParticleRing.RingData(yaw, pitch, 40, 0.9f, 0.9f, 1f, 0.4f, scale * 16f, false, ParticleRing.EnumRingBehavior.GROW_THEN_SHRINK), x + 1.5f * motionX, y + 1.5f *motionY, z + 1.5f * motionZ, 0, 0, 0);
            }

            if (ticksExisted == 1) {
                world.addParticle(new ParticleRing.RingData(yaw, pitch, 20, 0.9f, 0.9f, 1f, 0.4f, scale * 16f, false, ParticleRing.EnumRingBehavior.GROW), x, y, z, 0, 0, 0);
            }
        }
        if (ticksExisted > 50) remove();
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        setMotion(x * velocity, y * velocity, z * velocity);
    }

    private void explode() {
        if (world.isRemote) {
            for (int i = 0; i < 8; i++) {
                Vector3d particlePos = new Vector3d(rand.nextFloat() * 0.3, 0, 0);
                particlePos = particlePos.rotateYaw((float) (rand.nextFloat() * 2 * Math.PI));
                particlePos = particlePos.rotatePitch((float) (rand.nextFloat() * 2 * Math.PI));
                float value = rand.nextFloat() * 0.15f;
                world.addParticle(new ParticleCloud.CloudData(ParticleHandler.CLOUD.get(), 0.75f + value, 0.75f + value, 1f, 10f + rand.nextFloat() * 20f, 40, ParticleCloud.EnumCloudBehavior.GROW, 1f), getPosX() + particlePos.x, getPosY() + particlePos.y, getPosZ() + particlePos.z, particlePos.x, particlePos.y, particlePos.z);
            }
            for (int i = 0; i < 10; i++) {
                Vector3d particlePos = new Vector3d(rand.nextFloat() * 0.3, 0, 0);
                particlePos = particlePos.rotateYaw((float) (rand.nextFloat() * 2 * Math.PI));
                particlePos = particlePos.rotatePitch((float) (rand.nextFloat() * 2 * Math.PI));
                world.addParticle(new ParticleSnowFlake.SnowflakeData(40, false), getPosX() + particlePos.x, getPosY() + particlePos.y, getPosZ() + particlePos.z, particlePos.x, particlePos.y, particlePos.z);
            }
        }
        remove();
    }
}
