package darkbum.mdrailsnails.util;

import darkbum.mdrailsnails.block.BlockRailHighSpeedTransition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static darkbum.mdrailsnails.init.ModBlocks.*;
import static darkbum.mdrailsnails.util.CartLinkHandler.*;
import static darkbum.mdrailsnails.util.IsPassable.*;
import static darkbum.mdrailsnails.util.ItemUtils.*;
import static java.lang.Integer.*;
import static java.lang.Math.*;
import static java.lang.System.*;
import static java.util.Comparator.*;
import static net.minecraft.block.Block.*;
import static net.minecraft.item.ItemStack.*;
import static net.minecraft.util.AxisAlignedBB.*;
import static net.minecraftforge.common.util.ForgeDirection.*;

public class RailUtils {

    private static final Map<EntityPlayer, Long> lastBlockedMessage = new WeakHashMap<>();
    private static final long MESSAGE_COOLDOWN_MS = 1000;

    private static void sendBlockedMessageWithCooldown(World world, Entity passengerOrNearby, Entity cart) {
        EntityPlayer player = (passengerOrNearby instanceof EntityPlayer) ? (EntityPlayer) passengerOrNearby : world.getClosestPlayerToEntity(cart, 16.0);

        if (player == null) return;

        long now = currentTimeMillis();
        Long lastTime = lastBlockedMessage.get(player);

        if (lastTime != null && now - lastTime <= MESSAGE_COOLDOWN_MS) return;

        lastBlockedMessage.put(player, now);

        int x = (int) Math.floor(cart.posX);
        int y = (int) Math.floor(cart.posY);
        int z = (int) Math.floor(cart.posZ);

        Block block = world.getBlock(x, y, z);
        String baseName = block.getUnlocalizedName();
        String messageKey = baseName + ".mess.blocked";

        player.addChatMessage(new ChatComponentTranslation(messageKey));
    }

    public static float getMinecartSpeedFromRules(World world) {
        int ruleValue = minecartMaxSpeedBaseValue;

        GameRules rules = world.getGameRules();
        if (rules.hasRule("minecartMaxSpeed")) {
            try {
                ruleValue = parseInt(rules.getGameRuleStringValue("minecartMaxSpeed"));
            } catch (NumberFormatException ignored) {}
        }

        ruleValue = Math.max(1, Math.min(ruleValue, 1000));
        return ruleValue * 0.05f;
    }

    public static void updateHighSpeedState(World world, EntityMinecart cart) {
        NBTTagCompound data = cart.getEntityData();

        float lastCartSpeed = data.getFloat("LastCartSpeed");
        float cartSpeed = (float) Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);
        data.setFloat("LastCartSpeed", cartSpeed);

        float maxAllowedSpeed = getMinecartSpeedFromRules(world);
        float graceFactor = 1.4f;
        float highSpeedThreshold = maxAllowedSpeed * graceFactor;

        boolean wasOverHighSpeed = lastCartSpeed > highSpeedThreshold || cartSpeed > highSpeedThreshold;
        boolean wasOverNormal = lastCartSpeed > maxAllowedSpeed || cartSpeed > maxAllowedSpeed;

