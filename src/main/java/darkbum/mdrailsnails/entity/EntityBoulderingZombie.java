package darkbum.mdrailsnails.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import static darkbum.mdrailsnails.init.ModEntities.*;

public class EntityBoulderingZombie extends EntityZombie {

    public EntityBoulderingZombie(World world) {
        super(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickedResult(MovingObjectPosition target) {
        Integer id = getEntityEggId(this.getClass());
        return id != null ? new ItemStack(Items.spawn_egg, 1, id) : null;
    }
}
