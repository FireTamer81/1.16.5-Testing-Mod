package io.github.FireTamer81.GeoPlayerModelTest.server.capability;

import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleCloud;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleSnowFlake;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.frostmaw.EntityFrozenController;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class FrozenCapability {
    public static int MAX_FREEZE_DECAY_DELAY = 10;

    public interface IFrozenCapability {
        boolean getFrozen();

        float getFreezeProgress();

        void setFreezeProgress(float freezeProgress);

        float getFrozenYaw();

        void setFrozenYaw(float frozenYaw);

        float getFrozenPitch();

        void setFrozenPitch(float frozenPitch);

        float getFrozenYawHead();

        void setFrozenYawHead(float frozenYawHead);

        float getFrozenRenderYawOffset();

        void setFrozenRenderYawOffset(float frozenRenderYawOffset);

        float getFrozenSwingProgress();

        void setFrozenSwingProgress(float frozenSwingProgress);

        float getFrozenLimbSwingAmount();

        void setFrozenLimbSwingAmount(float frozenLimbSwingAmount);

        boolean prevHasAI();

        void setPrevHasAI(boolean prevHasAI);

        int getFreezeDecayDelay();

        void setFreezeDecayDelay(int freezeDecayDelay);

        boolean getPrevFrozen();

        void setPrevFrozen(boolean prevFrozen);

        UUID getPreAttackTarget();

        void setPreAttackTarget(UUID livingEntity);

        EntityFrozenController getFrozenController();

        void setFrozenController(EntityFrozenController frozenController);

        void addFreezeProgress(LivingEntity entity, float amount);

        void onFreeze(LivingEntity entity);

        void onUnfreeze(LivingEntity entity);

        void tick(LivingEntity entity);

        INBT writeNBT();

        void readNBT(INBT nbt);
    }

    public static class FrozenCapabilityImp implements IFrozenCapability {
        public boolean frozen;
        public float freezeProgress = 0;
        public float frozenYaw;
        public float frozenPitch;
        public float frozenYawHead;
        public float frozenRenderYawOffset;
        public float frozenSwingProgress;
        public float frozenLimbSwingAmount;
        public boolean prevHasAI = true;
        public UUID prevAttackTarget;

        // After taking freeze progress, this timer needs to reach zero before freeze progress starts to fade
        public int freezeDecayDelay;

        public boolean prevFrozen = false;
        public EntityFrozenController frozenController;

        @Override
        public boolean getFrozen() {
            return frozen;
        }

        @Override
        public float getFreezeProgress() {
            return freezeProgress;
        }

        @Override
        public void setFreezeProgress(float freezeProgress) {
            this.freezeProgress = freezeProgress;
        }

        @Override
        public float getFrozenYaw() {
            return frozenYaw;
        }

        @Override
        public void setFrozenYaw(float frozenYaw) {
            this.frozenYaw = frozenYaw;
        }

        @Override
        public float getFrozenPitch() {
            return frozenPitch;
        }

        @Override
        public void setFrozenPitch(float frozenPitch) {
            this.frozenPitch = frozenPitch;
        }

        @Override
        public float getFrozenYawHead() {
            return frozenYawHead;
        }

        @Override
        public void setFrozenYawHead(float frozenYawHead) {
            this.frozenYawHead = frozenYawHead;
        }

        @Override
        public float getFrozenRenderYawOffset() {
            return frozenRenderYawOffset;
        }

        @Override
        public void setFrozenRenderYawOffset(float frozenRenderYawOffset) {
            this.frozenRenderYawOffset = frozenRenderYawOffset;
        }

        @Override
        public float getFrozenSwingProgress() {
            return frozenSwingProgress;
        }

        @Override
        public void setFrozenSwingProgress(float frozenSwingProgress) {
            this.frozenSwingProgress = frozenSwingProgress;
        }

        @Override
        public float getFrozenLimbSwingAmount() {
            return frozenLimbSwingAmount;
        }

        @Override
        public void setFrozenLimbSwingAmount(float frozenLimbSwingAmount) {
            this.frozenLimbSwingAmount = frozenLimbSwingAmount;
        }

        @Override
        public boolean prevHasAI() {
            return prevHasAI;
        }

        @Override
        public void setPrevHasAI(boolean prevHasAI) {
            this.prevHasAI = prevHasAI;
        }

        @Override
        public int getFreezeDecayDelay() {
            return freezeDecayDelay;
        }

        @Override
        public void setFreezeDecayDelay(int freezeDecayDelay) {
            this.freezeDecayDelay = freezeDecayDelay;
        }

        @Override
        public boolean getPrevFrozen() {
            return prevFrozen;
        }

        @Override
        public void setPrevFrozen(boolean prevFrozen) {
            this.prevFrozen = prevFrozen;
        }

        @Override
        public UUID getPreAttackTarget() {
            return prevAttackTarget;
        }

        @Override
        public void setPreAttackTarget(UUID livingEntity) {
            prevAttackTarget = livingEntity;
        }

        @Override
        public EntityFrozenController getFrozenController() {
            return frozenController;
        }

        @Override
        public void setFrozenController(EntityFrozenController frozenController) {
            this.frozenController = frozenController;
        }

        @Override
        public void addFreezeProgress(LivingEntity entity, float amount) {
            if (!entity.world.isRemote && !entity.isPotionActive(EffectHandler.FROZEN)) {
                freezeProgress += amount;
                freezeDecayDelay = MAX_FREEZE_DECAY_DELAY;
            }
        }

        @Override
        public void onFreeze(LivingEntity entity) {
            if (entity != null) {
                frozen = true;
                frozenController = new EntityFrozenController(EntityHandler.FROZEN_CONTROLLER.get(), entity.world);
                frozenController.setPositionAndRotation(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
                entity.world.addEntity(frozenController);
                frozenController.setRenderYawOffset(entity.renderYawOffset);
                frozenYaw = entity.rotationYaw;
                frozenPitch = entity.rotationPitch;
                frozenYawHead = entity.rotationYawHead;
                frozenLimbSwingAmount = 0;//entity.limbSwingAmount;
                frozenRenderYawOffset = entity.renderYawOffset;
                frozenSwingProgress = entity.swingProgress;
                entity.startRiding(frozenController, true);
                entity.resetActiveHand();

                if (entity instanceof MobEntity) {
                    MobEntity mobEntity = (MobEntity) entity;
                    if (mobEntity.getAttackTarget() != null) setPreAttackTarget(mobEntity.getAttackTarget().getUniqueID());
                    prevHasAI = !((MobEntity) entity).isAIDisabled();
                    mobEntity.setNoAI(true);
                }

                if (entity.world.isRemote) {
                    int particleCount = (int) (10 + 1 * entity.getHeight() * entity.getWidth() * entity.getWidth());
                    for (int i = 0; i < particleCount; i++) {
                        double snowX = entity.getPosX() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                        double snowZ = entity.getPosZ() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                        double snowY = entity.getPosY() + entity.getHeight() * entity.getRNG().nextFloat();
                        Vector3d motion = new Vector3d(snowX - entity.getPosX(), snowY - (entity.getPosY() + entity.getHeight() / 2), snowZ - entity.getPosZ()).normalize();
                        entity.world.addParticle(new ParticleSnowFlake.SnowflakeData(40, false), snowX, snowY, snowZ, 0.1d * motion.x, 0.1d * motion.y, 0.1d * motion.z);
                    }
                }
                entity.playSound(MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1, 1);
            }
        }

        @Override
        public void onUnfreeze(LivingEntity entity) {
            if (entity != null) {
                freezeProgress = 0;
                if (frozen) {
                    entity.removeActivePotionEffect(EffectHandler.FROZEN);
                    frozen = false;
                    if (frozenController != null) {
                        Vector3d oldPosition = entity.getPositionVec();
                        entity.stopRiding();
                        entity.setPositionAndUpdate(oldPosition.getX(), oldPosition.getY(), oldPosition.getZ());
                        frozenController.remove();
                    }
                    entity.playSound(MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1, 0.5f);
                    if (entity.world.isRemote) {
                        int particleCount = (int) (10 + 1 * entity.getHeight() * entity.getWidth() * entity.getWidth());
                        for (int i = 0; i < particleCount; i++) {
                            double particleX = entity.getPosX() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                            double particleZ = entity.getPosZ() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                            double particleY = entity.getPosY() + entity.getHeight() * entity.getRNG().nextFloat() + 0.3f;
                            entity.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.ICE.getDefaultState()), particleX, particleY, particleZ, 0, 0, 0);
                        }
                    }
                    if (entity instanceof MobEntity) {
                        if (((MobEntity) entity).isAIDisabled() && prevHasAI) {
                            ((MobEntity) entity).setNoAI(false);
                        }
                        if (getPreAttackTarget() != null) {
                            PlayerEntity target = entity.world.getPlayerByUuid(getPreAttackTarget());
                            if (target != null) {
                                ((MobEntity) entity).setAttackTarget(target);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void tick(LivingEntity entity) {
            // Freeze logic
            if (getFreezeProgress() >= 1 && !entity.isPotionActive(EffectHandler.FROZEN)) {
                entity.addPotionEffect(new EffectInstance(EffectHandler.FROZEN, 50, 0, false, false));
                freezeProgress = 1f;
            } else if (freezeProgress > 0) {
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 9, MathHelper.floor(freezeProgress * 5 + 1), false, false));
            }

            if (frozenController == null) {
                Entity riding = entity.getRidingEntity();
                if (riding instanceof EntityFrozenController) frozenController = (EntityFrozenController) riding;
            }

            if (frozen) {
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2, 50, false, false));
                entity.setSneaking(false);

                if (entity.world.isRemote && entity.ticksExisted % 2 == 0) {
                    double cloudX = entity.getPosX() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                    double cloudZ = entity.getPosZ() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                    double cloudY = entity.getPosY() + entity.getHeight() * entity.getRNG().nextFloat();
                    entity.world.addParticle(new ParticleCloud.CloudData(ParticleHandler.CLOUD.get(), 0.75f, 0.75f, 1f, 15f, 25, ParticleCloud.EnumCloudBehavior.CONSTANT, 1f), cloudX, cloudY, cloudZ, 0f, -0.01f, 0f);

                    double snowX = entity.getPosX() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                    double snowZ = entity.getPosZ() + entity.getWidth() * entity.getRNG().nextFloat() - entity.getWidth() / 2;
                    double snowY = entity.getPosY() + entity.getHeight() * entity.getRNG().nextFloat();
                    entity.world.addParticle(new ParticleSnowFlake.SnowflakeData(40, false), snowX, snowY, snowZ, 0d, -0.01d, 0d);
                }
            }
            else {
                if (!entity.world.isRemote && getPrevFrozen()) {
                    onUnfreeze(entity);
                }
            }

            if (freezeDecayDelay <= 0) {
                freezeProgress -= 0.1;
                if (freezeProgress < 0) freezeProgress = 0;
            }
            else {
                freezeDecayDelay--;
            }
            prevFrozen = entity.isPotionActive(EffectHandler.FROZEN);
        }

        @Override
        public INBT writeNBT() {
            CompoundNBT compound = new CompoundNBT();
            compound.putFloat("freezeProgress", getFreezeProgress());
            compound.putInt("freezeDecayDelay", getFreezeDecayDelay());
            compound.putFloat("frozenLimbSwingAmount", getFrozenLimbSwingAmount());
            compound.putFloat("frozenRenderYawOffset", getFrozenRenderYawOffset());
            compound.putFloat("frozenSwingProgress", getFrozenSwingProgress());
            compound.putFloat("frozenPitch", getFrozenPitch());
            compound.putFloat("frozenYaw", getFrozenYaw());
            compound.putFloat("frozenYawHead", getFrozenYawHead());
            compound.putBoolean("prevHasAI", prevHasAI());
            if (getPreAttackTarget() != null) {
                compound.putUniqueId("prevAttackTarget", getPreAttackTarget());
            }
            compound.putBoolean("frozen", frozen);
            compound.putBoolean("prevFrozen", prevFrozen);
            return compound;
        }

        @Override
        public void readNBT(INBT nbt) {
            CompoundNBT compound = (CompoundNBT) nbt;
            setFreezeProgress(compound.getFloat("freezeProgress"));
            setFreezeDecayDelay(compound.getInt("freezeDecayDelay"));
            setFrozenLimbSwingAmount(compound.getFloat("frozenLimbSwingAmount"));
            setFrozenRenderYawOffset(compound.getFloat("frozenRenderYawOffset"));
            setFrozenSwingProgress(compound.getFloat("frozenSwingProgress"));
            setFrozenPitch(compound.getFloat("frozenPitch"));
            setFrozenYaw(compound.getFloat("frozenYaw"));
            setFrozenYawHead(compound.getFloat("frozenYawHead"));
            setPrevHasAI(compound.getBoolean("prevHasAI"));
            try {
                setPreAttackTarget(compound.getUniqueId("prevAttackTarget"));
            }
            catch (NullPointerException ignored) {}
            frozen = compound.getBoolean("frozen");
            prevFrozen = compound.getBoolean("prevFrozen");
        }
    }

    public static class FrozenStorage implements Capability.IStorage<IFrozenCapability> {
        @Override
        public INBT writeNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, Direction side) {
            return instance.writeNBT();
        }

        @Override
        public void readNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, Direction side, INBT nbt) {
            instance.readNBT(nbt);
        }
    }

    public static class FrozenProvider implements ICapabilitySerializable<INBT>
    {
        @CapabilityInject(IFrozenCapability.class)
        public static final Capability<IFrozenCapability> FROZEN_CAPABILITY = null;

        private final LazyOptional<IFrozenCapability> instance = LazyOptional.of(FROZEN_CAPABILITY::getDefaultInstance);

        @Override
        public INBT serializeNBT() {
            return FROZEN_CAPABILITY.getStorage().writeNBT(FROZEN_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")), null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            FROZEN_CAPABILITY.getStorage().readNBT(FROZEN_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")), null, nbt);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == FROZEN_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }
    }
}
