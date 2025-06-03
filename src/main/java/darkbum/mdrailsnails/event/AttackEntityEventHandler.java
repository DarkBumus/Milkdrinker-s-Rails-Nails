package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkbum.mdrailsnails.item.ItemRailwayLever;
import net.minecraft.entity.item.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.util.ItemUtils.*;

public class AttackEntityEventHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!(event.target instanceof EntityMinecart cart)) return;

        EntityPlayer player = event.entityPlayer;
        ItemStack held = player.getHeldItem();
        if (held == null || !(held.getItem() instanceof ItemRailwayLever)) return;

        if (!enableRailwayLeverWhackingAbility) return;

        World world = cart.worldObj;
        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                cart.setRollingDirection(-cart.getRollingDirection());
                cart.setRollingAmplitude(10);
                cart.setDamage(cart.getDamage() + 5.0F);
            }
            cart.setDead();

            if (!player.capabilities.isCreativeMode) {
                ItemStack drop = getSpecialDrop(cart);
                if (drop != null) {
                    world.spawnEntityInWorld(new EntityItem(world, cart.posX, cart.posY, cart.posZ, drop));
                }
                held.damageItem(1, player);
            }
            world.playSoundAtEntity(player, "mdrailsnails:item.railway_lever.attack", 0.7f, 1.0f);
        }
        event.setCanceled(true);
    }
}
