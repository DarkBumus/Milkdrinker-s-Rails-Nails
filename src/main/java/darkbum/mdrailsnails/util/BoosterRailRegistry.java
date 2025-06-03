package darkbum.mdrailsnails.util;

import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;

import static darkbum.mdrailsnails.init.ModBlocks.*;
import static net.minecraft.init.Blocks.*;

public class BoosterRailRegistry {

    private static final Set<Block> BOOSTER_RAILS = new HashSet<>();
    static {
        registerBoosterRail(golden_rail);
        registerBoosterRail(slowdown_rail);
    }

    @SuppressWarnings("unused")
    public static void registerBoosterRail(Block block) {
        if (block != null) {
            BOOSTER_RAILS.add(block);
        }
    }

    @SuppressWarnings("unused")
    public static void unregisterBoosterRail(Block block) {
        BOOSTER_RAILS.remove(block);
    }

    @SuppressWarnings("unused")
    public static boolean isBoosterRail(Block block) {
        return BOOSTER_RAILS.contains(block);
    }

    @SuppressWarnings("unused")
    public static Set<Block> getRegisteredBoosterRails() {
        return new HashSet<>(BOOSTER_RAILS);
    }
}
