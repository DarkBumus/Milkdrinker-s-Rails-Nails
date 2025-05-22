package darkbum.mdrailsnails.init.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static darkbum.mdrailsnails.init.ModExternalLoader.*;
import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationModCompatibility.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static net.minecraft.init.Items.*;
import static net.minecraft.init.Items.cake;

/**
 * Recipe remover class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModRemovedRecipes {

    /**
     * Initializes all removed recipes.
     */
    public static void init() {

        Item rabbit_stew = etFuturumItems.get("rabbit_stew");
        Item beetroot_soup = etFuturumItems.get("beetroot_soup");
        Block honeycomb_block = etFuturumBlocks.get("honeycomb_block");
        Block beehive = etFuturumBlocks.get("beehive");

/*        removeFirstRecipeFor(mushroom_stew,
            enableRecipeChanges);
        removeFirstRecipeFor(bread,
            enableRecipeChanges, enableDough);
        removeFirstRecipeFor(cake,
            enableRecipeChanges, enableDough);
        removeFirstRecipeFor(cake,
            enableRecipeChanges, replaceCake);
        removeFirstRecipeFor(cake,
            enableRecipeChanges, enableDough);
        removeFirstRecipeFor(cake,
            enableRecipeChanges, replaceCake);
        removeFirstRecipeFor(cookie,
            enableRecipeChanges, enableDough);
        removeFirstRecipeFor(pumpkin_pie,
            enableRecipeChanges, enableDough);

        removeAllRecipesFor(rabbit_stew,
            rabbit_stew != null);
        removeAllRecipesFor(beetroot_soup,
            beetroot_soup != null);
        removeAllRecipesFor(honeycomb_block,
            honeycomb_block != null, enableEFRHoneyCompatibility);
        removeAllRecipesFor(beehive,
            beehive != null, enableEFRHoneyCompatibility);*/
    }
}
