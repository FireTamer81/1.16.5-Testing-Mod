package io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ControlledAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleOrb;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.AdvancedParticleBase;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.ParticleComponent;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoRenderPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.damage.DamageUtil;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.LeaderSunstrikeImmune;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntitySolarBeam extends Entity {
    public static final double RADIUS_BARAKO = 30;
    public static final double RADIUS_PLAYER = 20;
    public LivingEntity caster;
    public double endPosX, endPosY, endPosZ;
    public double collidePosX, collidePosY, collidePosZ;
    public double prevCollidePosX, prevCollidePosY, prevCollidePosZ;
    public float renderYaw, renderPitch;
    public ControlledAnimation appear = new ControlledAnimation(3);

    public boolean on = true;

    public Direction blockSide = null;

    private static final DataParameter<Float> YAW = EntityDataManager.createKey(EntitySolarBeam.class, DataSerializers.FLOAT);

    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntitySolarBeam.class, DataSerializers.FLOAT);

    private static final DataParameter<Integer> DURATION = EntityDataManager.createKey(EntitySolarBeam.class, DataSerializers.VARINT);

    private static final DataParameter<Boolean> HAS_PLAYER = EntityDataManager.createKey(EntitySolarBeam.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Integer> CASTER = EntityDataManager.createKey(EntitySolarBeam.class, DataSerializers.VARINT);

    public float prevYaw;
    public float prevPitch;

    @OnlyIn(Dist.CLIENT)
    private Vector3d[] attractorPos;

    public EntitySolarBeam(EntityType<? extends EntitySolarBeam> type, World world) {
        super(type, world);
        ignoreFrustumCheck = true;
        if (world.isRemote) {
            attractorPos = new Vector3d[] {new Vector3d(0, 0, 0)};
        }
    }

    public EntitySolarBeam(EntityType<? extends EntitySolarBeam> type, World world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration) {
        this(type, world);
        this.caster = caster;
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setDuration(duration);
        this.setPosition(x, y, z);
        this.playSound(MMSounds.LASER.get(), 2f, 1);
        if (!world.isRemote) {
            this.setCasterID(caster.getEntityId());
        }
    }

    @Override
    public PushReaction getPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public void tick() {
        super.tick();
        prevCollidePosX = collidePosX;
        prevCollidePosY = collidePosY;
        prevCollidePosZ = collidePosZ;
        prevYaw = renderYaw;
        prevPitch = renderPitch;
        prevPosX = getPosX();
        prevPosY = getPosY();
        prevPosZ = getPosZ();
        if (ticksExisted == 1 && world.isRemote) {
            caster = (LivingEntity) world.getEntityByID(getCasterID());
        }
        if (getHasPlayer()) {
            if (!world.isRemote) {
                this.updateWithPlayer();
            }
        }
        if (caster != null) {
            renderYaw = (float) ((caster.rotationYawHead + 90.0d) * Math.PI / 180.0d);
            renderPitch = (float) (-caster.rotationPitch * Math.PI / 180.0d);
        }

        if (!on && appear.getTimer() == 0) {
            this.remove();
        }
        if (on && ticksExisted > 20) {
            appear.increaseTimer();
        } else {
            appear.decreaseTimer();
        }

        if (caster != null && !caster.isAlive()) remove();

        if (world.isRemote && ticksExisted <= 10 && caster != null) {
            int particleCount = 8;
            while (--particleCount != 0) {
                double radius = 2f * caster.getWidth();
                double yaw = rand.nextFloat() * 2 * Math.PI;
                double pitch = rand.nextFloat() * 2 * Math.PI;
                double ox = radius * Math.sin(yaw) * Math.sin(pitch);
                double oy = radius * Math.cos(pitch);
                double oz = radius * Math.cos(yaw) * Math.sin(pitch);
                double rootX = caster.getPosX();
                double rootY = caster.getPosY() + caster.getHeight() / 2f + 0.3f;
                double rootZ = caster.getPosZ();
                if (getHasPlayer()) {
                    if (caster instanceof PlayerEntity && !(caster == Minecraft.getInstance().player && Minecraft.getInstance().gameSettings.getPointOfView() == PointOfView.FIRST_PERSON)) {
                        GeckoPlayer geckoPlayer = GeckoPlayer.getGeckoPlayer((PlayerEntity) caster, GeckoPlayer.Perspective.THIRD_PERSON);
                        if (geckoPlayer != null) {
                            GeckoRenderPlayer renderPlayer = (GeckoRenderPlayer) geckoPlayer.getPlayerRenderer();
                            if (renderPlayer.betweenHandsPos != null) {
                                rootX += renderPlayer.betweenHandsPos.getX();
                                rootY += renderPlayer.betweenHandsPos.getY();
                                rootZ += renderPlayer.betweenHandsPos.getZ();
                            }
                        }
                    }
                }
                attractorPos[0] = new Vector3d(rootX, rootY, rootZ);
                AdvancedParticleBase.spawnParticle(world, ParticleHandler.ORB2.get(), rootX + ox, rootY + oy, rootZ + oz, 0, 0, 0, true, 0, 0, 0, 0, 5F, 1, 1, 1, 1, 1, 7, true, false, new ParticleComponent[]{
                        new ParticleComponent.Attractor(attractorPos, 1.7f, 0.0f, ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, new ParticleComponent.KeyTrack(
                                new float[]{0f, 0.8f},
                                new float[]{0f, 1f}
                        ), false),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, new ParticleComponent.KeyTrack(
                                new float[]{3f, 6f},
                                new float[]{0f, 1f}
                        ), false)
                });
            }
        }
        if (ticksExisted > 20) {
            List<LivingEntity> hit = raytraceEntities(world, new Vector3d(getPosX(), getPosY(), getPosZ()), new Vector3d(endPosX, endPosY, endPosZ), false, true, true).entities;
            if (blockSide != null) {
                spawnExplosionParticles(2);
            }
            if (!world.isRemote) {
                for (LivingEntity target : hit) {
                    float damageFire = 1f;
                    float damageMob = 1.5f;
                    if (caster instanceof PlayerEntity) {
                        damageFire *= ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get();
                        damageMob *= ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get();
                    }
                    DamageUtil.dealMixedDamage(target, DamageSource.causeIndirectDamage(this, caster), damageMob, DamageSource.ON_FIRE, damageFire);
                }
            } else {
                if (ticksExisted - 15 < getDuration()) {
                    int particleCount = 4;
                    while (particleCount --> 0) {
                        double radius = 1f;
                        double yaw = (float) (rand.nextFloat() * 2 * Math.PI);
                        double pitch = (float) (rand.nextFloat() * 2 * Math.PI);
                        double ox = (float) (radius * Math.sin(yaw) * Math.sin(pitch));
                        double oy = (float) (radius * Math.cos(pitch));
                        double oz = (float) (radius * Math.cos(yaw) * Math.sin(pitch));
                        double o2x = (float) (-1 * Math.cos(getYaw()) * Math.cos(getPitch()));
                        double o2y = (float) (-1 * Math.sin(getPitch()));
                        double o2z = (float) (-1 * Math.sin(getYaw()) * Math.cos(getPitch()));
                        world.addParticle(new ParticleOrb.OrbData((float) (collidePosX + o2x + ox), (float) (collidePosY + o2y + oy), (float) (collidePosZ + o2z + oz), 15), getPosX() + o2x + ox, getPosY() + o2y + oy, getPosZ() + o2z + oz, 0, 0, 0);
                    }
                    particleCount = 4;
                    while (particleCount --> 0) {
                        double radius = 2f;
                        double yaw = rand.nextFloat() * 2 * Math.PI;
                        double pitch = rand.nextFloat() * 2 * Math.PI;
                        double ox = radius * Math.sin(yaw) * Math.sin(pitch);
                        double oy = radius * Math.cos(pitch);
                        double oz = radius * Math.cos(yaw) * Math.sin(pitch);
                        double o2x = -1 * Math.cos(getYaw()) * Math.cos(getPitch());
                        double o2y = -1 * Math.sin(getPitch());
                        double o2z = -1 * Math.sin(getYaw()) * Math.cos(getPitch());
                        world.addParticle(new ParticleOrb.OrbData((float) (collidePosX + o2x + ox), (float) (collidePosY + o2y + oy), (float) (collidePosZ + o2z + oz), 20), collidePosX + o2x, collidePosY + o2y, collidePosZ + o2z, 0, 0, 0);
                    }
                }
            }
        }
        if (ticksExisted - 20 > getDuration()) {
            on = false;
        }
    }

    private void spawnExplosionParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            final float velocity = 0.1F;
            float yaw = (float) (rand.nextFloat() * 2 * Math.PI);
            float motionY = rand.nextFloat() * 0.08F;
            float motionX = velocity * MathHelper.cos(yaw);
            float motionZ = velocity * MathHelper.sin(yaw);
            world.addParticle(ParticleTypes.FLAME, collidePosX, collidePosY + 0.1, collidePosZ, motionX, motionY, motionZ);
        }
        for (int i = 0; i < amount / 2; i++) {
            world.addParticle(ParticleTypes.LAVA, collidePosX, collidePosY + 0.1, collidePosZ, 0, 0, 0);
        }
    }

    @Override
    protected void registerData() {
        getDataManager().register(YAW, 0F);
        getDataManager().register(PITCH, 0F);
        getDataManager().register(DURATION, 0);
        getDataManager().register(HAS_PLAYER, false);
        getDataManager().register(CASTER, -1);
    }

    public float getYaw() {
        return getDataManager().get(YAW);
    }

    public void setYaw(float yaw) {
        getDataManager().set(YAW, yaw);
    }

    public float getPitch() {
        return getDataManager().get(PITCH);
    }

    public void setPitch(float pitch) {
        getDataManager().set(PITCH, pitch);
    }

    public int getDuration() {
        return getDataManager().get(DURATION);
    }

    public void setDuration(int duration) {
        getDataManager().set(DURATION, duration);
    }

    public boolean getHasPlayer() {
        return getDataManager().get(HAS_PLAYER);
    }

    public void setHasPlayer(boolean player) {
        getDataManager().set(HAS_PLAYER, player);
    }

    public int getCasterID() {
        return getDataManager().get(CASTER);
    }

    public void setCasterID(int id) {
        getDataManager().set(CASTER, id);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {}

    @Override
    protected void writeAdditional(CompoundNBT nbt) {}

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public HitResult raytraceEntities(World world, Vector3d from, Vector3d to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        HitResult result = new HitResult();
        result.setBlockHit(world.rayTraceBlocks(new RayTraceContext(from, to, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)));
        if (result.blockHit != null) {
            Vector3d hitVec = result.blockHit.getHitVec();
            collidePosX = hitVec.x;
            collidePosY = hitVec.y;
            collidePosZ = hitVec.z;
            blockSide = result.blockHit.getFace();
        } else {
            collidePosX = endPosX;
            collidePosY = endPosY;
            collidePosZ = endPosZ;
            blockSide = null;
        }
        List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(Math.min(getPosX(), collidePosX), Math.min(getPosY(), collidePosY), Math.min(getPosZ(), collidePosZ), Math.max(getPosX(), collidePosX), Math.max(getPosY(), collidePosY), Math.max(getPosZ(), collidePosZ)).grow(1, 1, 1));
        for (LivingEntity entity : entities) {
            if (entity == caster) {
                continue;
            }
            float pad = entity.getCollisionBorderSize() + 0.5f;
            AxisAlignedBB aabb = entity.getBoundingBox().grow(pad, pad, pad);
            Optional<Vector3d> hit = aabb.rayTrace(from, to);
            if (aabb.contains(from)) {
                result.addEntityHit(entity);
            } else if (hit.isPresent()) {
                result.addEntityHit(entity);
            }
        }
        return result;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 1024;
    }

    private void updateWithPlayer() {
        this.setYaw((float) ((caster.rotationYawHead + 90) * Math.PI / 180.0d));
        this.setPitch((float) (-caster.rotationPitch * Math.PI / 180.0d));
        Vector3d vecOffset = caster.getLookVec().normalize().scale(1);
        this.setPosition(caster.getPosX() + vecOffset.getX(), caster.getPosY() + 1.2f + vecOffset.getY(), caster.getPosZ() + vecOffset.getZ());
    }

    @Override
    public void remove() {
        super.remove();
        if (caster instanceof PlayerEntity) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(caster, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
            if (playerCapability != null) {
                playerCapability.setUsingSolarBeam(false);
            }
        }
    }

    public static class HitResult {
        private BlockRayTraceResult blockHit;

        private final List<LivingEntity> entities = new ArrayList<>();

        public BlockRayTraceResult getBlockHit() {
            return blockHit;
        }

        public void setBlockHit(RayTraceResult rayTraceResult) {
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                this.blockHit = (BlockRayTraceResult) rayTraceResult;
        }

        public void addEntityHit(LivingEntity entity) {
            entities.add(entity);
        }
    }
}
