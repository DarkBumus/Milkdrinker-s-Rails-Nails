package darkbum.mdrailsnails.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

import static cpw.mods.fml.common.Loader.isModLoaded;
import static cpw.mods.fml.common.registry.GameRegistry.*;

/**
 * External Loader class.
 *
 * @author DarkBum
 * @since 2.0.0
 */
public class ModExternalLoader {

    public static boolean efr = isModLoaded("etfuturum");
    public static boolean cfb = isModLoaded("campfirebackport");

    public static final Map<String, Block> etFuturumBlocks = new HashMap<>();
    public static final Map<String, Item> etFuturumItems = new HashMap<>();
    public static final Map<String, Block> campfireBackportBlocks = new HashMap<>();

    /**
     * Initializes all external loaders.
     */
    public static void init() {
        loadAll();
    }

    /**
     * Loads external items and blocks from all supported mods.
     * Checks if each mod is loaded before attempting to load its items and blocks.
     */
    public static void loadAll() {
        if (efr) loadEtFuturum();
        if (cfb) loadCampfireBackport();
    }

    /**
     * Loads item and block references from Et Futurum Requiem.
     */
    public static void loadEtFuturum() {
        if (!efr) return;
        etFuturumBlocks.put("blue_ice", findBlock("etfuturum", "blue_ice"));
        etFuturumBlocks.put("deepslate", findBlock("etfuturum", "deepslate"));
        etFuturumBlocks.put("magma", findBlock("etfuturum", "magma"));
        etFuturumBlocks.put("nether_fungus", findBlock("etfuturum", "nether_fungus"));
        etFuturumBlocks.put("honeycomb_block", findBlock("etfuturum", "honeycomb_block"));
        etFuturumBlocks.put("beehive", findBlock("etfuturum", "beehive"));

        etFuturumItems.put("suspicious_stew", findItem("etfuturum", "suspicious_stew"));
        etFuturumItems.put("mutton_raw", findItem("etfuturum", "mutton_raw"));
        etFuturumItems.put("mutton_cooked", findItem("etfuturum", "mutton_cooked"));
        etFuturumItems.put("rabbit_raw", findItem("etfuturum", "rabbit_raw"));
        etFuturumItems.put("rabbit_cooked", findItem("etfuturum", "rabbit_cooked"));
        etFuturumItems.put("rabbit_stew", findItem("etfuturum", "rabbit_stew"));
        etFuturumItems.put("beetroot", findItem("etfuturum", "beetroot"));
        etFuturumItems.put("beetroot_seeds", findItem("etfuturum", "beetroot_seeds"));
        etFuturumItems.put("beetroot_soup", findItem("etfuturum", "beetroot_soup"));
        etFuturumItems.put("chorus_fruit", findItem("etfuturum", "chorus_fruit"));
        etFuturumItems.put("sweet_berries", findItem("etfuturum", "sweet_berries"));
        etFuturumItems.put("dye", findItem("etfuturum", "dye"));
        etFuturumItems.put("honey_bottle", findItem("etfuturum", "honey_bottle"));
    }

    /**
     * Loads block references from Campfire Backport.
     */
    public static void loadCampfireBackport() {
        if (!cfb) return;
        campfireBackportBlocks.put("campfire", findBlock("campfirebackport", "campfire"));
        campfireBackportBlocks.put("soul_campfire", findBlock("campfirebackport", "soul_campfire"));
    }
}
