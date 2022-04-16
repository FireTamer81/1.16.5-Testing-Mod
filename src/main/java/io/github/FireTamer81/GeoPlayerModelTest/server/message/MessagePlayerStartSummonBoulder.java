package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.power.PowerGeomancy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessagePlayerStartSummonBoulder {
    public MessagePlayerStartSummonBoulder() {

    }

    public static void serialize(final MessagePlayerStartSummonBoulder message, final PacketBuffer buf) {
    }

    public static MessagePlayerStartSummonBoulder deserialize(final PacketBuffer buf) {
        final MessagePlayerStartSummonBoulder message = new MessagePlayerStartSummonBoulder();
        return message;
    }

    public static class Handler implements BiConsumer<MessagePlayerStartSummonBoulder, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessagePlayerStartSummonBoulder message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> {
                if (player != null) {
                    PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
                    if (playerCapability != null) {
                        PowerGeomancy geomancy = playerCapability.getGeomancy();
                        geomancy.startSpawningBoulder(player);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
