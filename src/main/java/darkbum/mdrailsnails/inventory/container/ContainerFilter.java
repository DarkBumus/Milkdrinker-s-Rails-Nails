package darkbum.mdrailsnails.inventory.container;

import darkbum.mdrailsnails.inventory.slot.SlotFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFilter extends Container {

    private final IInventory inventory;

    public ContainerFilter(InventoryPlayer inventoryPlayer, IInventory inventory) {
        this.inventory = inventory;
        inventory.openInventory();
        int playerInventoryY = 84;
        int slots;
        for (slots = 0; slots < 5; slots++)
            addSlotToContainer(new Slot(inventory, slots, 44 + slots * 18, 22));
        for (slots = 5; slots < 10; slots++)
            addSlotToContainer(new SlotFilter(inventory, slots, 44 + (slots - 5) * 18, 53));
        for (slots = 0; slots < 3; slots++) {
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + slots * 9 + 9, 8 + j * 18, slots * 18 + playerInventoryY));
        }
        for (slots = 0; slots < 9; slots++)
            addSlotToContainer(new Slot(inventoryPlayer, slots, 8 + slots * 18, 58 + playerInventoryY));
    }

    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = null;
        Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if (slotIndex < inventory.getSizeInventory()) {
                if (!mergeItemStack(stack1, inventory.getSizeInventory(), inventorySlots.size(), true))
                    return null;
            } else if (!mergeItemStack(stack1, 0, inventory.getSizeInventory(), false)) {
                return null;
            }
            if (stack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        inventory.closeInventory();
    }
}
