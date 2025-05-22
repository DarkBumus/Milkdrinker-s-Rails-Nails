package darkbum.mdrailsnails.common.proxy;

import darkbum.mdrailsnails.creativetab.TabMDRNBlocks;
import darkbum.mdrailsnails.creativetab.TabMDRNItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Server-side proxy class responsible for server-side initializations like world generation.
 *
 * @author DarkBum
 * @since 1.9.f
 */
public class CommonProxy {

    /** Creative tabs for items and blocks. */
    public static CreativeTabs tabMDRNItems = new TabMDRNItems("mdrn_items");
    public static CreativeTabs tabMDRNBlocks = new TabMDRNBlocks("mdrn_blocks");

    /**
     * Called during Forge Mod Loader Pre-Initialization phase.
     * <p>
     * Handles event handler registering, network registry, GUI handler and dispenser behavior.
     */
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        // Register event handlers
        registerEventHandlers();
    }

    /**
     * Called during Forge Mod Loader Initialization phase.
     * <p>
     * Handles world generators and chest gen hooks.
     */
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
    }

    /**
     * Called during Forge Mod Loader Post-Initialization phase.
     */
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
    }

    /**
     * Registers the event handlers for pre-initialization.
     */
    private void registerEventHandlers() {
//        register(new AchievementEventHandler());
    }

    /**
     * Helper method for quick-registering event handlers.
     */
    private void register(Object handler) {
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
    }

    /**
     * Placeholder method for client-side renderer registration.
     * Overriden in {@link ClientProxy}.
     */
    public void setRenderers() {
    }
}
