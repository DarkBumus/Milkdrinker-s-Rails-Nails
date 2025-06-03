package darkbum.mdrailsnails.event;

import darkbum.mdrailsnails.item.ItemRailwayLever;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.util.CartLinkHandler.*;

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

            if (player.isSneaking() && !player.worldObj.isRemote && enableRailwayLeverLinkingAbility) {
                boolean result = handleLink(player, targetCart);
            } else if (!player.isSneaking() && !player.worldObj.isRemote && enableRailwayLeverPushingAbility) {
                handlePush(player, targetCart);
            }
        }
    }

    private static void handlePush(EntityPlayer player, EntityMinecart targetCart) {
        player.addExhaustion(1.0f);
        if (player.ridingEntity == null) {
            if (targetCart.posX < player.posX) {
                targetCart.motionX -= 0.07000000029802322D;
            } else {
                targetCart.motionX += 0.07000000029802322D;
            }
            if (targetCart.posZ < player.posZ) {
                targetCart.motionZ -= 0.07000000029802322D;
            } else {
                targetCart.motionZ += 0.07000000029802322D;
            }
        }
    }
}
