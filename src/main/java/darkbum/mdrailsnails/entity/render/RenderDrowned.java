package darkbum.mdrailsnails.entity.render;

import darkbum.mdrailsnails.entity.EntityDrowned;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDrowned extends RenderBiped {

    private static final ResourceLocation zombieOuterTextures = new ResourceLocation("mdrailsnails:textures/entity/zombie/drowned_outer_layer.png");
    private static final ResourceLocation zombieTextures = new ResourceLocation("mdrailsnails:textures/entity/zombie/drowned.png");

    public RenderDrowned(ModelBiped modelBiped1, ModelBiped modelBiped2, float shadowSize) {
        super(modelBiped1, shadowSize);
        setRenderPassModel(modelBiped2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityDrowned) entity);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDrowned entity) {
        return zombieOuterTextures;
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int renderPass, float partialTicks) {
        return shouldRenderPass((EntityZombie) entityLivingBase, renderPass, partialTicks);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityZombie entity, int pass, float partialTicks) {
        if (pass != 0) return -1;
        bindTexture(zombieTextures);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glDepthMask(!entity.isInvisible());

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        float brightness = 1.0f;
        GL11.glColor4f(brightness, brightness, brightness, 1.0F);

        return 1;
    }
}
