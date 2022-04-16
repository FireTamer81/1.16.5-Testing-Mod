package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityCameraShake;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.wroughtnaut.EntityWroughtnaut;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;

import java.util.List;

public class AnimationFWNVerticalAttackAI extends AnimationAttackAI<EntityWroughtnaut> {
    private final float arc;

    public AnimationFWNVerticalAttackAI(EntityWroughtnaut entity, Animation animation, SoundEvent sound, float applyKnockback, float range, float arc) {
        super(entity, animation, sound, null, applyKnockback, range, 0, 0);
        this.arc = arc;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        entity.playSound(MMSounds.ENTITY_WROUGHT_PRE_SWING_2.get(), 1.5F, 1F);
    }

    @Override
    public void tick() {
        entity.setMotion(0, entity.getMotion().y, 0);
        if (entity.getAnimationTick() < 21 && entityTarget != null) {
            entity.faceEntity(entityTarget, 30F, 30F);
        }
        else {
            entity.rotationYaw = entity.prevRotationYaw;
        }

        if (entity.getAnimationTick() == 6) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1F);
        } else if (entity.getAnimationTick() == 25) {
            entity.playSound(attackSound, 1.2F, 1);
        } else if (entity.getAnimationTick() == 27) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_SWING_2.get(), 1.5F, 1F);
            List<LivingEntity> entitiesHit = entity.getEntityLivingBaseNearby(range, 3, range, range);
            float damage = (float)entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
            for (LivingEntity entityHit : entitiesHit) {
                float entityHitAngle = (float) ((Math.atan2(entityHit.getPosZ() - entity.getPosZ(), entityHit.getPosX() - entity.getPosX()) * (180 / Math.PI) - 90) % 360);
                float entityAttackingAngle = entity.renderYawOffset % 360;
                if (entityHitAngle < 0) {
                    entityHitAngle += 360;
                }
                if (entityAttackingAngle < 0) {
                    entityAttackingAngle += 360;
                }
                float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
                float entityHitDistance = (float) Math.sqrt((entityHit.getPosZ() - entity.getPosZ()) * (entityHit.getPosZ() - entity.getPosZ()) + (entityHit.getPosX() - entity.getPosX()) * (entityHit.getPosX() - entity.getPosX()));
                if (entityHitDistance <= range && (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2) || (entityRelativeAngle >= 360 - arc / 2 || entityRelativeAngle <= -360 + arc / 2)) {
                    entityHit.attackEntityFrom(DamageSource.causeMobDamage(entity), damage * 1.5F);
                    if (entityHit.isActiveItemStackBlocking()) entityHit.getActiveItemStack().damageItem(400, entityHit, player -> player.sendBreakAnimation(entityHit.getActiveHand()));
                    entityHit.setMotion(entityHit.getMotion().x * applyKnockbackMultiplier, entityHit.getMotion().y, entityHit.getMotion().z * applyKnockbackMultiplier);
                }
            }
        } else if (entity.getAnimationTick() == 28) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_AXE_LAND.get(), 1, 0.5F);
            EntityCameraShake.cameraShake(entity.world, entity.getPositionVec(), 20, 0.3f, 0, 10);
        } else if (entity.getAnimationTick() == 44) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_PULL_1.get(), 1, 1F);
            entity.playSound(MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1F);
        } else if (entity.getAnimationTick() == 75) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_PULL_5.get(), 1, 1F);
        } else if (entity.getAnimationTick() == 83) {
            entity.playSound(MMSounds.ENTITY_WROUGHT_RELEASE_2.get(), 1, 1F);
        }
        if (entity.getAnimationTick() > 26 && entity.getAnimationTick() < 85) {
            entity.vulnerable = true;
            entity.rotationYaw = entity.prevRotationYaw;
            entity.renderYawOffset = entity.prevRenderYawOffset;
        } else {
            entity.vulnerable = false;
        }
    }

    @Override
    public void resetTask() {
        super.resetTask();
        entity.vulnerable = false;
    }
}