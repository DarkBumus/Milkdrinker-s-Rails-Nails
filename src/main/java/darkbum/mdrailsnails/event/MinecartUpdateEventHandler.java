package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkbum.mdrailsnails.block.rails.IHighSpeedRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;

public class MinecartUpdateEventHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onMinecartUpdate(MinecartUpdateEvent event) {
        EntityMinecart cart = event.minecart;
        World world = cart.worldObj;

        String speedState = cart.getEntityData().getString("SpeedState");

        int x = MathHelper.floor_double(cart.posX);
        int y = MathHelper.floor_double(cart.posY);
        int z = MathHelper.floor_double(cart.posZ);
        Block rail = world.getBlock(x, y, z);
        int railMeta = world.getBlockMetadata(x, y, z);

        boolean isHighSpeedRail = rail instanceof IHighSpeedRail;
        boolean isCurveOrSlope = isRailMetaDangerous(rail, railMeta);

        if ("HIGHSPEED".equals(speedState)) {
            if (!isHighSpeedRail) {
                explodeCart(world, cart);
            }
        } else if ("WARNING".equals(speedState)) {
            if (!isHighSpeedRail) {
                spawnWarningParticles(world, cart);
            }
        }

        if ("NORMAL".equals(speedState)) {
            cart.getEntityData().removeTag("SpeedState");
        }
    }

    private static boolean isRailMetaDangerous(Block rail, int railMeta) {
        if ((railMeta >= 2 && railMeta <= 5) || (railMeta >= 10 && railMeta <= 13)) {
            return rail instanceof BlockRailBase;
        }

        if (railMeta >= 6 && railMeta <= 9) {
            return rail instanceof BlockRail;
        }
        return false;
    }

    private static void explodeCart(World world, EntityMinecart cart) {
        world.createExplosion(cart, cart.posX, cart.posY, cart.posZ, 1.0F, true);
        cart.setDead();
    }

    private static void spawnWarningParticles(World world, EntityMinecart cart) {
        if (world.isRemote) return;
        world.spawnParticle("smoke", cart.posX, cart.posY + 0.5, cart.posZ, 0.0, 0.1, 0.0);
        world.playSoundEffect(cart.posX, cart.posY, cart.posZ, "random.fizz", 1.0F, 1.0F);
    }
}
