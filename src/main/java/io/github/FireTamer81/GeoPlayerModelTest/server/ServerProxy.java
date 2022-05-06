package io.github.FireTamer81.GeoPlayerModelTest.server;

import io.github.FireTamer81.GeoPlayerModelTest._library.server.network.AnimationMessage;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.BarakoaMaskModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.SolVisageModel;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.WroughtHelmModel;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySunstrike;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.*;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageLeftMouseDown;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageLeftMouseUp;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageRightMouseDown;
import io.github.FireTamer81.GeoPlayerModelTest.server.message.mouse.MessageRightMouseUp;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServerProxy {
    private int nextMessageId;

    public void init(final IEventBus modbus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_CONFIG);
    }

    public void onLateInit(final IEventBus modbus) {}

    public void playSunstrikeSound(EntitySunstrike strike) {}

    public void playIceBreathSound(Entity entity) {}

    public void playBoulderChargeSound(LivingEntity player) {}

    public void playBlackPinkSound(AbstractMinecartEntity entity) {}

    public void playSunblockSound(LivingEntity entity) {}

    public void minecartParticles(ClientWorld world, AbstractMinecartEntity minecart, float scale, double x, double y, double z, BlockState state, BlockPos pos) {}

    public void initNetwork() {
        final String version = "1";
        TestModMain.NETWORK = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(TestModMain.MODID, "net"))
                .networkProtocolVersion(() -> version)
                .clientAcceptedVersions(version::equals)
                .serverAcceptedVersions(version::equals)
                .simpleChannel();
        this.registerMessage(AnimationMessage.class, AnimationMessage::serialize, AnimationMessage::deserialize, new AnimationMessage.Handler());
        this.registerMessage(MessageLeftMouseDown.class, MessageLeftMouseDown::serialize, MessageLeftMouseDown::deserialize, new MessageLeftMouseDown.Handler());
        this.registerMessage(MessageLeftMouseUp.class, MessageLeftMouseUp::serialize, MessageLeftMouseUp::deserialize, new MessageLeftMouseUp.Handler());
        this.registerMessage(MessageRightMouseDown.class, MessageRightMouseDown::serialize, MessageRightMouseDown::deserialize, new MessageRightMouseDown.Handler());
        this.registerMessage(MessageRightMouseUp.class, MessageRightMouseUp::serialize, MessageRightMouseUp::deserialize, new MessageRightMouseUp.Handler());
        this.registerMessage(MessageFreezeEffect.class, MessageFreezeEffect::serialize, MessageFreezeEffect::deserialize, new MessageFreezeEffect.Handler());
        this.registerMessage(MessagePlayerAttackMob.class, MessagePlayerAttackMob::serialize, MessagePlayerAttackMob::deserialize, new MessagePlayerAttackMob.Handler());
        this.registerMessage(MessagePlayerSolarBeam.class, MessagePlayerSolarBeam::serialize, MessagePlayerSolarBeam::deserialize, new MessagePlayerSolarBeam.Handler());
        this.registerMessage(MessagePlayerSummonSunstrike.class, MessagePlayerSummonSunstrike::serialize, MessagePlayerSummonSunstrike::deserialize, new MessagePlayerSummonSunstrike.Handler());
        this.registerMessage(MessagePlayerStartSummonBoulder.class, MessagePlayerStartSummonBoulder::serialize, MessagePlayerStartSummonBoulder::deserialize, new MessagePlayerStartSummonBoulder.Handler());
        this.registerMessage(MessageSunblockEffect.class, MessageSunblockEffect::serialize, MessageSunblockEffect::deserialize, new MessageSunblockEffect.Handler());
        this.registerMessage(MessageUseAbility.class, MessageUseAbility::serialize, MessageUseAbility::deserialize, new MessageUseAbility.Handler());
        this.registerMessage(MessagePlayerUseAbility.class, MessagePlayerUseAbility::serialize, MessagePlayerUseAbility::deserialize, new MessagePlayerUseAbility.Handler());
        this.registerMessage(MessageInterruptAbility.class, MessageInterruptAbility::serialize, MessageInterruptAbility::deserialize, new MessageInterruptAbility.Handler());
    }

    private <MSG> void registerMessage(final Class<MSG> clazz, final BiConsumer<MSG, PacketBuffer> encoder, final Function<PacketBuffer, MSG> decoder, final BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
        TestModMain.NETWORK.messageBuilder(clazz, this.nextMessageId++)
                .encoder(encoder).decoder(decoder)
                .consumer(consumer)
                .add();
    }

    public void setTPS(float tickRate) {
    }


    public Entity getReferencedMob() {
        return null;
    }

    public void setReferencedMob(Entity referencedMob) {}

    public WroughtHelmModel<LivingEntity> getWroughtHelmModel() {
        return null;
    }

    public BarakoaMaskModel<LivingEntity> getBarakoaMaskModel() {
        return null;
    }

    public SolVisageModel<LivingEntity> getSolVisageModel() {
        return null;
    }
}
