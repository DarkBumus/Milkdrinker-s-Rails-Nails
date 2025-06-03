package darkbum.mdrailsnails.block.rails;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockModRailBase extends BlockRailBase {

    protected final boolean isPoweredRail;

/*    public static final boolean func_150049_b_(World p_150049_0_, int p_150049_1_, int p_150049_2_, int p_150049_3_) {
        return func_150051_a(p_150049_0_.getBlock(p_150049_1_, p_150049_2_, p_150049_3_));
    }

    public static final boolean func_150051_a(Block p_150051_0_) {
        return p_150051_0_ instanceof BlockRailBase;
    }*/

    public BlockModRailBase(boolean powered) {
        super(powered);
        isPoweredRail = powered;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        setCreativeTab(CreativeTabs.tabTransport);
    }

    /**
     * Returns true if the block is power related rail.
     */
    public boolean isPowered() {
        return isPoweredRail;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World worldIn, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        setBlockBoundsBasedOnState(worldIn, x, y, z);
        return super.collisionRayTrace(worldIn, x, y, z, startVec, endVec);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        int l = worldIn.getBlockMetadata(x, y, z);

        if (l >= 2 && l <= 5) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        } else {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType() {
        return renderType;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random) {
        return 1;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return World.doesBlockHaveSolidTopSurface(worldIn, x, y - 1, z);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World worldIn, int x, int y, int z) {
        if (!worldIn.isRemote) {
            setRailDirection(worldIn, x, y, z, true);

            if (isPoweredRail) {
                onNeighborBlockChange(worldIn, x, y, z, this);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (!world.isRemote) {
            int metadata = world.getBlockMetadata(x, y, z);
            int railShape = metadata;

            if (isPoweredRail) {
                railShape = metadata & 7;
            }

            boolean shouldDrop = !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);

            if (railShape == 2 && !World.doesBlockHaveSolidTopSurface(world, x + 1, y, z)) {
                shouldDrop = true;
            }

            if (railShape == 3 && !World.doesBlockHaveSolidTopSurface(world, x - 1, y, z)) {
                shouldDrop = true;
            }

            if (railShape == 4 && !World.doesBlockHaveSolidTopSurface(world, x, y, z - 1)) {
                shouldDrop = true;
            }

            if (railShape == 5 && !World.doesBlockHaveSolidTopSurface(world, x, y, z + 1)) {
                shouldDrop = true;
            }

            if (shouldDrop) {
                dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            } else {
                updateRailState(world, x, y, z, metadata, railShape, neighborBlock);
            }
        }
    }

    public void updateRailState(World world, int x, int y, int z, int currentMeta, int railShape, Block neighborBlock) {}

    public void setRailDirection(World world, int x, int y, int z, boolean fromNeighborUpdate) {
        if (!world.isRemote) {
            (new BlockRailBase.Rail(world, x, y, z)).func_150655_a(world.isBlockIndirectlyGettingPowered(x, y, z), fromNeighborUpdate);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 0;
    }

    public void breakBlock(World world, int x, int y, int z, Block blockBroken, int meta) {
        int railShape = meta;

        if (isPoweredRail) {
            railShape = meta & 7;
        }

        super.breakBlock(world, x, y, z, blockBroken, meta);

        if (railShape == 2 || railShape == 3 || railShape == 4 || railShape == 5) {
            world.notifyBlocksOfNeighborChange(x, y + 1, z, blockBroken);
        }

        if (isPoweredRail) {
            world.notifyBlocksOfNeighborChange(x, y, z, blockBroken);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, blockBroken);
        }
    }

    /* ======================================== FORGE START =====================================*/
    /**
     * Return true if the rail can make corners.
     * Used by placement logic.
     * @param world The world.
     * @param x The rail X coordinate.
     * @param y The rail Y coordinate.
     * @param z The rail Z coordinate.
     * @return True if the rail can make corners.
     */
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z) {
        return !isPowered();
    }

    /**
     * Returns true if the rail can make up and down slopes.
     * Used by placement logic.
     * @param world The world.
     * @param x The rail X coordinate.
     * @param y The rail Y coordinate.
     * @param z The rail Z coordinate.
     * @return True if the rail can make slopes.
     */
    public boolean canMakeSlopes(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    /**
     * Return the rail's metadata (without the power bit if the rail uses one).
     * Can be used to make the cart think the rail something other than it is,
     * for example when making diamond junctions or switches.
     * The cart parameter will often be null unless it it called from EntityMinecart.
     * <p>
     * Valid rail metadata is defined as follows:
     * 0x0: flat track going North-South
     * 0x1: flat track going West-East
     * 0x2: track ascending to the East
     * 0x3: track ascending to the West
     * 0x4: track ascending to the North
     * 0x5: track ascending to the South
     * 0x6: WestNorth corner (connecting East and South)
     * 0x7: EastNorth corner (connecting West and South)
     * 0x8: EastSouth corner (connecting West and North)
     * 0x9: WestSouth corner (connecting East and North)
     *
     * @param world The world.
     * @param cart The cart asking for the metadata, null if it is not called by EntityMinecart.
     * @param y The rail X coordinate.
     * @param x The rail Y coordinate.
     * @param z The rail Z coordinate.
     * @return The metadata.
     */
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if(isPowered()) {
            meta = meta & 7;
        }
        return meta;
    }

    /**
     * Returns the max speed of the rail at the specified position.
     * @param world The world.
     * @param cart The cart on the rail, may be null.
     * @param x The rail X coordinate.
     * @param y The rail Y coordinate.
     * @param z The rail Z coordinate.
     * @return The max speed of the current rail.
     */
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z) {
        return 0.4f;
    }

    /**
     * This function is called by any minecart that passes over this rail.
     * It is called once per update tick that the minecart is on the rail.
     * @param world The world.
     * @param cart The cart on the rail.
     * @param y The rail X coordinate.
     * @param x The rail Y coordinate.
     * @param z The rail Z coordinate.
     */
    public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {
    }

    /**
     * Forge: Moved render type to a field and a setter.
     * This allows for a mod to change the render type
     * for vanilla rails, and any mod rails that extend
     * this class.
     */
    private int renderType = 9;

    public void setRenderType(int value) {
        renderType = value;
    }
    /* ======================================== FORGE END =====================================*/

    public class ModRail {
        private final World railBlock;
        private final int x;
        private final int y;
        private final int z;
        private final boolean isNonFlexible;
        private final List<ChunkPosition> connectedRails = new ArrayList<>();
        private final boolean canMakeSlopes;

        public ModRail(World world, int railX, int railY, int railZ) {
            railBlock = world;
            x = railX;
            y = railY;
            z = railZ;
            BlockModRailBase rail = (BlockModRailBase)world.getBlock(railX, railY, railZ);
            int railMetadata = rail.getBasicRailMetadata(world, null, railX, railY, railZ);
            isNonFlexible = !rail.isFlexibleRail(world, railX, railY, railZ);
            canMakeSlopes = rail.canMakeSlopes(world, railX, railY, railZ);
            getDirectionOrdinal(railMetadata);
        }

        private void getDirectionOrdinal(int meta) {
            connectedRails.clear();

            if (meta == 0) {
                connectedRails.add(new ChunkPosition(x, y, z - 1));
                connectedRails.add(new ChunkPosition(x, y, z + 1));
            } else if (meta == 1) {
                connectedRails.add(new ChunkPosition(x - 1, y, z));
                connectedRails.add(new ChunkPosition(x + 1, y, z));
            } else if (meta == 2) {
                connectedRails.add(new ChunkPosition(x - 1, y, z));
                connectedRails.add(new ChunkPosition(x + 1, y + 1, z));
            } else if (meta == 3) {
                connectedRails.add(new ChunkPosition(x - 1, y + 1, z));
                connectedRails.add(new ChunkPosition(x + 1, y, z));
            } else if (meta == 4) {
                connectedRails.add(new ChunkPosition(x, y + 1, z - 1));
                connectedRails.add(new ChunkPosition(x, y, z + 1));
            } else if (meta == 5) {
                connectedRails.add(new ChunkPosition(x, y, z - 1));
                connectedRails.add(new ChunkPosition(x, y + 1, z + 1));
            } else if (meta == 6) {
                connectedRails.add(new ChunkPosition(x + 1, y, z));
                connectedRails.add(new ChunkPosition(x, y, z + 1));
            } else if (meta == 7) {
                connectedRails.add(new ChunkPosition(x - 1, y, z));
                connectedRails.add(new ChunkPosition(x, y, z + 1));
            } else if (meta == 8) {
                connectedRails.add(new ChunkPosition(x - 1, y, z));
                connectedRails.add(new ChunkPosition(x, y, z - 1));
            } else if (meta == 9) {
                connectedRails.add(new ChunkPosition(x + 1, y, z));
                connectedRails.add(new ChunkPosition(x, y, z - 1));
            }
        }

        private void removeInvalidConnections() {
            for (int index = 0; index < connectedRails.size(); ++index) {
                BlockModRailBase.ModRail rail = getRailAt(connectedRails.get(index));

                if (rail != null && rail.isConnectedTo(this)) {
                    connectedRails.set(index, new ChunkPosition(rail.x, rail.y, rail.z));
                } else {
                    connectedRails.remove(index--);
                }
            }
        }

        private boolean isRailAtWithOffset(int x, int y, int z) {
            return BlockModRailBase.func_150049_b_(railBlock, x, y, z) || (BlockModRailBase.func_150049_b_(railBlock, x, y + 1, z) || BlockModRailBase.func_150049_b_(railBlock, x, y - 1, z));
        }

        private BlockModRailBase.ModRail getRailAt(ChunkPosition chunkPosition) {
            return BlockModRailBase.func_150049_b_(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY, chunkPosition.chunkPosZ) ? BlockModRailBase.this.new ModRail(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY, chunkPosition.chunkPosZ) : (BlockModRailBase.func_150049_b_(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY + 1, chunkPosition.chunkPosZ) ? BlockModRailBase.this.new ModRail(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY + 1, chunkPosition.chunkPosZ) : (BlockModRailBase.func_150049_b_(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY - 1, chunkPosition.chunkPosZ) ? BlockModRailBase.this.new ModRail(railBlock, chunkPosition.chunkPosX, chunkPosition.chunkPosY - 1, chunkPosition.chunkPosZ) : null));
        }

        private boolean isConnectedTo(BlockModRailBase.ModRail rail) {
            for (ChunkPosition chunkposition : connectedRails) {
                if (chunkposition.chunkPosX == rail.x && chunkposition.chunkPosZ == rail.z) {
                    return true;
                }
            }
            return false;
        }

        private boolean updateConnectedRails(int x, int y, int z) {
            for (ChunkPosition connectedRail : connectedRails) {

                if (connectedRail.chunkPosX == x && connectedRail.chunkPosZ == z) {
                    return true;
                }
            }
            return false;
        }

        public int addConnectedRail() {
            int connectedRailsCount = 0;

            if (isRailAtWithOffset(x, y, z - 1)) {
                ++connectedRailsCount;
            }
            if (isRailAtWithOffset(x, y, z + 1)) {
                ++connectedRailsCount;
            }
            if (isRailAtWithOffset(x - 1, y, z)) {
                ++connectedRailsCount;
            }
            if (isRailAtWithOffset(x + 1, y, z)) {
                ++connectedRailsCount;
            }
            return connectedRailsCount;
        }

        private boolean getRailInDirection(BlockModRailBase.ModRail rail) {
            return isConnectedTo(rail) || (connectedRails.size() != 2);
        }

        private void countAdjacentRails(BlockModRailBase.ModRail rail) {
            connectedRails.add(new ChunkPosition(rail.x, rail.y, rail.z));
            boolean connectedNorth = updateConnectedRails(x, y, z - 1);
            boolean connectedSouth = updateConnectedRails(x, y, z + 1);
            boolean connectedWest = updateConnectedRails(x - 1, y, z);
            boolean connectedEast = updateConnectedRails(x + 1, y, z);
            byte railShape = -1;

            if (connectedNorth || connectedSouth) {
                railShape = 0;
            }
            if (connectedWest || connectedEast) {
                railShape = 1;
            }
            if (!isNonFlexible) {
                if (connectedSouth && connectedEast && !connectedNorth && !connectedWest) {
                    railShape = 6;
                }
                if (connectedSouth && connectedWest && !connectedNorth && !connectedEast) {
                    railShape = 7;
                }
                if (connectedNorth && connectedWest && !connectedSouth && !connectedEast) {
                    railShape = 8;
                }
                if (connectedNorth && connectedEast && !connectedSouth && !connectedWest) {
                    railShape = 9;
                }
            }

            if (railShape == 0 && canMakeSlopes) {
                if (BlockModRailBase.func_150049_b_(railBlock, x, y + 1, z - 1)) {
                    railShape = 4;
                }
                if (BlockModRailBase.func_150049_b_(railBlock, x, y + 1, z + 1)) {
                    railShape = 5;
                }
            }

            if (railShape == 1 && canMakeSlopes) {
                if (BlockModRailBase.func_150049_b_(railBlock, x + 1, y + 1, z)) {
                    railShape = 2;
                }
                if (BlockModRailBase.func_150049_b_(railBlock, x - 1, y + 1, z)) {
                    railShape = 3;
                }
            }

            if (railShape < 0) {
                railShape = 0;
            }

            int finalMetadata = railShape;
            if (isNonFlexible) {
                finalMetadata = railBlock.getBlockMetadata(x, y, z) & 8 | railShape;
            }
            railBlock.setBlockMetadataWithNotify(x, y, z, finalMetadata, 3);
        }

        private boolean isStraightRail(int x, int y, int z) {
            BlockModRailBase.ModRail rail = getRailAt(new ChunkPosition(x, y, z));

            if (rail == null) {
                return false;
            } else {
                rail.removeInvalidConnections();
                return rail.getRailInDirection(this);
            }
        }

        public void placeRailWithNeighbors(boolean prioritizeCurves, boolean forceUpdate) {
            boolean connectedNorth = isStraightRail(x, y, z - 1);
            boolean connectedSouth = isStraightRail(x, y, z + 1);
            boolean connectedWest = isStraightRail(x - 1, y, z);
            boolean connectedEast = isStraightRail(x + 1, y, z);
            byte railShape = -1;

            if ((connectedNorth || connectedSouth) && !connectedWest && !connectedEast) {
                railShape = 0;
            }
            if ((connectedWest || connectedEast) && !connectedNorth && !connectedSouth) {
                railShape = 1;
            }
            if (!isNonFlexible) {
                if (connectedSouth && connectedEast && !connectedNorth && !connectedWest) {
                    railShape = 6;
                }
                if (connectedSouth && connectedWest && !connectedNorth && !connectedEast) {
                    railShape = 7;
                }
                if (connectedNorth && connectedWest && !connectedSouth && !connectedEast) {
                    railShape = 8;
                }
                if (connectedNorth && connectedEast && !connectedSouth && !connectedWest) {
                    railShape = 9;
                }
            }

            if (railShape == -1) {
                if (connectedNorth || connectedSouth) {
                    railShape = 0;
                }
                if (connectedWest || connectedEast) {
                    railShape = 1;
                }
                if (!isNonFlexible) {
                    if (prioritizeCurves) {
                        if (connectedSouth && connectedEast) {
                            railShape = 6;
                        }
                        if (connectedWest && connectedSouth) {
                            railShape = 7;
                        }
                        if (connectedEast && connectedNorth) {
                            railShape = 9;
                        }
                        if (connectedNorth && connectedWest) {
                            railShape = 8;
                        }
                    } else {
                        if (connectedNorth && connectedWest) {
                            railShape = 8;
                        }
                        if (connectedEast && connectedNorth) {
                            railShape = 9;
                        }
                        if (connectedWest && connectedSouth) {
                            railShape = 7;
                        }
                        if (connectedSouth && connectedEast) {
                            railShape = 6;
                        }
                    }
                }
            }

            if (railShape == 0 && canMakeSlopes) {
                if (BlockModRailBase.func_150049_b_(railBlock, x, y + 1, z - 1)) {
                    railShape = 4;
                }
                if (BlockModRailBase.func_150049_b_(railBlock, x, y + 1, z + 1)) {
                    railShape = 5;
                }
            }

            if (railShape == 1 && canMakeSlopes) {
                if (BlockModRailBase.func_150049_b_(railBlock, x + 1, y + 1, z)) {
                    railShape = 2;
                }
                if (BlockModRailBase.func_150049_b_(railBlock, x - 1, y + 1, z)) {
                    railShape = 3;
                }
            }

            if (railShape < 0) {
                railShape = 0;
            }

            getDirectionOrdinal(railShape);
            int finalMetadata = railShape;

            if (isNonFlexible) {
                finalMetadata = railBlock.getBlockMetadata(x, y, z) & 8 | railShape;
            }

            if (forceUpdate || railBlock.getBlockMetadata(x, y, z) != finalMetadata) {
                railBlock.setBlockMetadataWithNotify(x, y, z, finalMetadata, 3);

                for (ChunkPosition connectedRail : connectedRails) {
                    ModRail rail = getRailAt(connectedRail);

                    if (rail != null) {
                        rail.removeInvalidConnections();

                        if (rail.getRailInDirection(this)) {
                            rail.countAdjacentRails(this);
                        }
                    }
                }
            }
        }
    }
}
