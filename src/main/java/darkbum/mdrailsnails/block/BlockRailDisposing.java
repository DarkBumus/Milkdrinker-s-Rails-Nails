package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.ISuspendedRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;
import static net.minecraft.item.Item.*;

public class BlockRailDisposing extends BlockRailPowered implements ISuspendedRail {

    private final int suspendedLength;

    public BlockRailDisposing(String name, CreativeTabs tab, int suspendedLength) {
        super();
        this.suspendedLength = suspendedLength;
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
        handleDisposingCartBehavior(world, cart, x, y, z);
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canMakeSlopes(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return canPlaceSuspendedRail(world, x, y, z, getSuspensionRange());
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        updateRailPowerState(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (isStillValid(world, x, y, z, getSuspensionRange())) {
            if (!world.isRemote) {
                handleSuspendedDestruction(world, x, y, z, this);
            }
        }
        updateRailPowerState(world, x, y, z);
    }

    /**
     * Updates the block's metadata based on its current redstone power state.
     * <p>
     * If the block is powered and not already in its powered state (metadata 4–7),
     * it increments the metadata by 4 to reflect the powered version.
     * If the block is unpowered and currently in a powered state, it decrements the metadata by 4.
     */
    private void updateRailPowerState(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z);

        if (isPowered && meta == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 8, 3);
        } else if (isPowered && meta == 1) {
            world.setBlockMetadataWithNotify(x, y, z ,9, 3);
        } else if (!isPowered && meta == 8) {
            world.setBlockMetadataWithNotify(x, y, z ,0, 3);
        } else if (!isPowered && meta == 9) {
            world.setBlockMetadataWithNotify(x, y, z ,1, 3);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (isStillValid(world, x, y, z, getSuspensionRange())) {
            handleSuspendedDestruction(world, x, y, z, this);
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return new ItemStack(disposing_rail);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(disposing_rail);
    }

    @Override
    public int getSuspensionRange() {
        return suspendedLength;
    }
}
