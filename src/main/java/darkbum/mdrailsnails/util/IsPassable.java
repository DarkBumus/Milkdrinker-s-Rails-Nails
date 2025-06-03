package darkbum.mdrailsnails.util;

import net.minecraft.block.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class IsPassable {

    public static boolean isPassable(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (block.isAir(world, x, y, z)) {
            return true;
        }
        if (block instanceof BlockBush) {
            return true;
        }

        AxisAlignedBB aabb = block.getCollisionBoundingBoxFromPool(world, x, y, z);
        return aabb == null;
    }
}
