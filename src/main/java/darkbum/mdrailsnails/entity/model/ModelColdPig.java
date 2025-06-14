package darkbum.mdrailsnails.entity.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public class ModelColdPig extends ModelQuadruped {

    public ModelColdPig() {
        this(0.0F);
    }

    public ModelColdPig(float scale) {
        super(6, scale);
        textureWidth = 64;
        textureHeight = 64;

        // Head - Has to be recreated to accomodate for the new texture size
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, scale);
        head.setRotationPoint(0.0F, (float)(18 - 6), -6.0F);
        head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, scale);
        field_78145_g = 4.0F;

        // Body - Has to be recreated to accomodate for the new texture size
        body = new ModelRenderer(this, 28, 8);
        body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, scale);
        body.setRotationPoint(0.0F, (float)(17 - 6), 2.0F);

        // Fur - NEW
        body.setTextureOffset(28, 32).addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, 0.5f + scale);

        // Legs - Have to be recreated to accomodate for the new texture size
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        leg1.setRotationPoint(-3.0F, (float)(24 - 6), 7.0F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        leg2.setRotationPoint(3.0F, (float)(24 - 6), 7.0F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        leg3.setRotationPoint(-3.0F, (float)(24 - 6), -5.0F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        leg4.setRotationPoint(3.0F, (float)(24 - 6), -5.0F);
    }
}
