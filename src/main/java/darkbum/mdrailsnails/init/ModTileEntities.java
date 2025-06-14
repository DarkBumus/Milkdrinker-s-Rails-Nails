package darkbum.mdrailsnails.init;

import darkbum.mdrailsnails.tileentity.TileEntityDistributor;
import darkbum.mdrailsnails.tileentity.TileEntityFilter;
import darkbum.mdrailsnails.tileentity.TileEntityUpper;

import static darkbum.mdrailsnails.util.ConditionalRegistrar.*;

/**
 * Tile Entity class.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ModTileEntities {

    /**
     * Initializes all tile entities.
     */
    public static void init() {
        registerTileEntity(TileEntityDistributor.class);
        registerTileEntity(TileEntityFilter.class);
        registerTileEntity(TileEntityUpper.class);
    }
}
