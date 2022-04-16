package io.github.FireTamer81.GeoPlayerModelTest.server.entity.grottol;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.function.Consumer;

public final class BlackPinkRailLine implements Consumer<AbstractMinecartEntity> {
    private final BlackPinkInYourArea action;

    private State state = new StateAcquireVertex();

    private BlackPinkRailLine(BlackPinkInYourArea action) {
        this.action = action;
    }

    @Override
    public void accept(AbstractMinecartEntity minecart) {
        state = next(minecart.world, minecart);
    }

    private State next(World world, AbstractMinecartEntity minecart) {
        BlockPos pos = getRailPosition(world, new BlockPos(minecart.getPositionVec()));
        if (AbstractRailBlock.isRail(world.getBlockState(pos))) {
            return state.apply(world, minecart, pos);
        }
        return state.derail();
    }

    private static BlockPos getRailPosition(World world, BlockPos pos) {
        BlockPos below = pos.down();
        return AbstractRailBlock.isRail(world, below) ? below : pos;
    }

    public static BlackPinkRailLine create() {
        return new BlackPinkRailLine(BlackPinkInYourArea.create());
    }

    private abstract class State {
        abstract State apply(World world, AbstractMinecartEntity minecart, BlockPos pos);

        abstract State derail();
    }

    private final class StateAcquireVertex extends State {
        @Override
        public State apply(World world, AbstractMinecartEntity minecart, BlockPos vertex) {
            return new StateAcquireEdge(vertex);
        }

        @Override
        State derail() {
            return this;
        }
    }

    private final class StateAcquireEdge extends State {
        private final BlockPos vertex;

        private StateAcquireEdge(BlockPos vertex) {
            this.vertex = vertex;
        }

        @Override
        public State apply(World world, AbstractMinecartEntity minecart, BlockPos vertex) {
            return new StateSearch(vertex.subtract(this.vertex), vertex);
        }

        @Override
        State derail() {
            return new StateAcquireVertex();
        }
    }

    private final class StateSearch extends State {
        private final long[] mask = {
                0xFFBAEA55D752A95L,
                0xFF5555EAAAAF57AL,
                0xFFFFFFBFFFFDFEFL,
                0xFFEFBFFF7DFFFFFL
        };

        private final long test = 0x10000000000000L;

        private Vector3i edge;

        private BlockPos vertex;

        private int ordinal;

        private long state = 0xFFFFFFFFFFFFEL;

        private StateSearch(Vector3i edge, BlockPos vertex) {
            this.edge = edge;
            this.vertex = vertex;
        }

        @Override
        public State apply(World world, AbstractMinecartEntity minecart, BlockPos vertex) {
            if (!this.vertex.equals(vertex)) {
                Vector3i edge = vertex.subtract(this.vertex);
                int ordinal = getOrdinal(this.edge, edge);
                if (ordinal >= 0 && ordinal < 4 && (ordinal != 1 || ordinal != this.ordinal) && ((state = (state | mask[ordinal]) << 1) & test) == 0) {
                    action.accept(world, minecart);
                }
                this.ordinal = ordinal;
                this.vertex = vertex;
                this.edge = edge;
            }
            return this;
        }

        private int getOrdinal(Vector3i v0, Vector3i v1) {
            return 1 + (v1.getZ() * v0.getX() - v1.getX() * v0.getZ()) + ((v0.getX() * v1.getX() + v0.getZ() * v1.getZ()) & 2);
        }

        @Override
        State derail() {
            return new StateAcquireVertex();
        }
    }
}
