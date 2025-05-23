package darkbum.mdrailsnails.util;

import darkbum.mdrailsnails.entity.EntityHaulerMinecart;
import darkbum.mdrailsnails.inventory.container.ContainerHaulerMinecart;
import darkbum.mdrailsnails.inventory.gui.GuiHaulerMinecart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(x);
        return switch (id) {
            case 0 -> new ContainerHaulerMinecart((IInventory) entity, player.inventory, (EntityHaulerMinecart) entity);
            default -> null;
        };
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(x);
        return switch (id) {
            case 0 -> new GuiHaulerMinecart((IInventory) entity, player.inventory, (EntityHaulerMinecart) entity);
            default -> null;
        };
    }
}
