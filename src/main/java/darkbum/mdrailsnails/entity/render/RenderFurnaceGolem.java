package darkbum.mdrailsnails.entity.render;

import darkbum.mdrailsnails.entity.EntityFurnaceGolem;
import darkbum.mdrailsnails.entity.model.ModelFurnaceGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFurnaceGolem extends RenderLiving {
    private static final ResourceLocation ironGolemTextures = new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem.png");
    private static final ResourceLocation hostileGolemTextures = new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_angry.png");
    private static final ResourceLocation lowCrackTextures = new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_crackiness_low.png");
    private static final ResourceLocation mediumCrackTextures = new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_crackiness_medium.png");
    private static final ResourceLocation highCrackTextures = new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_crackiness_high.png");

    private static final ResourceLocation[] FLAMES = new ResourceLocation[]{
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_0.png"),
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_1.png"),
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_2.png"),
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_3.png"),
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_4.png"),
        new ResourceLocation("mdrailsnails:textures/entity/furnace_golem/furnace_golem_flames_5.png")
    };

    /**
     * Furnace Golem's Model.
     */
    private final ModelFurnaceGolem ironGolemModel;

    public RenderFurnaceGolem(ModelBase modelBase1, ModelBase modelBase2, float shadowSize) {
        super(modelBase1, shadowSize);
        setRenderPassModel(modelBase2);
        ironGolemModel = (ModelFurnaceGolem) mainModel;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityIronGolem entity) {
        return ironGolemTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityIronGolem) entity);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float partialTicks) {
        return shouldRenderPass((EntityIronGolem) entity, pass, partialTicks);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityIronGolem entity, int pass, float partialTicks) {
        if (pass != 0) return -1;

        float health = entity.getHealth();

        if (health < 25.0f) {
            bindTexture(highCrackTextures);
            return 1;
        } else if (health < 50.0f) {
            bindTexture(mediumCrackTextures);
            return 1;
        } else if (health < 75.0f) {
            bindTexture(lowCrackTextures);
            return 1;
        }
        return -1;
    }

    protected void renderEquippedItems(EntityIronGolem entity, float p_77029_2_) {
        super.renderEquippedItems(entity, p_77029_2_);

        if (entity.getHoldRoseTick() != 0) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            GL11.glRotatef(5.0F + 180.0F * ironGolemModel.ironGolemRightArm.rotateAngleX / (float) Math.PI, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(-0.6875F, 1.25F, -0.9375F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            float f1 = 0.8F;
            GL11.glScalef(f1, -f1, f1);
            int i = entity.getBrightnessForRender(p_77029_2_);
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            bindTexture(TextureMap.locationBlocksTexture);
            field_147909_c.renderBlockAsItem(Blocks.red_flower, 0, 1.0F);
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }

    protected void renderEquippedItems(EntityLivingBase entity, float p_77029_2_) {
        renderEquippedItems((EntityIronGolem) entity, p_77029_2_);
    }

    protected void rotateCorpse(EntityIronGolem entity, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        super.rotateCorpse(entity, p_77043_2_, p_77043_3_, p_77043_4_);

        if ((double) entity.limbSwingAmount >= 0.01D) {
            float f3 = 13.0F;
            float f4 = entity.limbSwing - entity.limbSwingAmount * (1.0F - p_77043_4_) + 6.0F;
            float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
            GL11.glRotatef(6.5F * f5, 0.0F, 0.0F, 1.0F);
        }
    }

    protected void rotateCorpse(EntityLivingBase entity, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        rotateCorpse((EntityIronGolem) entity, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityIronGolem entity, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entity, x, y, z, yaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityIronGolem) entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityIronGolem) entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityIronGolem) entity, x, y, z, entityYaw, partialTicks);
    }
}
