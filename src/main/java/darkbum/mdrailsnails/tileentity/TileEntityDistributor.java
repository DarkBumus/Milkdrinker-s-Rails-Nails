package darkbum.mdrailsnails.tileentity;

import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntityHopper;

public class TileEntityDistributor extends TileEntityHopper {
    private int delay = 0;

    public TileEntityDistributor() {
        func_145886_a(I18n.format("container.distributor"));
    }

    public void updateEntity() {
        if (worldObj != null && !worldObj.isRemote) {
            delay++;
            if (delay >= 0) {
                blockMetadata = getBlockMetadata();
                switch (blockMetadata) {
                    case 2:
                        blockMetadata = 4;
                        break;
                    case 3:
                        blockMetadata = 5;
                        break;
                    case 4:
                        blockMetadata = 3;
                        break;
                    default:
                        blockMetadata = 2;
                        break;
                }
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blockMetadata, 3);
                delay = -4;
                func_145896_c(0);
                func_145887_i();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }
}
