package darkbum.mdrailsnails.mixins.early.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockRailBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;
import static darkbum.mdrailsnails.util.RailUtils.*;

@Mixin({BlockRailBase.class})
public abstract class MixinBlockRailBase {

    @ModifyReturnValue(
        method = "getRailMaxSpeed",
        at = @At("RETURN"),
        remap = false
    )
    public float getRailMaxSpeed(float original, @Local(argsOnly = true) World world) {
        if (changeRailMaxSpeed) return getMinecartSpeedFromRules(world);
        return 0.4f;
    }
}
