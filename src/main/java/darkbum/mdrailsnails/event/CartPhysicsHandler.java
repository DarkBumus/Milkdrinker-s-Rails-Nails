package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import java.util.*;

public class CartPhysicsHandler {

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

    private void applyLinkPhysics(EntityMinecart a, EntityMinecart b) {
        double dx = b.posX - a.posX;
        double dz = b.posZ - a.posZ;
        double distanceSq = dx * dx + dz * dz;
        double maxDistance = 2.0D;

        if (distanceSq > maxDistance * maxDistance) {
            double distance = Math.sqrt(distanceSq);
            double pullStrength = 0.1;

            double pullX = (dx / distance) * pullStrength;
            double pullZ = (dz / distance) * pullStrength;

            a.motionX += pullX;
            a.motionZ += pullZ;
            b.motionX -= pullX;
            b.motionZ -= pullZ;

            a.motionX *= 0.9;
            a.motionZ *= 0.9;
            b.motionX *= 0.9;
            b.motionZ *= 0.9;
        }
    }
}
