package darkbum.mdrailsnails.common.proxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkbum.mdrailsnails.block.render.RenderConductor;
import darkbum.mdrailsnails.block.render.RenderHopper;
import darkbum.mdrailsnails.block.render.RenderUpper;
import darkbum.mdrailsnails.entity.*;
import darkbum.mdrailsnails.entity.model.*;
import darkbum.mdrailsnails.entity.render.*;
import net.minecraft.client.model.ModelZombie;

import static cpw.mods.fml.client.registry.RenderingRegistry.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationBlocks.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationEntities.*;
import static darkbum.mdrailsnails.common.config.ModConfigurationVanillaChanges.changeHopperRenderer;
import static darkbum.mdrailsnails.init.ModExternalLoader.efr;

/**
 * Client-side proxy class responsible for client-only initializations like custom renderers for blocks and entities.
 * This class extends behavior defined in {@link CommonProxy}.
 *
 * @author DarkBum
 * @since 1.0.0
 */
public class ClientProxy extends CommonProxy {

    public static int hopperRenderType;
    public static int conductorRenderType;
    public static int upperRenderType;

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
        if (changeHopperRenderer) {
            hopperRenderType = getNextAvailableRenderId();
            registerBlockHandler(new RenderHopper(hopperRenderType));
        }
        if (efr && enableConductor) {
            conductorRenderType = getNextAvailableRenderId();
            registerBlockHandler(new RenderConductor(conductorRenderType));
        }
        if (enableUpper) {
            upperRenderType = getNextAvailableRenderId();
            registerBlockHandler(new RenderUpper(upperRenderType));
        }
    }

    /**
     * Registers custom entity renderers.
     * This method must only be called on the client side.
     */
    public void setEntityRenderers() {
        if (enableModernEntities) {
            registerEntityRenderingHandler(EntityNewCow.class, new RenderNewCow(new ModelNewCow(), 0.7f));
            registerEntityRenderingHandler(EntityWarmCow.class, new RenderWarmCow(new ModelWarmCow(), 0.7f));
            registerEntityRenderingHandler(EntityColdCow.class, new RenderColdCow(new ModelColdCow(), 0.7f));
            registerEntityRenderingHandler(EntityNewPig.class, new RenderNewPig(new ModelNewPig(), new ModelNewPig(0.5f), 0.7f));
            registerEntityRenderingHandler(EntityWarmPig.class, new RenderWarmPig(new ModelNewPig(), new ModelNewPig(0.5f), 0.7f));
            registerEntityRenderingHandler(EntityColdPig.class, new RenderColdPig(new ModelColdPig(), new ModelColdPig(0.51f), 0.7f)); // (Yes, the offset of 0.51f is necessary to prevent Z-fighting, I don't know, nor do I care if that's the same in 1.21.5)
            registerEntityRenderingHandler(EntityNewChicken.class, new RenderNewChicken(new ModelNewChicken(), 0.3f));
            registerEntityRenderingHandler(EntityWarmChicken.class, new RenderWarmChicken(new ModelNewChicken(), 0.3f));
            registerEntityRenderingHandler(EntityColdChicken.class, new RenderColdChicken(new ModelColdChicken(), 0.3f));
            registerEntityRenderingHandler(EntityDrowned.class, new RenderDrowned(new ModelZombie(0.25f, false), new ModelZombie(), 0.5f));
        }
        if (enableEarthEntities) {
            registerEntityRenderingHandler(EntityBoulderingZombie.class, new RenderBoulderingZombie(new ModelBoulderingZombie(), 0.5f));
            registerEntityRenderingHandler(EntityLobberZombie.class, new RenderLobberZombie(new ModelLobberZombie(), 0.5f));
            registerEntityRenderingHandler(EntityCluckshroom.class, new RenderCluckshroom(new ModelCluckshroom(), 0.3f));
            registerEntityRenderingHandler(EntityFancyChicken.class, new RenderFancyChicken(new ModelFancyChicken(), 0.3f));
            registerEntityRenderingHandler(EntityMuddyPig.class, new RenderMuddyPig(new ModelMuddyPig(), new ModelMuddyPig(0.5f), 0.7f));
            registerEntityRenderingHandler(EntityNewIronGolem.class, new RenderNewIronGolem(new ModelNewIronGolem(), new ModelNewIronGolem(0.01f), 0.5f)); // (If you can find a better way to render the cracking overlay without Z-fighting, feel free to implement it)
            registerEntityRenderingHandler(EntityFurnaceGolem.class, new RenderFurnaceGolem(new ModelFurnaceGolem(), new ModelFurnaceGolem(0.01f), 0.5f)); // (I've not yet implemented the animated flames or the angry texture, you have to find a way to read hostility)
            registerEntityRenderingHandler(EntityVilerWitch.class, new RenderVilerWitch());
        }
    }
}
