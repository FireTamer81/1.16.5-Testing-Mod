package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;

import java.util.EnumSet;

public class AnimationTakeDamage<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
    public AnimationTakeDamage(T entity) {
        super(entity, entity.getHurtAnimation());
        setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }
}