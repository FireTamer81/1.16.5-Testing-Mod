package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessagePlayerUseAbility {
    private int index;

    public MessagePlayerUseAbility() {

    }

    public MessagePlayerUseAbility(int index) {
        this.index = index;
    }

    public static void serialize(final MessagePlayerUseAbility message, final PacketBuffer buf) {
        buf.writeVarInt(message.index);
    }

    public static MessagePlayerUseAbility deserialize(final PacketBuffer buf) {
        final MessagePlayerUseAbility message = new MessagePlayerUseAbility();
        message.index = buf.readVarInt();
        return message;
    }

    public static class Handler implements BiConsumer<MessagePlayerUseAbility, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessagePlayerUseAbility message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> {
                AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(player, AbilityCapability.AbilityProvider.ABILITY_CAPABILITY);
                if (abilityCapability != null) {
                    AbilityHandler.INSTANCE.sendAbilityMessage(player, abilityCapability.getAbilityTypesOnEntity(player)[message.index]);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
