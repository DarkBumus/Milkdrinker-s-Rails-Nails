package darkbum.mdrailsnails.block;

import net.minecraft.block.BlockRailPowered;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;

public class BlockRailSlowdown extends BlockRailPowered {

    public BlockRailSlowdown(String name, CreativeTabs tab) {
        super();
        setBlockName(name);
        setCreativeTab(tab);
        propertiesTechnicalRails(this);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return getMinecartSpeedFromRules(world) * 0.3f;
    }
}
