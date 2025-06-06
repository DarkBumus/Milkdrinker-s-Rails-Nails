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
import net.minecraft.world.World;

import static darkbum.mdrailsnails.util.BlockUtils.*;

public class BlockCopperRailWindlass extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    @SideOnly(Side.CLIENT)
    private IIcon iconSidesOff;

    @SideOnly(Side.CLIENT)
    private IIcon iconSidesOn;

    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    @SideOnly(Side.CLIENT)
    private IIcon iconBackOff;

    @SideOnly(Side.CLIENT)
    private IIcon iconBackOn;

    public BlockCopperRailWindlass(String name, CreativeTabs tab) {
        super(Material.wood);
        setBlockName(name);
        setCreativeTab(tab);
        propertiesWindlass(this);
    }

    /**
     * Registers the textures for the different sides of the block.
     *
     * @param icon The icon register used to load textures.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        iconBottom = icon.registerIcon("mdrailsnails:windlass_bottom");
        iconTop = icon.registerIcon("mdrailsnails:windlass_top");
        iconSidesOff = icon.registerIcon("mdrailsnails:windlass_side_off");
        iconSidesOn = icon.registerIcon("mdrailsnails:windlass_side_on");
        iconFront = icon.registerIcon("mdrailsnails:windlass_front");
        iconBackOff = icon.registerIcon("mdrailsnails:windlass_back_off");
        iconBackOn = icon.registerIcon("mdrailsnails:windlass_back_on");
    }

    /**
     * Returns the appropriate icon for a given side and metadata value.
     *
     * @param side The side of the block being rendered.
     * @param meta The metadata of the block.
     * @return the icon to render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > 7) return null;
        IIcon[] icons = {iconBottom, iconTop, iconBackOff, iconBackOn, iconFront, iconSidesOff, iconSidesOn};
        int[][] iconMatrix = {
            {0, 1, 4, 2, 5, 5},
            {0, 1, 5, 5, 2, 4},
            {0, 1, 2, 4, 5, 5},
            {0, 1, 5, 5, 4, 2},
            {0, 1, 4, 3, 6, 6},
            {0, 1, 6, 6, 3, 4},
            {0, 1, 3, 4, 6, 6},
            {0, 1, 6, 6, 4, 3},
        };
        return icons[iconMatrix[meta][side]];
    }

    /**
     * Called when the block is placed by an entity.
     * Sets metadata based on player's rotation.
     *
     * @param world     The world the block is in.
     * @param x         The x-coordinate of the block.
     * @param y         The y-coordinate of the block.
     * @param z         The z-coordinate of the block.
     * @param entity    The entity placing the block.
     * @param stack     The item used to place the block.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        setBlockDirectionFromEntity(world, x, y, z, entity);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        updatePowerState(world, x, y, z);
    }

    /**
     * Called when a neighboring block changes.
     * Causes flowing water to appear in cardinal directions if there's air.
     *
     * @param neighborBlock The block that changed.
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        updatePowerState(world, x, y, z);
    }

    /**
     * Updates the block's metadata based on its current redstone power state.
     * <p>
     * If the block is powered and not already in its powered state (metadata 4–7),
     * it increments the metadata by 4 to reflect the powered version.
     * If the block is unpowered and currently in a powered state, it decrements the metadata by 4.
     */
    private void updatePowerState(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z);

        if (isPowered && meta >= 0 && meta <= 3) {
            world.setBlockMetadataWithNotify(x, y, z, meta + 4, 3);
        } else if (!isPowered && meta >= 4 && meta <= 7) {
            world.setBlockMetadataWithNotify(x, y, z, meta - 4, 3);
        }
    }
}
