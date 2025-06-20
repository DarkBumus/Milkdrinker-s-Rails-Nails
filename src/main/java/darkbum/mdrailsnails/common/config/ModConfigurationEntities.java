package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles the configuration settings for entity-related features in Milkdrinker's Rails&Nails.
 * <p>
 * This class is responsible for defining configuration options related to entities,
 * such as enabling or modifying specific entity types.
 * The configuration values are read from the "entities.cfg" file.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationEntities {

    // Category Strings
    private static final String categoryNameEnt = "entities";
    private static final String categoryDescriptionEnt = "All the Entities configuration";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Effects Config Options
    public static boolean enableBetaMinecartBoosting;

    public static boolean enableHaulerMinecart;

    public static boolean enableModernEntities;
    public static boolean enableEarthEntities;


    /**
     * Initializes the entity configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameEnt, categoryDescriptionEnt);

        enableBetaMinecartBoosting = config.getBoolean(
            "01-enableMetaMinecartBoosting",
            categoryNameEnt,
            true,
            "Regulates whether or not to re-implement Pre-Beta1.6 Minecart Boosting.");

        enableHaulerMinecart = config.getBoolean(
            "02-enableHaulerMinecart",
            categoryNameEnt,
            true,
            enableFeatures
            + "\nHauler Minecart"
            + "\n");

        enableModernEntities = config.getBoolean(
            "03-enableModernEntities",
            categoryNameEnt,
            true,
            enableFeatures + "TESTING ENTITIES, IGNORE!");
        enableEarthEntities = config.getBoolean(
            "04-enableEarthEntities",
            categoryNameEnt,
            true,
            enableFeatures + "TESTING ENTITIES, IGNORE!");
    }
}
