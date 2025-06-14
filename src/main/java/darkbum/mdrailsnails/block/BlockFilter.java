package darkbum.mdrailsnails.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.MDRailsNails;
import darkbum.mdrailsnails.tileentity.TileEntityFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFilter extends BlockHopper {

    private final Random random = new Random();

    public BlockFilter(String name, CreativeTabs tab) {
        setBlockName(name);
        setCreativeTab(tab);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        field_149923_M = icon.registerIcon("mdrailsnails:filter_top");
        field_149921_b = icon.registerIcon("hopper_outside");
        field_149924_N = icon.registerIcon("hopper_inside");
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFilter();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) return false;
        player.openGui(MDRailsNails.instance, 1, world, x, y, z);
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        TileEntityFilter tileEntity = (TileEntityFilter)world.getTileEntity(x, y, z);

        if (tileEntity != null) {
            for (int i1 = 0; i1 < tileEntity.getSizeInventory(); ++i1) {
                ItemStack stack = tileEntity.getStackInSlot(i1);

                if (stack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                    while (stack.stackSize > 0) {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > stack.stackSize) {
                            j1 = stack.stackSize;
                        }

                        stack.stackSize -= j1;
                        EntityItem entityItem = new EntityItem(world, (float)x + f, (float)y + f1, (float)z + f2, new ItemStack(stack.getItem(), j1, stack.getItemDamage()));

                        if (stack.hasTagCompound()) {
                            entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityItem.motionX = (float)this.random.nextGaussian() * f3;
                        entityItem.motionY = (float)this.random.nextGaussian() * f3 + 0.2F;
                        entityItem.motionZ = (float)this.random.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityItem);
                    }
                }
            }
            world.func_147453_f(x, y, z, blockBroken);
        }
    }

    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return "mdrailsnails:filter";
    }
}
