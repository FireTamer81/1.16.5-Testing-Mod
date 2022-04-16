package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.LeaderSunstrikeImmune;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarako;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;

import java.util.EnumSet;
import java.util.List;

public class AnimationRadiusAttack<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
    private final float radius;
    private final float damageMultiplier;
    private final float applyKnockbackMultiplier;
    private final int damageFrame;
    private final boolean pureapplyKnockback;

    public AnimationRadiusAttack(T entity, Animation animation, float radius, float damageMultiplier, float applyKnockbackMultiplier, int damageFrame, boolean pureapplyKnockback) {
        super(entity, animation);
        this.radius = radius;
        this.damageMultiplier = damageMultiplier;
        this.applyKnockbackMultiplier = applyKnockbackMultiplier;
        this.damageFrame = damageFrame;
        this.pureapplyKnockback = pureapplyKnockback;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override
    public void tick() {
        super.tick();
        if (entity.getAnimationTick() == damageFrame) {
            List<LivingEntity> hit = entity.getEntityLivingBaseNearby(radius, 2 * radius, radius, radius);
            for (LivingEntity aHit : hit) {
                if (entity instanceof EntityBarako && aHit instanceof LeaderSunstrikeImmune) {
                    continue;
                }
                entity.attackEntityAsMob(aHit, damageMultiplier, applyKnockbackMultiplier);
                if (pureapplyKnockback && !aHit.isInvulnerable()) {
                    if (aHit instanceof PlayerEntity && ((PlayerEntity)aHit).abilities.disableDamage) continue;
                    double angle = entity.getAngleBetweenEntities(entity, aHit);
                    double x = applyKnockbackMultiplier * Math.cos(Math.toRadians(angle - 90));
                    double z = applyKnockbackMultiplier * Math.sin(Math.toRadians(angle - 90));
                    aHit.setMotion(x, 0.3, z);
                    if (aHit instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) aHit).connection.sendPacket(new SEntityVelocityPacket(aHit));
                    }
                }
            }
        }
    }
}