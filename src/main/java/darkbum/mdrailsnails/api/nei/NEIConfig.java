package darkbum.mdrailsnails.api.nei;

import darkbum.mdrailsnails.init.ModItems;
import net.minecraft.item.ItemStack;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * NEI plugin configuration for Milkdrinker's Rails&Nails
 * Can register custom NEI listings and recipe handlers for the NEI (Not Enough Items) interface.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class NEIConfig implements IConfigureNEI {

    /**
     * Registers the three meta values (0, 1, 2) of the tallgrass block to be shown in the NEI item list.
     * Registers custom recipe handlers to allow NEI to display SME-specific Cooking Pot recipes.
     */
    @Override
    public void loadConfig() {
//        API.hideItem(new ItemStack(ModItems.dev_item, 1, 0));
    }

    /**
     * @return the display name String of this NEI plugin.
     */
    @Override
    public String getName() {
        return "Rails&Nails NEI Plugin";
    }

    /**
     * @return the version String of this NEI plugin.
     */
    @Override
    public String getVersion() {
        return "1.0";
    }
}
