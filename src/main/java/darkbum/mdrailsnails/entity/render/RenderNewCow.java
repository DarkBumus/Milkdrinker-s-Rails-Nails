package darkbum.mdrailsnails.entity.render;

import darkbum.mdrailsnails.entity.EntityNewCow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class RenderNewCow extends RenderLiving {

    private static final ResourceLocation cowTextures = new ResourceLocation("mdrailsnails:textures/entity/cow/temperate_cow.png");

    public RenderNewCow(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCow entity) {
        return cowTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityNewCow) entity);
    }
}
