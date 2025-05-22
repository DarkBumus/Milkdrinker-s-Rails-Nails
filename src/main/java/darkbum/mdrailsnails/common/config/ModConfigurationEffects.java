package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

/**
 * Handles the configuration settings for potion effects in Milkdrinker's Rails&Nails.
 * <p>
 * This class is responsible for defining the IDs and other properties of custom potion effects
 * introduced by the mod, such as "Swarmed", "Well Fed", and "Inspired".
 * The configuration values are read from the "effects.cfg" file.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationEffects {

    // Category Strings
    private static final String categoryNameEff = "effects";
    private static final String categoryDescriptionEff = "All the Effects configuration";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Effects Config Options


    /**
     * Initializes the effect configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameEff, categoryDescriptionEff);
    }
}
