package io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.AdvancedParticleBase;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.util.ParticleComponent;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemBarakoaMask;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityBarakoanToPlayer extends EntityBarakoan<PlayerEntity> {
    private static final DataParameter<ItemStack> MASK_STORED = EntityDataManager.createKey(EntityBarakoanToPlayer.class, DataSerializers.ITEMSTACK);
    @OnlyIn(Dist.CLIENT)
    public Vector3d[] feetPos;

    public EntityBarakoanToPlayer(EntityType<? extends EntityBarakoanToPlayer> type, World world) {
        this(type, world, null);
    }

    public EntityBarakoanToPlayer(EntityType<? extends EntityBarakoanToPlayer> type, World world, PlayerEntity leader) {
        super(type, world, PlayerEntity.class, leader);
        experienceValue = 0;
        if (world.isRemote) {
            feetPos = new Vector3d[]{new Vector3d(0, 0, 0)};
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        getDataManager().register(MASK_STORED, new ItemStack(ItemHandler.BARAKOA_MASK_FURY, 1));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (rand.nextFloat() < 0.5) return null;
        return super.getAmbientSound();
    }

    @Override
    public void tick() {
        if (ticksExisted > 30 && (getLeader() == null || getLeader().getHealth() <= 0)) {
            deactivate();
        }
        super.tick();
        if (world.isRemote && feetPos != null && feetPos.length > 0) {
            feetPos[0] = getPositionVec().add(0, 0.05f, 0);
            if (ticksExisted % 10 == 0) AdvancedParticleBase.spawnParticle(world, ParticleHandler.RING2.get(), feetPos[0].getX(), feetPos[0].getY(), feetPos[0].getZ(), 0, 0, 0, false, 0, Math.PI/2f, 0, 0, 1.5F, 1, 223 / 255f, 66 / 255f, 1, 1, 15, true, false, new ParticleComponent[]{
                    new ParticleComponent.PinLocation(feetPos),
                    new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1f, 0f), false),
                    new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1f, 10f), false)
            });
        }
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (playerIn == leader) {
            deactivate();
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    private void deactivate() {
        if (getActive() && getAnimation() != DEACTIVATE_ANIMATION) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, DEACTIVATE_ANIMATION);
            playSound(MMSounds.ENTITY_BARAKOA_RETRACT.get(), 1, 1);
        }
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MowzieEntity.createAttributes().createMutableAttribute(Attributes.ATTACK_DAMAGE, 7)
                .createMutableAttribute(Attributes.MAX_HEALTH, 20);
    }

    @Override
    protected int getTribeCircleTick() {
        PlayerCapability.IPlayerCapability capability = getPlayerCapability();
        if (capability == null) return 0;
        return capability.getTribeCircleTick();
    }

    @Override
    protected int getPackSize() {
        PlayerCapability.IPlayerCapability capability = getPlayerCapability();
        if (capability == null) return 0;
        return capability.getPackSize();
    }

    @Override
    protected void addAsPackMember() {
        PlayerCapability.IPlayerCapability capability = getPlayerCapability();
        if (capability == null) return;
        capability.addPackMember(this);
    }

    @Override
    protected void removeAsPackMember() {
        PlayerCapability.IPlayerCapability capability = getPlayerCapability();
        if (capability == null) return;
        capability.removePackMember(this);
    }

    private PlayerCapability.IPlayerCapability getPlayerCapability() {
        return CapabilityHandler.getCapability(leader, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
    }

    @Override
    public boolean isBarakoDevoted() {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    @Nullable
    public UUID getOwnerId() {
        return getLeader() == null ? null : getLeader().getUniqueID();
    }

    @Nullable
    public Entity getOwner() {
        return leader;
    }

    public boolean isTeleportFriendlyBlock(int x, int z, int y, int xOffset, int zOffset)
    {
        BlockPos blockpos = new BlockPos(x + xOffset, y - 1, z + zOffset);
        BlockState iblockstate = this.world.getBlockState(blockpos);
        return iblockstate.canEntitySpawn(this.world, blockpos, this.getType()) && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2));
    }

    public ItemStack getStoredMask() {
        return getDataManager().get(MASK_STORED);
    }

    public void setStoredMask(ItemStack mask) {
        getDataManager().set(MASK_STORED, mask);
    }

    @Override
    protected ItemStack getDeactivatedMask(ItemBarakoaMask mask) {
        return getStoredMask();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        CompoundNBT compoundnbt = compound.getCompound("storedMask");
        this.setStoredMask(ItemStack.read(compoundnbt));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (!this.getStoredMask().isEmpty()) {
            compound.put("storedMask", this.getStoredMask().write(new CompoundNBT()));
        }
    }
}
