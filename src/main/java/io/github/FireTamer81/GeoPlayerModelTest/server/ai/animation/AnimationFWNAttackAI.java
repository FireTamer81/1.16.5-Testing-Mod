package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.wroughtnaut.EntityWroughtnaut;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

import javax.management.Attribute;
import java.util.List;

public class AnimationFWNAttackAI extends AnimationAI<EntityWroughtnaut> {
    protected float applyKnockback = 1;
    protected float range;
    private final float arc;

    public AnimationFWNAttackAI(EntityWroughtnaut entity, float applyKnockback, float range, float arc) {
        super(entity);
        this.applyKnockback = applyKnockback;
        this.range = range;
        this.arc = arc;
    }

    @Override
    protected boolean test(Animation animation) {
        return animation == EntityWroughtnaut.ATTACK_ANIMATION || animation == EntityWroughtnaut.ATTACK_TWICE_ANIMATION || animation == EntityWroughtnaut.ATTACK_THRICE_ANIMATION;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        if (entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) entity.playSound(MMSounds.ENTITY_WROUGHT_PRE_SWING_1.get(), 1.5F, 1F);
    }

    @Override
    public void resetTask() {
        super.resetTask();
    }

    private boolean shouldFollowUp(float bonusRange) {
        LivingEntity entityTarget = entity.getAttackTarget();
        if (entityTarget != null && entityTarget.isAlive()) {
            Vector3d targetMoveVec = entityTarget.getMotion();
            Vector3d betweenEntitiesVec = entity.getPositionVec().subtract(entityTarget.getPositionVec());
            boolean targetComingCloser = targetMoveVec.dotProduct(betweenEntitiesVec) > 0;
            return entity.targetDistance < range + bonusRange || (entity.targetDistance < range + 5 + bonusRange && targetComingCloser);
        }
        return false;
    }

