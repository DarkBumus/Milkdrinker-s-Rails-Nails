package darkbum.mdrailsnails.init;

import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;

/**
 * Flammability Handler class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModFlammabilityHandler {

    /**
     * Initializes all flammability values.
     */
    public static void init() {
        BlockFire fire = Blocks.fire;

        fire.setFireInfo(ModBlocks.dev_block, 5, 5);
    }
}
