package darkbum.mdrailsnails.common.config;

import net.minecraftforge.common.config.Configuration;

import java.util.Locale;

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
    private static final String categoryNameBloRai = "blocks | rails";
    private static final String categoryDescriptionBloRai = "All the Rails configuration that doesn't touch Mod Compatibility";

    // Replacement Strings
    private static final String enableFeatures = "Enables the following features:";
    private static final String compatibilityStringEFR1 = "Notes: This is for when you have Et Futurum Requiem installed, but for some reason, don't want ";
    private static final String compatibilityStringEFR2 = " to be present";

    // Blocks Config Options
    public static boolean enableDirectionalRedstoneBlock;
    public static boolean enableWindlass;
    public static int windlassReach;
    public static boolean enableConductor;
    public static boolean enableDistributor;
    public static boolean enableFilter;
    public static boolean enableUpper;

    // Rail Blocks Config Options
    public static boolean enableJunctionRail;
    public static boolean enableWyeRail;
    public static int slowdownRailSpeed;
    public static boolean enableOneWayRail;
    public static boolean enableLockingRail;
    public static boolean enableDismountingRail;
    public static int dismountingRailDistance;
    public static boolean enableMountingRail;
    public static int mountingRailDistance;
    public static boolean enableCouplingRail;
    public static boolean enableSuspendedRail;
    public static int suspendedRailReach;
    public static boolean enableDisposingRail;
    public static boolean enableLaunchingRail;
    public static boolean enableCartDislocatingRail;
    public static boolean enableOneWayDetectorRail;
    public static boolean enableCartDetectorRail;
    public static boolean enableSlowdownRail;
    public static boolean enableHighSpeedRails;
    public static int highSpeedRailsSpeed;
    public static boolean enableHighSpeedConsequences;


    /**
     * Initializes the block configuration settings by reading values from the provided configuration file.
     *
     * @param config The configuration file object to read from.
     */
    public static void init(Configuration config) {
        config.setCategoryComment(categoryNameBlo, categoryDescriptionBlo);

        enableDirectionalRedstoneBlock = config.getBoolean(
            "01-enableDirectionalRedstoneBlock",
            categoryNameBlo,
            true,
            enableFeatures + "\nDirectional Redstone Block");

        enableWindlass = config.getBoolean(
            "02-enableWindlass",
            categoryNameBlo,
            true,
            enableFeatures + "\nWindlass"
            + "\n");
        windlassReach = config.getInt(
            "03-windlassReach",
            categoryNameBlo,
            1,
            1,
            1,
            "Regulates the reach (per iconFront level) that the windlass reels out."
            + "\n(Setting this to, for example, 3 means that the windlass will provide 3 rails when powered with one level of iconFront, 6 with two levels, 9 with three levels, etc.)");

        enableConductor = config.getBoolean(
            "04-enableConductor",
            categoryNameBlo,
            true,
            enableFeatures + "\nConductor"
            + "\n");

        enableDistributor = config.getBoolean(
            "05-enableDistributor",
            categoryNameBlo,
            true,
            enableFeatures + "\nDistributor");

        enableFilter = config.getBoolean(
            "06-enableFilter",
            categoryNameBlo,
            true,
            enableFeatures + "\nFilter");

        enableUpper = config.getBoolean(
            "07-enableUpper",
            categoryNameBlo,
            true,
            enableFeatures + "\nUpper");


        config.setCategoryComment(categoryNameBloRai, categoryDescriptionBloRai);

        enableJunctionRail = config.getBoolean(
            "01-enableJunctionRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nJunction Rail"
            + "\n");

        enableWyeRail = config.getBoolean(
            "02-enableJunctionRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nWye Rail"
            + "\n");

        enableSlowdownRail = config.getBoolean(
            "02-enableSlowdownRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nSlowdown Rail"
            + "\n");
        slowdownRailSpeed = config.getInt(
            "03-slowdownRailSpeed",
            categoryNameBloRai,
            3,
            1,
            9,
            "Regulates the speed penalty of the Slowdown Rail compared to regular rails. 1 = 10%, 9 = 90%");

        enableOneWayRail = config.getBoolean(
            "04-enableOneWayRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nOne-Way Rail"
            + "\n");

        enableLockingRail = config.getBoolean(
            "05-enableLockingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nLocking Rail"
            + "\n");

        enableDismountingRail = config.getBoolean(
            "06-enableDismountingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nDismounting Rail"
            + "\n");
        dismountingRailDistance = config.getInt(
            "07-dismountingRailDistance",
            categoryNameBloRai,
            1,
            1,
            3,
            "Regulates the distance that the Dismounting Rail drops entities off at");

        enableMountingRail = config.getBoolean(
            "08-enableMountingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nMounting Rail"
            + "\n");
        mountingRailDistance = config.getInt(
            "09-mountingRailDistance",
            categoryNameBloRai,
            2,
            1,
            5,
            "Regulates the distance that the Mounting Rail can pick entities off from");

        enableCouplingRail = config.getBoolean(
            "10-enableCouplingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nCoupling Rail"
            + "\n");

        enableSuspendedRail = config.getBoolean(
            "11-enableSuspendedRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nSuspended Rail"
            + "\n");
        suspendedRailReach = config.getInt(
            "12-suspendedRailReach",
            categoryNameBloRai,
            3,
            1,
            10,
            "Regulates the reach of suspended rails, before you can't place any more.");

        enableDisposingRail = config.getBoolean(
            "13-enableDisposingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nDisposing Rail"
            + "\n");

        enableLaunchingRail = config.getBoolean(
            "14-enableLaunchingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nLaunching Rail"
            + "\n");

        enableCartDislocatingRail = config.getBoolean(
            "15-enableCartDislocatingRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nCart Dislocating Rail"
            + "\n");

        enableOneWayDetectorRail = config.getBoolean(
            "16-enableOneWayDetectorRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nOne-Way Detector Rail"
            + "\n");

        enableCartDetectorRail = config.getBoolean(
            "17-enableCartDetectorRail",
            categoryNameBloRai,
            true,
            enableFeatures + "\nCart Detector Rail"
            + "\n");

        enableHighSpeedRails = config.getBoolean(
            "18-enableHighSpeedRails",
            categoryNameBloRai,
            true,
            enableFeatures + "\nHigh-Speed Rail" + "\nHigh-Speed Booster Rail" + "\nHigh-Speed Transition Rail"
            + "\n");
        highSpeedRailsSpeed = config.getInt(
            "19-highSpeedRailsSpeed",
            categoryNameBloRai,
            3,
            2,
            4,
            "Regulates the speed multiplier of High-Speed Rails compared to regular rails: 2 = 2x, 4 = 4x");
        enableHighSpeedConsequences = config.getBoolean(
            "20-enableHighSpeedConsequences",
            categoryNameBloRai,
            true,
            "Regulates whether or not high speed has dangerous consequences");
    }
}
