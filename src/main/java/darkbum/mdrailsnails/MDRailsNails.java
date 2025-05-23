package darkbum.mdrailsnails;

import java.io.File;

import darkbum.mdrailsnails.common.proxy.CommonProxy;
import darkbum.mdrailsnails.event.MinecartCollider;
import darkbum.mdrailsnails.init.*;

import darkbum.mdrailsnails.init.recipes.ModRemovedRecipes;
import darkbum.mdrailsnails.init.recipes.ModShapedRecipes;
import darkbum.mdrailsnails.init.recipes.ModShapelessRecipes;
import darkbum.mdrailsnails.init.recipes.ModSmeltingRecipes;
import net.minecraft.entity.item.EntityMinecart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkbum.mdrailsnails.api.nei.NEIConfig;
import darkbum.mdrailsnails.common.config.ModConfigurationBase;

import static cpw.mods.fml.common.Loader.isModLoaded;
import static cpw.mods.fml.common.registry.GameRegistry.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationEntities.*;
import static net.minecraft.util.EnumChatFormatting.*;

/**
 * Main mod class for Milkdrinker's Rails&Nails.
 * <p>
 * Handles the Forge Mod Loader events and sets up initialization, configuration and registration of all subclasses,
 * aswell as compatibility with other mods and NEI integration.
 *
 * @author DarkBum
 * @since 1.0.0
 */
@Mod(
    modid = MDRailsNails.MODID,
    name = MDRailsNails.NAME,
    version = MDRailsNails.VERSION,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = MDRailsNails.DEPENDENCIES)
public class MDRailsNails {

    /** Internal mod ID used for registration and logging. */
    public static final String MODID = "mdrailsnails";

    /** Name of the mod. */
    public static final String NAME = "Milkdrinker's Rails&Nails";

    /** Version string, replaced automatically during build. */
    public static final String VERSION = "GRADLETOKEN_VERSION";

    /** Dependency declaration. Requires to load after Et Futurum Requiem. */
    public static final String DEPENDENCIES = "after:etfuturum";

    /** Logger instance for rebug and runtime messages. **/
    public static final Logger logger = LogManager.getLogger(MODID);

    /** Root configuration object for mod configs. */
    public static ModConfigurationBase config;

    /** Singleton instance of the mod, used internally by Forge Mod Loader. */
    @Instance("mdrailsnails")
    public static MDRailsNails instance;

    /** Proxy for client/server-specific operations (GUI, Rendering, different registries, etc. */
    @SidedProxy(clientSide = "darkbum.mdrailsnails.common.proxy.ClientProxy", serverSide = "darkbum.mdrailsnails.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Called during the Forge Mod Loader Pre-Initialization phase.
     * <p>
     * Handles initial setup, config loading, initialization for blocks, items, potions and biomes.
     * Handles proxy pre-init methods.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.info("Started Milkdrinker's Rails&Nails PreInitialization");

        // Set custom mod metadata for display in the mod list.
        event.getModMetadata().name = GOLD + NAME + WHITE;
        event.getModMetadata().version = YELLOW + VERSION;
        event.getModMetadata().credits = AQUA
            + "Thanks to contributors ";

        // Load and initialize configuration files
        File configDir = new File(event.getModConfigurationDirectory(), "mdrailsnails");
        config = new ModConfigurationBase(configDir);
        config.preInit();

        // Register core systems (potions, blocks, items, tile entities, entities and biomes)
        ModBlocks.init();
        ModItems.init();
        ModTileEntities.init();
        ModEntities.init();

        // Register miscellaneous registries and handlers
        registerFuelHandler(new ModFuelHandler());
        ModFlammabilityHandler.init();
        if (enableBetaMinecartBoosting) EntityMinecart.setCollisionHandler(new MinecartCollider());

        proxy.preInit(event);

        logger.info("Finished Milkdrinker's Rails&Nails PreInitialization");
    }

    /**
     * Called during the Forge Mod Loader Initialization phase.
     * <p>
     * Handles runtime systems.
     * Handles proxy init methods.
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Started Milkdrinker's Rails&Nails Initialization");

        config.init();

        // Initialize Achievements
        ModAchievementList.init();

        proxy.init(event);
        proxy.setRenderers();

        logger.info("Finished Milkdrinker's Rails&Nails Initialization");
    }

    /**
     * Called during Forge Mod Loader Post-Initialization phase.
     * <p>
     * Handles compatibility-sensitive systems, recipe loading, ore dictionary, NEI integration and deprecated features.
     * Handles proxy post-init methods.
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Started Milkdrinker's Rails&Nails PostInitialization");

        config.postInit();

        // Load mod items, external value changes and OreDictionary
        ModExternalLoader.init();
        ModExternalValueRegistry.init();
        ModOreDictionary.init();

        // Load internal recipes
        ModRemovedRecipes.init();
        ModShapedRecipes.init();
        ModShapelessRecipes.init();
        ModSmeltingRecipes.init();

        // Register NEI recipes if NEI is loaded
        if (isModLoaded("NotEnoughItems")) {
            new NEIConfig().loadConfig();
            logger.info("NEI Config loaded");
        }

        proxy.postInit(event);

        logger.info("Finished Milkdrinker's Rails&Nails PostInitialization");
    }
}

/*
 * //TO-DO-LIST//
 * - Junction Rail
 * - Locking Rail
 * - Disembarking Rail
 * - Embarking Rail
 * - Coupler/Decoupler Rail
 * - One-Way Rail
 * - Directional Detector Rail
 * - Suspended Rail
 * - Disposal Rail
 * - Slow-down Rail
 * - Andesite-Fitted Rails
 * - High-speed Rails
 * - Priming Rail (?)
 * - Launcher Rail
 * - Elevator Rail
 * - Inventory Furnace Minecart
 * - Coupling mechanic
 * - Rail Layer Minecart
 * - Rail Remover Minecart
 * - Chunk Loader
 * - Chunk Loader Minecart
 * - Directional Redstone Block
 * - Block Breaker
 * - Block Placer
 * - Inventory Interface (?)
 * - Goggles (?)
 * - Minecart Speed Gamerule (?)
 *
 * - Fix the Hauler Cart GUI
 */

