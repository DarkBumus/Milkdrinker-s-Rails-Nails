package darkbum.mdrailsnails.common.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles the configuration settings related to mod compatibility in Milkdrinker's Rails&Nails.
 * <p>
 * This class manages configuration options that affect interaction with other mods,
 * including block and item compatibility as well as general compatibility settings.
 * The settings are read from the "mod_compatibility.cfg" file and its subcategories.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationModCompatibility {

    // Category Strings
    private static final String categoryNameMod = "mod compatibility";
    private static final String categoryDescriptionMod = "All the Mod Compatibility configuration that doesn't touch Blocks or Items";
    private static final String categoryNameModBlo = "mod compatibility | blocks";
    private static final String categoryDescriptionModBlo = "All the Mod Compatibility Blocks configuration";
    private static final String categoryNameModIte = "mod compatibility | items";
    private static final String categoryDescriptionModIte = "All the Mod Compatibility Items configuration";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Mod Compatibility Config Options


    /**
     * Initializes the mod compatibility configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameMod, categoryDescriptionMod);
    }
}
