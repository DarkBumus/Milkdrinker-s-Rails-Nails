package darkbum.mdrailsnails.block.itemblock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

import static darkbum.mdrailsnails.util.ItemUtils.*;

public class ItemBlockRail extends ItemBlock {

    public ItemBlockRail(Block block) {
        super(block);
    }

    /**
     * Adds additional information to the item tooltip when hovering over the item in the inventory.
     *
     * @param stack    The ItemStack for which the information is being added.
     * @param player   The player viewing the tooltip.
     * @param list     The list to which the tooltip lines are added.
     * @param advanced Whether advanced tooltips are enabled.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        addItemTooltip(stack, list);
    }
}
