package darkbum.mdrailsnails.inventory.container;

import darkbum.mdrailsnails.entity.EntityMinecartHauler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHaulerMinecart extends Container {

    private final IInventory inventory;

    public ContainerHaulerMinecart(IInventory inv, InventoryPlayer playerInventory, EntityMinecartHauler cart) {
        inventory = inv;

        addSlotToContainer(new Slot(inv, 0, 80, 44));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int index = col + row * 9 + 9;
                int x = 8 + col * 18;
                int y = 84 + row * 18;
                addSlotToContainer(new Slot(playerInventory, index, x, y));
            }
        }

        for (int col = 0; col < 9; col++) {
            int x = 8 + col * 18;
            addSlotToContainer(new Slot(playerInventory, col, x, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack resultStack = null;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack currentStack = slot.getStack();
            ItemStack originalStack = currentStack.copy();

            if (index == 0) {
                if (!mergeItemStack(currentStack, 1, 37, false)) {
                    return null;
                }
            } else {
                if (!mergeItemStack(currentStack, 0, 1, false)) {
                    return null;
                }
            }
            if (currentStack.stackSize <= 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (currentStack.stackSize == originalStack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, currentStack);
            resultStack = originalStack;
        }

        return resultStack;
    }
}
