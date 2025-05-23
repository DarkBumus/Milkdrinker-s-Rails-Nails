package darkbum.mdrailsnails.inventory.gui;

import darkbum.mdrailsnails.entity.EntityHaulerMinecart;
import darkbum.mdrailsnails.inventory.container.ContainerHaulerMinecart;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiHaulerMinecart extends GuiContainer {

    private static final ResourceLocation resourceLocation = new ResourceLocation("mdrailsnails:textures/gui/container/hauler_minecart.png");

    private final IInventory inventory;
    private final EntityHaulerMinecart minecart;

    public GuiHaulerMinecart(IInventory inv, InventoryPlayer playerInv, EntityHaulerMinecart cart) {
        super(new ContainerHaulerMinecart(inv, playerInv, cart));
        inventory = inv;
        minecart = cart;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = inventory.hasCustomInventoryName() ? inventory.getInventoryName() : I18n.format(inventory.getInventoryName());
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.hauler_minecart"), 8, ySize - 96 + 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(resourceLocation);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        drawTexturedModalRect(x + 80, y + 27, 176, 0, 14, 14); //CONDITIONAL

    }
}
