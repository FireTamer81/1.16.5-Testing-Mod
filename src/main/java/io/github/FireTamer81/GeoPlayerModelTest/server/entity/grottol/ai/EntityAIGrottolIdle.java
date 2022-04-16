package io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol.ai;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation.SimpleAnimationAI;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol.EntityGrottol;
import net.minecraft.util.SoundEvents;

public class EntityAIGrottolIdle extends SimpleAnimationAI<EntityGrottol> {
    private static final Animation ANIMATION = Animation.create(47);

    public EntityAIGrottolIdle(EntityGrottol entity) {
        super(entity, ANIMATION, false);
    }

    @Override
    public boolean shouldExecute() {
        return entity.getAnimation() == IAnimatedEntity.NO_ANIMATION && entity.getRNG().nextInt(180) == 0;
    }

    @Override
    public void startExecuting() {
        AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, ANIMATION);
        super.startExecuting();
    }

    @Override
    public void tick() {
        super.tick();
        if (entity.getAnimationTick() == 28 || entity.getAnimationTick() == 33) {
            entity.playSound(SoundEvents.BLOCK_STONE_STEP, 0.5F, 1.4F);
        }
    }

    public static Animation animation() {
        return ANIMATION;
    }
}
