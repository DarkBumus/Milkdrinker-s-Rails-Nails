package darkbum.mdrailsnails.block;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.common.proxy.ClientProxy;
import darkbum.mdrailsnails.tileentity.TileEntityUpper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static darkbum.mdrailsnails.block.render.RenderUpper.alternativeUpperFaces;

public class BlockUpper extends BlockContainer {

    @SideOnly(Side.CLIENT)
    public static IIcon iconOutside;

    @SideOnly(Side.CLIENT)
    public static IIcon iconBottom;

    @SideOnly(Side.CLIENT)
    public static IIcon iconInside;

    private final Random rand = new Random();

    private static final EnumMap<EnumFacing, List<AxisAlignedBB>> BOUNDS;

    static {
        List<AxisAlignedBB> commonBounds = ImmutableList.of(makeAABB(0, 0, 0, 16, 6, 16), makeAABB(4, 6, 4, 12, 12, 12));
        BOUNDS = Stream.of(EnumFacing.values()).filter(t -> t != EnumFacing.UP).collect(
            Collectors.toMap(a -> a, a -> new ArrayList<>(commonBounds), (u, v) -> {
                throw new IllegalStateException();
            }, () -> new EnumMap<>(EnumFacing.class)));

        BOUNDS.get(EnumFacing.DOWN).add(makeAABB(6, 12, 6, 10, 16, 10));

        BOUNDS.get(EnumFacing.NORTH).add(makeAABB(6, 8, 0, 10, 12, 4));
        BOUNDS.get(EnumFacing.SOUTH).add(makeAABB(6, 8, 12, 10, 12, 16));

        BOUNDS.get(EnumFacing.WEST).add(makeAABB(12, 8, 6, 16, 12, 10));
        BOUNDS.get(EnumFacing.EAST).add(makeAABB(0, 8, 6, 4, 12, 10));
    }

