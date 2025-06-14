package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public class ModelColdCow extends ModelQuadruped {

    public ModelColdCow() {
        super(12, 0.0F);
        textureWidth = 64;
        textureHeight = 64;

        // Head
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        head.setRotationPoint(0.0F, 4.0F, -8.0F);

        // Snout - NEW
        head.setTextureOffset(9, 33).addBox(-3.0f, 1.0f, -7.0f, 6, 3, 1, 0.0f);

        // Right Horn - NEW
        ModelRenderer rightHorn = new ModelRenderer(this, 0, 40);
        rightHorn.setTextureOffset(0, 40).addBox(-6.0f, -8.0f, 2.0f, 2, 6, 2, 0.0f);
        rightHorn.rotateAngleX = (float) Math.toRadians(90);
        head.addChild(rightHorn);

        // Left Horn - NEW
        ModelRenderer leftHorn = new ModelRenderer(this, 0, 32);
        leftHorn.setTextureOffset(0, 32).addBox(4.0f, -8.0f, 2.0f, 2, 6, 2, 0.0f);
        leftHorn.rotateAngleX = (float) Math.toRadians(90);
        head.addChild(leftHorn);

        // Body
        body = new ModelRenderer(this, 18, 4);
        body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);

        //Udder
        body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);

        // Fur - NEW
        body.setTextureOffset(20, 32).addBox(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.5f);

        // Legs - Have to be recreated to accomodate for the new texture size
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
        --leg1.rotationPointX;
        ++leg2.rotationPointX;
        leg1.rotationPointZ += 0.0F;
        leg2.rotationPointZ += 0.0F;
        --leg3.rotationPointX;
        ++leg4.rotationPointX;
        --leg3.rotationPointZ;
        --leg4.rotationPointZ;
        field_78151_h += 2.0F;
    }
}

