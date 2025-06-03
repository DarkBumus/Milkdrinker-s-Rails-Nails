package darkbum.mdrailsnails.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static net.minecraft.block.Block.*;

public final class BlockUtils {

    /**
     * Sets the block metadata based on the direction the player is facing when placing the block.
     *
     * @param world  The world where the block is placed.
     * @param x      The x-coordinate of the block.
     * @param y      The y-coordinate of the block.
     * @param z      The z-coordinate of the block.
     * @param entity The entity placing the block, expected to be a player.
     */
    public static void setBlockDirectionFromEntity(World world, int x, int y, int z, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            int direction = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
            world.setBlockMetadataWithNotify(x, y, z, direction, 2);
        }
    }

    public static void setWyeDirectionFromEntity(World world, int x, int y, int z, EntityLivingBase entity) {
        if (world.isRemote) return;

        int meta;
        int direction = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        meta = switch (direction) {
            case 0 -> 9;
            case 1 -> 6;
            case 2 -> 7;
            case 3 -> 8;
            default -> 9; //Fallback
        };
        world.setBlockMetadataWithNotify(x, y, z, meta, 3);
        world.markBlockForUpdate(x, y, z);
    }

    public static void validateAndFixWyeMetadata(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 6 || meta > 9) {
            world.setBlockMetadataWithNotify(x, y, z, 7, 3);
            world.markBlockForUpdate(x, y, z);
        }
    }


    /**
     * Sets properties for a block, including hardness, resistance, step sound, and harvest level.
     *
     * @param block The block to set the properties for.
     */
    public static void propertiesTechnicalRails(Block block) {
        block.setHardness(0.7f);
        block.setResistance(0.7f);
        block.setStepSound(soundTypeMetal);
        block.setHarvestLevel("pickaxe", 0);
    }

    public static void propertiesWindlass(Block block) {
        block.setHardness(2.5f);
        block.setResistance(2.5f);
        block.setStepSound(soundTypeWood);
        block.setHarvestLevel("axe", 0);
    }

    public static void propertiesCopperRail(Block block) {
        block.setBlockUnbreakable();
        block.setStepSound(soundTypeMetal);
    }
}
