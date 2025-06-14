package darkbum.mdrailsnails.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderColdChicken extends RenderLiving {

    private static final ResourceLocation chickenTextures = new ResourceLocation("mdrailsnails:textures/entity/chicken/cold_chicken.png");

    public RenderColdChicken(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityChicken)entity);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityChicken entity) {
        return chickenTextures;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLivingBase entityLivingBase, float partialTicks) {
        return handleRotationFloat((EntityChicken)entityLivingBase, partialTicks);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityChicken entityLivingBase, float partialTicks) {
        float cycle = entityLivingBase.field_70888_h + (entityLivingBase.field_70886_e - entityLivingBase.field_70888_h) * partialTicks;
        float amplitude = entityLivingBase.field_70884_g + (entityLivingBase.destPos - entityLivingBase.field_70884_g) * partialTicks;
        return (MathHelper.sin(cycle) + 1.0F) * amplitude;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityChicken)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityChicken)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityChicken)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityChicken entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
