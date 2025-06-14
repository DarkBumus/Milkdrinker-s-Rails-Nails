package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelLobberZombie extends ModelBiped {

    public ModelLobberZombie() {
        this(0.0F);
    }

    public ModelLobberZombie(float modelSize) {
        super(modelSize, 0.0F, 64, 64);
        // Head
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        bipedHead.setRotationPoint(0.0F, 0.0F + 0.0f, 0.0F);

        // Body
        bipedBody = new ModelRenderer(this, 16, 16);
        bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
        bipedBody.setRotationPoint(0.0F, 0.0F + 0.0f, 0.0F);

        // Arms - NEW
        bipedRightArm = new ModelRenderer(this, 16, 32);
        bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 11, 4, modelSize);
        bipedRightArm.setRotationPoint(-5.0F, 2.0F + 0.0f, 0.0F);

        bipedLeftArm = new ModelRenderer(this, 34, 32);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 5, 14, 4, modelSize);
        bipedLeftArm.setRotationPoint(5.0F, 2.0F + 0.0f, 0.0F);

        // Legs - NEW
        bipedRightLeg = new ModelRenderer(this, 0, 16);
        bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        bipedRightLeg.setRotationPoint(-1.9F, 12.0F + 0.0f, 0.0F);

        bipedLeftLeg = new ModelRenderer(this, 0, 32);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        bipedLeftLeg.setRotationPoint(1.9F, 12.0F + 0.0f, 0.0F);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, scale, entity);
        float attackSwing = MathHelper.sin(onGround * (float)Math.PI);
        float attackSwingDecay = MathHelper.sin((1.0F - (1.0F - onGround) * (1.0F - onGround)) * (float)Math.PI);
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightArm.rotateAngleY = -(0.1F - attackSwing * 0.6F);
        bipedLeftArm.rotateAngleY = 0.1F - attackSwing * 0.6F;
        bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
        bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F);
        bipedRightArm.rotateAngleX -= attackSwing * 1.2F - attackSwingDecay * 0.4F;
        bipedLeftArm.rotateAngleX -= attackSwing * 1.2F - attackSwingDecay * 0.4F;
        bipedRightArm.rotateAngleZ += MathHelper.cos(entityTickTime * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(entityTickTime * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(entityTickTime * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(entityTickTime * 0.067F) * 0.05F;
    }
}
