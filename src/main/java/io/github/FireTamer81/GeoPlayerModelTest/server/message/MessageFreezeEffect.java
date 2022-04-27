package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.FrozenCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessageFreezeEffect {
    private int entityID;
    private boolean isFrozen;

    public MessageFreezeEffect() {

    }

    public MessageFreezeEffect(LivingEntity entity, boolean activate) {
        entityID = entity.getEntityId();
        this.isFrozen = activate;
    }

    public static void serialize(final MessageFreezeEffect message, final PacketBuffer buf) {
        buf.writeVarInt(message.entityID);
        buf.writeBoolean(message.isFrozen);
    }

    public static MessageFreezeEffect deserialize(final PacketBuffer buf) {
        final MessageFreezeEffect message = new MessageFreezeEffect();
        message.entityID = buf.readVarInt();
        message.isFrozen = buf.readBoolean();
        return message;
    }

    public static class Handler implements BiConsumer<MessageFreezeEffect, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageFreezeEffect message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (Minecraft.getInstance().world != null) {
                    Entity entity = Minecraft.getInstance().world.getEntityByID(message.entityID);
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity) entity;
                        FrozenCapability.IFrozenCapability livingCapability = CapabilityHandler.getCapability(living, FrozenCapability.FrozenProvider.FROZEN_CAPABILITY);
                        if (livingCapability != null) {
                            if (message.isFrozen) livingCapability.onFreeze(living);
                            else livingCapability.onUnfreeze(living);
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}