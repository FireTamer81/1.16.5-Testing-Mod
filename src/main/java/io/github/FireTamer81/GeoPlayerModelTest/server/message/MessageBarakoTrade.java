package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.animation.AnimationHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarako;
import io.github.FireTamer81.GeoPlayerModelTest.server.inventory.ContainerBarakoTrade;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessageBarakoTrade {
    private int entityID;

    public MessageBarakoTrade() {

    }

    public MessageBarakoTrade(LivingEntity sender) {
        entityID = sender.getEntityId();
    }

    public static void serialize(final MessageBarakoTrade message, final PacketBuffer buf) {
        buf.writeVarInt(message.entityID);
    }

    public static MessageBarakoTrade deserialize(final PacketBuffer buf) {
        final MessageBarakoTrade message = new MessageBarakoTrade();
        message.entityID = buf.readVarInt();
        return message;
    }

    public static class Handler implements BiConsumer<MessageBarakoTrade, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessageBarakoTrade message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> {
                if (player != null) {
                    Entity entity = player.world.getEntityByID(message.entityID);
                    if (!(entity instanceof EntityBarako)) {
                        return;
                    }
                    EntityBarako barako = (EntityBarako) entity;
                    if (barako.getCustomer() != player) {
                        return;
                    }
                    Container container = player.openContainer;
                    if (!(container instanceof ContainerBarakoTrade)) {
                        return;
                    }
                    boolean satisfied = barako.hasTradedWith(player);
                    if (!satisfied) {
                        if (satisfied = barako.fulfillDesire(container.getSlot(0))) {
                            barako.rememberTrade(player);
                            ((ContainerBarakoTrade) container).returnItems();
                            container.detectAndSendChanges();
                        }
                    }
                    if (satisfied) {
                        player.addPotionEffect(new EffectInstance(EffectHandler.SUNS_BLESSING, ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get() * 60 * 20, 0, false, false));
                        if (barako.getAnimation() != EntityBarako.BLESS_ANIMATION) {
                            barako.setAnimationTick(0);
                            AnimationHandler.INSTANCE.sendAnimationMessage(barako, EntityBarako.BLESS_ANIMATION);
                            barako.playSound(MMSounds.ENTITY_BARAKO_BLESS.get(), 2, 1);
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}
