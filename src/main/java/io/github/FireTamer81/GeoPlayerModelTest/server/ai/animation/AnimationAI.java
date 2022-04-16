package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public abstract class AnimationAI<T extends MowzieEntity & IAnimatedEntity> extends Goal {
    protected final T entity;

    protected final boolean hurtInterruptsAnimation;

    protected AnimationAI(T entity) {
        this(entity, true, false);
    }

    protected AnimationAI(T entity, boolean interruptsAI) {
        this(entity, interruptsAI, false);
    }

    protected AnimationAI(T entity, boolean interruptsAI, boolean hurtInterruptsAnimation) {
        this.entity = entity;
        if (interruptsAI) this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.hurtInterruptsAnimation = hurtInterruptsAnimation;
    }

    @Override
    public boolean shouldExecute() {
        return this.test(this.entity.getAnimation());
    }

    @Override
    public void startExecuting() {
        this.entity.hurtInterruptsAnimation = this.hurtInterruptsAnimation;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.test(this.entity.getAnimation()) && this.entity.getAnimationTick() < this.entity.getAnimation().getDuration();
    }

    @Override
    public void resetTask() {
        if (this.test(this.entity.getAnimation())) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, IAnimatedEntity.NO_ANIMATION);
        }
    }

    protected abstract boolean test(Animation animation);
}