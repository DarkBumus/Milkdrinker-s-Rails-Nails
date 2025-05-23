package darkbum.mdrailsnails.inventory.container;

import darkbum.mdrailsnails.entity.EntityHaulerMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerHaulerMinecart extends Container {

    private final IInventory inventory;

    public ContainerHaulerMinecart(IInventory inv, InventoryPlayer playerInventory, EntityHaulerMinecart cart) {
        inventory = inv;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(inv, 0, 80, 44));
            }
        }

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
}
