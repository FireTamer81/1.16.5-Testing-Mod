package io.github.FireTamer81.GeoPlayerModelTest.server.ability.abilities;

import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilitySection;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityAxeAttack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;

import static io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityAxeAttack.SWING_DURATION_HOR;
import static io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityAxeAttack.SWING_DURATION_VER;

public class WroughtAxeSlamAbility extends Ability {
    private EntityAxeAttack axeAttack;

    public WroughtAxeSlamAbility(AbilityType<WroughtAxeSlamAbility> abilityType, LivingEntity user) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, SWING_DURATION_VER / 2 - 2),
                new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, SWING_DURATION_VER / 2 + 2)
        });
    }

    @Override
    public void start() {
        super.start();
        if (!getUser().world.isRemote()) {
            EntityAxeAttack axeAttack = new EntityAxeAttack(EntityHandler.AXE_ATTACK.get(), getUser().world, getUser(), true);
            axeAttack.setPositionAndRotation(getUser().getPosX(), getUser().getPosY(), getUser().getPosZ(), getUser().rotationYaw, getUser().rotationPitch);
            getUser().world.addEntity(axeAttack);
            this.axeAttack = axeAttack;
        }
        else {
            playAnimation("axe_swing_vertical", false);
            heldItemMainHandVisualOverride = getUser().getHeldItemMainhand();
        }
    }

    @Override
    public void tickUsing() {
        super.tickUsing();
        if (getTicksInUse() == SWING_DURATION_VER && getUser() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) getUser();
            player.resetCooldown();
        }
    }

    @Override
    public void end() {
        super.end();
        if (axeAttack != null) {
            this.axeAttack.remove();
        }
    }

    @Override
    public boolean preventsAttacking() {
        return false;
    }
}
