package darkbum.mdrailsnails.mixins.early.minecraft;

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static darkbum.mdrailsnails.util.RailUtils.*;

@Mixin({BlockRailBase.class})
public abstract class MixinBlockRailBase {

    /**
     * @author Jack
     * @reason Cart Speed adjust was necessary. Thanks for the help, Jack <3
     */
    @Overwrite(remap = false)
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return getMinecartSpeedFromRules(world);
    }
}
