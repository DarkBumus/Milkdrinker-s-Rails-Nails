package darkbum.mdrailsnails.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import static darkbum.mdrailsnails.init.ModEntities.*;

public class EntityNewIronGolem extends EntityIronGolem {

    public EntityNewIronGolem(World world) {
        super(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickedResult(MovingObjectPosition target) {
        Integer id = getEntityEggId(this.getClass());
        return id != null ? new ItemStack(Items.spawn_egg, 1, id) : null;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack heldItem = player.getHeldItem();

        if (heldItem != null && heldItem.getItem() == Items.iron_ingot && this.getHealth() < this.getMaxHealth()) {
            if (!player.capabilities.isCreativeMode) {
                heldItem.stackSize--;
            }
            this.heal(25.0f);
            return true;
        }
        return super.interact(player);
    }
}
