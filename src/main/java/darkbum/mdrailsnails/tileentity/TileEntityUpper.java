package darkbum.mdrailsnails.tileentity;

import darkbum.mdrailsnails.block.BlockUpper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityUpper extends TileEntityHopper implements IHopper {

    private ItemStack[] hopperItemStacks = new ItemStack[5];
    private int transferCooldown = -1;

    public TileEntityUpper() {
        func_145886_a(I18n.format("container.upper"));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList invTag = nbt.getTagList("Items", 10);
        hopperItemStacks = new ItemStack[getSizeInventory()];
        transferCooldown = nbt.getInteger("TransferCooldown");
        for (int i = 0; i < invTag.tagCount(); ++i) {
            NBTTagCompound stackTag = invTag.getCompoundTagAt(i);
            byte b0 = stackTag.getByte("Slot");
            if (b0 >= 0 && b0 < hopperItemStacks.length) {
                hopperItemStacks[b0] = ItemStack.loadItemStackFromNBT(stackTag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < hopperItemStacks.length; ++i) {
            if (hopperItemStacks[i] != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                hopperItemStacks[i].writeToNBT(stackTag);
                nbttaglist.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", nbttaglist);
        nbt.setInteger("TransferCooldown", transferCooldown);
    }

    public int getSizeInventory() {
        return hopperItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return hopperItemStacks[slot];
    }

    /**
     * Removes from an hopperItemStacks slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (hopperItemStacks[slotIndex] != null) {
            ItemStack itemstack;

            if (hopperItemStacks[slotIndex].stackSize <= amount) {
                itemstack = hopperItemStacks[slotIndex];
                hopperItemStacks[slotIndex] = null;
            } else {
                itemstack = hopperItemStacks[slotIndex].splitStack(amount);

                if (hopperItemStacks[slotIndex].stackSize == 0) {
                    hopperItemStacks[slotIndex] = null;
                }

            }
            return itemstack;
        } else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        if (hopperItemStacks[slotIndex] != null) {
            ItemStack itemstack = hopperItemStacks[slotIndex];
            hopperItemStacks[slotIndex] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the hopperItemStacks (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack stack) {
        hopperItemStacks[slotIndex] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    /**
     * Returns the maximum stack size for a hopperItemStacks slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack stack) {
        return true;
    }

    @Override
    public void updateEntity() {
        if (worldObj != null && !worldObj.isRemote) {
            --transferCooldown;

            if (!func_145888_j()) {
                func_145896_c(0);
                func_145887_i();
            }
        }
    }

    public boolean func_145887_i() {
        if (worldObj != null && !worldObj.isRemote) {
            if (!func_145888_j() && BlockHopper.func_149917_c(getBlockMetadata())) {
                boolean didTransfer = false;

                if (!func_152104_k()) {
                    didTransfer = func_145883_k();
                }
                if (!func_152105_l()) {
                    didTransfer = func_145891_a(this) || didTransfer;
                }
                if (didTransfer) {
                    func_145896_c(8);
                    markDirty();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean func_152104_k() {
        ItemStack[] stacks = hopperItemStacks;

        for (ItemStack stack : stacks) {
            if (stack != null) {
                return false;
            }
        }
        return true;
    }

    private boolean func_152105_l() {
        ItemStack[] stacks = hopperItemStacks;

        for (ItemStack stack : stacks) {
            if (stack == null || stack.stackSize != stack.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    private boolean func_145883_k() {
        IInventory inventory = func_145895_l();

        if (inventory != null) {
            int i = Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(getBlockMetadata())];
            if (!func_152102_a(inventory, i)) {
                for (int j = 0; j < getSizeInventory(); ++j) {
                    if (getStackInSlot(j) != null) {
                        ItemStack stack = getStackInSlot(j).copy();
                        ItemStack stack1 = func_145889_a(inventory, decrStackSize(j, 1), i);

                        if (stack1 == null || stack1.stackSize == 0) {
                            inventory.markDirty();
                            return true;
                        }
                        setInventorySlotContents(j, stack);
                    }
                }
            }
        }
        return false;
    }

    private boolean func_152102_a(IInventory inventory, int p_152102_2_) {
        if (inventory instanceof ISidedInventory isidedinventory && p_152102_2_ > -1) {
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(p_152102_2_);

            for (int i : aint) {
                ItemStack itemstack1 = isidedinventory.getStackInSlot(i);

                if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize()) {
                    return false;
                }
            }
        } else {
            int j = inventory.getSizeInventory();

            for (int k = 0; k < j; ++k) {
                ItemStack itemstack = inventory.getStackInSlot(k);

                if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean func_152103_b(IInventory inventory, int p_152103_1_) {
        if (inventory instanceof ISidedInventory isidedinventory && p_152103_1_ > -1) {
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(p_152103_1_);

            for (int i : aint) {
                if (isidedinventory.getStackInSlot(i) != null) {
                    return false;
                }
            }
        } else {
            int j = inventory.getSizeInventory();

            for (int k = 0; k < j; ++k) {
                if (inventory.getStackInSlot(k) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean func_145891_a(IHopper hopper) {
        IInventory iinventory = func_145884_b(hopper);

        if (iinventory != null) {
            byte b0 = 0;

            if (func_152103_b(iinventory, b0)) {
                return false;
            }
            if (iinventory instanceof ISidedInventory isidedinventory) {
                int[] aint = isidedinventory.getAccessibleSlotsFromSide(b0);

                for (int i : aint) {
                    if (func_145892_a(hopper, iinventory, i, b0)) {
                        return true;
                    }
                }
            } else {
                int i = iinventory.getSizeInventory();

                for (int j = 0; j < i; ++j) {
                    if (func_145892_a(hopper, iinventory, j, b0)) {
                        return true;
                    }
                }
            }
        } else {
            EntityItem entityitem = func_145897_a(hopper.getWorldObj(), hopper.getXPos(), hopper.getYPos() - 1.0D, hopper.getZPos());

            if (entityitem != null) {
                return func_145898_a(hopper, entityitem);
            }
        }
        return false;
    }

    private static boolean func_145892_a(IHopper hopper, IInventory inventory, int p_145892_2_, int p_145892_3_) {
        ItemStack itemstack = inventory.getStackInSlot(p_145892_2_);

        if (itemstack != null && func_145890_b(inventory, itemstack, p_145892_2_, p_145892_3_)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = func_145889_a(hopper, inventory.decrStackSize(p_145892_2_, 1), -1);

            if (itemstack2 == null || itemstack2.stackSize == 0) {
                inventory.markDirty();
                return true;
            }
            inventory.setInventorySlotContents(p_145892_2_, itemstack1);
        }
        return false;
    }

    public static boolean func_145898_a(IInventory inventory, EntityItem entityItem) {
        boolean flag = false;

        if (entityItem == null) {
            return false;
        } else {
            ItemStack stack = entityItem.getEntityItem().copy();
            ItemStack stack1 = func_145889_a(inventory, stack, -1);

            if (stack1 != null && stack1.stackSize != 0) {
                entityItem.setEntityItemStack(stack1);
            } else {
                flag = true;
                entityItem.setDead();
            }
            return flag;
        }
    }

    public static ItemStack func_145889_a(IInventory inventory, ItemStack stack, int p_145889_2_) {
        if (inventory instanceof ISidedInventory isidedinventory && p_145889_2_ > -1) {
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(p_145889_2_);

            for (int l = 0; l < aint.length && stack != null && stack.stackSize > 0; ++l) {
                stack = func_145899_c(inventory, stack, aint[l], p_145889_2_);
            }
        } else {
            int j = inventory.getSizeInventory();

            for (int k = 0; k < j && stack != null && stack.stackSize > 0; ++k) {
                stack = func_145899_c(inventory, stack, k, p_145889_2_);
            }
        }
        if (stack != null && stack.stackSize == 0) {
            stack = null;
        }
        return stack;
    }

    private static boolean func_145885_a(IInventory inventory, ItemStack stack, int p_145885_2_, int p_145885_3_) {
        return inventory.isItemValidForSlot(p_145885_2_, stack) && (!(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(p_145885_2_, stack, p_145885_3_));
    }

    private static boolean func_145890_b(IInventory inventory, ItemStack stack, int p_145890_2_, int p_145890_3_) {
        return !(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canExtractItem(p_145890_2_, stack, p_145890_3_);
    }

    private static ItemStack func_145899_c(IInventory inventory, ItemStack stack, int p_145899_2_, int p_145899_3_) {
        ItemStack itemstack1 = inventory.getStackInSlot(p_145899_2_);

        if (func_145885_a(inventory, stack, p_145899_2_, p_145899_3_)) {
            boolean flag = false;

            if (itemstack1 == null) {
                int max = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max >= stack.stackSize) {
                    inventory.setInventorySlotContents(p_145899_2_, stack);
                    stack = null;
                } else {
                    inventory.setInventorySlotContents(p_145899_2_, stack.splitStack(max));
                }
                flag = true;
            } else if (func_145894_a(itemstack1, stack)) {
                int max = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max > itemstack1.stackSize) {
                    int l = Math.min(stack.stackSize, max - itemstack1.stackSize);
                    stack.stackSize -= l;
                    itemstack1.stackSize += l;
                    flag = l > 0;
                }
            }
            if (flag) {
                if (inventory instanceof TileEntityHopper) {
                    ((TileEntityHopper) inventory).func_145896_c(8);
                    inventory.markDirty();
                }

                inventory.markDirty();
            }
        }
        return stack;
    }

    private IInventory func_145895_l() {
        World world = this.getWorldObj();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        int meta = this.getBlockMetadata();
        int facing = BlockUpper.getDirectionFromMetadata(meta);

        if (meta == 0) {
            IInventory above = func_145893_b(world, x, y + 1, z);
            if (above != null) {
                return above;
            }
        }

        TileEntity aboveTile = world.getTileEntity(x, y + 1, z);
        if (aboveTile instanceof TileEntityUpper) {
            IInventory above = func_145893_b(world, x, y + 1, z);
            if (above != null) {
                return above;
            }
        }

        int dx = x + Facing.offsetsXForSide[facing];
        int dy = y + Facing.offsetsYForSide[facing];
        int dz = z + Facing.offsetsZForSide[facing];

        return func_145893_b(world, dx, dy, dz);
    }

    public static IInventory func_145884_b(IHopper hopper) {
        return func_145893_b(hopper.getWorldObj(), hopper.getXPos(), hopper.getYPos() - 1.0D, hopper.getZPos());
    }

    public static EntityItem func_145897_a(World world, double p_145897_1_, double p_145897_3_, double p_145897_5_) {
        List<EntityItem> list = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ - 1.0D, p_145897_3_ - 1.0D, p_145897_5_ - 1.0D), IEntitySelector.selectAnything);
        return !list.isEmpty() ? list.get(0) : null;
    }

    public static IInventory func_145893_b(World world, double p_145893_1_, double p_145893_3_, double p_145893_5_) {
        IInventory iinventory = null;
        int i = MathHelper.floor_double(p_145893_1_);
        int j = MathHelper.floor_double(p_145893_3_);
        int k = MathHelper.floor_double(p_145893_5_);
        TileEntity tileentity = world.getTileEntity(i, j, k);

        if (tileentity instanceof IInventory) {
            iinventory = (IInventory) tileentity;

            if (iinventory instanceof TileEntityChest) {
                Block block = world.getBlock(i, j, k);

                if (block instanceof BlockChest) {
                    iinventory = ((BlockChest) block).func_149951_m(world, i, j, k);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(p_145893_1_, p_145893_3_, p_145893_5_, p_145893_1_ - 1.0D, p_145893_3_ - 1.0D, p_145893_5_ - 1.0D), IEntitySelector.selectInventories);

            if (list != null && !list.isEmpty()) {
                iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
            }
        }
        return iinventory;
    }

    private static boolean func_145894_a(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() && (stack1.stackSize <= stack1.getMaxStackSize() && ItemStack.areItemStackTagsEqual(stack1, stack2)));
    }

    /**
     * Gets the world X position for this hopper entity.
     */
    @Override
    public double getXPos() {
        return xCoord;
    }

    /**
     * Gets the world Y position for this hopper entity.
     */
    @Override
    public double getYPos() {
        return yCoord;
    }

    /**
     * Gets the world Z position for this hopper entity.
     */
    @Override
    public double getZPos() {
        return zCoord;
    }

    @Override
    public void func_145896_c(int cooldownTicks) {
        transferCooldown = cooldownTicks;
    }

    @Override
    public boolean func_145888_j() {
        return transferCooldown > 0;
    }
}
