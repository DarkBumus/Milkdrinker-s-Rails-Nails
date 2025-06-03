package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.block.rails.ISuspendedRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;
import static net.minecraft.item.Item.*;

public class BlockRailSuspended extends BlockRail implements ISuspendedRail {

    private final int suspendedLength;

    public BlockRailSuspended(String name, CreativeTabs tab, int suspendedLength) {
        super();
        this.suspendedLength = suspendedLength;
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        blockIcon = icon.registerIcon(this.getTextureName());
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (isStillValid(world, x, y, z, getSuspensionRange())) {
            if (!world.isRemote) {
                handleSuspendedDestruction(world, x, y, z, this);
            }
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
        return new ItemStack(suspended_rail);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(suspended_rail);
    }

    @Override
    public int getSuspensionRange() {
        return suspendedLength;
    }
}
