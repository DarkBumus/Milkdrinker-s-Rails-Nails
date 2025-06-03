package darkbum.mdrailsnails.init.recipes;

import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static net.minecraft.init.Blocks.*;

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

        removeFirstRecipeFor(golden_rail,
            enableRecipeChanges);
        removeFirstRecipeFor(detector_rail,
            enableRecipeChanges);
        removeFirstRecipeFor(activator_rail,
            enableRecipeChanges);
    }
}
