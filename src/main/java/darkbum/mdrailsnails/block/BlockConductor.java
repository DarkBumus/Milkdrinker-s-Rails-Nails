package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static darkbum.mdrailsnails.common.proxy.ClientProxy.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static net.minecraft.block.BlockPistonBase.*;
import static net.minecraft.util.AxisAlignedBB.*;
import static net.minecraft.util.Facing.*;

public class BlockConductor extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    @SideOnly(Side.CLIENT)
    private IIcon iconSide;

    @SideOnly(Side.CLIENT)
    private IIcon iconBackOff;

    @SideOnly(Side.CLIENT)
    private IIcon iconBackOn;

    public BlockConductor(String name, CreativeTabs tab) {
        super(Material.rock);
        setBlockName(name);
        setCreativeTab(tab);
        propertiesConductor(this);
    }

    public IIcon getIcon(int side, int meta) {
        int k = getPistonOrientation(meta);
        boolean powered = (meta & 8) != 0;
        if (side == k) {
            return this.iconFront;
        } else if (side == oppositeSide[k]) {
            return powered ? this.iconBackOn : this.iconBackOff;
        } else {
            int topSide = switch (k) {
                case 2, 3, 4, 5 -> 1;
                default -> 2;
            };
            return side != topSide && side != oppositeSide[topSide] ? this.iconSide : this.iconTop;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        this.iconSide = icon.registerIcon("mdrailsnails:conductor_side");
        this.iconFront = icon.registerIcon("mdrailsnails:conductor_front");
        this.iconTop = icon.registerIcon("mdrailsnails:conductor_top");
        this.iconBackOff = icon.registerIcon("mdrailsnails:conductor_back");
        this.iconBackOn = icon.registerIcon("mdrailsnails:conductor_back_on");
    }

    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int getRenderType() {
        return conductorRenderType;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int l = determineOrientation(world, x, y, z, entity);
        world.setBlockMetadataWithNotify(x, y, z, oppositeSide[l], 2);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (world.isRemote) return;

        if (checkForMatchAndUpdate(world, x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote) return;

        if (checkForMatchAndUpdate(world, x, y, z)) {
            world.scheduleBlockUpdate(x, y, z, this, 2);
        }
    }

    private boolean checkForMatchAndUpdate(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        int observerSide = meta & 7;
        int oppositeSide = Facing.oppositeSide[observerSide];

        int dx = x + offsetsXForSide[observerSide];
        int dy = y + offsetsYForSide[observerSide];
        int dz = z + offsetsZForSide[observerSide];

        AxisAlignedBB obsBox = getBoundingBox(
            dx + 0.1, dy, dz + 0.1,
            dx + 0.9, dy + 1.0, dz + 0.9
        );
        List<EntityMinecart> incomingCarts = world.getEntitiesWithinAABB(EntityMinecart.class, obsBox);

        boolean matchFound = false;

        for (EntityMinecart incoming : incomingCarts) {
            Class<?> incomingClass = incoming.getClass();

            for (int side = 0; side < 6; side++) {
                if (side == oppositeSide || side == observerSide) continue;

                int nx = x + offsetsXForSide[side];
                int ny = y + offsetsYForSide[side];
                int nz = z + offsetsZForSide[side];

                AxisAlignedBB neighborBox = getBoundingBox(
                    nx + 0.1, ny, nz + 0.1,
                    nx + 0.9, ny + 1.0, nz + 0.9
                );

                List<EntityMinecart> neighborCarts = world.getEntitiesWithinAABB(EntityMinecart.class, neighborBox);

                for (EntityMinecart neighborCart : neighborCarts) {
                    if (neighborCart.getClass() == incomingClass) {
                        matchFound = true;
                        break;
                    }
                }

                if (matchFound) break;
            }

            if (matchFound) break;
        }

        int newMeta = matchFound ? (meta | 8) : (meta & 7);
        if (meta != newMeta) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
        }

        return matchFound;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.onNeighborBlockChange(world, x, y, z, this);
    }

    public boolean canProvidePower() {
        return true;
    }

    public int isProvidingStrongPower(IBlockAccess worldIn, int x, int y, int z, int side) {
        return this.isProvidingWeakPower(worldIn, x, y, z, side);
    }

    public int isProvidingWeakPower(IBlockAccess worldIn, int x, int y, int z, int side) {
        int metadata = worldIn.getBlockMetadata(x, y, z);
        if ((metadata & 8) == 0) {
            return 0;
        } else {
            return getPistonOrientation(metadata) == side ? 15 : 0;
        }
    }
}
