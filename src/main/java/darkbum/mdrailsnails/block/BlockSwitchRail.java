package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.IModeableRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
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

public class BlockSwitchRail extends BlockRail implements IModeableRail {

    private Block turnedBlock;
    private String modeMessageKey;

    public BlockSwitchRail(String name, CreativeTabs tab) {
        super();
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    public boolean canMakeSlopes(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z) {
        return true;
    }

/*    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        return handleSwitchRedirectingCartBehavior(world, cart, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Block block = world.getBlock(x, y, z);

        if ((block == switch_rail_right_ne)
            || (block == switch_rail_right_sw)) {
            return new ItemStack(switch_rail_right_ne);
        } else if ((block == switch_rail_left_ne)
        || (block == switch_rail_left_sw)) {
            return new ItemStack(switch_rail_left_ne);
        }
        return null;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(switch_rail_right_ne);
    }*/

    @Override
    public Block getTurnedBlock() {
        return turnedBlock;
    }

    @Override
    public void setTurnedBlock(Block block) {
        turnedBlock = block;
    }

    @Override
    public String getModeChangeMessageKey() {
        return modeMessageKey;
    }

    @Override
    public void setModeChangeMessageKey(String key) {
        modeMessageKey = key;
    }
}
