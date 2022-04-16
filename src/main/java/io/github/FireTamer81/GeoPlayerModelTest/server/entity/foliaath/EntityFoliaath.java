package io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ControlledAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.IntermittentAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation.AnimationAttackAI;
import io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation.AnimationDieAI;
import io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation.AnimationTakeDamage;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.loot.LootTableHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

public class EntityFoliaath extends MowzieEntity implements IMob {
    public static final Animation DIE_ANIMATION = Animation.create(50);
    public static final Animation HURT_ANIMATION = Animation.create(10);
    public static final Animation ATTACK_ANIMATION = Animation.create(14);
    private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.createKey(EntityFoliaath.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ACTIVATE_TARGET = EntityDataManager.createKey(EntityFoliaath.class, DataSerializers.VARINT);
    private static final int ACTIVATE_DURATION = 30;
    public IntermittentAnimation<EntityFoliaath> openMouth = new IntermittentAnimation<>(this, 15, 30, 50, !world.isRemote);
    public ControlledAnimation activate = new ControlledAnimation(ACTIVATE_DURATION);
    public ControlledAnimation deathFlail = new ControlledAnimation(5);
    public ControlledAnimation stopDance = new ControlledAnimation(10);
    public int lastTimeDecrease = 0;
    private int resettingTargetTimer = 0;
    private double prevOpenMouth;
    private double prevActivate;
    private int activateTarget;

    public EntityFoliaath(EntityType<? extends EntityFoliaath> type, World world) {
        super(type, world);
        this.experienceValue = 5;
        this.addIntermittentAnimation(openMouth);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public PushReaction getPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void addVelocity(double x, double y, double z) {
        super.addVelocity(0, y, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AnimationAttackAI<>(this, ATTACK_ANIMATION, MMSounds.ENTITY_FOLIAATH_BITE_1.get(), null, 2, 4F, 1, 3));
        this.goalSelector.addGoal(1, new AnimationTakeDamage<>(this));
        this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, false, e ->
                        (CreatureEntity.class.isAssignableFrom(e.getClass()) || e instanceof PlayerEntity) && !(e instanceof EntityFoliaath || e instanceof EntityBabyFoliaath || e instanceof CreeperEntity)) {
                    @Override
                    public boolean shouldContinueExecuting() {
                        findNearestTarget();
                        if (nearestTarget != getAttackTarget()) return false;
                        return super.shouldContinueExecuting();
                    }
                }
        );
    }

