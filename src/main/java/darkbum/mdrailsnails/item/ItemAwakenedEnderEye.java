package darkbum.mdrailsnails.item;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

import static darkbum.mdrailsnails.util.ItemUtils.*;

public class ItemAwakenedEnderEye extends Item {

    public ItemAwakenedEnderEye(String name, CreativeTabs tab) {
        setUnlocalizedName(name);
        setCreativeTab(tab);
        setMaxStackSize(1);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            NBTTagCompound tag = getItemTag(stack);
            tag.setInteger("dimension", world.provider.dimensionId);
            tag.setInteger("x", x);
            tag.setInteger("y", y);
            tag.setInteger("z", z);
            tag.setInteger("iconSide", side);
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote)
            return itemStack;

        NBTTagCompound tag = itemStack.getTagCompound();
        boolean hasLink = tag != null && tag.hasKey("x") && tag.hasKey("y") && tag.hasKey("z") && tag.hasKey("dimension");

        if (player.isSneaking() && hasLink) {
            MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
            if (mop == null || mop.typeOfHit == MovingObjectPosition.MovingObjectType.MISS) {
                itemStack.setTagCompound(null);
                player.addChatMessage(new ChatComponentText(I18n.format("item.awakened_ender_eye.mess")));
                return itemStack;
            }
        }
        if (hasLink) {
            int x = tag.getInteger("x");
            int y = tag.getInteger("y");
            int z = tag.getInteger("z");
            int dimension = tag.getInteger("dimension");
            if (world.provider.dimensionId == dimension && world.blockExists(x, y, z)) {
                clickBlock(world, player, x, y, z, tag.getInteger("iconSide"));
            }
        }
        return itemStack;
    }

    private static void clickBlock(World world, EntityPlayer player, int x, int y, int z, int side) {
        Block block = world.getBlock(x, y, z);
        if (block != Blocks.air)
            block.onBlockActivated(world, x, y, z, player, side, 0.0F, 0.0F, 0.0F);
    }

    public static NBTTagCompound getItemTag(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.stackTagCompound = new NBTTagCompound();
        return stack.stackTagCompound;
    }

    /**
     * Adds additional information to the item tooltip when hovering over the item in the inventory.
     *
     * @param stack    The ItemStack for which the information is being added.
     * @param player   The player viewing the tooltip.
     * @param list     The list to which the tooltip lines are added.
     * @param advanced Whether advanced tooltips are enabled.
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        addItemTooltip(stack, list);
    }
}
