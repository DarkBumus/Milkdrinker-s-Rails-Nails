package darkbum.mdrailsnails.creativetab;

import darkbum.mdrailsnails.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Custom Creative Tab for Milkdrinker's Rails&Nails Blocks.
 * This tab will display blocks in the creative inventory.
 * The icon of this tab is the Salt Block.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class TabMDRNBlocks extends CreativeTabs {

    /**
     * Constructs the custom Creative Tab.
     *
     * @param label The label for the creative tab.
     */
    public TabMDRNBlocks(String label) {
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
        return Item.getItemFromBlock(ModBlocks.dev_block);
    }
}
