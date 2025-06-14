package darkbum.mdrailsnails.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderNewPig extends RenderLiving {

    private static final ResourceLocation saddledPigTextures = new ResourceLocation("mdrailsnails:textures/entity/equipment/pig_saddle/saddle.png");
    private static final ResourceLocation pigTextures = new ResourceLocation("mdrailsnails:textures/entity/pig/temperate_pig.png");

    public RenderNewPig(ModelBase modelBase1, ModelBase modelBase2, float shadowSize) {
        super(modelBase1, shadowSize);
        setRenderPassModel(modelBase2);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityPig)entity);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPig entity) {
        return pigTextures;
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int renderPass, float partialTicks) {
        return shouldRenderPass((EntityPig)entityLivingBase, renderPass, partialTicks);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityPig entity, int renderPass, float partialTicks) {
        if (renderPass == 0 && entity.getSaddled()) {
            bindTexture(saddledPigTextures);
            return 1;
        } else {
            return -1;
        }
    }
}
