package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.SoundEvent;

public class AnimationProjectileAttackAI<T extends MowzieEntity & IAnimatedEntity & IRangedAttackMob> extends SimpleAnimationAI<T> {
    private final int attackFrame;
    private final SoundEvent attackSound;

    public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound) {
        this(entity, animation, attackFrame, attackSound, false);
    }

    public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound, boolean hurtInterrupts) {
        super(entity, animation, true, hurtInterrupts);
        this.attackFrame = attackFrame;
        this.attackSound = attackSound;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity entityTarget = entity.getAttackTarget();
        if (entityTarget != null) {
            entity.faceEntity(entityTarget, 100, 100);
            entity.getLookController().setLookPositionWithEntity(entityTarget, 30F, 30F);
            if (entity.getAnimationTick() == attackFrame) {
                entity.attackEntityWithRangedAttack(entityTarget, 0);
                entity.playSound(attackSound, 1, 1);
            }
        }
    }
}