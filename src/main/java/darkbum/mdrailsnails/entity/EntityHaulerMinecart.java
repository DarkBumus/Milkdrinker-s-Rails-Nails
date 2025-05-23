package darkbum.mdrailsnails.entity;

import darkbum.mdrailsnails.MDRailsNails;
import darkbum.mdrailsnails.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

import static darkbum.mdrailsnails.init.ModExternalLoader.efr;
import static darkbum.mdrailsnails.init.ModExternalLoader.etFuturumBlocks;

public class EntityHaulerMinecart extends EntityMinecartContainer implements IInventory {
    private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };

    private int fuel;
    private double pushX;
    private double pushZ;
    private double savedPushX;
    private double savedPushZ;

    public EntityHaulerMinecart(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @SuppressWarnings("unused")
    public EntityHaulerMinecart(World world) {
        super(world);
    }

    public int getMinecartType() {
        return 10;
    }

    public void onUpdate() {
        super.onUpdate();
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);
        Block block = this.worldObj.getBlock(x, y, z);
        int meta = this.worldObj.getBlockMetadata(x, y, z);
        if (!this.worldObj.isRemote) {
            if (this.fuel > 0 && (!block.equals(Blocks.golden_rail) || (meta & 0x8) != 0))
                this.fuel--;
            if (this.fuel <= 0) {
                boolean reloaded = false;
                for (int i = 0; i < 9; i++) {
                    if (useFuelFromSlot(i)) {
                        reloaded = true;
                        break;
                    }
                }
                if (!reloaded) {
                    this.pushX = 0.0D;
                    this.pushZ = 0.0D;
                }
            } else {
                if (Math.sqrt(this.savedPushX * this.savedPushX + this.savedPushZ * this.savedPushZ) < 0.1D) {
                    this.savedPushX = this.motionX;
                    this.savedPushZ = this.motionZ;
                }
                if (block.equals(Blocks.golden_rail) && (meta & 0x8) == 0) {
                    this.pushX = 0.0D;
                    this.pushZ = 0.0D;
                } else {
                    this.pushX = this.savedPushX;
                    this.pushZ = this.savedPushZ;
                }
            }
            setMinecartPowered((this.fuel > 0));
        }
        if (this.worldObj.isRemote && isMinecartPowered()) {
            if (this.rand.nextInt(3) == 0)
                this.worldObj.spawnParticle("largesmoke", this.posX + this.rand.nextDouble() - 0.5D, this.posY + 0.8D, this.posZ + this.rand.nextDouble() - 0.5D, 0.0D, 0.0D, 0.0D);
            if (this.rand.nextInt(20) == 0)
                this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0.0D, 0.05D, 0.0D);
        }
    }

    protected boolean useFuelFromSlot(int i) {
        ItemStack stack = getStackInSlot(i);
        if (stack == null)
            return false;
        Item item = stack.getItem();
        if (item == null)
            return false;
        if (item.equals(Items.coal)) {
            this.fuel += 3600;
            decrStackSize(i);
            return true;
        }
        if (item.equals(Item.getItemFromBlock(Blocks.coal_block))) {
            this.fuel += 36000;
            decrStackSize(i);
            return true;
        }
        if (item.equals(Items.blaze_rod)) {
            this.fuel += 7200;
            decrStackSize(i);
            return true;
        }
        if (item.equals(Items.lava_bucket)) {
            this.fuel += 45000;
            decrStackSize(i);
            return true;
        }
        return false;
    }

    private void decrStackSize(int i) {
        if (--(getStackInSlot(i)).stackSize == 0)
            setInventorySlotContents(i, null);
    }

    public void killMinecart(DamageSource damageSource) {
        if (damageSource.getSourceOfDamage() instanceof EntityPlayer) {
            dropItem(Items.minecart, 1);
            dropItem(Item.getItemFromBlock(Blocks.furnace), 1);
        } else {
            dropItem(ModItems.hauler_minecart, 1);
        }
        setDead();
    }

    protected void applyDrag() {
        double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (d0 > 1.0E-4D) {
            d0 = MathHelper.sqrt_double(d0);
            this.pushX /= d0;
            this.pushZ /= d0;
            double d1 = 0.05D;
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.800000011920929D;
            this.motionX += this.pushX * d1;
            this.motionZ += this.pushZ * d1;
        } else {
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.9800000190734863D;
        }
        super.applyDrag();
    }

    protected void func_145821_a(int x, int y, int z, double maxSpeed, double SlopeAdjustment, Block block, int meta) {
        super.func_145821_a(x, y, z, maxSpeed, SlopeAdjustment, block, meta);
        double d = this.pushX * this.pushX + this.pushZ * this.pushZ;
        rotateSavedPush(meta);
        if (d > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
            d = MathHelper.sqrt_double(d);
            this.pushX /= d;
            this.pushZ /= d;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
                this.pushX = 0.0D;
                this.pushZ = 0.0D;
            } else {
                this.pushX = this.motionX;
                this.pushZ = this.motionZ;
            }
        }
    }

    private void rotateSavedPush(int meta) {
        int[][] aint = matrix[meta];
        double d2 = (aint[1][0] - aint[0][0]);
        double d3 = (aint[1][2] - aint[0][2]);
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        double d5 = this.savedPushX * d2 + this.savedPushZ * d3;
        if (d5 < 0.0D) {
            d2 = -d2;
            d3 = -d3;
        }
        double d6 = Math.sqrt(this.savedPushX * this.savedPushX + this.savedPushZ * this.savedPushZ);
        if (d6 > 2.0D)
            d6 = 2.0D;
        this.savedPushX = d6 * d2 / d4;
        this.savedPushZ = d6 * d3 / d4;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    protected boolean isMinecartPowered() {
        return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
    }

    protected void setMinecartPowered(boolean powered) {
        if (powered) {
            this.dataWatcher.updateObject(16, (byte) (this.dataWatcher.getWatchableObjectByte(16) | 0x1));
        } else {
            this.dataWatcher.updateObject(16, (byte) (this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE));
        }
    }

    protected void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.pushX = nbt.getDouble("PushX");
        this.pushZ = nbt.getDouble("PushZ");
        this.savedPushX = nbt.getDouble("SavedPushX");
        this.savedPushZ = nbt.getDouble("SavedPushZ");
        this.fuel = nbt.getInteger("Fuel");
    }

    protected void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setDouble("PushX", this.pushX);
        nbt.setDouble("PushZ", this.pushZ);
        nbt.setDouble("SavedPushX", this.savedPushX);
        nbt.setDouble("SavedPushZ", this.savedPushZ);
        nbt.setInteger("Fuel", this.fuel);
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
    public int getSizeInventory() {
        return 9;
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            player.openGui(MDRailsNails.instance, 0, worldObj, this.getEntityId(), 0, 0);
        }
        return true;
    }
}
