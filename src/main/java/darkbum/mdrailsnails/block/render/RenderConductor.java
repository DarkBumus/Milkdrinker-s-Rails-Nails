package darkbum.mdrailsnails.block.render;

import darkbum.mdrailsnails.util.BlockModelBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import static net.minecraft.block.BlockPistonBase.*;

public class RenderConductor extends BlockModelBase {

    public RenderConductor(int modelID) {
        super(modelID);
    }

    protected void renderStandardInventoryCube(Block block, int meta, int modelID, RenderBlocks renderer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        setRotation(5, renderer);
        super.renderStandardInventoryCube(block, 5, modelID, renderer, minX, minY, minZ, maxX, maxY, maxZ);
        resetRotation(renderer);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        setRotation(meta, renderer);
        boolean flag = renderer.renderStandardBlock(block, x, y, z);
        resetRotation(renderer);
        return flag;
    }

    private void setRotation(int meta, RenderBlocks renderer) {
        int orientation = getPistonOrientation(meta);
        switch (orientation) {
            case 0:
                renderer.uvRotateNorth = 1;
                renderer.uvRotateSouth = 2;
                break;
            case 1:
                renderer.uvRotateNorth = 1;
                renderer.uvRotateSouth = 2;
                renderer.uvRotateWest = 3;
                renderer.uvRotateEast = 3;
                break;
            case 2:
                renderer.uvRotateTop = 3;
                renderer.uvRotateBottom = 3;
                break;
            case 4:
                renderer.uvRotateTop = 1;
                renderer.uvRotateBottom = 2;
                break;
            case 5:
                renderer.uvRotateTop = 2;
                renderer.uvRotateBottom = 1;
                break;
            default:
                break;
        }
    }

    private void resetRotation(RenderBlocks renderer) {
        renderer.flipTexture = false;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
    }
}

