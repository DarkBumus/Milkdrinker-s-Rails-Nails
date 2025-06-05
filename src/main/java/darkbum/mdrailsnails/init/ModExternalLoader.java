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
 * @since 1.0.0
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
        etFuturumBlocks.put("slime_block", findBlock("etfuturum", "slime"));
        etFuturumBlocks.put("blast_furnace", findBlock("etfuturum", "blast_furnace"));
        etFuturumBlocks.put("lit_blast_furnace", findBlock("etfuturum", "lit_blast_furnace"));

        etFuturumItems.put("netherite_ingot", findItem("etfuturum", "netherite_ingot"));
        etFuturumItems.put("dye", findItem("etfuturum", "dye"));
        etFuturumItems.put("copper_ingot", findItem("etfuturum", "copper_ingot"));
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
