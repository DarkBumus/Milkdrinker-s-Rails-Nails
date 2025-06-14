package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public class ModelWarmCow extends ModelQuadruped {

    public ModelWarmCow() {
        super(12, 0.0F);
        textureWidth = 64;
        textureHeight = 64;

        // Head
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        head.setRotationPoint(0.0F, 4.0F, -8.0F);

        // Snout - NEW
        head.setTextureOffset(1, 33).addBox(-3.0f, 1.0f, -7.0f, 6, 3, 1, 0.0f);

        // Right Horn - NEW
        head.setTextureOffset(27, 0).addBox(-8.0f, -3.0f, -5.0f, 4, 2, 2, 0.0f);
        head.setTextureOffset(39, 0).addBox(-8.0f, -5.0f, -5.0f, 2, 2, 2, 0.0f);

        // Left Horn - NEW (Yes, this separation is necessary. Blame Mojang's shitty modeling engine)
        ModelRenderer leftHorn1 = new ModelRenderer(this, 27, 0);
        leftHorn1.mirror = true;
        leftHorn1.setTextureOffset(27, 0).addBox(4.0f, -3.0f, -5.0f, 4, 2, 2, 0.0f);
        head.addChild(leftHorn1);
        ModelRenderer leftHorn2 = new ModelRenderer(this, 39, 0);
        leftHorn2.mirror = true;
        leftHorn2.setTextureOffset(39, 0).addBox(6.0f, -5.0f, -5.0f, 2, 2, 2, 0.0f);
        head.addChild(leftHorn2);

        // Body
        body = new ModelRenderer(this, 18, 4);
        body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);

        // Udder
        body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);

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
