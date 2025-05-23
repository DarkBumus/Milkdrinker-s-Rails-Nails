package darkbum.mdrailsnails.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.item.ItemRailwayLever.NON_POWERED_RAILS;
import static darkbum.mdrailsnails.item.ItemRailwayLever.POWERED_RAILS;

/**
 * Utility class for handling various item-related functionalities.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ItemUtils {

    /**
     * Adds a tooltip to the provided ItemStack based on its unlocalized name.
     *
     * @param stack the ItemStack to add the tooltip to
     * @param list  the list to which the tooltip will be added
     */
    public static void addItemTooltip(ItemStack stack, List<String> list) {
        String baseKey = stack.getUnlocalizedName();
        String tooltipKey = baseKey + ".tooltip";

        boolean foundAny = false;
        for (int i = 0; i < 10; i++) {
            String lineKey = tooltipKey + "." + i;
            String line = I18n.format(lineKey);
            if (!line.equals(lineKey)) {
                list.add(line);
                foundAny = true;
            } else if (i == 0) {
                break;
            } else {
                break;
            }
        }

        if (!foundAny) {
            String singleTooltip = I18n.format(tooltipKey);
            if (!singleTooltip.equals(tooltipKey)) {
                list.add(singleTooltip);
            }
        }
    }

    public static boolean railwayLeverInteraction(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        turnNonPoweredRails(stack, player, world, x, y, z);
        turnPoweredRails(stack, player, world, x, y, z);
        changePoweredRailActivity(stack, player, world, x, y, z);
        return false;
    }

    /**
     * Turns {@link BlockRailBase} (non-powered) in certain directions if shift+right-clicked.
     *
     * @param player the player interacting with the block
     * @param world  the world the block is placed in
     * @param x      the x-coordinate of the block
     * @param y      the y-coordinate of the block
     * @param z      the z-coordinate of the block
     */
    public static void turnNonPoweredRails(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (!player.isSneaking()) return;

        Block block = world.getBlock(x, y, z);

        if (!(block instanceof BlockRailBase)) return;
        if (!NON_POWERED_RAILS.contains(block)) return;

        int meta = world.getBlockMetadata(x, y, z);
        int newMeta;

        switch (meta) {
            case 0: newMeta = 1; break;
            case 1: newMeta = 0; break;
            case 2: newMeta = 3; break;
            case 3: newMeta = 4; break;
            case 4: newMeta = 5; break;
            case 5: newMeta = 2; break;
            case 6: newMeta = 7; break;
            case 7: newMeta = 8; break;
            case 8: newMeta = 9; break;
            case 9: newMeta = 6; break;

            default: return;
        }

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
            stack.damageItem(1, player);
        }
    }

    /**
     * Turns {@link BlockRailBase} (powered) in certain directions if shift+right-clicked.
     *
     * @param player the player interacting with the block
     * @param world  the world the block is placed in
     * @param x      the x-coordinate of the block
     * @param y      the y-coordinate of the block
     * @param z      the z-coordinate of the block
     */
    public static void turnPoweredRails(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (!player.isSneaking()) return;

        Block block = world.getBlock(x, y, z);

        if (!(block instanceof BlockRailBase)) return;
        if (!POWERED_RAILS.contains(block)) return;

        int meta = world.getBlockMetadata(x, y, z);
        int newMeta;

        switch (meta) {
            case 0: newMeta = 1; break;
            case 1: newMeta = 0; break;
            case 2: newMeta = 3; break;
            case 3: newMeta = 4; break;
            case 4: newMeta = 5; break;
            case 5: newMeta = 2; break;

            case 8: newMeta = 9; break;
            case 9: newMeta = 8; break;
            case 10: newMeta = 11; break;
            case 11: newMeta = 12; break;
            case 12: newMeta = 13; break;
            case 13: newMeta = 10; break;

            default: return;
        }

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
            stack.damageItem(1, player);
        }
    }

    /**
     * Changes the activity status of {@link BlockRailBase} (powered) if shift+right-clicked.
     *
     * @param player the player interacting with the block
     * @param world  the world the block is placed in
     * @param x      the x-coordinate of the block
     * @param y      the y-coordinate of the block
     * @param z      the z-coordinate of the block
     */
    public static void changePoweredRailActivity(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (!enableRailwayLeverDebugAbility) return;
        if (player.isSneaking()) return;

        Block block = world.getBlock(x, y, z);


        if (!(block instanceof BlockRailBase)) return;
        if (!POWERED_RAILS.contains(block)) return;

        int meta = world.getBlockMetadata(x, y, z);
        int newMeta;

        switch (meta) {
            case 0: newMeta = 8; break;
            case 1: newMeta = 9; break;
            case 2: newMeta = 10; break;
            case 3: newMeta = 11; break;
            case 4: newMeta = 12; break;
            case 5: newMeta = 13; break;

            case 8: newMeta = 0; break;
            case 9: newMeta = 1; break;
            case 10: newMeta = 2; break;
            case 11: newMeta = 3; break;
            case 12: newMeta = 4; break;
            case 13: newMeta = 5; break;

            default: return;
        }

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
            stack.damageItem(1, player);
        }
    }
}
