package darkbum.mdrailsnails.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.init.ModItems;

/**
 * Custom Creative Tab for Milkdrinker's Rails&Nails Items.
 * This tab will display items in the creative inventory.
 * The icon of this tab is the Salt Item.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class TabMDRNItems extends CreativeTabs {

    /**
     * Constructs the custom Creative Tab.
     *
     * @param label The label for the creative tab.
     */
    public TabMDRNItems(String label) {
        super(label);
    }

    /**
     * Returns the item icon that will be displayed for this tab in the creative inventory.
     *
     * @return The item to be displayed as the tab's icon.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModItems.railway_lever;
    }
}
