package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.block.rails.IModeableRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
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

public class BlockWyeRail extends BlockRail implements IModeableRail {

    private Block turnedBlock;
    private String modeMessageKey;

    public BlockWyeRail(String name, CreativeTabs tab) {
        super();
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
    public boolean canMakeSlopes(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        return handleWyeRedirectingCartBehavior(world, cart, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        setWyeDirectionFromEntity(world, x, y, z, entity);
    }

    @Override
    protected void func_150048_a(World world, int x, int y, int z, int currentMeta, int railShape, Block neighborBlock) {
    }

    @Override
    protected void func_150052_a(World world, int x, int y, int z, boolean fromNeighborUpdate) {
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        validateAndFixWyeMetadata(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        validateAndFixWyeMetadata(world, x, y, z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return new ItemStack(wye_rail_j);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return getItemFromBlock(wye_rail_j);
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
