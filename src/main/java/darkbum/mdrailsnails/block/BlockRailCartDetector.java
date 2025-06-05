package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.ISuspendedRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailDetector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import java.util.Random;

import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;

public class BlockRailCartDetector extends BlockRailDetector implements ISuspendedRail {

    private final int suspendedLength;

    public BlockRailCartDetector(String name, CreativeTabs tab) {
        super();
        this.suspendedLength = 1;
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
        handleCartTypeDetectingBehavior(world, cart, x, y, z);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return canPlaceSuspendedRail(world, x, y, z, getSuspensionRange());
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (isStillValid(world, x, y, z, getSuspensionRange())) {
            if (!world.isRemote) {
                handleSuspendedDestruction(world, x, y, z, this);
            }
        }
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (isStillValid(world, x, y, z, getSuspensionRange())) {
            handleSuspendedDestruction(world, x, y, z, this);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn) {}

    @Override
    public int getSuspensionRange() {
        return suspendedLength;
    }
}
