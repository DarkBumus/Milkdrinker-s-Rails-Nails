package darkbum.mdrailsnails.event;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.*;

public class CartLinkHandler {

    private static final Map<UUID, UUID> linkStartMap = new HashMap<>();

    private static final Map<UUID, Set<UUID>> linkedCarts = new HashMap<>();

    public static boolean handleLink(EntityPlayer player, EntityMinecart targetCart) {
        UUID playerId = player.getUniqueID();
        UUID targetId = targetCart.getUniqueID();

        if (linkStartMap.containsKey(playerId)) {
            UUID startId = linkStartMap.remove(playerId);

            if (startId.equals(targetId)) {
                sendStatus(player, "link.failed_same");
                return false;
            }

            EntityMinecart startCart = findCartByUUID(player.worldObj, startId);
            if (startCart == null) {
                sendStatus(player, "link.failed_not_found");
                return false;
            }

            double distanceSq = startCart.getDistanceSqToEntity(targetCart);
            if (distanceSq > 9.0D) {
                sendStatus(player, "link.failed_too_far");
                return false;
            }

            if (areLinked(startId, targetId)) {
                unlink(startId, targetId);
                sendStatus(player, "link.broken");
                return true;
            }

            if (getLinkCount(startId) >= 2 || getLinkCount(targetId) >= 2 || wouldExceedGroupLimit(startId, targetId)) {
                sendStatus(player, "link.failed_too_many");
                return false;
            }

            link(startId, targetId);
            sendStatus(player, "link.finished");
        } else {
            linkStartMap.put(playerId, targetId);
            sendStatus(player, "link.started");
        }
        return true;
    }

    private static void sendStatus(EntityPlayer player, String key) {
        player.addChatMessage(new ChatComponentTranslation("item.railway_lever." + key));
    }

    private static void link(UUID a, UUID b) {
        linkedCarts.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        linkedCarts.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    private static void unlink(UUID a, UUID b) {
        linkedCarts.getOrDefault(a, new HashSet<>()).remove(b);
        linkedCarts.getOrDefault(b, new HashSet<>()).remove(a);
    }

    private static boolean areLinked(UUID a, UUID b) {
        return linkedCarts.getOrDefault(a, Collections.emptySet()).contains(b);
    }

    private static int getLinkCount(UUID cart) {
        return linkedCarts.getOrDefault(cart, Collections.emptySet()).size();
    }

    public static EntityMinecart findCartByUUID(World world, UUID uuid) {
        for (Object obj : world.loadedEntityList) {
            if (obj instanceof EntityMinecart cart) {
                if (cart.getUniqueID().equals(uuid)) {
                    return cart;
                }
            }
        }
        return null;
    }

    private static boolean wouldExceedGroupLimit(UUID a, UUID b) {
        Set<UUID> group = new HashSet<>();
        collectLinkedCarts(group, a);
        collectLinkedCarts(group, b);
        return group.size() > 4;
    }

    private static void collectLinkedCarts(Set<UUID> visited, UUID start) {
        if (!visited.add(start)) return;
        for (UUID linked : linkedCarts.getOrDefault(start, Collections.emptySet())) {
            collectLinkedCarts(visited, linked);
        }
    }

    public static Set<UUID> getLinkedCarts(UUID id) {
        return linkedCarts.getOrDefault(id, Collections.emptySet());
    }
}
