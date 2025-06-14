package darkbum.mdrailsnails.entity.render;

import darkbum.mdrailsnails.entity.model.ModelVilerWitch;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class RenderVilerWitch extends RenderLiving {
    private static final ResourceLocation witchTextures = new ResourceLocation("mdrailsnails:textures/entity/witch/viler_witch.png");
    private final ModelVilerWitch witchModel;

    public RenderVilerWitch() {
        super(new ModelVilerWitch(0.0F), 0.5F);
        witchModel = (ModelVilerWitch)mainModel;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWitch entity) {
        return witchTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return getEntityTexture((EntityWitch)entity);
    }

    protected void func_82410_b() {
        GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityWitch entity, float p_77041_2_) {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entity, float p_77041_2_) {
        preRenderCallback((EntityWitch)entity, p_77041_2_);
    }

    protected void renderEquippedItems(EntityWitch entity, float p_77029_2_) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(entity, p_77029_2_);
        ItemStack itemstack = entity.getHeldItem();

        if (itemstack != null) {
            GL11.glPushMatrix();
            float f1;

            if (mainModel.isChild) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(f1, f1, f1);
            }

            witchModel.villagerNose.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);

            if (itemstack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())) {
                f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f1 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
            } else if (itemstack.getItem() == Items.bow) {
                f1 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (Objects.requireNonNull(itemstack.getItem()).isFull3D()) {
                f1 = 0.625F;

                if (itemstack.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                func_82410_b();
                GL11.glScalef(f1, -f1, f1);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                f1 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f1, f1, f1);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
            renderManager.itemRenderer.renderItem(entity, itemstack, 0);

            if (Objects.requireNonNull(itemstack.getItem()).requiresMultipleRenderPasses()) {
                renderManager.itemRenderer.renderItem(entity, itemstack, 1);
            }
            GL11.glPopMatrix();
        }
    }

    protected void renderEquippedItems(EntityLivingBase entity, float p_77029_2_) {
        renderEquippedItems((EntityWitch)entity, p_77029_2_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityWitch entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ItemStack itemStack = entity.getHeldItem();
        witchModel.isHoldingItem = itemStack != null;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityWitch)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityWitch)entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        doRender((EntityWitch)entity, x, y, z, entityYaw, partialTicks);
    }
}
