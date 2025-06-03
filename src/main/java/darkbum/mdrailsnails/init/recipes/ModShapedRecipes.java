package darkbum.mdrailsnails.init.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationEntities.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.init.ModExternalLoader.*;
import static darkbum.mdrailsnails.init.ModItems.*;
import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;
import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Items.*;

/**
 * Recipe class for Shaped Recipes.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModShapedRecipes {

    /**
     * Initializes all shaped recipes.
     */
    public static void init() {

        Block blast_furnace = etFuturumBlocks.get("blast_furnace");

        Item copper_ingot = etFuturumItems.get("copper_ingot");

        addShapedRecipe(new ItemStack(golden_rail, 8, 0),
            new Object[]{"x x", "yzy", "xax",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(gold_ingot),
                'z', new ItemStack(stick),
                'a', new ItemStack(redstone)},
            enableRecipeChanges);
        addShapedRecipe(new ItemStack(detector_rail, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(stone_pressure_plate),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableRecipeChanges);
        addShapedRecipe(new ItemStack(activator_rail, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(redstone_torch),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableRecipeChanges);

        addShapedRecipe(new ItemStack(railway_lever),
            new Object[]{"x", "y", "y",
                'x', new ItemStack(copper_ingot),
                'y', new ItemStack(stick)},
            enableRailwayLever);

        addShapedRecipe(new ItemStack(hauler_minecart),
            new Object[]{"x ", "yz",
                'x', new ItemStack(blast_furnace),
                'y', new ItemStack(minecart),
                'z', new ItemStack(tripwire_hook)},
            enableHaulerMinecart, efr);
        addShapedRecipe(new ItemStack(hauler_minecart),
            new Object[]{"x ", "yz",
                'x', new ItemStack(furnace),
                'y', new ItemStack(minecart),
                'z', new ItemStack(tripwire_hook)},
            enableHaulerMinecart, !efr);

        addShapedOreRecipe(new ItemStack(one_way_rail_r, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', "fenceGateWood",
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableOneWayRail);

        addShapedRecipe(new ItemStack(locking_rail, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(tripwire_hook),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableLockingRail);

        addShapedRecipe(new ItemStack(dismounting_rail_se, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(piston),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableDismountingRail);

        addShapedRecipe(new ItemStack(mounting_rail, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(ender_pearl),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableMountingRail);

        addShapedRecipe(new ItemStack(coupling_rail, 8, 0),
            new Object[]{"xyx", "zaz", "xbx",
                'x', new ItemStack(iron_ingot),
                'y', new ItemStack(string),
                'z', new ItemStack(copper_ingot),
                'a', new ItemStack(stick),
                'b', new ItemStack(redstone)},
            enableCouplingRail);
    }
}
