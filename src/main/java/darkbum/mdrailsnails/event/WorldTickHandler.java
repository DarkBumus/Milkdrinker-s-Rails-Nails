package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import darkbum.mdrailsnails.util.CartLinkHandler;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import java.util.*;

import static darkbum.mdrailsnails.util.CartLinkHandler.*;
import static java.lang.Math.*;

public class WorldTickHandler {

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        World world = event.world;
        List<EntityMinecart> carts = new ArrayList<>();

        for (Object obj : world.loadedEntityList) {
            if (obj instanceof EntityMinecart) {
                carts.add((EntityMinecart) obj);
            }
        }

        Set<UUID> processed = new HashSet<>();

        for (EntityMinecart cart : carts) {
            UUID cartId = cart.getUniqueID();
            Set<UUID> linked = CartLinkHandler.getLinkedCarts(cartId);
            if (linked == null || linked.isEmpty()) continue;

            for (UUID linkedId : linked) {
                if (processed.contains(linkedId)) continue;

                EntityMinecart linkedCart = CartLinkHandler.findCartByUUID(world, linkedId);
                if (linkedCart != null) {
                    applyLinkPhysics(cart, linkedCart);
                }
            }
            processed.add(cartId);
        }
    }

    private void applyLinkPhysics(EntityMinecart cart1, EntityMinecart cart2) {
        if (cart1.worldObj.provider.dimensionId != cart2.worldObj.provider.dimensionId)
            return;

        double dx = cart2.posX - cart1.posX;
        double dz = cart2.posZ - cart1.posZ;
        double distance = sqrt(dx * dx + dz * dz);
        if (distance > 8.0D) {
            unlink(cart1.getUniqueID(), cart2.getUniqueID());
            return;
        }

        double unitX = dx / distance;
        double unitZ = dz / distance;

        double optimalDistance = getOptimalDistance(cart1, cart2);
        double stretch = distance - optimalDistance;

        double stiffness = 0.7D;
        double springX = stiffness * stretch * unitX;
        double springZ = stiffness * stretch * unitZ;

        springX = limitForce(springX);
        springZ = limitForce(springZ);

        double relVelX = cart2.motionX - cart1.motionX;
        double relVelZ = cart2.motionZ - cart1.motionZ;
        double dot = relVelX * unitX + relVelZ * unitZ;

        double damping = 0.4D;
        double dampX = damping * dot * unitX;
        double dampZ = damping * dot * unitZ;

        dampX = limitForce(dampX);
        dampZ = limitForce(dampZ);

        cart1.motionX += springX + dampX;
        cart1.motionZ += springZ + dampZ;
        cart2.motionX -= springX + dampX;
        cart2.motionZ -= springZ + dampZ;

        cart1.motionX *= 1.0D;
        cart1.motionZ *= 1.0D;
        cart2.motionX *= 1.0D;
        cart2.motionZ *= 1.0D;
    }

    @SuppressWarnings("unused")
    private double getOptimalDistance(EntityMinecart a, EntityMinecart b) {
        double base = 0.75D;
        return base + base;
    }

    private double limitForce(double f) {
        double max = 6.0D;
        return copySign(min(abs(f), max), f);
    }
}
