package darkbum.mdrailsnails.item;

import darkbum.mdrailsnails.entity.EntityHaulerMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ItemHaulerMinecart extends Item {

    public ItemHaulerMinecart(String name, CreativeTabs tab) {
        setUnlocalizedName(name);
        setCreativeTab(tab);
        maxStackSize = 1;
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
            if (!world.isRemote) {
                EntityHaulerMinecart entity = new EntityHaulerMinecart(world, (x + 0.5F), (y + 0.5F), (z + 0.5F));
                if (stack.hasDisplayName())
                    entity.setMinecartName(stack.getDisplayName());
                world.spawnEntityInWorld(entity);
            }
            stack.stackSize--;
            return true;
        }
        return false;
    }

    private static final IBehaviorDispenseItem dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {

        private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
            World world = source.getWorld();

            double spawnX = source.getX() + (enumfacing.getFrontOffsetX() * 1.125F);
            double spawnY = source.getY() + (enumfacing.getFrontOffsetY() * 1.125F);
            double spawnZ = source.getZ() + (enumfacing.getFrontOffsetZ() * 1.125F);

            int blockX = source.getXInt() + enumfacing.getFrontOffsetX();
            int blockY = source.getYInt() + enumfacing.getFrontOffsetY();
            int blockZ = source.getZInt() + enumfacing.getFrontOffsetZ();

            Block block = world.getBlock(blockX, blockY, blockZ);
            double yOffset;

            if (BlockRailBase.func_150051_a(block)) {
                yOffset = 0.0D;
            } else {
                if (block.getMaterial() != Material.air || !BlockRailBase.func_150051_a(world.getBlock(blockX, blockY - 1, blockZ)))
                    return this.behaviourDefaultDispenseItem.dispense(source, stack);
                yOffset = -1.0D;
            }

            EntityHaulerMinecart entityModMinecart = new EntityHaulerMinecart(world, spawnX, spawnY + yOffset, spawnZ);
            if (stack.hasDisplayName()) entityModMinecart.setMinecartName(stack.getDisplayName());
            world.spawnEntityInWorld(entityModMinecart);
            stack.splitStack(1);
            return stack;
        }
    };
}
