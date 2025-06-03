package darkbum.mdrailsnails.util;

import darkbum.mdrailsnails.block.rails.IModeableRail;
import darkbum.mdrailsnails.common.config.ModConfigurationItems;
import darkbum.mdrailsnails.entity.EntityMinecartHauler;
import darkbum.mdrailsnails.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static darkbum.mdrailsnails.common.config.ModConfigurationItems.*;
import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.SoundHelper.*;
import static net.minecraft.init.Blocks.*;
import static org.lwjgl.input.Keyboard.*;

/**
 * Utility class for handling various item-related functionalities.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public final class ItemUtils {

    private static final Set<Block> NON_POWERED_RAILS = new HashSet<>(Arrays.asList(
        rail
    ));

    private static final Set<Block> POWERED_RAILS = new HashSet<>(Arrays.asList(
        detector_rail,
        golden_rail,
        activator_rail,
        one_way_rail_r,
        one_way_rail_l,
        locking_rail,
        locking_release_rail,
        locking_release_rail_i,
        locking_release_rail_r,
        locking_release_rail_l,
        dismounting_rail_se,
        dismounting_rail_nw,
        mounting_rail,
        coupling_rail,
        decoupling_rail,
        suspended_rail,
        cart_dislocating_rail
    ));

    private static final Set<Block> OTHER_RAILS = new HashSet<>(Arrays.asList(
        junction_rail,
        wye_rail_j,
        wye_rail_r,
        wye_rail_l
    ));

    public static final Set<Block> EFFECTIVE_BLOCKS = new HashSet<>();
    static {
        EFFECTIVE_BLOCKS.addAll(NON_POWERED_RAILS);
        EFFECTIVE_BLOCKS.addAll(POWERED_RAILS);
        EFFECTIVE_BLOCKS.addAll(OTHER_RAILS);
    }

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

            if (line.equals(lineKey)) break;

            String conditionKey = I18n.format(lineKey + ".condition");
            if (!conditionKey.equals(lineKey + ".condition")) {
                if (isConditionMet(conditionKey)) {
                    list.add(line);
                    foundAny = true;
                }
            } else {
                list.add(line);
                foundAny = true;
            }
        }

        if (!foundAny) {
            String singleTooltip = I18n.format(tooltipKey);
            if (!singleTooltip.equals(tooltipKey)) {
                String condition = I18n.format(tooltipKey + ".condition");
                if (!condition.equals(tooltipKey + ".condition")) {
                    if (isConditionMet(condition)) {
                        list.add(singleTooltip);
                    }
                } else {
                    list.add(singleTooltip);
                }
            }
        }
    }

    public static boolean isConditionMet(String configKey) {
        return switch (configKey) {
            case "enableRailwayLeverTurningAbility" -> ModConfigurationItems.enableRailwayLeverTurningAbility;
            case "enableRailwayLeverModeAbility" -> ModConfigurationItems.enableRailwayLeverModeAbility;
            case "enableRailwayLeverLinkingAbility" -> ModConfigurationItems.enableRailwayLeverLinkingAbility;
            case "enableRailwayLeverPushingAbility" -> ModConfigurationItems.enableRailwayLeverPushingAbility;
            default -> false;
        };
    }

    public static void addItemHiddenTooltip(ItemStack stack, List<String> list) {
        if (isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT)) {
            addItemTooltip(stack, list);
        } else {
            list.add(I18n.format("item.all.tooltip.hidden"));
        }
    }

    public static boolean railwayLeverInteraction(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (enableRailwayLeverModeAbility) changeRailMode(stack, player, world, x, y, z);
        if (enableRailwayLeverTurningAbility) turnNonPoweredRails(stack, player, world, x, y, z); turnOtherRails(stack, player, world, x, y, z);
        if (enableRailwayLeverTurningAbility) turnPoweredRails(stack, player, world, x, y, z);// turnSwitchRails(stack, player, world, x, y, z);

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
        int newMeta = switch (meta) {
            case 0 -> 1;
            case 1 -> 0;
            case 2 -> 3;
            case 3 -> 4;
            case 4 -> 5;
            case 5 -> 2;
            case 6 -> 7;
            case 7 -> 8;
            case 8 -> 9;
            case 9 -> 6;
            default -> -1;
        };
        if (newMeta == -1) return;

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

            boolean state = getToggleState(x, y, z);
            float pitch = state ? 0.6F : 0.5F;
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, pitch);

            stack.damageItem(1, player);
        }
    }

    public static void turnOtherRails(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (!player.isSneaking()) return;

        Block block = world.getBlock(x, y, z);

        if (!(block instanceof BlockRailBase)) return;
        if (!OTHER_RAILS.contains(block)) return;

        int meta = world.getBlockMetadata(x, y, z);
        int newMeta = switch (meta) {
            case 6 -> 7;
            case 7 -> 8;
            case 8 -> 9;
            case 9 -> 6;
            default -> -1;
        };
        if (newMeta == -1) return;

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

            boolean state = getToggleState(x, y, z);
            float pitch = state ? 0.6F : 0.5F;
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, pitch);

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
        int newMeta = switch (meta) {
            case 0 -> 1;
            case 1 -> 0;
            case 2 -> 3;
            case 3 -> 4;
            case 4 -> 5;
            case 5 -> 2;
            case 8 -> 9;
            case 9 -> 8;
            case 10 -> 11;
            case 11 -> 12;
            case 12 -> 13;
            case 13 -> 10;
            default -> -1;
        };
        if (newMeta == -1) return;

        if (!world.isRemote) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

            boolean state = getToggleState(x, y, z);
            float pitch = state ? 0.6F : 0.5F;
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, pitch);

            stack.damageItem(1, player);
        }
    }

    public static void changeRailMode(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        if (player.isSneaking()) return;

        Block currentBlock = world.getBlock(x, y, z);

        if (!(currentBlock instanceof IModeableRail)) return;
        if (!POWERED_RAILS.contains(currentBlock) && !OTHER_RAILS.contains(currentBlock)) return;

        Block targetBlock = ((IModeableRail) currentBlock).getTurnedBlock();
        int meta = world.getBlockMetadata(x, y, z);

        if (targetBlock != null && !world.isRemote) {
            world.setBlock(x, y, z, targetBlock, meta, 3);
            world.setBlockMetadataWithNotify(x, y, z, meta, 3);

            if (targetBlock instanceof IModeableRail) {
                String translationKey = ((IModeableRail) targetBlock).getModeChangeMessageKey();
                if (translationKey != null && !translationKey.isEmpty()) {
                    player.addChatMessage(new ChatComponentTranslation(translationKey));
                }
            }

            boolean state = getToggleState(x, y, z);
            float pitch = state ? 0.6F : 0.5F;
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, pitch);

            stack.damageItem(1, player);
        }
    }

    public static ItemStack getSpecialDrop(EntityMinecart cart) {
        if (cart instanceof EntityMinecartEmpty) return new ItemStack(Items.minecart);
        else if (cart instanceof EntityMinecartChest) return new ItemStack(Items.chest_minecart);
        else if (cart instanceof EntityMinecartFurnace) return new ItemStack(Items.furnace_minecart);
        else if (cart instanceof EntityMinecartTNT) return new ItemStack(Items.tnt_minecart);
        else if (cart instanceof EntityMinecartHopper) return new ItemStack(Items.hopper_minecart);
        else if (cart instanceof EntityMinecartCommandBlock) return new ItemStack(Items.command_block_minecart);
        else if (cart instanceof EntityMinecartHauler) return new ItemStack(ModItems.hauler_minecart);

        return null;
    }
}
