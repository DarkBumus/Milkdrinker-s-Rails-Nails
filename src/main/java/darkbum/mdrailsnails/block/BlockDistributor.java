package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.tileentity.TileEntityDistributor;
import net.minecraft.block.BlockHopper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockDistributor extends BlockHopper {

    public BlockDistributor(String name, CreativeTabs tab) {
        setBlockName(name);
        setCreativeTab(tab);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        int facing = Facing.oppositeSide[side];

        if (facing == 0 || facing == 1) {
            return 2;
        }
        return facing;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityDistributor();
    }

    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return "mdrailsnails:distributor";
    }
}
