package darkbum.mdrailsnails.block.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import darkbum.mdrailsnails.block.BlockUpper;
import darkbum.mdrailsnails.common.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import static darkbum.mdrailsnails.block.BlockUpper.*;

public class RenderUpper implements ISimpleBlockRenderingHandler {

    private final int modelId;
    public static int alternativeUpperFaces = 0;

    public RenderUpper(int modelId) {
        this.modelId = modelId;
    }

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        return renderBlockUpper(renderer, (BlockUpper) block, x, y, z, meta);
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
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

    public boolean renderBlockUpper(RenderBlocks renderer, BlockUpper block, int x, int y, int z, int meta) {
        Tessellator tessellator = Tessellator.instance;
        int facing = BlockUpper.getDirectionFromMetadata(meta);

        // Largest Pipe
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderStandardBlock(block, x, y, z);

        // Inside
        float sideInset = 0.125f;
        tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
        renderer.renderFaceXPos(block, (x - 1.0f + sideInset), y, z, iconOutside);
        tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
        renderer.renderFaceXNeg(block, (x + 1.0f - sideInset), y, z, iconOutside);
        tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
        renderer.renderFaceZPos(block, x, y, (z - 1.0f + sideInset), iconOutside);
        tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
        renderer.renderFaceZNeg(block, x, y, (z + 1.0f - sideInset), iconOutside);
        tessellator.setColorOpaque_F(0.5f, 0.5f, 0.5f);
        renderer.renderFaceYNeg(block, x, y + 0.375D, z, iconInside);
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);

        // Middle Pipe
        double innerInset = 0.25D;
        double innerHeight = 0.375D;
        renderer.setRenderBounds(innerInset, innerHeight + 0.002D, innerInset, 1.0D - innerInset, 0.75D, 1.0D - innerInset);
        applyColorFromBlock(tessellator, block, renderer.blockAccess, x, y, z);
        renderer.renderStandardBlock(block, x, y, z);

        // Smallest Pipe
        double pipeInset = 0.375D;
        double pipeHeight = 0.25D;
        alternativeUpperFaces = 1;
        try {
            switch (facing) {
                case 0:
                    renderer.setRenderBounds(pipeInset, 0.75D, pipeInset, 1.0D - pipeInset, 1.0D, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 2:
                    renderer.setRenderBounds(pipeInset, 0.5D, 0.0D, 1.0D - pipeInset, 0.5D + pipeHeight, innerInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 3:
                    renderer.setRenderBounds(pipeInset, 0.5D, 1.0D - innerInset, 1.0D - pipeInset, 0.5D + pipeHeight, 1.0D);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 4:
                    renderer.setRenderBounds(0.0D, 0.5D, pipeInset, innerInset, 0.5D + pipeHeight, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
                case 5:
                    renderer.setRenderBounds(1.0D - innerInset, 0.5D, pipeInset, 1.0D, 0.5D + pipeHeight, 1.0D - pipeInset);
                    renderer.renderStandardBlock(block, x, y, z);
                    break;
            }
        } finally {
            alternativeUpperFaces = 0;
        }
        return true;
    }
}
