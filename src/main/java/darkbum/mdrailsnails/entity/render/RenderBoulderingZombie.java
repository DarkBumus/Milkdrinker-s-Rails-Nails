package darkbum.mdrailsnails.entity.render;

import darkbum.mdrailsnails.entity.EntityBoulderingZombie;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoulderingZombie extends RenderBiped {

    private static final ResourceLocation zombieTextures = new ResourceLocation("mdrailsnails:textures/entity/zombie/bouldering_zombie.png");

    public RenderBoulderingZombie(ModelBiped modelBiped, float shadowSize) {
        super(modelBiped, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBoulderingZombie entity) {
        return zombieTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityBoulderingZombie)entity);
    }
}
