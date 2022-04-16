package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.LivingCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessageSunblockEffect {
    private int entityID;
    private boolean hasSunblock;

    public MessageSunblockEffect() {

    }

    public MessageSunblockEffect(LivingEntity entity, boolean activate) {
        entityID = entity.getEntityId();
        this.hasSunblock = activate;
    }

    public static void serialize(final MessageSunblockEffect message, final PacketBuffer buf) {
        buf.writeVarInt(message.entityID);
        buf.writeBoolean(message.hasSunblock);
    }

    public static MessageSunblockEffect deserialize(final PacketBuffer buf) {
        final MessageSunblockEffect message = new MessageSunblockEffect();
        message.entityID = buf.readVarInt();
        message.hasSunblock = buf.readBoolean();
        return message;
    }

    public static class Handler implements BiConsumer<MessageSunblockEffect, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageSunblockEffect message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (Minecraft.getInstance().world != null) {
                    Entity entity = Minecraft.getInstance().world.getEntityByID(message.entityID);
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity) entity;
                        LivingCapability.ILivingCapability livingCapability = CapabilityHandler.getCapability(living, LivingCapability.LivingProvider.LIVING_CAPABILITY);
                        if (livingCapability != null) {
                            livingCapability.setHasSunblock(message.hasSunblock);
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
