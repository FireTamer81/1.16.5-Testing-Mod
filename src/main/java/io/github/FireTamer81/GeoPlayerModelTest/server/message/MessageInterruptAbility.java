package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessageInterruptAbility {
    private int entityID;
    private int index;

    public MessageInterruptAbility() {

    }

    public MessageInterruptAbility(int entityID, int index) {
        this.entityID = entityID;
        this.index = index;
    }

    public static void serialize(final MessageInterruptAbility message, final PacketBuffer buf) {
        buf.writeVarInt(message.entityID);
        buf.writeVarInt(message.index);
    }

    public static MessageInterruptAbility deserialize(final PacketBuffer buf) {
        final MessageInterruptAbility message = new MessageInterruptAbility();
        message.entityID = buf.readVarInt();
        message.index = buf.readVarInt();
        return message;
    }

    public static class Handler implements BiConsumer<MessageInterruptAbility, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageInterruptAbility message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) Minecraft.getInstance().world.getEntityByID(message.entityID);
                if (entity != null) {
                    AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(entity, AbilityCapability.AbilityProvider.ABILITY_CAPABILITY);
                    if (abilityCapability != null) {
                        AbilityType<?> abilityType = abilityCapability.getAbilityTypesOnEntity(entity)[message.index];
                        Ability instance = abilityCapability.getAbilityMap().get(abilityType);
                        if (instance.isUsing()) instance.interrupt();
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
