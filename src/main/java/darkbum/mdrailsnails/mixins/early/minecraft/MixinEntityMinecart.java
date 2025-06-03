package darkbum.mdrailsnails.mixins.early.minecraft;

import darkbum.mdrailsnails.util.BoosterRailRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityMinecart.class)
public abstract class MixinEntityMinecart {

/*    @Redirect(
        method = "func_145821_a",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/init/Blocks;golden_rail:Lnet/minecraft/block/Block;"
        )
    )

    private Block redirectGoldenRail() {
        return Blocks.air;
    }

    @Redirect(
        method = "func_145821_a",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/Object;equals(Ljava/lang/Object;)Z",
            ordinal = 0
        )
    )

    private boolean redirectEquals(Object a, Object b) {
        if (b instanceof Block) {
            return BoosterRailRegistry.isBoosterRail((Block) b);
        }
        return a.equals(b);
    }*/
}
