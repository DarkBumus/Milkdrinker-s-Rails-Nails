package darkbum.mdrailsnails.init;

import darkbum.mdrailsnails.MDRailsNails;

import darkbum.mdrailsnails.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import java.util.HashMap;
import java.util.Map;

import static cpw.mods.fml.common.registry.EntityRegistry.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationEntities.*;

/**
 * Entities class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModEntities {

    private static final Map<Class<? extends Entity>, Integer> ENTITY_IDS = new HashMap<>();
    static int startEntityId = 610;

    /**
     * Initializes and registers all entities.
     */
    public static void init() {
        if (enableHaulerMinecart) {
            int haulerMinecartId = getUniqueEntityId();
            registerModEntity(EntityMinecartHauler.class, "hauler_minecart", haulerMinecartId, MDRailsNails.instance, 128, 1, true);
        }

        if (enableModernEntities) {
            int newCowId = getUniqueEntityId();
            registerModEntity(EntityNewCow.class, "new_cow", newCowId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityNewCow.class, newCowId, 4470310, 10592673);

            int warmCowId = getUniqueEntityId();
            registerModEntity(EntityWarmCow.class, "warm_cow", warmCowId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityWarmCow.class, warmCowId, 4470310, 10592673);

            int coldCowId = getUniqueEntityId();
            registerModEntity(EntityColdCow.class, "cold_cow", coldCowId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityColdCow.class, coldCowId, 4470310, 10592673);

            int newPigId = getUniqueEntityId();
            registerModEntity(EntityNewPig.class, "new_pig", newPigId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityNewPig.class, newPigId, 15771042, 14377823);

            int warmPigId = getUniqueEntityId();
            registerModEntity(EntityWarmPig.class, "warm_pig", warmPigId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityWarmPig.class, warmPigId, 15771042, 14377823);

            int coldPigId = getUniqueEntityId();
            registerModEntity(EntityColdPig.class, "cold_pig", coldPigId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityColdPig.class, coldPigId, 15771042, 14377823);

            int newChickenId = getUniqueEntityId();
            registerModEntity(EntityNewChicken.class, "new_chicken", newChickenId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityNewChicken.class, newChickenId, 10592673, 16711680);

            int warmChickenId = getUniqueEntityId();
            registerModEntity(EntityWarmChicken.class, "warm_chicken", warmChickenId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityWarmChicken.class, warmChickenId, 10592673, 16711680);

            int coldChickenId = getUniqueEntityId();
            registerModEntity(EntityColdChicken.class, "cold_chicken", coldChickenId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityColdChicken.class, coldChickenId, 10592673, 16711680);

            int drownedId = getUniqueEntityId();
            registerModEntity(EntityDrowned.class, "drowned", drownedId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityDrowned.class, drownedId, 44975, 7969893);
        }
        if (enableEarthEntities) {
            int boulderingZombieId = getUniqueEntityId();
            registerModEntity(EntityBoulderingZombie.class, "bouldering_zombie", boulderingZombieId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityBoulderingZombie.class, boulderingZombieId, 44975, 7969893);

            int lobberZombieId = getUniqueEntityId();
            registerModEntity(EntityLobberZombie.class, "lobber_zombie", lobberZombieId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityLobberZombie.class, lobberZombieId, 44975, 7969893);

            int cluckshroomId = getUniqueEntityId();
            registerModEntity(EntityCluckshroom.class, "cluckshroom", cluckshroomId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityCluckshroom.class, cluckshroomId, 10592673, 16711680);

            int fancyChickenId = getUniqueEntityId();
            registerModEntity(EntityFancyChicken.class, "fancy_chicken", fancyChickenId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityFancyChicken.class, fancyChickenId, 10592673, 16711680);

            int muddyPigId = getUniqueEntityId();
            registerModEntity(EntityMuddyPig.class, "muddy_pig", muddyPigId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityMuddyPig.class, muddyPigId, 15771042, 14377823);

            int newIronGolemId = getUniqueEntityId();
            registerModEntity(EntityNewIronGolem.class, "new_iron_golem", newIronGolemId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityNewIronGolem.class, newIronGolemId, 14405058, 7643954);

            int furnaceGolemId = getUniqueEntityId();
            registerModEntity(EntityFurnaceGolem.class, "furnace_golem", furnaceGolemId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityFurnaceGolem.class, furnaceGolemId, 14405058, 7643954);

            int vilerWitchId = getUniqueEntityId();
            registerModEntity(EntityVilerWitch.class, "viler_witch", vilerWitchId, MDRailsNails.instance, 64, 3, true);
            registerEntityEgg(EntityVilerWitch.class, vilerWitchId, 3407872, 5349438);
        }
    }

    /**
     * Finds a unique entity ID that is not currently in use.
     * This method ensures that entity IDs do not conflict with other mods.
     *
     * @return a unique entity ID.
     */
    public static int getUniqueEntityId() {
        while (EntityList.getStringFromID(startEntityId) != null) {
            startEntityId++;
        }
        int id = startEntityId;
        startEntityId++;
        return id;
    }

    /**
     * Registers a spawn egg for a custom entity.
     * The egg will be assigned a primary and secondary color for its appearance in the game.
     *
     * @param entity        The entity class to register the egg for.
     * @param primaryColor  The primary color of the spawn egg.
     * @param secondaryColor The secondary color of the spawn egg.
     */
    public static void registerEntityEgg(Class<? extends Entity> entity, int meta, int primaryColor, int secondaryColor) {
        EntityList.IDtoClassMapping.put(meta, entity);
        EntityList.entityEggs.put(meta, new EntityList.EntityEggInfo(meta, primaryColor, secondaryColor));
        ENTITY_IDS.put(entity, meta);
    }

    /**
     * Retrieves the spawn egg meta associated with the given entity class.
     *
     * @param clazz The entity class for which to get the spawn egg ID.
     * @return the spawn egg metadata if registered, otherwise null.
     */
    public static Integer getEntityEggId(Class<? extends Entity> clazz) {
        return ENTITY_IDS.get(clazz);
    }
}
