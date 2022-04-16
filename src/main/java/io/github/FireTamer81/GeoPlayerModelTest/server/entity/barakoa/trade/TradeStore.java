package io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.trade;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.Random;

public final class TradeStore {
    public static final TradeStore EMPTY = new TradeStore(ImmutableSet.of(), 0);

    private final ImmutableSet<Trade> trades;

    private final int totalWeight;

    private TradeStore(ImmutableSet<Trade> trades, int totalWeight) {
        this.trades = trades;
        this.totalWeight = totalWeight;
    }

    public boolean hasStock() {
        return trades.size() > 0;
    }

    public Trade get(Random rng) {
        if (totalWeight <= 0) {
            return null;
        }
        int w = rng.nextInt(totalWeight);
        for (Trade t : trades) {
            w -= t.getWeight();
            if (w < 0) {
                return t;
            }
        }
        return null;
    }

    public CompoundNBT serialize() {
        CompoundNBT compound = new CompoundNBT();
        ListNBT tradesList = new ListNBT();
        for (Trade trade : trades) {
            tradesList.add(trade.serialize());
        }
        compound.put("trades", tradesList);
        return compound;
    }

    public static TradeStore deserialize(CompoundNBT compound) {
        ListNBT tradesList = compound.getList("trades", NBT.TAG_COMPOUND);
        int totalWeight = 0;
        ImmutableSet.Builder<Trade> trades = new ImmutableSet.Builder<>();
        for (int i = 0; i < tradesList.size(); i++) {
            Trade trade = Trade.deserialize(tradesList.getCompound(i));
            if (trade != null) {
                trades.add(trade);
                totalWeight += trade.getWeight();
            }
        }
        return new TradeStore(trades.build(), totalWeight);
    }

    public static final class Builder {
        private final ImmutableSet.Builder<Trade> trades = new ImmutableSet.Builder<>();

        private int totalWeight;

        public Builder addTrade(Item input, int inputCount, Item output, int outputCount, int weight) {
            return addTrade(input, inputCount, null, output, outputCount, null, weight);
        }

        public Builder addTrade(Item input, int inputCount, CompoundNBT inputMeta, Item output, int outputCount, CompoundNBT outputMeta, int weight) {
            return addTrade(new ItemStack(input, inputCount, inputMeta), new ItemStack(output, outputCount, outputMeta), weight);
        }

        public Builder addTrade(ItemStack input, ItemStack output, int weight) {
            trades.add(new Trade(input, output, weight));
            totalWeight += weight;
            return this;
        }

        public TradeStore build() {
            return new TradeStore(trades.build(), totalWeight);
        }
    }
}
