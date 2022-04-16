package io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySunstrike;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.IAnimatedEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AnimationSunStrike<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
    protected LivingEntity entityTarget;
    public double prevX;
    public double prevZ;
    private int newX;
    private int newZ;
    private int y;

    public AnimationSunStrike(T entity, Animation animation) {
        super(entity, animation, false);
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        entityTarget = entity.getAttackTarget();
        if (entityTarget != null) {
            prevX = entityTarget.getPosX();
            prevZ = entityTarget.getPosZ();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (entityTarget == null) {
            return;
        }
        if (entity.getAnimationTick() < 9) {
            entity.getLookController().setLookPositionWithEntity(entityTarget, 30, 30);
        }

        if (entity.getAnimationTick() == 7) {
            double x = entityTarget.getPosX();
            y = MathHelper.floor(entityTarget.getPosY() - 1);
            double z = entityTarget.getPosZ();
            double vx = (x - prevX) / 9;
            double vz = (z - prevZ) / 9;
            int t = EntitySunstrike.STRIKE_EXPLOSION + 3;
            newX = MathHelper.floor(x + vx * t);
            newZ = MathHelper.floor(z + vz * t);
            double dx = newX - entity.getPosX();
            double dz = newZ - entity.getPosZ();
            double dist2ToBarako = dx * dx + dz * dz;
            if (dist2ToBarako < 3) {
                newX = MathHelper.floor(entityTarget.getPosX());
                newZ = MathHelper.floor(entityTarget.getPosZ());
            }
            for (int i = 0; i < 5; i++) {
                if (!entity.world.canBlockSeeSky(new BlockPos(newX, y, newZ))) {
                    y++;
                } else {
                    break;
                }
            }
        }
        if (!entity.world.isRemote && entity.getAnimationTick() == 9) {
            entity.playSound(MMSounds.ENTITY_BARAKO_ATTACK.get(), 1.4f, 1);
            EntitySunstrike sunstrike = new EntitySunstrike(EntityHandler.SUNSTRIKE.get(), entity.world, entity, newX, y, newZ);
            sunstrike.onSummon();
            entity.world.addEntity(sunstrike);
        }
        if (entity.getAnimationTick() > 6) {
            entity.getLookController().setLookPosition(newX, y, newZ, 20, 20);
        }
    }
}