    @Override
    protected void registerData() {
        super.registerData();
        getDataManager().register(CAN_DESPAWN, true);
        getDataManager().register(ACTIVATE_TARGET, 0);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MowzieEntity.createAttributes().createMutableAttribute(Attributes.ATTACK_DAMAGE, 8)
                .createMutableAttribute(Attributes.MAX_HEALTH, 10)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MMSounds.ENTITY_FOLIAATH_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return MMSounds.ENTITY_FOLIAATH_DIE.get();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        activate.updatePrevTimer();
        deathFlail.updatePrevTimer();
        stopDance.updatePrevTimer();
        openMouth.updatePrevTimer();
        setMotion(0, getMotion().y, 0);
        // Open mouth animation
        if (getAnimation() == NO_ANIMATION && !activate.canIncreaseTimer()) {
            openMouth.update();
        } else {
            openMouth.stop();
        }

        if (activate.getAnimationFraction() >= 0.8F) {
            if (!active) {
                active = true;
            }
        } else if (activate.getAnimationFraction() < 0.8F) {
            if (active) {
                active = false;
            }
        }

        // Sounds
        if (frame % 13 == 3 && getAnimation() != DIE_ANIMATION) {
            if (openMouth.getTimeRunning() >= 10) {
                playSound(MMSounds.ENTITY_FOLIAATH_PANT_1.get(), 1, 1);
            } else if (activate.getTimer() >= 25) {
                playSound(MMSounds.ENTITY_FOLIAATH_PANT_2.get(), 1, 1);
            }
        }

        int openMouthTime = openMouth.getTimeRunning();
        if (prevOpenMouth - openMouthTime < 0) {
            if (openMouthTime == 1) {
                playSound(MMSounds.ENTITY_FOLIAATH_RUSTLE.get(), 1, 1);
            } else if (openMouthTime == 13) {
                playSound(MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 1, 1);
            }
        }

        prevOpenMouth = openMouthTime;

        int activateTime = activate.getTimer();
        if (!world.isRemote) {
            SoundEvent sound = null;
            if (prevActivate - activateTime < 0) {
                switch (activateTime) {
                    case 1:
                        sound = MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
                        break;
                    case 5:
                        sound = MMSounds.ENTITY_FOLIAATH_MERGE.get();
                        break;
                }
            } else if (prevActivate - activateTime > 0) {
                switch (activateTime) {
                    case 24:
                        sound = MMSounds.ENTITY_FOLIAATH_RETREAT.get();
                        break;
                    case 28:
                        sound = MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
                        break;
                }
            }
            if (sound != null) {
                playSound(sound, 1, 1);
            }
        }

        prevActivate = activateTime;

        // Targetting, attacking, and activating
        renderYawOffset = 0;
        rotationYaw = 0;

        if (resettingTargetTimer > 0 && !world.isRemote) {
            rotationYawHead = prevRotationYawHead;
        }

        if (getAttackTarget() != null) {
            rotationYawHead = targetAngle;

            if (targetDistance <= 4 && getAttackTarget().getPosY() - getPosY() >= -1 && getAttackTarget().getPosY() - getPosY() <= 2 && getAnimation() == NO_ANIMATION && active) {
                AnimationHandler.INSTANCE.sendAnimationMessage(this, ATTACK_ANIMATION);
            }

            if (targetDistance <= 10.5 && getAttackTarget().getPosY() - getPosY() >= -1.5 && getAttackTarget().getPosY() - getPosY() <= 2) {
                setActivateTarget(ACTIVATE_DURATION);
                lastTimeDecrease = 0;
            } else if (lastTimeDecrease <= 30 && getAnimation() == NO_ANIMATION) {
                setActivateTarget(0);
                lastTimeDecrease++;
            }
        } else if (!world.isRemote && lastTimeDecrease <= 30 && getAnimation() == NO_ANIMATION && resettingTargetTimer == 0) {
            setActivateTarget(0);
            lastTimeDecrease++;
        }

        if (getAnimation() == DIE_ANIMATION) {
            if (getAnimationTick() <= 12) {
                deathFlail.increaseTimer();
            } else {
                deathFlail.decreaseTimer();
            }
            stopDance.increaseTimer();
            setActivateTarget(ACTIVATE_DURATION);
        }

        if (resettingTargetTimer > 0) {
            resettingTargetTimer--;
        }

        if (activateTarget == activateTime) {
            activateTarget = getActivateTarget();
        } else if (activateTime < activateTarget && activate.canIncreaseTimer() || activateTime > activateTarget && activate.canDecreaseTimer()) {
            activate.increaseTimer(activateTime < activateTarget ? 1 : -2);
        }

        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL)
        {
            this.remove();
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        openMouth.resetTimeRunning();
        return (damageSource.canHarmInCreative() || active) && super.attackEntityFrom(damageSource, amount);
    }

    @Override
    public Animation getDeathAnimation() {
        return DIE_ANIMATION;
    }

    @Override
    public Animation getHurtAnimation() {
        return HURT_ANIMATION;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected ConfigHandler.SpawnConfig getSpawnConfig() {
        return ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig;
    }

    @Override
    protected ConfigHandler.CombatConfig getCombatConfig() {
        return ConfigHandler.COMMON.MOBS.FOLIAATH.combatConfig;
    }

    @Override
    public boolean canSpawn(IWorld world, SpawnReason reason) {
        Biome biome = world.getBiome(getPosition());
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(this.getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        Block floor = world.getBlockState(pos.down()).getBlock();
        BlockState floorDown1 = world.getBlockState(pos.down(2));
        BlockState floorDown2 = world.getBlockState(pos.down(3));
        boolean notInTree = true;
        BlockState topBlock = biome.getGenerationSettings().getSurfaceBuilder().get().getConfig().getTop();
        if (floor instanceof LeavesBlock && floorDown1 != topBlock && floorDown2 != topBlock) notInTree = false;
        return super.canSpawn(world, reason) && notInTree && getEntitiesNearby(AnimalEntity.class, 5, 5, 5, 5).isEmpty() && world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public void onKillEntity(ServerWorld world, LivingEntity killedEntity) {
        this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 300, 1, true, true));
        super.onKillEntity(world, killedEntity);
    }

    @Override
    public boolean preventDespawn() {
        return !getDataManager().get(CAN_DESPAWN);
    }

    public void setCanDespawn(boolean canDespawn) {
        getDataManager().set(CAN_DESPAWN, canDespawn);
    }

    public int getActivateTarget() {
        return getDataManager().get(ACTIVATE_TARGET);
    }

    public void setActivateTarget(int activateTarget) {
        getDataManager().set(ACTIVATE_TARGET, activateTarget);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("canDespawn", getDataManager().get(CAN_DESPAWN));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setCanDespawn(compound.getBoolean("canDespawn"));
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {

    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{DIE_ANIMATION, HURT_ANIMATION, ATTACK_ANIMATION};
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.FOLIAATH;
    }
}
