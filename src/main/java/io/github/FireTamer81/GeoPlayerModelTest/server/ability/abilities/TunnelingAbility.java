package io.github.FireTamer81.GeoPlayerModelTest.server.ability.abilities;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.geckolib.MowzieGeoBone;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.AdvancedParticleBase;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.ParticleComponent;
import io.github.FireTamer81.GeoPlayerModelTest.client.render.entity.player.GeckoPlayer;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilitySection;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityBlockSwapper;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityFallingBlock;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.objects.ItemEarthboreGauntlet;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.effects.EffectGeomancy;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.List;

public class TunnelingAbility extends Ability {
    private int doubleTapTimer = 0;
    public boolean prevUnderground;
    public BlockState justDug = Blocks.DIRT.getDefaultState();
    boolean underground = false;

    private float spinAmount = 0;
    private float pitch = 0;

    private int timeUnderground = 0;
    private int timeAboveGround = 0;

    public TunnelingAbility(AbilityType<? extends Ability> abilityType, LivingEntity user) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE)
        });
    }

    @Override
    public void tickNotUsing() {
        super.tickNotUsing();
        if (doubleTapTimer > 0) doubleTapTimer--;
    }

    public void playGauntletAnimation() {
        if (getUser() instanceof PlayerEntity) {
            ItemStack stack = getUser().getActiveItemStack();
            if (stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET) {
                PlayerEntity player = (PlayerEntity) getUser();
                ItemHandler.EARTHBORE_GAUNTLET.playAnimation(player, stack, ItemEarthboreGauntlet.ANIM_OPEN);
            }
        }
    }

    public void stopGauntletAnimation() {
        if (getUser() instanceof PlayerEntity) {
            ItemStack[] stacks = new ItemStack[] {getUser().getHeldItemMainhand(), getUser().getHeldItemOffhand()};
            for (ItemStack stack : stacks) {
                if (stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET) {
                    PlayerEntity player = (PlayerEntity) getUser();
                    ItemHandler.EARTHBORE_GAUNTLET.playAnimation(player, stack, ItemEarthboreGauntlet.ANIM_REST);
                }
            }
        }
    }

    @Override
    public void start() {
        super.start();
        underground = false;
        prevUnderground = false;
        if (getUser().isOnGround()) getUser().addVelocity(0, 0.8f, 0);
        if (getUser().world.isRemote()) {
            spinAmount = 0;
            pitch = 0;
        }
    }

    public boolean damageGauntlet() {
        ItemStack stack = getUser().getActiveItemStack();
        if (stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET) {
            Hand handIn = getUser().getActiveHand();
            if (stack.getDamage() + 5 < stack.getMaxDamage()) {
                stack.damageItem(5, getUser(), p -> p.sendBreakAnimation(handIn));
                return true;
            }
            else {
                if (ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
                    stack.damageItem(5, getUser(), p -> p.sendBreakAnimation(handIn));
                }
                return false;
            }
        }
        return false;
    }

    public void restoreGauntlet(ItemStack stack) {
        if (stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET) {
            if (!ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
                stack.setDamage(Math.max(stack.getDamage() - 1, 0));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!isUsing() && getUser() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) getUser();
            for (ItemStack stack : player.inventory.mainInventory) {
                restoreGauntlet(stack);
            }
            for (ItemStack stack : player.inventory.offHandInventory) {
                restoreGauntlet(stack);
            }
        }
    }

    @Override
    public void tickUsing() {
        super.tickUsing();
        getUser().fallDistance = 0;
        if (getUser() instanceof PlayerEntity) ((PlayerEntity)getUser()).abilities.isFlying = false;
        underground = !getUser().world.getEntitiesWithinAABB(EntityBlockSwapper.class, getUser().getBoundingBox().grow(0.3)).isEmpty();
        Vector3d lookVec = getUser().getLookVec();
        float tunnelSpeed = 0.3f;
        ItemStack stack = getUser().getActiveItemStack();
        boolean usingGauntlet = stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET;
        if (underground) {
            timeUnderground++;
            if (usingGauntlet && damageGauntlet()) {
                getUser().setMotion(lookVec.normalize().scale(tunnelSpeed));
            }
            else {
                getUser().setMotion(lookVec.mul(0.3, 0, 0.3).add(0, 1, 0).normalize().scale(tunnelSpeed));
            }

            List<LivingEntity> entitiesHit = getEntityLivingBaseNearby(getUser(),2, 2, 2, 2);
            for (LivingEntity entityHit : entitiesHit) {
                DamageSource damageSource = DamageSource.causeMobDamage(getUser());
                if (getUser() instanceof PlayerEntity) damageSource = DamageSource.causePlayerDamage((PlayerEntity) getUser());
                entityHit.attackEntityFrom(damageSource, 6 * ConfigHandler.COMMON.TOOLS_AND_ABILITIES.geomancyAttackMultiplier.get().floatValue());
            }
        }
        else {
            timeAboveGround++;
            getUser().setMotion(getUser().getMotion().subtract(0, 0.07, 0));
            if (getUser().getMotion().getY() < -1.3) getUser().setMotion(getUser().getMotion().getX(), -1.3, getUser().getMotion().getZ());
        }

        if ((underground && (prevUnderground || lookVec.y < 0) && timeAboveGround > 5) || (getTicksInUse() > 1 && usingGauntlet && lookVec.y < 0 && stack.getDamage() + 5 < stack.getMaxDamage())) {
            if (getUser().ticksExisted % 16 == 0) getUser().playSound(MMSounds.EFFECT_GEOMANCY_RUMBLE.get(rand.nextInt(3)).get(), 0.6f, 0.5f + rand.nextFloat() * 0.2f);
            Vector3d userCenter = getUser().getPositionVec().add(0, getUser().getHeight() / 2f, 0);
            float radius = 2f;
            AxisAlignedBB aabb = new AxisAlignedBB(-radius, -radius, -radius, radius, radius, radius);
            aabb = aabb.offset(userCenter);
            for (int i = 0; i < getUser().getMotion().length() * 4; i++) {
                for (int x = (int) Math.floor(aabb.minX); x <= Math.floor(aabb.maxX); x++) {
                    for (int y = (int) Math.floor(aabb.minY); y <= Math.floor(aabb.maxY); y++) {
                        for (int z = (int) Math.floor(aabb.minZ); z <= Math.floor(aabb.maxZ); z++) {
                            Vector3d posVec = new Vector3d(x, y, z);
                            if (posVec.add(0.5, 0.5, 0.5).subtract(userCenter).lengthSquared() > radius * radius) continue;
                            Vector3d motionScaled = getUser().getMotion().normalize().scale(i);
                            posVec = posVec.add(motionScaled);
                            BlockPos pos = new BlockPos(posVec);
                            BlockState blockState = getUser().world.getBlockState(pos);
                            if (EffectGeomancy.isBlockDiggable(blockState) && blockState.getBlock() != Blocks.BEDROCK) {
                                justDug = blockState;
                                EntityBlockSwapper.swapBlock(getUser().world, pos, Blocks.AIR.getDefaultState(), 15, false, false);
                            }
                        }
                    }
                }
            }
        }
        if (!prevUnderground && underground) {
            timeUnderground = 0;
            getUser().playSound(MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM.get(rand.nextInt(3)).get(), 1f, 0.9f + rand.nextFloat() * 0.1f);
            if (getUser().world.isRemote)
                AdvancedParticleBase.spawnParticle(getUser().world, ParticleHandler.RING2.get(), (float) getUser().getPosX(), (float) getUser().getPosY() + 0.02f, (float) getUser().getPosZ(), 0, 0, 0, false, 0, Math.PI/2f, 0, 0, 3.5F, 0.83f, 1, 0.39f, 1, 1, 10, true, true, new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1f, 0f), false),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10f, 30f), false)
                });
            playGauntletAnimation();
        }
        if (prevUnderground && !underground) {
            timeAboveGround = 0;
            getUser().playSound(MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1f, 0.9f + rand.nextFloat() * 0.1f);
            if (getUser().world.isRemote)
                AdvancedParticleBase.spawnParticle(getUser().world, ParticleHandler.RING2.get(), (float) getUser().getPosX(), (float) getUser().getPosY() + 0.02f, (float) getUser().getPosZ(), 0, 0, 0, false, 0, Math.PI/2f, 0, 0, 3.5F, 0.83f, 1, 0.39f, 1, 1, 10, true, true, new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1f, 0f), false),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(10f, 30f), false)
                });
            if (timeUnderground > 10)
                getUser().setMotion(getUser().getMotion().scale(10f));
            else
                getUser().setMotion(getUser().getMotion().mul(3, 7, 3));

            for (int i = 0; i < 6; i++) {
                if (justDug == null) justDug = Blocks.DIRT.getDefaultState();
                EntityFallingBlock fallingBlock = new EntityFallingBlock(EntityHandler.FALLING_BLOCK.get(), getUser().world, 80, justDug);
                fallingBlock.setPosition(getUser().getPosX(), getUser().getPosY() + 1, getUser().getPosZ());
                fallingBlock.setMotion(getUser().getRNG().nextFloat() * 0.8f - 0.4f, 0.4f + getUser().getRNG().nextFloat() * 0.8f, getUser().getRNG().nextFloat() * 0.8f - 0.4f);
                getUser().world.addEntity(fallingBlock);
            }
            stopGauntletAnimation();
        }
        prevUnderground = underground;
    }

    @Override
    public void end() {
        super.end();
        stopGauntletAnimation();
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    protected boolean canContinueUsing() {
        boolean canContinueUsing = (getTicksInUse() <= 1 || !getUser().isOnGround() || underground) && super.canContinueUsing();
        return canContinueUsing;
    }

    @Override
    public boolean preventsItemUse(ItemStack stack) {
        if (stack.getItem() == ItemHandler.EARTHBORE_GAUNTLET) return false;
        return super.preventsItemUse(stack);
    }

    @Override
    public <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> e, GeckoPlayer.Perspective perspective) {
        e.getController().transitionLengthTicks = 4;
        if (perspective == GeckoPlayer.Perspective.THIRD_PERSON) {
            float yMotionThreshold = getUser() == Minecraft.getInstance().player ? 1 : 2;
            if (!underground && getUser().getActiveItemStack().getItem() != ItemHandler.EARTHBORE_GAUNTLET && getUser().getMotion().getY() < yMotionThreshold) {
                e.getController().setAnimation(new AnimationBuilder().addAnimation("tunneling_fall", false));
            }
            else {
                e.getController().setAnimation(new AnimationBuilder().addAnimation("tunneling_drill", true));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void codeAnimations(MowzieAnimatedGeoModel<? extends IAnimatable> model, float partialTick) {
        super.codeAnimations(model, partialTick);
        float faceMotionController = 1f - model.getControllerValue("FaceVelocityController");
        Vector3d moveVec = getUser().getMotion().normalize();
        pitch = (float) MathHelper.lerp(0.3 * partialTick, pitch, moveVec.getY());
        MowzieGeoBone com = model.getMowzieBone("CenterOfMass");
        com.setRotationX((float) (-Math.PI/2f + Math.PI/2f * pitch) * faceMotionController);

        float spinSpeed = 0.35f;
        if (faceMotionController < 1 && spinAmount < Math.PI * 2f - 0.01 && spinAmount > 0.01) {
            float f = (float) ((Math.PI * 2f - spinAmount) / (Math.PI * 2f));
            f = (float) Math.pow(f, 0.5);
            spinAmount += partialTick * spinSpeed * f;
            if (spinAmount > Math.PI * 2f) {
                spinAmount = 0;
            }
        }
        else {
            spinAmount += faceMotionController * partialTick * spinSpeed;
            spinAmount = (float) (spinAmount % (Math.PI * 2));
        }
        MowzieGeoBone waist = model.getMowzieBone("Waist");
        waist.addRotationY(-spinAmount);
    }
}