    public BlockUpper(String name, CreativeTabs tab) {
        super(Material.iron);
        setBlockName(name);
        setCreativeTab(tab);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int getRenderType() {
        return ClientProxy.upperRenderType;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        iconOutside = icon.registerIcon("mdrailsnails:upper_outside");
        iconBottom = icon.registerIcon("hopper_top");
        iconInside = icon.registerIcon("hopper_inside");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (alternativeUpperFaces == 1)
            return side == 0 ? iconInside : (side == 1 ? iconInside : iconOutside);
        else if (alternativeUpperFaces == 0)
            return side == 0 ? iconBottom : (side == 1 ? iconInside : iconOutside);
        return side == 0 ? iconBottom : (side == 1 ? iconInside : iconOutside);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collider) {
        float sideThickness = 0.125f;
        addBoxToList(x, y, z, mask, list, AxisAlignedBB.getBoundingBox(0.0, 0.375, 0.0, 1.0, 1.0, 1.0));
        addBoxToList(x, y, z, mask, list, AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, sideThickness, 0.75, 1.0));
        addBoxToList(x, y, z, mask, list, AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 1.0, 0.75, sideThickness));
        addBoxToList(x, y, z, mask, list, AxisAlignedBB.getBoundingBox(1.0 - sideThickness, 0.0, 0.0, 1.0, 0.75, 1.0));
        addBoxToList(x, y, z, mask, list, AxisAlignedBB.getBoundingBox(0.0, 0.0, 1.0 - sideThickness, 1.0, 0.75, 1.0));
    }

    private void addBoxToList(int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, AxisAlignedBB box) {
        AxisAlignedBB shifted = box.offset(x, y, z);
        if (mask == null || mask.intersectsWith(shifted)) {
            list.add(shifted);
        }
    }

    private static AxisAlignedBB makeAABB(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return AxisAlignedBB.getBoundingBox(fromX / 16f, fromY / 16f, fromZ / 16f, toX / 16f, toY / 16f, toZ / 16f);
    }

    private static MovingObjectPosition rayTrace(Vec3 pos, Vec3 start, Vec3 end, AxisAlignedBB boundingBox) {
        final Vec3 vec3d = start.addVector(-pos.xCoord, -pos.yCoord, -pos.zCoord);
        final Vec3 vec3d1 = end.addVector(-pos.xCoord, -pos.yCoord, -pos.zCoord);

        final MovingObjectPosition raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        if (raytraceresult == null) return null;

        final Vec3 res = raytraceresult.hitVec.addVector(pos.xCoord, pos.yCoord, pos.zCoord);
        return new MovingObjectPosition(
            (int) res.xCoord,
            (int) res.yCoord,
            (int) res.zCoord,
            raytraceresult.sideHit,
            pos);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        final Vec3 pos = Vec3.createVectorHelper(x, y, z);
        final EnumFacing facing = EnumFacing.values()[(BlockUpper.getDirectionFromMetadata(world.getBlockMetadata(x, y, z)))];
        List<AxisAlignedBB> list = BOUNDS.get(facing);
        if (list == null) return super.collisionRayTrace(world, x, y, z, start, end);
        return list.stream().map(bb -> rayTrace(pos, start, end, bb)).anyMatch(Objects::nonNull) ? super.collisionRayTrace(world, x, y, z, start, end) : null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityUpper();
    }

    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float subX, float subY, float subZ, int meta) {
        int j1 = Facing.oppositeSide[side];

        if (j1 == 1) {
            j1 = 0;
        }

        return j1;
    }

    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(worldIn, x, y, z, placer, itemIn);

        if (itemIn.hasDisplayName()) {
            TileEntityUpper tileEntityUpper = func_149920_e(worldIn, x, y, z);
            tileEntityUpper.func_145886_a(itemIn.getDisplayName());
        }
    }

    public void onBlockAdded(World worldIn, int x, int y, int z) {
        super.onBlockAdded(worldIn, x, y, z);
        this.func_149919_e(worldIn, x, y, z);
    }

    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (!worldIn.isRemote) {
            TileEntityUpper tileEntityUpper = func_149920_e(worldIn, x, y, z);

            if (tileEntityUpper != null) {
                player.func_146093_a(tileEntityUpper);
            }

        }
        return true;
    }

    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        this.func_149919_e(worldIn, x, y, z);
    }

    private void func_149919_e(World p_149919_1_, int p_149919_2_, int p_149919_3_, int p_149919_4_) {
        int l = p_149919_1_.getBlockMetadata(p_149919_2_, p_149919_3_, p_149919_4_);
        int i1 = getDirectionFromMetadata(l);
        boolean flag = !p_149919_1_.isBlockIndirectlyGettingPowered(p_149919_2_, p_149919_3_, p_149919_4_);
        boolean flag1 = func_149917_c(l);

        if (flag != flag1) {
            p_149919_1_.setBlockMetadataWithNotify(p_149919_2_, p_149919_3_, p_149919_4_, i1 | (flag ? 0 : 8), 4);
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        TileEntityUpper tileEntityUpper = (TileEntityUpper) world.getTileEntity(x, y, z);

        if (tileEntityUpper != null) {
            for (int i1 = 0; i1 < tileEntityUpper.getSizeInventory(); ++i1) {
                ItemStack itemstack = tileEntityUpper.getStackInSlot(i1);

                if (itemstack != null) {
                    float f = rand.nextFloat() * 0.8f + 0.1f;
                    float f1 = rand.nextFloat() * 0.8f + 0.1f;
                    float f2 = rand.nextFloat() * 0.8f + 0.1f;

                    while (itemstack.stackSize > 0) {
                        int j1 = rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }
                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(world, (float) x + f, (float) y + f1, (float) z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }
                        float f3 = 0.05f;
                        entityitem.motionX = (float) rand.nextGaussian() * f3;
                        entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2f;
                        entityitem.motionZ = (float) rand.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
            world.func_147453_f(x, y, z, blockBroken);
        }
        super.breakBlock(world, x, y, z, blockBroken, meta);
    }

    public static int getDirectionFromMetadata(int meta) {
        return meta & 7;
    }

    public static boolean func_149917_c(int p_149917_0_) {
        return (p_149917_0_ & 8) != 8;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World worldIn, int x, int y, int z, int side) {
        return Container.calcRedstoneFromInventory(func_149920_e(worldIn, x, y, z));
    }

    public static TileEntityUpper func_149920_e(IBlockAccess p_149920_0_, int p_149920_1_, int p_149920_2_, int p_149920_3_) {
        return (TileEntityUpper) p_149920_0_.getTileEntity(p_149920_1_, p_149920_2_, p_149920_3_);
    }

    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return "mdrailsnails:upper";
    }
}
