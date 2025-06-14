package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelNewIronGolem extends ModelBase {
    /** The head model for the iron golem. */
    public ModelRenderer ironGolemHead;
    /** The body model for the iron golem. */
    public ModelRenderer ironGolemBody;
    /** The right arm model for the iron golem. */
    public ModelRenderer ironGolemRightArm;
    /** The left arm model for the iron golem. */
    public ModelRenderer ironGolemLeftArm;
    /** The left leg model for the Iron Golem. */
    public ModelRenderer ironGolemLeftLeg;
    /** The right leg model for the Iron Golem. */
    public ModelRenderer ironGolemRightLeg;
    private static final String __OBFID = "CL_00000863";

    public ModelNewIronGolem() {
        this(0.0F);
    }

    public ModelNewIronGolem(float modelSize) {
        this(modelSize, -7.0F);
    }

    public ModelNewIronGolem(float modelSize, float yOffset) {
        short textureWidth = 128;
        short textureHeight = 128;
        ironGolemHead = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        ironGolemHead.setRotationPoint(0.0F, 0.0F + yOffset, -2.0F);
        ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, modelSize);
        ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, modelSize);
        ironGolemBody = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        ironGolemBody.setRotationPoint(0.0F, 0.0F + yOffset, 0.0F);
        ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, modelSize);
        ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, modelSize + 0.5F);
        ironGolemRightArm = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, modelSize);
        ironGolemLeftArm = (new ModelRenderer(this)).setTextureSize(textureWidth, textureHeight);
        ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, modelSize);
        ironGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
        ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + yOffset, 0.0F);
        ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, modelSize);
        ironGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidth, textureHeight);
        ironGolemRightLeg.mirror = true;
        ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + yOffset, 0.0F);
        ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, modelSize);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, scale, entity);
        ironGolemHead.render(scale);
        ironGolemBody.render(scale);
        ironGolemLeftLeg.render(scale);
        ironGolemRightLeg.render(scale);
        ironGolemRightArm.render(scale);
        ironGolemLeftArm.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale, Entity entity) {
        ironGolemHead.rotateAngleY = rotationYaw / (180F / (float)Math.PI);
        ironGolemHead.rotateAngleX = rotationPitch / (180F / (float)Math.PI);
        ironGolemLeftLeg.rotateAngleX = -1.5F * calculateSwing(limbSwing, 13.0F) * limbSwingAngle;
        ironGolemRightLeg.rotateAngleX = 1.5F * calculateSwing(limbSwing, 13.0F) * limbSwingAngle;
        ironGolemLeftLeg.rotateAngleY = 0.0F;
        ironGolemRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAngle, float entityTickTime) {
        EntityIronGolem entityIronGolem = (EntityIronGolem)entityLivingBase;
        int attackTimer = entityIronGolem.getAttackTimer();

        if (attackTimer > 0) {
            ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * calculateSwing((float) attackTimer - entityTickTime, 10.0F);
            ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * calculateSwing((float) attackTimer - entityTickTime, 10.0F);
        } else {
            int holdRoseTimer = entityIronGolem.getHoldRoseTick();

            if (holdRoseTimer > 0) {
                ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * calculateSwing((float) holdRoseTimer, 70.0F);
                ironGolemLeftArm.rotateAngleX = 0.0F;
            } else {
                ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * calculateSwing(limbSwing, 13.0F)) * limbSwingAngle;
                ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * calculateSwing(limbSwing, 13.0F)) * limbSwingAngle;
            }
        }
    }

    private float calculateSwing(float time, float swingPeriod) {
        return (Math.abs(time % swingPeriod - swingPeriod * 0.5F) - swingPeriod * 0.25F) / (swingPeriod * 0.25F);
    }
}
