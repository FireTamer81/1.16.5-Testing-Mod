package io.github.FireTamer81.GeoPlayerModelTest.server.inventory;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarako;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryBarako implements IInventory {
    private final EntityBarako barako;

    private ItemStack input = ItemStack.EMPTY;

    private List<ChangeListener> listeners;

    public InventoryBarako(EntityBarako barako) {
        this.barako = barako;
    }

    public void addListener(ChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index == 0 ? input : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack;
        if (index == 0 && input != ItemStack.EMPTY && count > 0) {
            ItemStack split = input.split(count);
            if (input.getCount() == 0) {
                input = ItemStack.EMPTY;
            }
            stack = split;
            markDirty();
        } else {
            stack = ItemStack.EMPTY;
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack s = input;
        input = ItemStack.EMPTY;
        markDirty();
        return s;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index == 0) {
            input = stack;
            if (stack != ItemStack.EMPTY && stack.getCount() > getInventoryStackLimit()) {
                stack.setCount(getInventoryStackLimit());
            }
            markDirty();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if (listeners != null) {
            for (ChangeListener listener : listeners) {
                listener.onChange(this);
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return barako.getCustomer() == player;
    }

    @Override
    public void openInventory(PlayerEntity player) {}

    @Override
    public void closeInventory(PlayerEntity player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public void clear() {
        input = ItemStack.EMPTY;
        markDirty();
    }

    public interface ChangeListener {
        void onChange(IInventory inv);
    }

    @Override
    public boolean isEmpty() {
        return !input.isEmpty();
    }
}
