package darkbum.mdrailsnails.entity;

import darkbum.mdrailsnails.MDRailsNails;
import darkbum.mdrailsnails.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

import static darkbum.mdrailsnails.init.ModExternalLoader.*;

public class EntityMinecartHauler extends EntityMinecartContainer implements IInventory {
    private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };

    public int fuel;
    private double pushX;
    private double pushZ;
    private double savedPushX;
    private double savedPushZ;

    public EntityMinecartHauler(World world, double x, double y, double z) {
        super(world, x, y, z);
        dataWatcher.addObject(31, 0);
    }

    @SuppressWarnings("unused")
    public EntityMinecartHauler(World world) {
        super(world);
        dataWatcher.addObject(31, 0);
    }

    @Override
    public int getMinecartType() {
        return 10;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(posY);
        int z = MathHelper.floor_double(posZ);
        Block block = worldObj.getBlock(x, y, z);
        int meta = worldObj.getBlockMetadata(x, y, z);
        if (!worldObj.isRemote) {
            if (fuel > 0 && (!block.equals(Blocks.golden_rail) || (meta & 0x8) != 0))
                fuel--;
            if (fuel <= 0) {
                boolean reloaded = false;
                for (int i = 0; i < 9; i++) {
                    if (useFuelFromSlot()) {
                        reloaded = true;
                        break;
                    }
                }
                if (!reloaded) {
                    pushX = 0.0D;
                    pushZ = 0.0D;
                }
            } else {
                if (Math.sqrt(savedPushX * savedPushX + savedPushZ * savedPushZ) < 0.1D) {
                    savedPushX = motionX;
                    savedPushZ = motionZ;
                }
                if (block.equals(Blocks.golden_rail) && (meta & 0x8) == 0) {
                    pushX = 0.0D;
                    pushZ = 0.0D;
                } else {
                    pushX = savedPushX;
                    pushZ = savedPushZ;
                }
            }
            setMinecartPowered((fuel > 0));
            dataWatcher.updateObject(31, fuel);
        }
        if (worldObj.isRemote && isMinecartPowered()) {
            if (rand.nextInt(3) == 0)
                worldObj.spawnParticle("largesmoke", posX + rand.nextDouble() - 0.5D, posY + 0.8D, posZ + rand.nextDouble() - 0.5D, 0.0D, 0.0D, 0.0D);
            if (rand.nextInt(20) == 0)
                worldObj.spawnParticle("flame", posX, posY, posZ, 0.0D, 0.05D, 0.0D);
        }
    }

    protected boolean useFuelFromSlot() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null)
            return false;

        int burnTime = TileEntityFurnace.getItemBurnTime(stack);

        if (burnTime > 0) {
            fuel += burnTime;
            dataWatcher.updateObject(31, fuel);
            decrStackSize();
            return true;
        }

        return false;
    }

    public int getFuelLevel() {
        return worldObj.isRemote ? dataWatcher.getWatchableObjectInt(31) : fuel;
    }

    private void decrStackSize() {
        ItemStack stack = getStackInSlot(0);
        if (stack == null) return;

        if (stack.getItem() instanceof ItemBucket) {
            setInventorySlotContents(0, new ItemStack(Items.bucket));
        } else {
            stack.stackSize--;
            if (stack.stackSize <= 0) {
                setInventorySlotContents(0, null);
            }
        }

        markDirty();
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public void killMinecart(DamageSource damageSource) {
        setDead();
        if (worldObj.isRemote) return;
        entityDropItem(new ItemStack(ModItems.hauler_minecart), 0.0f);
    }

    @Override
    public void setDead() {
        if (worldObj.isRemote)
            for (int slot = 0; slot < getSizeInventory(); slot++)
                setInventorySlotContents(slot, null);
        super.setDead();
    }

    protected void applyDrag() {
        double d0 = pushX * pushX + pushZ * pushZ;
        if (d0 > 1.0E-4D) {
            d0 = MathHelper.sqrt_double(d0);
            pushX /= d0;
            pushZ /= d0;
            double d1 = 0.05D;
            motionX *= 0.800000011920929D;
            motionY *= 0.0D;
            motionZ *= 0.800000011920929D;
            motionX += pushX * d1;
            motionZ += pushZ * d1;
        } else {
            motionX *= 0.9800000190734863D;
            motionY *= 0.0D;
            motionZ *= 0.9800000190734863D;
        }
        super.applyDrag();
    }

    protected void func_145821_a(int x, int y, int z, double maxSpeed, double SlopeAdjustment, Block block, int meta) {
        super.func_145821_a(x, y, z, maxSpeed, SlopeAdjustment, block, meta);
        double d = pushX * pushX + pushZ * pushZ;
        rotateSavedPush(meta);
        if (d > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D) {
            d = MathHelper.sqrt_double(d);
            pushX /= d;
            pushZ /= d;
            if (pushX * motionX + pushZ * motionZ < 0.0D) {
                pushX = 0.0D;
                pushZ = 0.0D;
            } else {
                pushX = motionX;
                pushZ = motionZ;
            }
        }
    }

    private void rotateSavedPush(int meta) {
        int[][] aint = matrix[meta];
        double d2 = (aint[1][0] - aint[0][0]);
        double d3 = (aint[1][2] - aint[0][2]);
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        double d5 = savedPushX * d2 + savedPushZ * d3;
        if (d5 < 0.0D) {
            d2 = -d2;
            d3 = -d3;
        }
        double d6 = Math.sqrt(savedPushX * savedPushX + savedPushZ * savedPushZ);
        if (d6 > 2.0D)
            d6 = 2.0D;
        savedPushX = d6 * d2 / d4;
        savedPushZ = d6 * d3 / d4;
    }

    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(16, (byte) 0);
    }

    public boolean isMinecartPowered() {
        return ((dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
    }

    protected void setMinecartPowered(boolean powered) {
        if (powered) {
            dataWatcher.updateObject(16, (byte) (dataWatcher.getWatchableObjectByte(16) | 0x1));
        } else {
            dataWatcher.updateObject(16, (byte) (dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE));
        }
    }

    protected void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        pushX = nbt.getDouble("PushX");
        pushZ = nbt.getDouble("PushZ");
        savedPushX = nbt.getDouble("SavedPushX");
        savedPushZ = nbt.getDouble("SavedPushZ");
        fuel = nbt.getInteger("Fuel");
    }

    protected void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setDouble("PushX", pushX);
        nbt.setDouble("PushZ", pushZ);
        nbt.setDouble("SavedPushX", savedPushX);
        nbt.setDouble("SavedPushZ", savedPushZ);
        nbt.setInteger("Fuel", fuel);
    }

    public Block func_145817_o() {
        if (efr) {
            return etFuturumBlocks.get("lit_blast_furnace");
        }
        return Blocks.lit_furnace;
    }

    public int getDefaultDisplayTileData() {
        return 2;
    }

    public boolean hasDisplayTile() {
        return false;
    }

    public ItemStack getCartItem() {
        return new ItemStack(ModItems.hauler_minecart, 1);
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        if (!worldObj.isRemote) {
            player.openGui(MDRailsNails.instance, 0, worldObj, getEntityId(), 0, 0);
        }
        return true;
    }
}
