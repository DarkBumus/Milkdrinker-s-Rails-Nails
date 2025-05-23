package darkbum.mdrailsnails.event;

import darkbum.mdrailsnails.item.ItemRailwayLever;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityInteractEventHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        EntityPlayer player = event.entityPlayer;

        if (player == null || event.target == null) return;

        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null || !(heldItem.getItem() instanceof ItemRailwayLever)) return;

        if (event.target instanceof EntityMinecart targetCart) {
            event.setCanceled(true);

            if (player.isSneaking() && !player.worldObj.isRemote) {
                boolean result = CartLinkHandler.handleLink(player, targetCart);
            }
        }
    }
}