    @Override
    public void tick() {
        LivingEntity entityTarget = entity.getAttackTarget();
        entity.setMotion(0, entity.getMotion().y, 0);
        if (entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) {
            if (entity.getAnimationTick() < 23 && entityTarget != null) {
                entity.faceEntity(entityTarget, 30F, 30F);
            } else {
                entity.rotationYaw = entity.prevRotationYaw;
            }
            if (entity.getAnimationTick() == 6) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1);
            } else if (entity.getAnimationTick() == 25) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1);
            } else if (entity.getAnimationTick() == 27) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_SWING_1.get(), 1.5F, 1);
                List<LivingEntity> entitiesHit = entity.getEntityLivingBaseNearby(range, 3, range, range);
                float damage = (float)entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                boolean hit = false;
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
                    float entityHitDistance = (float) Math.sqrt((entityHit.getPosZ() - entity.getPosZ()) * (entityHit.getPosZ() - entity.getPosZ()) + (entityHit.getPosX() - entity.getPosX()) * (entityHit.getPosX() - entity.getPosX())) - entityHit.getWidth() / 2f;
                    if (entityHitDistance <= range && (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2) || (entityRelativeAngle >= 360 - arc / 2 || entityRelativeAngle <= -360 + arc / 2)) {
                        entityHit.attackEntityFrom(DamageSource.causeMobDamage(entity), damage);
                        if (entityHit.isActiveItemStackBlocking())
                            entityHit.getActiveItemStack().damageItem(400, entityHit, player -> player.sendBreakAnimation(entityHit.getActiveHand()));
                        entityHit.setMotion(entityHit.getMotion().x * applyKnockback, entityHit.getMotion().y, entityHit.getMotion().z * applyKnockback);
                        hit = true;
                    }
                }
                if (hit) {
                    entity.playSound(MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1, 0.5F);
                }
            } else if (entity.getAnimationTick() == 37 && shouldFollowUp(2.5f) && entity.getHealthRatio() <= 0.9 && entity.getRNG().nextFloat() < 0.6F) {
                AnimationHandler.INSTANCE.sendAnimationMessage(entity, EntityWroughtnaut.ATTACK_TWICE_ANIMATION);
            }
        }
        else if (entity.getAnimation() == EntityWroughtnaut.ATTACK_TWICE_ANIMATION) {
            if (entity.getAnimationTick() < 7 && entityTarget != null) {
                entity.faceEntity(entityTarget, 30F, 30F);
            } else {
                entity.rotationYaw = entity.prevRotationYaw;
            }
            if (entity.getAnimationTick() == 10) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1);
            }
            else if (entity.getAnimationTick() == 12) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_SWING_3.get(), 1.5F, 1);
                List<LivingEntity> entitiesHit = entity.getEntityLivingBaseNearby(range - 0.3, 3, range - 0.3, range - 0.3);
                float damage = (float)entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                boolean hit = false;
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
                    if (entityHitDistance <= range - 0.3 && (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2) || (entityRelativeAngle >= 360 - arc / 2 || entityRelativeAngle <= -360 + arc / 2)) {
                        entityHit.attackEntityFrom(DamageSource.causeMobDamage(entity), damage);
                        if (entityHit.isActiveItemStackBlocking())
                            entityHit.getActiveItemStack().damageItem(400, entityHit, player -> player.sendBreakAnimation(entityHit.getActiveHand()));
                        entityHit.setMotion(entityHit.getMotion().x * applyKnockback, entityHit.getMotion().y, entityHit.getMotion().z * applyKnockback);
                        hit = true;
                    }
                }
                if (hit) {
                    entity.playSound(MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1, 0.5F);
                }
            } else if (entity.getAnimationTick() == 23 && shouldFollowUp(3.5f) && entity.getHealthRatio() <= 0.6 && entity.getRNG().nextFloat() < 0.6f) {
                AnimationHandler.INSTANCE.sendAnimationMessage(entity, EntityWroughtnaut.ATTACK_THRICE_ANIMATION);
            }
        }
        else if (entity.getAnimation() == EntityWroughtnaut.ATTACK_THRICE_ANIMATION) {
            if (entity.getAnimationTick() == 1) entity.playSound(MMSounds.ENTITY_WROUGHT_PRE_SWING_3.get(), 1.2F, 1f);
            if (entity.getAnimationTick() < 22 && entityTarget != null) {
                entity.faceEntity(entityTarget, 30F, 30F);
            } else {
                entity.rotationYaw = entity.prevRotationYaw;
            }
            if (entity.getAnimationTick() == 20) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 0.9f);
            } else if (entity.getAnimationTick() == 24) {
                entity.playSound(MMSounds.ENTITY_WROUGHT_GRUNT_3.get(), 1.5F, 1.13f);
                entity.move(MoverType.SELF, new Vector3d(Math.cos(Math.toRadians(entity.rotationYaw + 90)), 0, Math.sin(Math.toRadians(entity.rotationYaw + 90))));
                List<LivingEntity> entitiesHit = entity.getEntityLivingBaseNearby(range + 0.2, 3, range + 0.2, range + 0.2);
                float damage = (float)entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                boolean hit = false;
                for (LivingEntity entityHit : entitiesHit) {
                    float entityHitDistance = (float) Math.sqrt((entityHit.getPosZ() - entity.getPosZ()) * (entityHit.getPosZ() - entity.getPosZ()) + (entityHit.getPosX() - entity.getPosX()) * (entityHit.getPosX() - entity.getPosX()));
                    if (entityHitDistance <= range + 0.2) {
                        entityHit.attackEntityFrom(DamageSource.causeMobDamage(entity), damage);
                        if (entityHit.isActiveItemStackBlocking())
                            entityHit.getActiveItemStack().damageItem(400, entityHit, player -> player.sendBreakAnimation(entityHit.getActiveHand()));
                        entityHit.setMotion(entityHit.getMotion().x * applyKnockback, entityHit.getMotion().y, entityHit.getMotion().z * applyKnockback);
                        hit = true;
                    }
                }
                if (hit) {
                    entity.playSound(MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1, 0.5F);
                }
            }
        }
    }
}