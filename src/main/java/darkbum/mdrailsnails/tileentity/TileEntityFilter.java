package darkbum.mdrailsnails.tileentity;

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
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityFilter extends TileEntityHopper implements IHopper, ISidedInventory {

    private ItemStack[] hopperItemStacks = new ItemStack[10];
    private int transferCooldown = -1;

    public TileEntityFilter() {
        func_145886_a(I18n.format("container.filter"));
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList invTag = nbt.getTagList("Items", 10);
        hopperItemStacks = new ItemStack[10];
        transferCooldown = nbt.getInteger("TransferCooldown");
        for (int i = 0; i < invTag.tagCount(); i++) {
            NBTTagCompound stackTag = invTag.getCompoundTagAt(i);
            byte b0 = stackTag.getByte("Slot");
            if (b0 >= 0 && b0 < hopperItemStacks.length)
                hopperItemStacks[b0] = ItemStack.loadItemStackFromNBT(stackTag);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList invTag = new NBTTagList();
        for (int i = 0; i < hopperItemStacks.length; i++) {
            if (hopperItemStacks[i] != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte)i);
                hopperItemStacks[i].writeToNBT(stackTag);
                invTag.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", invTag);
        nbt.setInteger("TransferCooldown", transferCooldown);
    }

    public int getSizeInventory() {
        return 5;
    }

    public ItemStack getStackInSlot(int slot) {
        return hopperItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount) {
        if (hopperItemStacks[slot] != null) {
            if ((hopperItemStacks[slot]).stackSize <= amount) {
                ItemStack itemStack = hopperItemStacks[slot];
                hopperItemStacks[slot] = null;
                return itemStack;
            }
            ItemStack itemstack = hopperItemStacks[slot].splitStack(amount);
            if ((hopperItemStacks[slot]).stackSize == 0)
                hopperItemStacks[slot] = null;
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (hopperItemStacks[slot] != null) {
            ItemStack itemstack = hopperItemStacks[slot];
            hopperItemStacks[slot] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        hopperItemStacks[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && ((player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D));
    }

    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return true;
    }

    public void updateEntity() {
        if (worldObj != null && !worldObj.isRemote) {
            transferCooldown--;
            if (isCoolingDown()) {
                setTransferCooldown(0);
                updateHopper();
            }
        }
    }

    public void updateHopper() {
        if (worldObj != null && !worldObj.isRemote) {
            if (isCoolingDown() && BlockHopper.func_149917_c(getBlockMetadata())) {
                boolean didTransfer = insertItemToInventory();
                didTransfer = (tryPullItemsIntoHopper(this) || didTransfer);
                if (didTransfer) {
                    setTransferCooldown(8);
                    markDirty();
                }
            }
        }
    }

    private boolean insertItemToInventory() {
        IInventory inventory = getOutputInventory();
        if (inventory == null)
            return false;
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null && isValidFilterItem(getStackInSlot(i))) {
                ItemStack stack = getStackInSlot(i).copy();
                ItemStack stack1 = insertStack(inventory, decrStackSize(i, 1), Facing.oppositeSide[BlockHopper.getDirectionFromMetadata(getBlockMetadata())]);
                if (stack1 == null || stack1.stackSize == 0) {
                    inventory.markDirty();
                    return true;
                }
                setInventorySlotContents(i, stack);
            }
        }
        return false;
    }

    public static boolean tryPullItemsIntoHopper(TileEntityFilter tileEntity) {
        IInventory inventory = getInventoryAboveHopper(tileEntity);
        if (inventory != null) {
            byte side = 0;
            if (inventory instanceof ISidedInventory iSidedInventory) {
                int[] accessibleSlots = iSidedInventory.getAccessibleSlotsFromSide(side);
                for (int slotIndex : accessibleSlots) {
                    if (tryExtractItemFromSlot(tileEntity, inventory, slotIndex, side))
                        return true;
                }
            } else {
                int slotCount = inventory.getSizeInventory();
                for (int slotIndex = 0; slotIndex < slotCount; slotIndex++) {
                    if (tryExtractItemFromSlot(tileEntity, inventory, slotIndex, side))
                        return true;
                }
            }
        } else {
            EntityItem entityitem = findNearbyItemEntity(tileEntity.getWorldObj(), tileEntity.getXPos(), tileEntity.getYPos() + 1.0D, tileEntity.getZPos());
            if (entityitem != null)
                return tryAbsorbEntityItem(tileEntity, entityitem);
        }
        return false;
    }

    private static boolean tryExtractItemFromSlot(TileEntityFilter tileEntity, IInventory inventory, int slotIndex, int side) {
        ItemStack stack = inventory.getStackInSlot(slotIndex);
        if (stack != null && canExtractItemFromInventory(inventory, stack, slotIndex, side)) {
            ItemStack stack1 = stack.copy();
            ItemStack stack2 = insertStack(tileEntity, inventory.decrStackSize(slotIndex, 1), -1);
            if (stack2 == null || stack2.stackSize == 0) {
                inventory.markDirty();
                return true;
            }
            inventory.setInventorySlotContents(slotIndex, stack1);
        }
        return false;
    }

    public static boolean tryAbsorbEntityItem(IInventory inventory, EntityItem entityItem) {
        boolean flag = false;
        if (entityItem == null)
            return false;
        ItemStack stack = entityItem.getEntityItem().copy();
        ItemStack stack1 = insertStack(inventory, stack, -1);
        if (stack1 != null && stack1.stackSize != 0) {
            entityItem.setEntityItemStack(stack1);
        } else {
            flag = true;
            entityItem.setDead();
        }
        return flag;
    }

    public static ItemStack insertStack(IInventory inventory, ItemStack stack, int side) {
        if (inventory instanceof ISidedInventory isidedinventory && side > -1) {
            int[] accessibleSlots = isidedinventory.getAccessibleSlotsFromSide(side);
            for (int slotIndex = 0; slotIndex < accessibleSlots.length && stack != null && stack.stackSize > 0; slotIndex++)
                stack = stackToInsert(inventory, stack, accessibleSlots[slotIndex], side);
        } else {
            int inventorySize = inventory.getSizeInventory();
            for (int slotIndex = 0; slotIndex < inventorySize && stack != null && stack.stackSize > 0; slotIndex++)
                stack = stackToInsert(inventory, stack, slotIndex, side);
        }
        if (stack != null && stack.stackSize == 0)
            stack = null;
        return stack;
    }

    private static boolean canInsertItemToInventory(IInventory inventory, ItemStack stack, int slotIndex, int fromSide) {
        return inventory.isItemValidForSlot(slotIndex, stack) && ((!(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(slotIndex, stack, fromSide)));
    }

    private static boolean canExtractItemFromInventory(IInventory inventory, ItemStack stack, int slotIndex, int fromSide) {
        return (!(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canExtractItem(slotIndex, stack, fromSide));
    }

    private static ItemStack stackToInsert(IInventory inventory, ItemStack stack, int slotIndex, int fromSide) {
        ItemStack stack1 = inventory.getStackInSlot(slotIndex);
        if (canInsertItemToInventory(inventory, stack, slotIndex, fromSide)) {
            boolean didInsert = false;
            if (stack1 == null) {
                int max = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max >= stack.stackSize) {
                    inventory.setInventorySlotContents(slotIndex, stack);
                    stack = null;
                } else {
                    inventory.setInventorySlotContents(slotIndex, stack.splitStack(max));
                }
                didInsert = true;
            } else if (areItemStacksEqualItem(stack1, stack)) {
                int max = Math.min(stack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max > stack1.stackSize) {
                    int transferAmount = Math.min(stack.stackSize, max - stack1.stackSize);
                    stack.stackSize -= transferAmount;
                    stack1.stackSize += transferAmount;
                    didInsert = (transferAmount > 0);
                }
            }
            if (didInsert) {
                if (inventory instanceof TileEntityHopper) {
                    ((TileEntityHopper) inventory).func_145896_c(8);
                    inventory.markDirty();
                }
                inventory.markDirty();
            }
        }
        return stack;
    }

    private IInventory getOutputInventory() {
        int i = BlockHopper.getDirectionFromMetadata(getBlockMetadata());
        return getInventoryAtLocation(getWorldObj(), (xCoord + Facing.offsetsXForSide[i]), (yCoord + Facing.offsetsYForSide[i]), (zCoord + Facing.offsetsZForSide[i]));
    }

    public static IInventory getInventoryAboveHopper(TileEntityFilter tileEntity) {
        return getInventoryAtLocation(tileEntity.getWorldObj(), tileEntity.getXPos(), tileEntity.getYPos() + 1.0D, tileEntity.getZPos());
    }

    public static EntityItem findNearbyItemEntity(World world, double x, double y, double z) {
        List<EntityItem> list = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectAnything);
        return (!list.isEmpty()) ? list.get(0) : null;
    }

    public static IInventory getInventoryAtLocation(World world, double x, double y, double z) {
        IInventory inventory = null;
        int blockX = MathHelper.floor_double(x);
        int blockY = MathHelper.floor_double(y);
        int blockZ = MathHelper.floor_double(z);
        TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);
        if (tileEntity instanceof IInventory) {
            inventory = (IInventory) tileEntity;
            if (inventory instanceof net.minecraft.tileentity.TileEntityChest) {
                Block block = world.getBlock(blockX, blockY, blockZ);
                if (block instanceof BlockChest)
                    inventory = ((BlockChest)block).func_149951_m(world, blockX, blockY, blockZ);
            }
        }
        if (inventory == null) {
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), IEntitySelector.selectInventories);
            if (list != null && !list.isEmpty())
                inventory = (IInventory) list.get(world.rand.nextInt(list.size()));
        }
        return inventory;
    }

    private static boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() && (stack1.stackSize <= stack1.getMaxStackSize() && ItemStack.areItemStackTagsEqual(stack1, stack2)));
    }

    public double getXPos() {
        return xCoord;
    }

    public double getYPos() {
        return yCoord;
    }

    public double getZPos() {
        return zCoord;
    }

    public void setTransferCooldown(int cooldownTicks) {
        transferCooldown = cooldownTicks;
    }

    public boolean isCoolingDown() {
        return (transferCooldown <= 0);
    }

    private static final int[] slots_inventory = new int[] { 0, 1, 2, 3, 4 };

    public int[] getAccessibleSlotsFromSide(int var1) {
        return slots_inventory;
    }

    private boolean isValidFilterItem(ItemStack stack) {
        for (int slot = 5; slot < 10; slot++) {
            if (hopperItemStacks[slot] != null)
                if (hopperItemStacks[slot].getItem() == stack.getItem() && hopperItemStacks[slot].getItemDamage() == stack.getItemDamage())
                    return true;
        }
        return false;
    }

    public boolean canInsertItem(int slotIndex, ItemStack stack, int side) {
        if (side == -1 || side == 1) {
            return isValidFilterItem(stack);
        }
        return true;
    }

    public boolean canExtractItem(int slotIndex, ItemStack stack, int side) {
        return !isValidFilterItem(stack);
    }

    public void openInventory() {}

    public void closeInventory() {}
}
