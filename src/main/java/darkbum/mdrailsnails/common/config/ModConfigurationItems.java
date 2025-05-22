package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles the configuration settings for item-related features in Milkdrinker's Rails&Nails.
 * <p>
 * This class is responsible for defining configuration options related to items,
 * including regular items, armor/tools, and food items. The configuration values
 * are read from the "items.cfg" file and its subcategories.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationItems {

    // Category Strings
    private static final String categoryNameIte = "items";
    private static final String categoryDescriptionIte = "All the Items configuration that doesn't touch Armor, Food or Mod Compatibility";
    private static final String categoryNameIteArm = "items | armor + tools";
    private static final String categoryDescriptionIteArm = "All the Armor and Tool Items configuration";
    private static final String categoryNameIteFoo = "items | food";
    private static final String categoryDescriptionIteFoo = "All the Food Items configuration";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Items Config Options
    public static boolean enableRailwayLever;
    public static boolean enableRailwayLeverDebugAbility;


    /**
     * Initializes the item configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameIteArm, categoryDescriptionIte);

        enableRailwayLever = config.getBoolean(
            "01-enableRailwayLever",
            categoryNameIteArm,
            true,
            enableFeatures
                + "\nRailway Lever"
                + "\n");

        enableRailwayLeverDebugAbility = config.getBoolean(
            "02-enableRailwayLeverDebugAbility",
            categoryNameIteArm,
            true,
            "Regulates whether or not the Railway Lever should be able to change powered rails from active to inactive and vice-versa");
    }
}
