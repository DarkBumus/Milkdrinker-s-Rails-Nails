package darkbum.mdrailsnails.util;

import darkbum.mdrailsnails.entity.EntityMinecartHauler;
import darkbum.mdrailsnails.inventory.container.ContainerFilter;
import darkbum.mdrailsnails.inventory.container.ContainerHaulerMinecart;
import darkbum.mdrailsnails.inventory.gui.GuiFilter;
import darkbum.mdrailsnails.inventory.gui.GuiHaulerMinecart;
import darkbum.mdrailsnails.tileentity.TileEntityFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(x);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return switch (id) {
            case 0 -> new ContainerHaulerMinecart((IInventory) entity, player.inventory, (EntityMinecartHauler) entity);
            case 1 -> new ContainerFilter(player.inventory, (TileEntityFilter) tileEntity);
            default -> null;
        };
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(x);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        return switch (id) {
            case 0 -> new GuiHaulerMinecart((IInventory) entity, player.inventory, (EntityMinecartHauler) entity);
            case 1 -> new GuiFilter(player.inventory, (TileEntityFilter) tileEntity);
            default -> null;
        };
    }
}
