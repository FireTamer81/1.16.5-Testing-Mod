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

public class MessageLeftMouseDown {
    public MessageLeftMouseDown() {}

    public static void serialize(final MessageLeftMouseDown message, final PacketBuffer buf) {

    }

    public static MessageLeftMouseDown deserialize(final PacketBuffer buf) {
        final MessageLeftMouseDown message = new MessageLeftMouseDown();
        return message;
    }

    public static final class Handler implements BiConsumer<MessageLeftMouseDown, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageLeftMouseDown message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> this.accept(message, player));
            context.setPacketHandled(true);
        }

        private void accept(final MessageLeftMouseDown message, final ServerPlayerEntity player) {
            if (player != null) {
                PlayerCapability.IPlayerCapability capability = CapabilityHandler.getCapability(player, PlayerCapability.PlayerProvider.PLAYER_CAPABILITY);
                if (capability != null) {
                    capability.setMouseLeftDown(true);
                    Power[] powers = capability.getPowers();
                    for (int i = 0; i < powers.length; i++) {
                        powers[i].onLeftMouseDown(player);
                    }
                }
                AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
                if (abilityCapability != null) {
                    for (Ability ability : abilityCapability.getAbilities()) {
                        ability.onLeftMouseDown(player);
                    }
                }
            }
        }
    }
}
