package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles configuration settings for vanilla Minecraft content changes in Milkdrinker's Rails&Nails.
 * <p>
 * This class manages configuration options that adjust the properties, recipes, and stack sizes
 * of vanilla Minecraft items, particularly foods. These changes are intended to align with
 * the new balancing introduced by MDRailsNails.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationVanillaChanges {

    // Category Strings
    private static final String categoryNameVan = "vanilla changes";
    private static final String categoryDescriptionVan = "All the Vanilla Changes configuration";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityString1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityString2 = " to be present";

    // Config Options VanillaChanges
    public static boolean enableRecipeChanges;

    public static boolean enableMinecartMaxSpeedGameRule;
    public static int minecartMaxSpeedBaseValue;


    /**
     * Initializes the configuration settings for vanilla Minecraft changes by reading values from the provided configuration file.
     * <p>
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameVan, categoryDescriptionVan);

        enableRecipeChanges = config.getBoolean(
            "01-enableRecipeChanges",
            categoryNameVan,
            true,
            "Changes the following vanilla Crafting Recipes:"
                + "\nBooster Rail - Now gets crafted with Iron Ingots aswell as Gold Ingots and produces 8 instead of 6 Booster Rails when crafted."
                + "\nDetector Rail - Now gets crafted with Copper Ingots aswell as a Stick"
                + "\nActivator Rail - Now gets crafted with Copper Ingots aswell as Redstone"
                + "\n");

        enableMinecartMaxSpeedGameRule = config.getBoolean(
            "02-enableMinecartMaxSpeedGameRule",
            categoryNameVan,
            true,
            enableFeatures + "minecartMaxSpeed Game Rule"
            + "\n");
        minecartMaxSpeedBaseValue = config.getInt(
            "03-minecartMaxSpeedBaseValue",
            categoryNameVan,
            8,
            1,
            10,
            "Regulates the base maximum speed of minecarts, before the minecartMaxSpeed Game Rule is applied.");
    }
}
