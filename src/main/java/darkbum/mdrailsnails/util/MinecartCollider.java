package darkbum.mdrailsnails.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.IMinecartCollisionHandler;

public class MinecartCollider implements IMinecartCollisionHandler {

    @Override
    public void onEntityCollision(EntityMinecart minecart, Entity otherEntity) {

        if (minecart.worldObj.isRemote) return;

        if (otherEntity == minecart.riddenByEntity) return;

        if (otherEntity instanceof net.minecraft.entity.EntityLivingBase
            && !(otherEntity instanceof net.minecraft.entity.player.EntityPlayer)
            && !(otherEntity instanceof net.minecraft.entity.monster.EntityIronGolem)
            && minecart.canBeRidden()
            && (minecart.motionX * minecart.motionX + minecart.motionZ * minecart.motionZ) > 0.01D
            && minecart.riddenByEntity == null
            && otherEntity.ridingEntity == null) {
            otherEntity.mountEntity(minecart);
        }

        double deltaX = otherEntity.posX - minecart.posX;
        double deltaZ = otherEntity.posZ - minecart.posZ;
        double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;

        if (distanceSquared < 0.0001D) return;

        double distance = MathHelper.sqrt_double(distanceSquared);

        deltaX /= distance;
        deltaZ /= distance;
        double distanceInverse = 1.0D / distance;
        double distanceInverseClamped = Math.min(distanceInverse, 1.0D);

        deltaX *= distanceInverseClamped * 0.1D * (1.0F - minecart.entityCollisionReduction) * 0.5D;
        deltaZ *= distanceInverseClamped * 0.1D * (1.0F - minecart.entityCollisionReduction) * 0.5D;

        if (otherEntity instanceof EntityMinecart otherCart) {

            double combinedMotionX = otherCart.motionX + minecart.motionX;
            double combinedMotionZ = otherCart.motionZ + minecart.motionZ;

            if (otherCart.isPoweredCart() && !minecart.isPoweredCart()) {
                minecart.motionX *= 0.2D;
                minecart.motionZ *= 0.2D;
                minecart.addVelocity(otherCart.motionX - deltaX, 0.0D, otherCart.motionZ - deltaZ);
                otherCart.motionX *= 0.95D;
                otherCart.motionZ *= 0.95D;

            } else if (minecart.isPoweredCart() && !otherCart.isPoweredCart()) {
                otherCart.motionX *= 0.2D;
                otherCart.motionZ *= 0.2D;
                otherCart.addVelocity(minecart.motionX + deltaX, 0.0D, minecart.motionZ + deltaZ);
                minecart.motionX *= 0.95D;
                minecart.motionZ *= 0.95D;

            } else {
                combinedMotionX /= 2.0D;
                combinedMotionZ /= 2.0D;

                minecart.motionX *= 0.2D;
                minecart.motionZ *= 0.2D;
                minecart.addVelocity(combinedMotionX - deltaX, 0.0D, combinedMotionZ - deltaZ);

                otherCart.motionX *= 0.2D;
                otherCart.motionZ *= 0.2D;
                otherCart.addVelocity(combinedMotionX + deltaX, 0.0D, combinedMotionZ + deltaZ);
            }
        } else {
            minecart.addVelocity(-deltaX, 0.0D, -deltaZ);
            otherEntity.addVelocity(deltaX / 4.0D, 0.0D, deltaZ / 4.0D);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBox(EntityMinecart minecart, Entity other) {
        return other.canBePushed() ? other.boundingBox : null;
    }

    @Override
    public AxisAlignedBB getMinecartCollisionBox(EntityMinecart minecart) {
        return minecart.boundingBox.expand(0.2D, 0.0D, 0.2D);
    }

    @Override
    public AxisAlignedBB getBoundingBox(EntityMinecart minecart) {
        return null;
    }
}