        if (wasOverHighSpeed) {
            data.setString("SpeedState", "HIGHSPEED");
        } else if (wasOverNormal) {
            data.setString("SpeedState", "WARNING");
        } else {
            data.setString("SpeedState", "NORMAL");
        }
    }

    /*======================================== JUNCTION RAIL UTIL =====================================*/

    public static int handleJunctionRedirectingCartBehavior(EntityMinecart cart) {
        if (cart == null)
            return 0;

        double motionX = Math.abs(cart.motionX);
        double motionZ = Math.abs(cart.motionZ);

        if (motionZ >= motionX)
            return 0;
        return 1;
    }

    /*======================================== WYE RAIL UTILS =====================================*/

    public static int handleWyeRedirectingCartBehavior(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        if (cart == null) return 0;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        double motionX = cart.motionX;
        double motionZ = cart.motionZ;

        boolean xOverZ = Math.abs(motionX) > Math.abs(motionZ);
        boolean positiveX = motionX > 0;
        boolean positiveZ = motionZ > 0;

        if (block == wye_rail_j) {
            return handleWyeJ(meta, xOverZ, positiveX, positiveZ);
        } else if (block == wye_rail_r) {
            return handleWyeR(meta, xOverZ, positiveX, positiveZ);
        } else if (block == wye_rail_l) {
            return handleWyeL(meta, xOverZ, positiveX, positiveZ);
        }
        return 0;
    }

    private static int handleWyeJ(int meta, boolean xOverZ, boolean positiveX, boolean positiveZ) {
        return switch (meta) {
            case 6 -> xOverZ ? 1 : (positiveZ ? 9 : 6);
            case 7 -> xOverZ ? (positiveX ? 7 : 6) : 0;
            case 8 -> xOverZ ? 1 : (positiveZ ? 8 : 7);
            case 9 -> xOverZ ? (positiveX ? 8 : 9) : 0;
            default -> 0;
        };
    }

    private static int handleWyeR(int meta, boolean xOverZ, boolean positiveX, boolean positiveZ) {
        return switch (meta) {
            case 6 -> xOverZ ? (positiveX ? 1 : 9) : (positiveZ ? 9 : 0);
            case 7 -> xOverZ ? (positiveX ? 1 : 6) : (positiveZ ? 0 : 6);
            case 8 -> xOverZ ? (positiveX ? 7 : 1) : (positiveZ ? 0 : 7);
            case 9 -> xOverZ ? (positiveX ? 8 : 1) : (positiveZ ? 8 : 0);
            default -> 0;
        };
    }

    private static int handleWyeL(int meta, boolean xOverZ, boolean positiveX, boolean positiveZ) {
        return switch (meta) {
            case 6 -> xOverZ ? (positiveX ? 1 : 6) : (positiveZ ? 0 : 6);
            case 7 -> xOverZ ? (positiveX ? 7 : 1) : (positiveZ ? 0 : 7);
            case 8 -> xOverZ ? (positiveX ? 8 : 1) : (positiveZ ? 8 : 0);
            case 9 -> xOverZ ? (positiveX ? 1 : 9) : (positiveZ ? 9 : 0);
            default -> 0;
        };
    }

    /*======================================== ONE-WAY RAIL UTIL =====================================*/

    public static void handleOneWayCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;
        double motionX = cart.motionX;
        double motionZ = cart.motionZ;

        Block block = world.getBlock(x, y, z);

        boolean isRightRail = block == one_way_rail_r;
        boolean allowNorthSouth = (meta == 0 || meta == 4 || meta == 5);
        boolean allowEastWest = (meta == 1 || meta == 2 || meta == 3);

        if (allowNorthSouth || allowEastWest) return;

        if (meta == 8 || meta == 12 || meta == 13) {
            if ((motionZ > 0 && !isRightRail) || (motionZ < 0 && isRightRail))
                return;

            cart.motionZ = -motionZ;
        }
        if ((motionX < 0 && !isRightRail) || (motionX > 0 && isRightRail))
            return;

        cart.motionX = -motionX;
    }

    /*======================================== LOCKING RAIL UTILS =====================================*/

    public static void handleLockingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z) & 15;

        if (meta <= 5) return;

        if ((block == locking_release_rail || block == locking_release_rail_i) && meta >= 8 && meta <= 13) {
            NBTTagCompound data = cart.getEntityData();
            if (!data.hasKey("storedMotionX") && !data.hasKey("storedMotionZ")) {
                data.setDouble("storedMotionX", cart.motionX);
                data.setDouble("storedMotionZ", cart.motionZ);
            }
        }

        cart.motionX = 0.0D;
        cart.motionY = 0.0D;
        cart.motionZ = 0.0D;
        cart.setVelocity(0.0D, 0.0D, 0.0D);
        cart.velocityChanged = true;

        cart.posX = x + 0.5D;
        cart.posZ = z + 0.5D;
    }

    public static void releaseCart(World world, int x, int y, int z) {
        if (world.isRemote) return;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z) & 15;

        if (meta > 5) return;

        List<EntityMinecart> carts = world.getEntitiesWithinAABB(EntityMinecart.class, getBoundingBox(x, y, z, x + 1, y + 1, z + 1));

        for (EntityMinecart cart : carts) {
            double pushX = 0.0D;
            double pushZ = 0.0D;

            NBTTagCompound data = cart.getEntityData();

            if (block == locking_release_rail || block == locking_release_rail_i) {
                pushX = data.getDouble("storedMotionX");
                pushZ = data.getDouble("storedMotionZ");

                if (pushX == 0 && pushZ == 0) continue;

                if (block == locking_release_rail_i) {
                    pushX = -pushX;
                    pushZ = -pushZ;
                }
            } else if (block == locking_release_rail_r) {
                if (meta == 0 || meta == 4 || meta == 5) pushZ = -0.2D;
                else pushX = 0.2D;
            } else if (block == locking_release_rail_l) {
                if (meta == 0 || meta == 4 || meta == 5) pushZ = 0.2D;
                else pushX = -0.2D;
            }

            cart.motionX = pushX;
            cart.motionZ = pushZ;
            cart.setVelocity(pushX, 0.0D, pushZ);
            cart.velocityChanged = true;

            data.removeTag("storedMotionX");
            data.removeTag("storedMotionZ");
        }
    }

    /*======================================== DISMOUNTING RAIL UTILS =====================================*/

    public static void handleDismountingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;
        Block block = world.getBlock(x, y, z);

        if (meta <= 5) return;
        if (cart.riddenByEntity == null) return;

        Entity passenger = cart.riddenByEntity;

        double dismountX = cart.posX;
        double dismountY = cart.posY + 0.1;
        double dismountZ = cart.posZ;

        boolean isNW = block == dismounting_rail_nw;
        boolean isSE = block == dismounting_rail_se;

        if (!isNW && !isSE) return;

        if (meta == 8 || meta == 12 || meta == 13) {
            if (isNW) {
                dismountX = x - dismountingRailDistance + 0.5;
            } else {
                dismountX = x + dismountingRailDistance + 0.5;
            }
            dismountZ = z + 0.5;
        }
        if (meta == 9 || meta == 10 || meta == 11) {
            dismountX = x + 0.5;
            if (isNW) {
                dismountZ = z - dismountingRailDistance + 0.5;
            } else {
                dismountZ = z + dismountingRailDistance + 0.5;
            }
        }

        int dx = (int) Math.floor(dismountX);
        int dz = (int) Math.floor(dismountZ);
        int dy = (int) Math.floor(dismountY);

        int validY = isDismountValid(world, dx, dy, dz);

        if (validY == -1) {
            if ((!(passenger instanceof EntityPlayer))) {
                world.getClosestPlayerToEntity(cart, 16.0);
            }

            sendBlockedMessageWithCooldown(world, passenger, cart);

            return;
        }

        cart.riddenByEntity.mountEntity(null);

        dismountY = validY + 0.1;

        if (passenger instanceof EntityPlayer) {
            ((EntityPlayer) passenger).setPositionAndUpdate(dismountX, dismountY, dismountZ);
        } else {
            passenger.setPosition(dismountX, dismountY, dismountZ);
        }
    }

    private static int isDismountValid(World world, int x, int y, int z) {
        if (isPassable(world, x, y, z)) {
            if (isPassable(world, x, y + 1, z)) {
                return y;
            }
        }
        if (isPassable(world, x, y + 1, z) && isPassable(world, x, y + 2, z)) {
            return y + 1;
        }
        return -1;
    }

    /*======================================== MOUNTING RAIL UTIL =====================================*/

    public static void handleMountingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        if (!(cart instanceof EntityMinecartEmpty)) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;
        if (meta <= 5) return;

        if (cart.riddenByEntity != null) return;

        double radius = mountingRailDistance + 0.5;
        AxisAlignedBB searchBox = getBoundingBox(
            cart.posX - radius, cart.posY - 1, cart.posZ - radius,
            cart.posX + radius, cart.posY + 2, cart.posZ + radius
        );

        List<Entity> nearby = world.getEntitiesWithinAABB(Entity.class, searchBox);
        List<EntityLivingBase> candidates = new ArrayList<>();

        for (Entity entity : nearby) {
            if (entity instanceof EntityLivingBase && entity.ridingEntity == null && entity != cart) {
                candidates.add((EntityLivingBase) entity);
            }
        }

        if (candidates.isEmpty()) return;

        candidates.sort(comparingDouble(e -> e.getDistanceSqToEntity(cart)));

        double minDist = candidates.get(0).getDistanceSqToEntity(cart);
        List<EntityLivingBase> closest = new ArrayList<>();
        for (EntityLivingBase e : candidates) {
            if (Math.abs(e.getDistanceSqToEntity(cart) - minDist) < 0.01) {
                closest.add(e);
            }
        }

        EntityLivingBase chosen = closest.size() > 1
            ? closest.get(world.rand.nextInt(closest.size()))
            : closest.get(0);

        chosen.mountEntity(cart);
    }

    /*======================================== COUPLING RAIL UTIL =====================================*/

    public static void handleCouplingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z) & 15;

        boolean isCouplingRail = block == coupling_rail;
        boolean isDecouplingRail = block == decoupling_rail;

        if (!isCouplingRail && !isDecouplingRail) return;

        if (meta <= 5) return;

        if (isCouplingRail) {
            handleAutomaticLink(world, cart);
        } else {
            handleAutomaticUnlink(world, cart);
        }
    }

    /*======================================== SUSPENDED RAILS UTILS =====================================*/

    public static boolean canPlaceSuspendedRail(World world, int x, int y, int z, int maxLength) {
        if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)) {
            return true;
        }

        for (ForgeDirection dir : VALID_DIRECTIONS) {
            if (dir == DOWN) continue;

            int nx = x + dir.offsetX;
            int ny = y + dir.offsetY;
            int nz = z + dir.offsetZ;
            Block neighbor = world.getBlock(nx, ny, nz);

            if (neighbor instanceof BlockRailBase) {
                if (isWithinSuspensionLimit(world, nx, ny, nz, dir, maxLength)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isWithinSuspensionLimit(World world, int x, int y, int z, ForgeDirection fromDir, int maxLength) {
        int count = 0;
        int dx = fromDir.offsetX;
        int dy = fromDir.offsetY;
        int dz = fromDir.offsetZ;

        while (count <= (maxLength - 1)) {
            Block block = world.getBlock(x, y, z);
            if (block instanceof BlockRailBase) {
                if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)) {
                    return true;
                }
                x += dx;
                y += dy;
                z += dz;
                count++;
            } else {
                return false;
            }
        }

        return false;
    }

    public static boolean isStillValid(World world, int x, int y, int z, int maxLength) {
        if (world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, UP)) {
            return false;
        }

        for (ForgeDirection dir : VALID_DIRECTIONS) {
            Block neighbor = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (neighbor instanceof BlockRailBase) {
                if (isWithinSuspensionLimit(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, maxLength)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void handleSuspendedDestruction(World world, int x, int y, int z, Block block) {
        world.playAuxSFX(2001, x, y, z, getIdFromBlock(block));
        world.setBlockToAir(x, y, z);
        world.removeTileEntity(x, y, z);
        block.dropBlockAsItem(world, x, y, z, 0, 0);
    }

    /*======================================== DISPOSING RAIL UTIL =====================================*/

    public static void handleDisposingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;
        if (cart.riddenByEntity == null) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;
        if (meta != 8 && meta != 9) return;

        Entity passenger = cart.riddenByEntity;

        int dy = y - 1;

        boolean blockBelowFree = isPassable(world, x, dy, z);
        boolean blockTwoBelowFree = isPassable(world, x, dy - 1, z);

        if (!blockBelowFree || !blockTwoBelowFree) {
            sendBlockedMessageWithCooldown(world, passenger, cart);
            return;
        }

        cart.riddenByEntity.mountEntity(null);

        double dismountX = x + 0.5;
        double dismountY = y - 1.9;
        double dismountZ = z + 0.5;

        if (passenger instanceof EntityPlayer) {
            ((EntityPlayer) passenger).setPositionAndUpdate(dismountX, dismountY, dismountZ);
        } else {
            passenger.setPosition(dismountX, dismountY, dismountZ);
        }
    }

    /*======================================== LAUNCHING RAIL UTIL =====================================*/

    public static void handleLaunchingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        Block block = world.getBlock(x, y, z);
        if (block != launching_rail) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;

        if (meta == 0 || meta == 1) return;

        if (meta == 8 || meta == 9) {
            if (Math.abs(cart.motionX) > 0.009) {
                cart.motionX = copySign(0.6D, cart.motionX);
            }
            if (Math.abs(cart.motionZ) > 0.009) {
                cart.motionZ = copySign(0.6D, cart.motionZ);
            }

            cart.setMaxSpeedAirLateral(0.6F);
            cart.setMaxSpeedAirVertical(0.5F);
            cart.setDragAir(0.99999D);

            cart.motionY = 0.5D;

            cart.moveEntity(cart.motionX, 1.5D, cart.motionZ);
        }
    }

    /*======================================== CART DISLOCATING RAIL UTILS =====================================*/

    public static void handleDislocatingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        if (world.isRemote) return;

        int meta = world.getBlockMetadata(x, y, z) & 15;
        if (meta <= 5) return;

        if (meta >= 8 && meta <= 13) {
            cart.setDead();

            ItemStack drop = getSpecialDrop(cart);
            if (drop == null) return;

            TileEntity tileBelow = world.getTileEntity(x, y - 1, z);

            if (tileBelow instanceof IInventory inventory) {
                ItemStack remaining = tryInsertStackIntoInventory(inventory, drop.copy());

                if (remaining == null || remaining.stackSize == 0) return;

                drop = remaining;
            }

            EntityItem item = new EntityItem(world, cart.posX, cart.posY, cart.posZ, drop);
            world.spawnEntityInWorld(item);
        }
    }

    private static ItemStack tryInsertStackIntoInventory(IInventory inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slotStack = inventory.getStackInSlot(i);

            if (slotStack == null) {
                inventory.setInventorySlotContents(i, stack);
                return null;
            }

            if (inventory.isItemValidForSlot(i, stack)
                && slotStack.getItem() == stack.getItem()
                && areItemStackTagsEqual(slotStack, stack)
                && slotStack.stackSize < slotStack.getMaxStackSize()) {

                int transferable = Math.min(stack.stackSize, slotStack.getMaxStackSize() - slotStack.stackSize);
                slotStack.stackSize += transferable;
                stack.stackSize -= transferable;

                if (stack.stackSize <= 0) return null;
            }
        }
        return stack.stackSize > 0 ? stack : null;
    }

    /*======================================== ONE-WAY DETECTOR RAIL UTILS =====================================*/

    public static void handleOneWayCartDetectingBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        boolean shouldActivate = isActivated(cart, block, meta);

        if (shouldActivate) {
            world.setBlockMetadataWithNotify(x, y, z, meta | 8, 3);
            world.notifyBlocksOfNeighborChange(x, y, z, block);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, block);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);

            world.scheduleBlockUpdate(x, y, z, block, 20);
            world.func_147453_f(x, y, z, block);
        }
    }

    private static boolean isActivated(EntityMinecart cart, Block block, int meta) {
        boolean shouldActivate = false;

        double motionX = cart.motionX;
        double motionZ = cart.motionZ;

        if (block == one_way_detector_rail_r) {
            if ((meta == 0 || meta == 4 || meta == 5) && motionZ < 0) {
                shouldActivate = true;
            } else if ((meta == 1 || meta == 2 || meta == 3) && motionX > 0) {
                shouldActivate = true;
            }
        }

        if (block == one_way_detector_rail_l) {
            if ((meta == 0 || meta == 4 || meta == 5) && motionZ > 0) {
                shouldActivate = true;
            } else if ((meta == 1 || meta == 2 || meta == 3) && motionX < 0) {
                shouldActivate = true;
            }
        }
        return shouldActivate;
    }

    /*======================================== CART DETECTOR RAIL UTIL =====================================*/

    public static void handleCartTypeDetectingBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        Class<? extends EntityMinecart> targetClass = cart.getClass();

        int[][] offsets = {
            { 1,  0,  0},
            {-1,  0,  0},
            { 0,  1,  0},
            { 0, -1,  0},
            { 0,  0,  1},
            { 0,  0, -1}
        };

        boolean foundSameType = false;

        for (int[] offset : offsets) {
            int dx = x + offset[0];
            int dy = y + offset[1];
            int dz = z + offset[2];

            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(dx, dy, dz, dx + 1, dy + 1, dz + 1);
            List<EntityMinecart> cartsInBlock = world.getEntitiesWithinAABB(EntityMinecart.class, box);

            for (EntityMinecart other : cartsInBlock) {
                if (other != cart && other.getClass() == targetClass) {
                    foundSameType = true;
                    break;
                }
            }

            if (foundSameType) break;
        }

        if (foundSameType && (meta & 8) == 0) {
            world.setBlockMetadataWithNotify(x, y, z, meta | 8, 3);
            world.notifyBlocksOfNeighborChange(x, y, z, block);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, block);
            world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
            world.scheduleBlockUpdate(x, y, z, block, 20);
            world.func_147453_f(x, y, z, block);
        }
    }

    /*======================================== BOOSTER RAIL UTILS =====================================*/

    public static void handleBoostingCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (!(block instanceof BlockRailBase railBlock)) {
            return;
        }

        int railShape = railBlock.getBasicRailMetadata(world, cart, x, y, z) & 0x7;
        boolean powered = (world.getBlockMetadata(x, y, z) & 0x8) != 0;
        double maxSpeed = railBlock.getRailMaxSpeed(world, cart, y, x, z);

        applyBoost(world, cart, x, y, z, railShape, maxSpeed, powered);
    }

    public static void applyBoost(World world, EntityMinecart cart, int x, int y, int z, int railShape, double maxSpeed, boolean powered) {
        double directionX = 0.0D;
        double directionZ = switch (railShape) {
            case 0 -> {
                directionX = 0.0D;
                yield 1.0D;
            }
            case 1 -> {
                directionX = 1.0D;
                yield 0.0D;
            }
            case 2 -> {
                directionX = 1.0D;
                yield -1.0D;
            }
            case 3 -> {
                directionX = -1.0D;
                yield -1.0D;
            }
            case 4 -> {
                directionX = 1.0D;
                yield 1.0D;
            }
            case 5 -> {
                directionX = -1.0D;
                yield 1.0D;
            }
            case 6, 7 -> {
                directionX = railShape == 6 ? 1.0D : -1.0D;
                yield 0.0D;
            }
            default -> 0.0D;
        };

        double directionLength = Math.sqrt(directionX * directionX + directionZ * directionZ);
        double cartDirectionDot = cart.motionX * directionX + cart.motionZ * directionZ;

        if (cartDirectionDot < 0.0D) {
            directionX = -directionX;
            directionZ = -directionZ;
        }

        double cartSpeed = Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);
        if (cartSpeed > maxSpeed) {
            cartSpeed = maxSpeed;
        }

        cart.motionX = cartSpeed * directionX / directionLength;
        cart.motionZ = cartSpeed * directionZ / directionLength;

        if (powered) {
            double boostedSpeed = Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);
            if (boostedSpeed > 0.01D) {
                double boostAmount = 0.06D;
                cart.motionX += cart.motionX / boostedSpeed * boostAmount;
                cart.motionZ += cart.motionZ / boostedSpeed * boostAmount;

                double newSpeed = Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);
                if (newSpeed > maxSpeed) {
                    double scale = maxSpeed / newSpeed;
                    cart.motionX *= scale;
                    cart.motionZ *= scale;
                }
            } else if (railShape == 1) {
                if (world.getBlock(x - 1, y, z).isNormalCube()) {
                    cart.motionX = 0.02D;
                } else if (world.getBlock(x + 1, y, z).isNormalCube()) {
                    cart.motionX = -0.02D;
                }
            } else if (railShape == 0) {
                if (world.getBlock(x, y, z - 1).isNormalCube()) {
                    cart.motionZ = 0.02D;
                } else if (world.getBlock(x, y, z + 1).isNormalCube()) {
                    cart.motionZ = -0.02D;
                }
            }
        } else {
            cart.motionX = 0.0D;
            cart.motionY = 0.0D;
            cart.motionZ = 0.0D;
        }

        cart.setVelocity(cart.motionX, cart.motionY, cart.motionZ);
    }

    /*======================================== HIGH-SPEED TRANSITION RAIL UTIL =====================================*/

    public static void handleTransitioningCartBehavior(World world, EntityMinecart cart, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (!(block instanceof BlockRailBase railBlock)) {
            return;
        }

        int meta = world.getBlockMetadata(x, y, z);
        int railShape = railBlock.getBasicRailMetadata(world, cart, x, y, z) & 0x7;
        boolean powered = (meta & 0x8) != 0;

        float normalSpeed = getMinecartSpeedFromRules(world);
        float highSpeed = block instanceof BlockRailHighSpeedTransition
            ? ((BlockRailHighSpeedTransition) block).getRailMaxSpeed(world, cart, y, x, z)
            : normalSpeed * (highSpeedRailsSpeed * 1.0f);

        double motionX = cart.motionX;
        double motionZ = cart.motionZ;

        float maxSpeed;

        if (block == high_speed_transition_rail_r) {
            if (meta == 8 || meta == 12 || meta == 13) {
                if (motionZ < 0) {
                    maxSpeed = highSpeed;
                } else {
                    maxSpeed = normalSpeed;
                }
            } else if (meta == 9 || meta == 10 || meta == 11) {
                if (motionX > 0) {
                    maxSpeed = highSpeed;
                } else {
                    maxSpeed = normalSpeed;
                }
            } else {
                return;
            }
        } else if (block == high_speed_transition_rail_l) {
            if (meta == 8 || meta == 12 || meta == 13) {
                if (motionZ > 0) {
                    maxSpeed = highSpeed;
                } else {
                    maxSpeed = normalSpeed;
                }
            } else if (meta == 9 || meta == 10 || meta == 11) {
                if (motionX < 0) {
                    maxSpeed = highSpeed;
                } else {
                    maxSpeed = normalSpeed;
                }
            } else {
                return;
            }
        } else {
            return;
        }

        applyBoost(world, cart, x, y, z, railShape, maxSpeed, powered);
    }
}
