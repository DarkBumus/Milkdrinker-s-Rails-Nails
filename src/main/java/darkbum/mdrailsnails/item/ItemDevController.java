package darkbum.mdrailsnails.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDevController extends Item {

    public ItemDevController(String name, CreativeTabs tab) {
        setUnlocalizedName(name);
        setCreativeTab(tab);
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            int newMeta;

            if (player.isSneaking()) {
                newMeta = (meta + 15) % 16;
            } else {
                newMeta = (meta + 1) % 16;
            }

            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

            player.addChatMessage(new ChatComponentText(
                "Set block meta at (" + x + "," + y + "," + z + ") to: " + newMeta));
        }
        return true;
    }
}
