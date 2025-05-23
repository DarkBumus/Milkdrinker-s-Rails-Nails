package darkbum.mdrailsnails.init;

import darkbum.mdrailsnails.MDRailsNails;

import cpw.mods.fml.common.registry.EntityRegistry;
import darkbum.mdrailsnails.entity.EntityHaulerMinecart;

/**
 * Entities class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModEntities {

    /**
     * Initializes and registers all entities.
     */
    public static void init() {
        EntityRegistry.registerModEntity(EntityHaulerMinecart.class, "hauler_minecart", 0, MDRailsNails.instance, 128, 1, true);
    }
}
