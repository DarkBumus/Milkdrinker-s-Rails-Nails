package darkbum.mdrailsnails.inventory.gui;

import darkbum.mdrailsnails.inventory.container.ContainerFilter;
import darkbum.mdrailsnails.tileentity.TileEntityFilter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFilter extends GuiContainer {

    ResourceLocation resourceLocation = new ResourceLocation("mdrailsnails:textures/gui/container/filter.png");

    public GuiFilter(InventoryPlayer inventoryPlayer, TileEntityFilter tileEntity) {
        super(new ContainerFilter(inventoryPlayer, tileEntity));
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(I18n.format("container.filter"), 8, 6, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
