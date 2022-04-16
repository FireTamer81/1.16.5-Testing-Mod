package io.github.FireTamer81.GeoPlayerModelTest.server.ability.abilities;

import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilitySection;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;

public class SimpleAnimationAbility extends Ability {
    private String animationName;
    private boolean separateLeftAndRight;
    private boolean lockHeldItemMainHand;

    public SimpleAnimationAbility(AbilityType<SimpleAnimationAbility> abilityType, LivingEntity user, String animationName, int duration) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
        });
        this.animationName = animationName;
    }

    public SimpleAnimationAbility(AbilityType<SimpleAnimationAbility> abilityType, LivingEntity user, String animationName, int duration, boolean separateLeftAndRight, boolean lockHeldItemMainHand) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, duration)
        });
        this.animationName = animationName;
        this.separateLeftAndRight = separateLeftAndRight;
        this.lockHeldItemMainHand = lockHeldItemMainHand;
    }

    @Override
    public void start() {
        super.start();
        if (separateLeftAndRight) {
            boolean handSide = getUser().getPrimaryHand() == HandSide.RIGHT;
            playAnimation(animationName + "_" + (handSide ? "right" : "left"), GeckoPlayer.Perspective.THIRD_PERSON, false);
            playAnimation(animationName, GeckoPlayer.Perspective.FIRST_PERSON, false);
        }
        else {
            playAnimation(animationName, false);
        }
        if (lockHeldItemMainHand)
            heldItemMainHandVisualOverride = getUser().getHeldItemMainhand();
    }
}