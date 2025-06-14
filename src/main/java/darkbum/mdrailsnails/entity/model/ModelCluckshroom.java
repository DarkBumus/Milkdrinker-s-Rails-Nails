package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelCluckshroom extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public ModelCluckshroom() {
        byte baseYOffset = 16;

        // Head
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
        head.setRotationPoint(0.0F, (float)(-1 + baseYOffset), -4.0F);

        // Head Mushroom - NEW
        ModelRenderer headMushroom1 = new ModelRenderer(this, 38, 0);
        headMushroom1.setTextureOffset(38, 0).addBox(-3.5f, -10.0f, 0.0f, 5, 5, 0, 0.0f);
        headMushroom1.rotateAngleY = (float) Math.toRadians(180);
        head.addChild(headMushroom1);

        ModelRenderer headMushroom2 = new ModelRenderer(this, 38, 0);
        headMushroom2.setTextureOffset(38, 0).addBox(-2.5f, -10.0f, 1.0f, 5, 5, 0, 0.0f);
        headMushroom2.rotateAngleY = (float) Math.toRadians(90);
        head.addChild(headMushroom2);

        // Bill
        bill = new ModelRenderer(this, 14, 0);
        bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
        bill.setRotationPoint(0.0F, (float)(-1 + baseYOffset), -4.0F);

        // Chin
        chin = new ModelRenderer(this, 14, 4);
        chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
        chin.setRotationPoint(0.0F, (float)(-1 + baseYOffset), -4.0F);

        // Body
        body = new ModelRenderer(this, 0, 9);
        body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        body.setRotationPoint(0.0F, baseYOffset, 0.0F);

        // Body Mushroom - NEW
        ModelRenderer bodyMushroom1 = new ModelRenderer(this, 38, 0);
        bodyMushroom1.setTextureOffset(38, 0).addBox(-3.5f, -8.0f, 1.5f, 5, 5, 0, 0.0f);
        bodyMushroom1.rotateAngleX = (float) Math.toRadians(-90);
        body.addChild(bodyMushroom1);

        ModelRenderer bodyMushroom2 = new ModelRenderer(this, 38, 0);
        bodyMushroom2.setTextureOffset(38, 0).addBox(-1.0f, -8.0f, 1.0f, 5, 5, 0, 0.0f);
        bodyMushroom2.rotateAngleX = (float) Math.toRadians(-90);
        bodyMushroom2.rotateAngleZ = (float) Math.toRadians(90);
        body.addChild(bodyMushroom2);

        // Legs
        rightLeg = new ModelRenderer(this, 26, 0);
        rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        rightLeg.setRotationPoint(-2.0F, (float)(3 + baseYOffset), 1.0F);
        leftLeg = new ModelRenderer(this, 26, 0);
        leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
        leftLeg.setRotationPoint(1.0F, (float)(3 + baseYOffset), 1.0F);

        // Wings
        rightWing = new ModelRenderer(this, 24, 13);
        rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
        rightWing.setRotationPoint(-4.0F, (float)(-3 + baseYOffset), 0.0F);
        leftWing = new ModelRenderer(this, 24, 13);
        leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
        leftWing.setRotationPoint(4.0F, (float)(-3 + baseYOffset), 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, scale, entity);

        if (isChild) {
            float childScale = 2.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 5.0F * scale, 2.0F * scale);
            head.render(scale);
            bill.render(scale);
            chin.render(scale);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / childScale, 1.0F / childScale, 1.0F / childScale);
            GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F);
            body.render(scale);
            rightLeg.render(scale);
            leftLeg.render(scale);
            rightWing.render(scale);
            leftWing.render(scale);
            GL11.glPopMatrix();
        } else {
            head.render(scale);
            bill.render(scale);
            chin.render(scale);
            body.render(scale);
            rightLeg.render(scale);
            leftLeg.render(scale);
            rightWing.render(scale);
            leftWing.render(scale);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale, Entity entity) {
        head.rotateAngleX = rotationPitch / (180F / (float)Math.PI);
        head.rotateAngleY = rotationYaw / (180F / (float)Math.PI);
        bill.rotateAngleX = head.rotateAngleX;
        bill.rotateAngleY = head.rotateAngleY;
        chin.rotateAngleX = head.rotateAngleX;
        chin.rotateAngleY = head.rotateAngleY;
        body.rotateAngleX = ((float)Math.PI / 2F);
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAngle;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAngle;
        rightWing.rotateAngleZ = entityTickTime;
        leftWing.rotateAngleZ = -entityTickTime;
    }
}
