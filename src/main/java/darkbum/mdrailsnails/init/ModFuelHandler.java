package darkbum.mdrailsnails.init;

import darkbum.mdrailsnails.common.config.ModConfigurationWorldGeneration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import darkbum.mdrailsnails.common.config.ModConfigurationItems;

/**
 * Fuel Handler class for custom fuels.
 *
 * @author DarkBum
 * @since 2.0.0
 */
public class ModFuelHandler implements IFuelHandler {

    /**
     * Gets the burn time for a specific {@link ItemStack}.
     *
     * @param itemStack The {@link ItemStack} for which the burn time is being checked.
     * @return the burn time in ticks (or 0 if the item is not registered as fuel).
     */
    public int getBurnTime(ItemStack itemStack) {
        if (itemStack == null) return 0;

        if (ModConfigurationItems.enableHoney) {
            if (itemStack.getItem() == ModItems.dev_item) return 400;
        }
        if (ModConfigurationWorldGeneration.enableSaltMarsh) {
            if (itemStack.getItem() == Item.getItemFromBlock(ModBlocks.dev_block)) return 80;
        }
        return 0;
    }
}
