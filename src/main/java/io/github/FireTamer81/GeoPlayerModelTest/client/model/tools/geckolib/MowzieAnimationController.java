package io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.IAnimationTickable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

public class MowzieAnimationController<T extends IAnimatable & IAnimationTickable> extends AnimationController<T> {
    private double tickOffset;

    public MowzieAnimationController(T animatable, String name, float transitionLengthTicks, IAnimationPredicate<T> animationPredicate) {
        super(animatable, name, transitionLengthTicks, animationPredicate);
        tickOffset = 0.0d;
    }

    public void playAnimation(T animatable, AnimationBuilder animationBuilder) {
        markNeedsReload();
        setAnimation(animationBuilder);
        currentAnimation = this.animationQueue.poll();
        isJustStarting = true;
        adjustTick(animatable.tickTimer());
        transitionLengthTicks = 0;
    }

    protected void adjustTick(double tick) { //TODO This might need to have something funky since the source had it as an override protected geckolib thing
        if (this.shouldResetTick) {
            if (getAnimationState() == AnimationState.Transitioning) {
                this.tickOffset = tick;
            }
            else if (getAnimationState() == AnimationState.Running) {
                this.tickOffset += transitionLengthTicks;
            }
            this.shouldResetTick = false;
        }
    }
}