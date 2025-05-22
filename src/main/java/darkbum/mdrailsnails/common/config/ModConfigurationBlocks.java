package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles the configuration settings for all block-related features in Milkdrinker's Rails&Nails.
 * <p>
 * This includes enabling or disabling specific blocks, adjusting their properties,
 * and controlling their behavior, such as drying speeds, growth rates, and operational speeds.
 * Configuration settings are organized into categories and subcategories.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle
 * and reads values from the "blocks.cfg" configuration file.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationBlocks {

    // Category Strings
    private static final String categoryNameBlo = "blocks";
    private static final String categoryDescriptionBlo = "All the Blocks configuration that doesn't touch Mod Compatibility";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Blocks Config Options


    /**
     * Initializes the block configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameBlo, categoryDescriptionBlo);
    }
}
