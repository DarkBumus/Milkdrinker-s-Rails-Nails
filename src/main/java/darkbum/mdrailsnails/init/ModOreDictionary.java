package darkbum.mdrailsnails.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static darkbum.mdrailsnails.init.ModExternalLoader.*;
import static net.minecraftforge.oredict.OreDictionary.*;
import static net.minecraft.init.Blocks.*;

/**
 * OreDictionary class.
 *
 * @author DarkBum
 * @since 1.9.f
 */
public class ModOreDictionary {

    /**
     * Initializes all ore dictionaries.
     */
    public static void init() {

        Block magma = etFuturumBlocks.get("magma");
        Block nether_fungus = etFuturumBlocks.get("nether_fungus");
        Item suspicious_stew = etFuturumItems.get("suspicious_stew");
        Item mutton_raw = etFuturumItems.get("mutton_raw");
        Item mutton_cooked = etFuturumItems.get("mutton_cooked");
        Item rabbit_raw = etFuturumItems.get("rabbit_raw");
        Item rabbit_cooked = etFuturumItems.get("rabbit_cooked");
        Item rabbit_stew = etFuturumItems.get("rabbit_stew");
        Item beetroot = etFuturumItems.get("beetroot");
        Item beetroot_soup = etFuturumItems.get("beetroot_soup");
        Item chorus_fruit = etFuturumItems.get("chorus_fruit");
        Item sweet_berries = etFuturumItems.get("sweet_berries");

        Block campfire = campfireBackportBlocks.get("campfire");
        Block soul_campfire = campfireBackportBlocks.get("soul_campfire");

        // Salty Mod Ore Dictionaries
        registerOre("blockMushroom", red_mushroom);
    }
}
