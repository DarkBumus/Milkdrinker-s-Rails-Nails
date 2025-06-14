package darkbum.mdrailsnails.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFilter extends Slot {

    public SlotFilter(IInventory iInventory, int slotIndex, int x, int y) {
        super(iInventory, slotIndex, x, y);
    }

    public int getSlotStackLimit() {
        return 1;
    }

    public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
        super.onPickupFromSlot(entityPlayer, itemStack);
    }
}
