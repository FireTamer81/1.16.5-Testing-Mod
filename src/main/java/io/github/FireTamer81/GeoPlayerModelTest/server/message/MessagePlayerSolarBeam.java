package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySolarBeam;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessagePlayerSolarBeam {
    public MessagePlayerSolarBeam() {

    }

    public static void serialize(final MessagePlayerSolarBeam message, final PacketBuffer buf) {
    }

    public static MessagePlayerSolarBeam deserialize(final PacketBuffer buf) {
        final MessagePlayerSolarBeam message = new MessagePlayerSolarBeam();
        return message;
    }

    public static class Handler implements BiConsumer<MessagePlayerSolarBeam, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessagePlayerSolarBeam message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> {
                EntitySolarBeam solarBeam = new EntitySolarBeam(EntityHandler.SOLAR_BEAM.get(), player.world, player, player.getPosX(), player.getPosY() + 1.2f, player.getPosZ(), (float) ((player.rotationYawHead + 90) * Math.PI / 180), (float) (-player.rotationPitch * Math.PI / 180), 55);
                solarBeam.setHasPlayer(true);
                player.world.addEntity(solarBeam);
                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 80, 2, false, false));
                int duration = player.getActivePotionEffect(EffectHandler.SUNS_BLESSING).getDuration();
                player.removePotionEffect(EffectHandler.SUNS_BLESSING);
                int solarBeamCost = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get() * 60 * 20;
                if (duration - solarBeamCost > 0) {
                    player.addPotionEffect(new EffectInstance(EffectHandler.SUNS_BLESSING, duration - solarBeamCost, 0, false, false));
                }
            });
            context.setPacketHandled(true);
        }
    }
}
