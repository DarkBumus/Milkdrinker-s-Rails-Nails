package darkbum.mdrailsnails.init.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static darkbum.mdrailsnails.init.ModExternalLoader.*;

/**
 * Recipe class for Shaped Recipes.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModShapedRecipes {

    /**
     * Initializes all shaped recipes.
     */
    public static void init() {

        Item beetroot = etFuturumItems.get("beetroot");
        Item beetroot_seeds = etFuturumItems.get("beetroot_seeds");
        Item sweet_berries = etFuturumItems.get("sweet_berries");
        Block honeycomb_block = etFuturumBlocks.get("honeycomb_block");
        Block beehive = etFuturumBlocks.get("beehive");

        Block campfire = campfireBackportBlocks.get("campfire");
    }
}
