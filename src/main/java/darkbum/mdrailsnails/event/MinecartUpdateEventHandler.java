package darkbum.mdrailsnails.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkbum.mdrailsnails.block.rails.IHighSpeedRail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraft.util.DamageSource;

public class MinecartUpdateEventHandler {

    public static final DamageSource HIGH_SPEED_EXPLOSION_DAMAGE = new DamageSource("high_speed_explosion_damage").setExplosion();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onMinecartUpdate(MinecartUpdateEvent event) {
        EntityMinecart cart = event.minecart;
        World world = cart.worldObj;

        String speedState = cart.getEntityData().getString("SpeedState");

        int x = MathHelper.floor_double(cart.posX);
        int y = MathHelper.floor_double(cart.posY);
        int z = MathHelper.floor_double(cart.posZ);
        Block rail = world.getBlock(x, y, z);
        int railMeta = world.getBlockMetadata(x, y, z);

        boolean isHighSpeedRail = rail instanceof IHighSpeedRail;
        boolean isCurveOrSlope = isRailMetaDangerous(rail, railMeta);

        if ("HIGHSPEED".equals(speedState)) {
            if (!isHighSpeedRail) {
                handleCartExplosion(world, cart);
            }
        } else if ("WARNING".equals(speedState)) {
            if (!isHighSpeedRail) {
                handleSpeedWarning(world, cart);
            }
        } else if ("NORMAL".equals(speedState)) {
            cart.getEntityData().removeTag("SpeedState");
        }
    }

    private static boolean isRailMetaDangerous(Block rail, int railMeta) {
        if ((railMeta >= 2 && railMeta <= 5) || (railMeta >= 10 && railMeta <= 13)) {
            return rail instanceof BlockRailBase;
        }

        if (railMeta >= 6 && railMeta <= 9) {
            return rail instanceof BlockRail;
        }
        return false;
    }

    private static void handleCartExplosion(World world, EntityMinecart cart) {
        double speed = Math.sqrt(cart.motionX * cart.motionX + cart.motionY * cart.motionY + cart.motionZ * cart.motionZ);
        float explosionStrength = (float) Math.min(4.0, 1.0 + speed * 5.0);

        if (cart.riddenByEntity != null) {
            cart.riddenByEntity.attackEntityFrom(HIGH_SPEED_EXPLOSION_DAMAGE, explosionStrength * 5.0F);
            cart.riddenByEntity.setFire(5);
        }

        world.createExplosion(cart, cart.posX, cart.posY, cart.posZ, explosionStrength, true);
        cart.setDead();
    }

    private static void handleSpeedWarning(World world, EntityMinecart cart) {
        if (world.isRemote) return;

        NBTTagCompound data = cart.getEntityData();

        int ticksLeft = data.getInteger("WarningTicksLeft");
        int cooldown = data.getInteger("WarningCooldown");

        if (ticksLeft <= 0) {
            data.removeTag("SpeedState");
            data.removeTag("WarningTicksLeft");
            data.removeTag("WarningCooldown");
            return;
        }

        if (cooldown <= 0) {
            world.playSoundEffect(cart.posX, cart.posY, cart.posZ, "random.fizz", 1.0F, 1.0F);
            data.setInteger("WarningCooldown", 4);
        } else {
            data.setInteger("WarningCooldown", cooldown - 1);
        }

        int x = MathHelper.floor_double(cart.posX);
        int y = MathHelper.floor_double(cart.posY);
        int z = MathHelper.floor_double(cart.posZ);

        if (Math.abs(cart.motionX) > Math.abs(cart.motionZ)) {
            trySpawnFire(world, x, y, z + 1);
            trySpawnFire(world, x, y, z - 1);
        } else {
            trySpawnFire(world, x + 1, y, z);
            trySpawnFire(world, x - 1, y, z);
        }
        if (cart.riddenByEntity != null) {
            cart.riddenByEntity.setFire(5);
        }

        data.setInteger("WarningTicksLeft", ticksLeft - 1);
    }

    private static void trySpawnFire(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block.isAir(world, x, y, z)) {
            world.setBlock(x, y, z, Blocks.fire);
        }
    }
}
