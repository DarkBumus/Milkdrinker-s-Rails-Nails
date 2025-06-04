package darkbum.mdrailsnails.common.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.block.render.ConductorRenderer;

/**
 * Client-side proxy class responsible for client-only initializations like custom renderers for blocks and entities.
 * This class extends behavior defined in {@link CommonProxy}.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ClientProxy extends CommonProxy {

    public static int conductorRenderType;

    /**
     * Helper method that overrides from CommonProxy and makes sure the renderer methods are only called client-side.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void setRenderers() {
        setBlockRenderers();
        setEntityRenderers();
    }

    /**
     * Registers custom block renderers using unique render IDs.
     * Called during client-side initialization.
     */
    public void setBlockRenderers() {
        conductorRenderType = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new ConductorRenderer(conductorRenderType));

    }

    /**
     * Registers custom entity renderers.
     * This method must only be called on the client side.
     */
    public void setEntityRenderers() {
    }
}
