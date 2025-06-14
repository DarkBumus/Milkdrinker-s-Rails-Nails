package darkbum.mdrailsnails.block.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import darkbum.mdrailsnails.common.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderHopper implements ISimpleBlockRenderingHandler {

    private final int modelId;
    public static int alternativeHopperFaces = 0;

    public RenderHopper(int modelId) {
        this.modelId = modelId;
    }

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        return renderBlockHopper(renderer, (BlockHopper) block, x, y, z, meta);
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return modelId;
    }

    private void applyColorFromBlock(Tessellator tessellator, Block block, IBlockAccess world, int x, int y, int z) {
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        int color = block.colorMultiplier(world, x, y, z);
        float red = (color >> 16 & 0xFF) / 255.0f;
        float green = (color >> 8 & 0xFF) / 255.0f;
        float blue = (color & 0xFF) / 255.0f;

        if (EntityRenderer.anaglyphEnable) {
            float redAdj = (red * 30.0f + green * 59.0f + blue * 11.0f) / 100.0f;
            float greenAdj = (red * 30.0f + green * 70.0f) / 100.0f;
            float blueAdj = (red * 30.0f + blue * 70.0f) / 100.0f;
            red = redAdj;
            green = greenAdj;
            blue = blueAdj;
        }
        tessellator.setColorOpaque_F(red, green, blue);
    }

    public boolean renderBlockHopper(RenderBlocks renderer, BlockHopper block, int x, int y, int z, int meta) {
        Tessellator tessellator = Tessellator.instance;
        int facing = BlockHopper.getDirectionFromMetadata(meta);

        // Largest Tube
        renderer.setRenderBounds(0.0D, 0.625D, 0.0D, 1.0D, 1.0D, 1.0D);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderStandardBlock(block, x, y, z);

        // Inside
        IIcon iconOutside = BlockHopper.getHopperIcon("hopper_outside");
        IIcon iconInside = BlockHopper.getHopperIcon("hopper_inside");
        float sideInset = 0.125F;
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderFaceXPos(block, (x - 1.0F + sideInset), y, z, iconOutside);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderFaceXNeg(block, (x + 1.0F - sideInset), y, z, iconOutside);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderFaceZPos(block, x, y, (z - 1.0F + sideInset), iconOutside);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderFaceZNeg(block, x, y, (z + 1.0F - sideInset), iconOutside);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderFaceYPos(block, x, (y - 1.0F) + 0.625D, z, iconInside);

        // Middle Pipe
        double innerInset = 0.25D;
        double innerHeight = 0.25D;
        renderer.setRenderBounds(innerInset, innerHeight, innerInset, 1.0D - innerInset, 0.625D - 0.002D, 1.0D - innerInset);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderStandardBlock(block, x, y, z);

        // Smallest Pipe
        double pipeInset = 0.375D;
        double pipeHeight = 0.25D;
        alternativeHopperFaces = 1;
        try {
            switch (facing) {
                case 0:
                    renderer.setRenderBounds(pipeInset, 0.0D, pipeInset, 1.0D - pipeInset, 0.25D, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 2:
                    renderer.setRenderBounds(pipeInset, innerHeight, 0.0D, 1.0D - pipeInset, innerHeight + pipeHeight, innerInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 3:
                    renderer.setRenderBounds(pipeInset, innerHeight, 1.0D - innerInset, 1.0D - pipeInset, innerHeight + pipeHeight, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 4:
                    renderer.setRenderBounds(0.0D, innerHeight, pipeInset, innerInset, innerHeight + pipeHeight, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 5:
                    renderer.setRenderBounds(1.0D - innerInset, innerHeight, pipeInset, 1.0D, innerHeight + pipeHeight, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
            }
        } finally {
            alternativeHopperFaces = 0;
        }
        return true;
    }
}
