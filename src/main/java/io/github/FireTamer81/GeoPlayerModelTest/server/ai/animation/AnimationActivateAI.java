package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;

public class AnimationActivateAI<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
    public AnimationActivateAI(T entity, Animation animation) {
        super(entity, animation);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        entity.active = true;
    }
}
