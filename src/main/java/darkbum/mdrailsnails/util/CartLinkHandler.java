package darkbum.mdrailsnails.util;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.*;

public class CartLinkHandler {

    private static final Map<UUID, UUID> linkStartMap = new HashMap<>();

    private static final Map<UUID, Set<UUID>> linkedCarts = new HashMap<>();

    private static final Map<World, EntityMinecart> pendingAutoLink = new WeakHashMap<>();

    private static final Map<EntityPlayer, Map<String, Long>> lastAutoMessages = new WeakHashMap<>();
    private static final long AUTO_MESSAGE_COOLDOWN_MS = 3000;

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
            sendStatus(player, "link.created");
        } else {
            linkStartMap.put(playerId, targetId);
            sendStatus(player, "link.started");
        }
        return true;
    }

    private static void sendStatus(EntityPlayer player, String key) {
        player.addChatMessage(new ChatComponentTranslation("item.railway_lever.mess." + key));
    }

    private static void sendAutoStatus(EntityPlayer player, String key) {
        long currentTime = System.currentTimeMillis();

        Map<String, Long> playerCooldowns = lastAutoMessages.computeIfAbsent(player, p -> new HashMap<>());
        Long lastTime = playerCooldowns.get(key);

        if (lastTime == null || (currentTime - lastTime) >= AUTO_MESSAGE_COOLDOWN_MS) {
            player.addChatMessage(new ChatComponentTranslation("tile.coupling_rail.mess." + key));
            playerCooldowns.put(key, currentTime);
        }
    }

    private static void link(UUID a, UUID b) {
        linkedCarts.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        linkedCarts.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    public static void unlink(UUID a, UUID b) {
        if (linkedCarts.containsKey(a)) {
            linkedCarts.get(a).remove(b);
            if (linkedCarts.get(a).isEmpty()) {
                linkedCarts.remove(a);
            }
        }

        if (linkedCarts.containsKey(b)) {
            linkedCarts.get(b).remove(a);
            if (linkedCarts.get(b).isEmpty()) {
                linkedCarts.remove(b);
            }
        }
    }

    private static boolean areLinked(UUID a, UUID b) {
        return linkedCarts.getOrDefault(a, Collections.emptySet()).contains(b);
    }

    static int getLinkCount(UUID cart) {
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

    public static void handleAutomaticLink(World world, EntityMinecart cart) {
        EntityMinecart first = pendingAutoLink.get(world);

        if (first == null || first == cart || first.isDead || cart.isDead) {
            pendingAutoLink.put(world, cart);
            notifyNearestPlayer(world, cart, "link.started");
            return;
        }

        UUID id1 = first.getUniqueID();
        UUID id2 = cart.getUniqueID();

        if (areLinked(id1, id2)) {
            pendingAutoLink.remove(world);
            notifyNearestPlayer(world, cart, "link.failed_already_linked");
            return;
        }

        double distanceSq = first.getDistanceSqToEntity(cart);
        if (distanceSq > 9.0D) {
            pendingAutoLink.remove(world);
            notifyNearestPlayer(world, cart, "link.failed_too_far");
            return;
        }

        if (getLinkCount(id1) >= 2 || getLinkCount(id2) >= 2 || wouldExceedGroupLimit(id1, id2)) {
            pendingAutoLink.remove(world);
            notifyNearestPlayer(world, cart, "link.failed_too_many");
            return;
        }

        link(id1, id2);
        pendingAutoLink.remove(world);
        notifyNearestPlayer(world, cart, "link.created");
    }

    @SuppressWarnings("unused")
    public static void handleAutomaticUnlink(World world, EntityMinecart cart) {
        UUID id = cart.getUniqueID();

        Set<UUID> linked = new HashSet<>(getLinkedCarts(id));
        for (UUID other : linked) {
            unlink(id, other);
            notifyNearestPlayer(world, cart, "link.broken");
        }
    }

    private static void notifyNearestPlayer(World world, EntityMinecart cart, String key) {
        double radius = 16.0D;
        AxisAlignedBB aabb = cart.boundingBox.expand(radius, radius, radius);

        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, aabb);

        EntityPlayer closest = null;
        double closestDistSq = Double.MAX_VALUE;

        for (EntityPlayer player : players) {
            double distSq = player.getDistanceSqToEntity(cart);
            if (distSq < closestDistSq) {
                closestDistSq = distSq;
                closest = player;
            }
        }

        if (closest != null) {
            sendAutoStatus(closest, key);
        }
    }
}
