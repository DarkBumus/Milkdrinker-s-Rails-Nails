package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.IModeableRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;
import static net.minecraft.item.Item.*;

public class BlockRailOneWay extends BlockRailPowered implements IModeableRail {

    private Block turnedBlock;
    private String modeMessageKey;

    public BlockRailOneWay(String name, CreativeTabs tab) {
        super();
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
        handleOneWayCartBehavior(world, cart, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Block block = world.getBlock(x, y, z);

        if ((block == one_way_rail_r)
            || (block == one_way_rail_l)) {
            return new ItemStack(one_way_rail_r);
        }
        return null;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(one_way_rail_r);
    }

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
