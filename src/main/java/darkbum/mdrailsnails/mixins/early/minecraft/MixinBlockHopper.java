package darkbum.mdrailsnails.mixins.early.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.common.proxy.ClientProxy;
import net.minecraft.block.BlockHopper;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static darkbum.mdrailsnails.block.render.RenderHopper.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.*;

@Mixin({BlockHopper.class})
public abstract class MixinBlockHopper {

    @Unique
    @SideOnly(Side.CLIENT)
    public IIcon field_149921_b; //iconOutside
    @Unique
    @SideOnly(Side.CLIENT)
    public IIcon field_149923_M; //iconTop
    @Unique
    @SideOnly(Side.CLIENT)
    public IIcon field_149924_N; //iconInside

    @ModifyReturnValue(
        method = "getIcon",
        at = @At("RETURN"),
        remap = false
    )
    public IIcon getIcon(IIcon original, int side) {
        if (changeHopperRenderer) {
            if (alternativeHopperFaces == 1) return side == 0 ? field_149924_N : field_149921_b;
            else if (alternativeHopperFaces == 0)
                return side == 0 ? field_149924_N : (side == 1 ? field_149923_M : field_149921_b);
        }
        return side == 1 ? field_149923_M : field_149921_b;
    }

    @ModifyReturnValue(
        method = "getRenderType",
        at = @At("RETURN"),
        remap = false
    )
    public int getRenderType(int original) {
        if (changeHopperRenderer) return ClientProxy.hopperRenderType;
        return 38;
    }
}
