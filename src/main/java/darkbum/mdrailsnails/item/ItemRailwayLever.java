package darkbum.mdrailsnails.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import java.util.List;

import static darkbum.mdrailsnails.util.ItemUtils.*;

public class ItemRailwayLever extends ItemTool {

    public ItemRailwayLever(String name, CreativeTabs tab) {
        super(2.0f, ToolMaterial.IRON, EFFECTIVE_BLOCKS);
        setUnlocalizedName(name);
        setCreativeTab(tab);
        setMaxDamage(500);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return railwayLeverInteraction(stack, player, world, x, y, z);
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
        addItemHiddenTooltip(stack, list);
    }
}
