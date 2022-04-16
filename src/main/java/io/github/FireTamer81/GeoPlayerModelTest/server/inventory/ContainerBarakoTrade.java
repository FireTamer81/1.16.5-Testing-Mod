package io.github.FireTamer81.GeoPlayerModelTest.server.inventory;

import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarako;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarakoaVillager;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.trade.Trade;
import io.github.FireTamer81.TestModMain;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class ContainerBarakoTrade extends Container {
    private final EntityBarako barako;

    private final PlayerEntity player;

    private final InventoryBarako inventory;

    public ContainerBarakoTrade(int id, PlayerInventory playerInventory) {
        this(id, (EntityBarako) TestModMain.PROXY.getReferencedMob(), playerInventory);
    }

    public ContainerBarakoTrade(int id, EntityBarako barako, PlayerInventory playerInv) {
        this(id, barako, new InventoryBarako(barako), playerInv);
    }

    public ContainerBarakoTrade(int id, EntityBarako Barako, InventoryBarako inventory, PlayerInventory playerInv) {
        super(ContainerHandler.CONTAINER_BARAKO_TRADE, id);
        this.barako = Barako;
        this.player = playerInv.player;
        this.inventory = inventory;
        if (barako != null && !barako.hasTradedWith(playerInv.player)) addSlot(new Slot(inventory, 0, 69, 54));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return barako != null && inventory.isUsableByPlayer(player) && barako.isAlive() && barako.getDistance(player) < 8;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack contained = slot.getStack();
            stack = contained.copy();
            if (index != 0) {
                if (index >= 1 && index < 28) {
                    if (!mergeItemStack(contained, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 28 && index < 37 && !mergeItemStack(contained, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(contained, 1, 37, false)) {
                return ItemStack.EMPTY;
            }
            if (contained.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (contained.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, contained);
        }
        return stack;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        if (barako != null) barako.setCustomer(null);
        if (!player.world.isRemote) {
            ItemStack stack = inventory.removeStackFromSlot(0);
            if (stack != ItemStack.EMPTY) {
                ItemEntity dropped = player.dropItem(stack, false);
                if (dropped != null) {
                    dropped.setMotion(dropped.getMotion().scale(0.5));
                }
            }
        }
    }

    public void returnItems() {
        if (!player.world.isRemote) {
            ItemStack stack = inventory.removeStackFromSlot(0);
            if (stack != ItemStack.EMPTY) {
                ItemHandlerHelper.giveItemToPlayer(player, stack);
            }
        }
    }

    public EntityBarako getBarako() {
        return barako;
    }

    public InventoryBarako getInventoryBarako() {
        return inventory;
    }
}
