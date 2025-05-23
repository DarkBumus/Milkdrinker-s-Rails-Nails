package darkbum.mdrailsnails.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static darkbum.mdrailsnails.util.ItemUtils.*;

public class ItemRailwayLever extends ItemTool {

    public static final Set<Block> NON_POWERED_RAILS = new HashSet<>(Arrays.asList(
        Blocks.rail
    ));

    public static final Set<Block> POWERED_RAILS = new HashSet<>(Arrays.asList(
        Blocks.detector_rail,
        Blocks.golden_rail,
        Blocks.activator_rail
    ));

    public static final Set<Block> OTHER_RAIL = new HashSet<>(Arrays.asList(
    ));

    private static final Set<Block> EFFECTIVE_BLOCKS = new HashSet<>();
    static {
        EFFECTIVE_BLOCKS.addAll(NON_POWERED_RAILS);
        EFFECTIVE_BLOCKS.addAll(POWERED_RAILS);
        EFFECTIVE_BLOCKS.addAll(OTHER_RAIL);
    }

    public ItemRailwayLever(String name, CreativeTabs tab) {
        super(1.0f, ToolMaterial.IRON, EFFECTIVE_BLOCKS);
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
     * @param stack     The ItemStack for which the information is being added.
     * @param player    The player viewing the tooltip.
     * @param list      The list to which the tooltip lines are added.
     * @param advanced  Whether advanced tooltips are enabled.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        addItemTooltip(stack, list);
    }
}
