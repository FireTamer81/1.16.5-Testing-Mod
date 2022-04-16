package io.github.FireTamer81.GeoPlayerModelTest.server.entity.sculptor;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.IAnimationTickable;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySculptor extends MowzieEntity implements IAnimatable, IAnimationTickable {

    public boolean handLOpen = true;
    public boolean handROpen = true;

    private AnimationFactory factory = new AnimationFactory(this);

    public EntitySculptor(EntityType<? extends MowzieEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MowzieEntity.createAttributes().createMutableAttribute(Attributes.ATTACK_DAMAGE, 10)
                .createMutableAttribute(Attributes.MAX_HEALTH, 40)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("testStart", true));
        return PlayState.CONTINUE;
    }

    private <ENTITY extends IAnimatable> void instructionListener(CustomInstructionKeyframeEvent<ENTITY> event) {
        if (event.instructions.contains("closeHandR")) {
            handROpen = false;
        }
        if (event.instructions.contains("closeHandL")) {
            handLOpen = false;
        }
        if (event.instructions.contains("openHandR")) {
            handROpen = true;
        }
        if (event.instructions.contains("openHandL")) {
            handLOpen = true;
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, "controller", 10, this::predicate);
        controller.registerCustomInstructionListener(this::instructionListener);
        data.addAnimationController(controller);

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void tick() {
        super.tick();

    }
}
