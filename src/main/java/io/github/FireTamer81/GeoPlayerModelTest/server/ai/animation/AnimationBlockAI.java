package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;

public class AnimationBlockAI<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
    public AnimationBlockAI(T entity, Animation animation) {
        super(entity, animation);
    }

    @Override
    public void tick() {
        super.tick();
        if (entity != null && entity.blockingEntity != null) {
            entity.faceEntity(entity.blockingEntity, 100, 100);
            entity.getLookController().setLookPositionWithEntity(entity.blockingEntity, 200F, 30F);
        }
    }
}