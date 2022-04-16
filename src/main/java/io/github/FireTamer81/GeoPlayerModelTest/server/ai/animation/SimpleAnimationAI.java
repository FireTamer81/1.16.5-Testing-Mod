package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;

import java.util.EnumSet;

public class SimpleAnimationAI<T extends MowzieEntity & IAnimatedEntity> extends AnimationAI<T> {
    protected final Animation animation;

    public SimpleAnimationAI(T entity, Animation animation) {
        super(entity);
        this.animation = animation;
    }

    public SimpleAnimationAI(T entity, Animation animation, boolean interruptsAI) {
        super(entity, interruptsAI);
        this.animation = animation;
    }

    public SimpleAnimationAI(T entity, Animation animation, boolean interruptsAI, boolean hurtInterruptsAnimation) {
        super(entity, interruptsAI, hurtInterruptsAnimation);
        this.animation = animation;
    }

    public SimpleAnimationAI(T entity, Animation animation, EnumSet<Flag> interruptFlagTypes) {
        super(entity);
        this.animation = animation;
        setMutexFlags(interruptFlagTypes);
    }

    @Override
    protected boolean test(Animation animation) {
        return animation == this.animation;
    }
}