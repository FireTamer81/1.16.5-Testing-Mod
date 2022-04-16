package io.github.FireTamer81.GeoPlayerModelTest.server.entity;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.dynamics.DynamicChain;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityDynamicsTester extends MowzieEntity {
    @OnlyIn(Dist.CLIENT)
    public DynamicChain dc;

    public EntityDynamicsTester(World world) {
        super(EntityHandler.NAGA.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(4, new RandomWalkingGoal(this, 0.3));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    public Animation getDeathAnimation() {
        return null;
    }

    @Override
    public Animation getHurtAnimation() {
        return null;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[0];
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isRemote) {
            if (ticksExisted == 1) {
                dc = new DynamicChain(this);
            }
            dc.updateSpringConstraint(0.1f, 0.3f, 0.6f, 1f, true, 0.5f, 1);
            renderYawOffset = rotationYaw;
        }
    }
}
