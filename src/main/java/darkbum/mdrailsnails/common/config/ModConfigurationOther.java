package darkbum.mdrailsnails.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;

import darkbum.mdrailsnails.MDRailsNails;

/**
 * Handles configuration settings that are marked to be phased out in future versions of Milkdrinker's Rails&Nails.
 * <p>
 * This class manages configuration options that are deprecated or planned for removal,
 * allowing for legacy support and transition handling. Currently, the only configuration
 * handled here is cloud level height per dimension.
 * <p>
 * This class is initialized during the pre-initialization stage of the mod lifecycle.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModConfigurationOther {

    // Category Strings
    private static final String categoryNameOth = "to be phased out";
    private static final String categoryDescriptionOth = "TO BE PHASED OUT | IGNORE";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityString1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityString2 = " to be present";

    // Config Options TOBEPHASEDOUT


    /**
     * Initializes the configuration settings for the "to be phased out" category by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameOth, categoryDescriptionOth);
    }
}
