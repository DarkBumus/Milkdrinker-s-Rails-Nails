package darkbum.mdrailsnails.inventory.gui;

import darkbum.mdrailsnails.entity.EntityMinecartHauler;
import darkbum.mdrailsnails.inventory.container.ContainerHaulerMinecart;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiHaulerMinecart extends GuiContainer {

    private static final ResourceLocation resourceLocation = new ResourceLocation("mdrailsnails:textures/gui/container/hauler_minecart.png");

    private final EntityMinecartHauler minecart;

    public GuiHaulerMinecart(IInventory inv, InventoryPlayer playerInv, EntityMinecartHauler cart) {
        super(new ContainerHaulerMinecart(inv, playerInv, cart));
        minecart = cart;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String string = I18n.format("container.hauler_minecart");
        fontRendererObj.drawString(string, xSize / 2 - fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(resourceLocation);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        if (minecart != null && minecart.isMinecartPowered() && minecart.getFuelLevel() > 0) {
            drawTexturedModalRect(x + 80, y + 27, 176, 0, 14, 14);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        if (mouseX >= x + 79 && mouseY >= y + 26 && mouseX < x + 79 + 16 && mouseY < y + 26 + 16) {
            ArrayList<String> toolTip = new ArrayList<>();
            int fuel = minecart.getFuelLevel();
            if (fuel > 0) {
                toolTip.add(fuel / 20 + " " + I18n.format("container.hauler_minecart.fueltime"));
            }
            drawText(toolTip, mouseX, mouseY, fontRendererObj);
        }
    }

    protected void drawText(List<String> list, int mouseX, int mouseY, FontRenderer font) {
        if (!list.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int k = 0;
            for (String aList : list) {
                int l = font.getStringWidth(aList);
                if (l > k) k = l;
            }
            int i1 = mouseX + 12;
            int j1 = mouseY - 12;
            int k1 = 8;
            if (list.size() > 1) k1 += 2 + (list.size() - 1) * 10;
            if (i1 + k > width) i1 -= 28 + k;
            if (j1 + k1 + 6 > height) j1 = height - k1 - 6;
            zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int l1 = -267386864;
            drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < list.size(); k2++) {
                String s1 = list.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);
                if (k2 == 0) j1 += 2;
                j1 += 10;
            }
            zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }
}
