package io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol;

import io.github.FireTamer81.GeoPlayerModelTest.server.message.MessageBlackPinkInYourArea;
import io.github.FireTamer81.TestModMain;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.BiConsumer;

public final class BlackPinkInYourArea implements BiConsumer<World, AbstractMinecartEntity> {
    private BlackPinkInYourArea() {}

    @Override
    public void accept(World world, AbstractMinecartEntity minecart) {
        /*BlockState state = minecart.getDisplayTile();
        if (state.getBlock() != BlockHandler.GROTTOL.get()) {
            state = BlockHandler.GROTTOL.get().getDefaultState();
            minecart.setDisplayTileOffset(minecart.getDefaultDisplayTileOffset());
        }
        minecart.setDisplayTile(state.with(BlockGrottol.VARIANT, BlockGrottol.Variant.BLACK_PINK));*/
        TestModMain.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> minecart), new MessageBlackPinkInYourArea(minecart));
    }

    public static BlackPinkInYourArea create() {
        return new BlackPinkInYourArea();
    }
}
