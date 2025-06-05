package darkbum.mdrailsnails.block;

import darkbum.mdrailsnails.block.rails.IHighSpeedRail;
import net.minecraft.block.BlockRail;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.util.BlockUtils.*;
import static darkbum.mdrailsnails.util.RailUtils.*;

public class BlockRailHighSpeed extends BlockRail implements IHighSpeedRail {

    public BlockRailHighSpeed(String name, CreativeTabs tab) {
        super();
        setBlockName(name);
        setCreativeTab(tab);
        propertiesHighSpeedRails(this);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return getMinecartSpeedFromRules(world) * (highSpeedRailsSpeed * 1.0f);
    }

    public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
        updateHighSpeedState(world, cart);
    }
}
