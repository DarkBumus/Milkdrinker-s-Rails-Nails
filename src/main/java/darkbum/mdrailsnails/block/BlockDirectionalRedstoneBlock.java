package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirectionalRedstoneBlock extends Block {

    @SideOnly(Side.CLIENT)
    public IIcon iconFront;

    @SideOnly(Side.CLIENT)
    public IIcon iconSide;

    public BlockDirectionalRedstoneBlock(String name, CreativeTabs tab) {
        super(Material.rock);
        setBlockName(name);
        setCreativeTab(tab);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        iconFront = icon.registerIcon("mdrailsnails:directional_redstone_block_side");
        iconSide = icon.registerIcon("furnace_top");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1)
            return iconFront;
        return iconSide;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        if (side == world.getBlockMetadata(x, y, z))
            return iconFront;
        return iconSide;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = determineOrientation(x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    public static int determineOrientation(int x, int y, int z, EntityLivingBase player) {
        if (MathHelper.abs((float)player.posX - x) < 2.0f &&
            MathHelper.abs((float)player.posZ - z) < 2.0f) {
            double d0 = player.posY + 1.82D - player.yOffset;
            if (d0 - y > 2.0D)
                return 1;
            if (y - d0 > 0.0D)
                return 0;
        }
        int l = MathHelper.floor_double((player.rotationYaw * 4.0f / 360.0f) + 0.5D) & 0x3;
        return l == 0 ? 2 : l == 1 ? 5 : l == 2 ? 3 : 4;
    }

    public boolean canProvidePower() {
        return true;
    }

    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        if (invertMetadata(world.getBlockMetadata(x, y, z)) == side)
            return 15;
        return 0;
    }

    public static int invertMetadata(int side) {
        return switch (side) {
            case 0 -> 1;
            case 2 -> 3;
            case 3 -> 2;
            case 4 -> 5;
            case 5 -> 4;
            default -> 0;
        };
    }
}
