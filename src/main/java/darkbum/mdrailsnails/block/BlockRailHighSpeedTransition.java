package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.IHighSpeedRail;
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

import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;
import static net.minecraft.item.Item.*;

public class BlockRailHighSpeedTransition extends BlockRailPowered implements IHighSpeedRail, IModeableRail {

    private Block turnedBlock;
    private String modeMessageKey;

    public BlockRailHighSpeedTransition(String name, CreativeTabs tab) {
        super();
        setBlockName(name);
        setCreativeTab(tab);
        propertiesHighSpeedRails(this);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return getMinecartSpeedFromRules(world) * (highSpeedRailsSpeed * 1.0f);
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
        handleTransitioningCartBehavior(world, cart, x, y, z);
        if (enableHighSpeedConsequences) updateHighSpeedState(world, cart);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        Block block = world.getBlock(x, y, z);

        if ((block == high_speed_transition_rail_r)
            || (block == high_speed_transition_rail_l)) {
            return new ItemStack(high_speed_transition_rail_r);
        }
        return null;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(high_speed_transition_rail_r);
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
