package io.github.FireTamer81.GeoPlayerModelTest.server.message;

import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySunstrike;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MessagePlayerSummonSunstrike {
    private static final double REACH = 15;

    public MessagePlayerSummonSunstrike() {

    }

    private static BlockRayTraceResult rayTrace(LivingEntity entity, double reach) {
        Vector3d pos = entity.getEyePosition(0);
        Vector3d segment = entity.getLookVec();
        segment = pos.add(segment.x * reach, segment.y * reach, segment.z * reach);
        return entity.world.rayTraceBlocks(new RayTraceContext(pos, segment, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
    }

    public static void serialize(final MessagePlayerSummonSunstrike message, final PacketBuffer buf) {
    }

    public static MessagePlayerSummonSunstrike deserialize(final PacketBuffer buf) {
        final MessagePlayerSummonSunstrike message = new MessagePlayerSummonSunstrike();
        return message;
    }

    public static class Handler implements BiConsumer<MessagePlayerSummonSunstrike, Supplier<NetworkEvent.Context>> {
        @Override
        public void accept(final MessagePlayerSummonSunstrike message, final Supplier<NetworkEvent.Context> contextSupplier) {
            final NetworkEvent.Context context = contextSupplier.get();
            final ServerPlayerEntity player = context.getSender();
            context.enqueueWork(() -> {
                BlockRayTraceResult raytrace = rayTrace(player, REACH);
                if (raytrace.getType() == RayTraceResult.Type.BLOCK && raytrace.getFace() == Direction.UP && player.inventory.getCurrentItem().isEmpty() && player.isPotionActive(EffectHandler.SUNS_BLESSING)) {
                    BlockPos hit = raytrace.getPos();
                    EntitySunstrike sunstrike = new EntitySunstrike(EntityHandler.SUNSTRIKE.get(), player.world, player, hit.getX(), hit.getY(), hit.getZ());
                    sunstrike.onSummon();
                    player.world.addEntity(sunstrike);
                }
            });
            context.setPacketHandled(true);
        }
    }
}
