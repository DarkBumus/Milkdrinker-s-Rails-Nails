package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelVilerWitch extends ModelVillager {

    public boolean isHoldingItem;
    public ModelRenderer witchBody;

    public ModelVilerWitch(float scale) {
        super(scale, 0.0f, 128, 128);

        villagerHead.setTextureOffset(32, 0).addBox(-4.5f, -10.0f, -4.5f, 9, 11, 9, 0.0f);

        witchBody = (new ModelRenderer(this)).setTextureSize(128, 128);
        witchBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        witchBody.setTextureOffset(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8, 12, 6, 0.0f);
        witchBody.setTextureOffset(0, 38).addBox(-5.0f, 0.0f, -3.5f, 10, 19, 7, scale + 0.0f);
        witchBody.setTextureOffset(34, 54).addBox(-5.5f, 9.0f, -4.0f, 11, 2, 8, 0.0f);

        villagerArms.setTextureOffset(106, 22).addBox(-8.09f, -3.0f, -3.0f, 5, 5, 6, 0.0f);
        villagerArms.setTextureOffset(106, 33).addBox(3.09f, -3.0f, -3.0f, 5, 5, 6, 0.0f);

        rightVillagerLeg.setTextureOffset(64, 22).addBox(-2.5f, 6.0f, -2.5f, 5, 3, 5, 0.0f);
        leftVillagerLeg.setTextureOffset(64, 30).addBox(-2.5f, 6.0f, -2.5f, 5, 3, 5, 0.0f);

        ModelRenderer noseWart = new ModelRenderer(this).setTextureSize(128, 128);
        noseWart.setRotationPoint(0.0f, -2.0f, 0.0f);
        noseWart.setTextureOffset(0, 0).addBox(0.0f, 3.0f, -6.75f, 1, 1, 1, -0.25f);
        villagerNose.addChild(noseWart);
        ModelRenderer hatBase = (new ModelRenderer(this)).setTextureSize(128, 128);
        hatBase.setRotationPoint(-5.0f, -10.03125f, -5.0f);
        hatBase.setTextureOffset(28, 64).addBox(-5.0f, 0.0f, -5.0f, 20, 2, 20);
        villagerHead.addChild(hatBase);
        ModelRenderer hatMid = (new ModelRenderer(this)).setTextureSize(128, 128);
        hatMid.setRotationPoint(1.75f, -4.0f, 2.0f);
        hatMid.setTextureOffset(0, 76).addBox(0.0f, 0.0f, 0.0f, 7, 4, 7);
        hatMid.rotateAngleX = -0.05235988f;
        hatMid.rotateAngleZ = 0.02617994f;
        hatBase.addChild(hatMid);
        ModelRenderer hatTop = (new ModelRenderer(this)).setTextureSize(128, 128);
        hatTop.setRotationPoint(1.75f, -4.0f, 2.0f);
        hatTop.setTextureOffset(0, 87).addBox(0.0f, 0.0f, 0.0f, 4, 4, 4);
        hatTop.rotateAngleX = -0.10471976f;
        hatTop.rotateAngleZ = 0.05235988f;
        hatMid.addChild(hatTop);
        ModelRenderer hatTip = (new ModelRenderer(this)).setTextureSize(128, 128);
        hatTip.setRotationPoint(1.75f, -2.0f, 2.0f);
        hatTip.setTextureOffset(0, 95).addBox(0.0f, 0.0f, 0.0f, 1, 2, 1, 0.25f);
        hatTip.rotateAngleX = -0.20943952f;
        hatTip.rotateAngleZ = 0.10471976f;
        hatTop.addChild(hatTip);
    }

    public void render(Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, scale, entity);
        villagerHead.render(scale);
        witchBody.render(scale);
        rightVillagerLeg.render(scale);
        leftVillagerLeg.render(scale);
        villagerArms.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float scale, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAngle, entityTickTime, rotationYaw, rotationPitch, scale, entity);
        villagerNose.offsetX = villagerNose.offsetY = villagerNose.offsetZ = 0.0f;
        float wiggleFactor = 0.01f * (float)(entity.getEntityId() % 10);
        villagerNose.rotateAngleX = MathHelper.sin((float)entity.ticksExisted * wiggleFactor) * 4.5f * (float)Math.PI / 180.0f;
        villagerNose.rotateAngleY = 0.0f;
        villagerNose.rotateAngleZ = MathHelper.cos((float)entity.ticksExisted * wiggleFactor) * 2.5f * (float)Math.PI / 180.0f;

        if (isHoldingItem) {
            villagerNose.rotateAngleX = -0.9f;
            villagerNose.offsetZ = -0.09375f;
            villagerNose.offsetY = 0.1875f;
        }
    }
}
