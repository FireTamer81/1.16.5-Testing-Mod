package io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.power.Power;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessageRightMouseUp {
    public MessageRightMouseUp() {}

    public static void serialize(final MessageRightMouseUp message, final PacketBuffer buf) {

    }

    public static MessageRightMouseUp deserialize(final PacketBuffer buf) {
        final MessageRightMouseUp message = new MessageRightMouseUp();
        return message;
    }

    public static final class Handler implements BiConsumer<MessageRightMouseUp, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageRightMouseUp message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> this.accept(message, player));
            context.setPacketHandled(true);
        }

        private void accept(final MessageRightMouseUp message, final ServerPlayerEntity player) {
            if (player != null) {
                PlayerCapability.IPlayerCapability capability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
                if (capability != null) {
                    capability.setMouseRightDown(false);
                    Power[] powers = capability.getPowers();
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onRightMouseUp(player);
                    }
                }
                AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                if (abilityCapability != null) {
                    for (Ability ability : abilityCapability.getAbilities()) {
                        ability.onRightMouseUp(player);
                    }
                }
            }
        }
    }
}
