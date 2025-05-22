package darkbum.mdrailsnails.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile_entity = world.getTileEntity(x, y, z);
        return switch (id) {
//            case 0 -> new ContainerEvaporator(player.inventory, (TileEntityEvaporator) tile_entity);
            default -> null;
        };
    }

    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile_entity = world.getTileEntity(x, y, z);
        return switch (id) {
//            case 0 -> new GuiEvaporator(player.inventory, (TileEntityEvaporator) tile_entity);
            default -> null;
        };
    }
}
