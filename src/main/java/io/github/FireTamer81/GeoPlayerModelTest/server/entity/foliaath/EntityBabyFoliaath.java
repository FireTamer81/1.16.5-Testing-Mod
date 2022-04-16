package io.github.FireTamer81.GeoPlayerModelTest.server.entity.foliaath;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.ControlledAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.server.ai.animation.AnimationBabyFoliaathEatAI;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.Animation;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Food;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityBabyFoliaath extends MowzieEntity {
    private static final DataParameter<Integer> GROWTH = EntityDataManager.createKey(EntityBabyFoliaath.class, DataSerializers.VARINT);

    private static final DataParameter<Boolean> INFANT = EntityDataManager.createKey(EntityBabyFoliaath.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> HUNGRY = EntityDataManager.createKey(EntityBabyFoliaath.class, DataSerializers.BOOLEAN);

    private static final DataParameter<ItemStack> EATING = EntityDataManager.createKey(EntityBabyFoliaath.class, DataSerializers.ITEMSTACK);

    public static final Animation EAT_ANIMATION = Animation.create(20);
    public ControlledAnimation activate = new ControlledAnimation(5);
    private double prevActivate;

    public EntityBabyFoliaath(EntityType<? extends EntityBabyFoliaath> type, World world) {
        super(type, world);
        setInfant(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AnimationBabyFoliaathEatAI<EntityBabyFoliaath>(this, EAT_ANIMATION));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MowzieEntity.createAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 1)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void addVelocity(double x, double y, double z) {
        super.addVelocity(0, y, 0);
    }

    @Override
    public void tick() {
        super.tick();
        setMotion(0, getMotion().y, 0);
        renderYawOffset = 0;

        if (arePlayersCarryingMeat(getPlayersNearby(3, 3, 3, 3)) && getAnimation() == NO_ANIMATION && getHungry()) {
            activate.increaseTimer();
        } else {
            activate.decreaseTimer();
        }

        if (activate.getTimer() == 1 && prevActivate - activate.getTimer() < 0) {
            playSound(MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 0.5F, 1.5F);
        }
        prevActivate = activate.getTimer();

        if (!world.isRemote && getHungry() && getAnimation() == NO_ANIMATION) {
            for (ItemEntity meat : getMeatsNearby(0.4, 0.2, 0.4, 0.4)) {
                ItemStack stack = meat.getItem().split(1);
                if (!stack.isEmpty()) {
                    setEating(stack);
                    AnimationHandler.INSTANCE.sendAnimationMessage(this, EAT_ANIMATION);
                    playSound(MMSounds.ENTITY_FOLIAATH_BABY_EAT.get(), 0.5F, 1.2F);
                    incrementGrowth();
                    setHungry(false);
                    break;
                }
            }
        }
        if (world.isRemote && getAnimation() == EAT_ANIMATION && (getAnimationTick() == 3 || getAnimationTick() == 7 || getAnimationTick() == 11 || getAnimationTick() == 15 || getAnimationTick() == 19)) {
            for (int i = 0; i <= 5; i++) {
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, getEating()), getPosX(), getPosY() + 0.2, getPosZ(), rand.nextFloat() * 0.2 - 0.1, rand.nextFloat() * 0.2, rand.nextFloat() * 0.2 - 0.1);
            }
        }

        //Growing
        if (!world.isRemote) {
            if (ticksExisted % 20 == 0 && !getHungry()) {
                incrementGrowth();
            }
            // TODO: cleanup this poor logic
            setInfant(getGrowth() < 600);
            if (getInfant()) {
                setHungry(false);
            }
            if (getGrowth() == 600) {
                setHungry(true);
            }
            if (getGrowth() == 1200) {
                setHungry(true);
            }
            if (getGrowth() == 1800) {
                setHungry(true);
            }
            if (getGrowth() == 2400) {
                EntityFoliaath adultFoliaath = new EntityFoliaath(EntityHandler.FOLIAATH.get(), world);
                adultFoliaath.setPosition(getPosX(), getPosY(), getPosZ());
                adultFoliaath.setCanDespawn(false);
                world.addEntity(adultFoliaath);
                remove();
            }
        }
    }

    @Override
    public Animation getDeathAnimation() {
        return null;
    }

    @Override
    public Animation getHurtAnimation() {
        return null;
    }

    private boolean arePlayersCarryingMeat(List<PlayerEntity> players) {
        if (players.size() > 0) {
            for (PlayerEntity player : players) {
                Food food = player.getHeldItemMainhand().getItem().getFood();
                if (food != null && food.isMeat()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        for (int i = 0; i < 10; i++) {
            world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, Blocks.JUNGLE_LEAVES.getDefaultState()), getPosX(), getPosY() + 0.2, getPosZ(), 0, 0, 0);
        }
        remove();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity collider) {
        setMotion(0, getMotion().y, 0);
    }

    @Override
    protected SoundEvent getDeathSound() {
        playSound(SoundEvents.BLOCK_GRASS_BREAK, 1, 0.8F);
        return null;
    }

    @Override
    public boolean canSpawn(IWorld world, SpawnReason reason) {
        if (world.checkNoEntityCollision(this) && world.hasNoCollisions(this) && !world.containsAnyLiquid(getBoundingBox())) {
            BlockPos ground = new BlockPos(
                    MathHelper.floor(getPosX()),
                    MathHelper.floor(getBoundingBox().minY) - 1,
                    MathHelper.floor(getPosZ())
            );

            BlockState block = world.getBlockState(ground);

            if (block.getBlock() == Blocks.GRASS_BLOCK || block.getMaterial() == Material.EARTH || block.getMaterial() == Material.LEAVES) {
                playSound(SoundEvents.BLOCK_GRASS_HIT, 1, 0.8F);
                return true;
            }
        }
        return false;
    }

    public List<ItemEntity> getMeatsNearby(double distanceX, double distanceY, double distanceZ, double radius) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(distanceX, distanceY, distanceZ));
        ArrayList<ItemEntity> listEntityItem = new ArrayList<>();
        for (Entity entityNeighbor : list) {
            if (entityNeighbor instanceof ItemEntity && getDistance(entityNeighbor) <= radius) {
                Food food = ((ItemEntity) entityNeighbor).getItem().getItem().getFood();
                if (food != null && food.isMeat()) {
                    listEntityItem.add((ItemEntity) entityNeighbor);
                }
            }
        }
        return listEntityItem;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("tickGrowth", getGrowth());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setGrowth(compound.getInt("tickGrowth"));
    }

    @Override
    public boolean preventDespawn() {
        return true;
    }

    @Override
    protected void registerData() {
        super.registerData();
        getDataManager().register(GROWTH, 0);
        getDataManager().register(INFANT, false);
        getDataManager().register(HUNGRY, false);
        getDataManager().register(EATING, ItemStack.EMPTY);
    }

    public int getGrowth() {
        return getDataManager().get(GROWTH);
    }

    public void setGrowth(int growth) {
        getDataManager().set(GROWTH, growth);
    }

    public void incrementGrowth() {
        setGrowth(getGrowth() + 1);
    }

    public boolean getInfant() {
        return getDataManager().get(INFANT);
    }

    public void setInfant(boolean infant) {
        getDataManager().set(INFANT, infant);
    }

    public boolean getHungry() {
        return getDataManager().get(HUNGRY);
    }

    public void setHungry(boolean hungry) {
        getDataManager().set(HUNGRY, hungry);
    }

    public void setEating(ItemStack stack) {
        getDataManager().set(EATING, stack);
    }

    public ItemStack getEating() {
        return getDataManager().get(EATING);
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{EAT_ANIMATION};
    }
}
