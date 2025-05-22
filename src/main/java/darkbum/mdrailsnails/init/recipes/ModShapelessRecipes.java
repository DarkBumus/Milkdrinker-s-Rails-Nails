package darkbum.mdrailsnails.init.recipes;

import net.minecraft.item.Item;

import static darkbum.mdrailsnails.init.ModExternalLoader.*;

/**
 * Recipe class for Shapeless Recipes.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModShapelessRecipes {

    /**
     * Initializes all shapeless recipes.
     */
    public static void init() {

        Item mutton_cooked = etFuturumItems.get("mutton_cooked");
        Item rabbit_cooked = etFuturumItems.get("rabbit_cooked");
        Item beetroot = etFuturumItems.get("beetroot");
        Item beetroot_seeds = etFuturumItems.get("beetroot_seeds");
        Item sweet_berries = etFuturumItems.get("sweet_berries");
        Item dye = etFuturumItems.get("dye");
    }
}
