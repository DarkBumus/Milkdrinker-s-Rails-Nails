package darkbum.mdrailsnails.init;

import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;

import darkbum.mdrailsnails.block.*;

import static darkbum.mdrailsnails.common.proxy.CommonProxy.*;
import static darkbum.mdrailsnails.util.ConditionalRegistrar.registerBlock;

/**
 * Blocks class.
 *
 * @author DarkBum
 * @since 1.9.f
 */
public class ModBlocks {

    static CreativeTabs tab = tabMDRNBlocks;

    public static Block dev_block;

    /**
     * Initializes and registers all blocks.
     */
    public static void init() {

        dev_block = new BlockDevBlock("dev_block", tab);


        registerBlock(dev_block, "dev_block");
    }
}
