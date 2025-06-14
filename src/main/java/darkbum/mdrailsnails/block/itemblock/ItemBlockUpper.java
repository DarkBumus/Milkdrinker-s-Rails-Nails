package darkbum.mdrailsnails.block.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class ItemBlockUpper extends ItemBlock {

    @SideOnly(Side.CLIENT)
    private IIcon iconUpper;

    public ItemBlockUpper(Block block) {
        super(block);
        setHasSubtypes(false);
    }

    /**
     * Registers the icon for the item.
     * This method is called during initialization to assign the texture to the item.
     *
     * @param icon The icon register used to load textures.
     */
    @Override
    public void registerIcons(IIconRegister icon) {
        iconUpper = icon.registerIcon("mdrailsnails:upper");
    }

    /**
     * Returns the icon to be used for rendering based on the item's damage value.
     *
     * @param meta The damage value of the item.
     * @return the icon for the item.
     */
    @Override
    public IIcon getIconFromDamage(int meta) {
        return iconUpper;
    }
}
