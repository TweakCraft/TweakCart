package com.tweakcart.model;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public enum Direction
{
    NORTH(-1, 0, 0),
    EAST(0, 0, -1),
    SOUTH(1, 0, 0),
    WEST(0, 0, 1),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    SELF(0, 0, 0),
    EAST_WEST(1, 1, 1), // for track direction.
    NORTH_SOUTH(-1, -1, -1); // for track direction.

    private static final Direction[][][] directions = new Direction[3][3][3];
    private final int modX;
    private final int modY;
    private final int modZ;

    static
    {
        for (Direction dir : values()) directions[dir.getModX() + 1][dir.getModY() + 1][dir.getModZ() + 1] = dir;
    }

    private Direction(final int modX, final int modY, final int modZ)
    {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private Direction(final Direction dir1, final Direction dir2)
    {
        this.modX = dir1.getModX() + dir2.getModX();
        this.modY = dir1.getModY() + dir2.getModY();
        this.modZ = dir1.getModZ() + dir2.getModZ();
    }

    /**
     * Get the amount of X-coordinates to modify to get the represented block
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX()
    {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to get the represented block
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY()
    {
        return modY;
    }

    /**
     * Get the amount of Z-coordinates to modify to get the represented block
     *
     * @return Amount of Z-coordinates to modify
     */
    public int getModZ()
    {
        return modZ;
    }

    public Vector mod(double mod)
    {
        return new Vector(getModX() * mod, getModY() * mod, getModZ() * mod);
    }

    public static final Direction getDirection(int modx, int mody, int modz)
    {
        return directions[modx + 1][mody + 1][modz + 1];
    }


    /**
     * Returns the direction the track is pointing at.
     * few rules:
     * - curved rails will return the direction their CURVE is pointing AT.
     * - ascending tracks will return the direction they are ascending TO.
     *
     * @param track
     * @return direction.
     */
    public static final Direction getHorizontalTrackDirection(Block track)
    {
        byte data = track.getData();
        switch (track.getTypeId())
        {
            case 27:
            case 66:
                break;
            case 28:
                data &= 7;
            default:
                return SELF;
        }
        switch (data)
        {
            case 0:
                return EAST_WEST;
            case 1:
                return NORTH_SOUTH;
            case 2:
                return SOUTH;
            case 3:
                return NORTH;
            case 4:
                return EAST;
            case 5:
                return WEST;
            case 6:
                return NORTH_EAST;
            case 7:
                return SOUTH_EAST;
            case 8:
                return SOUTH_WEST;
            case 9:
                return NORTH_WEST;
            default:
                return SELF;
        }
    }

    /**
     * Returns the vertical direction the track is pointing to.
     * few rules:
     * - returns UP for all ascending tracks.
     * - return SELF for the rest.
     *
     * @param track
     * @return direction
     */
    public static final Direction getVerticalTrackDirection(Block track)
    {
        switch (track.getTypeId())
        {
            case 27:
            case 66:
            case 28:
                byte data = track.getData();
                if ((((data & 2) == 2 && (data & 4) != 4) || ((data & 2) != 2 && (data & 4) == 4)) && (data & 8) != 8) //Somewhere i hope the cpu can see this optimization ;D
                    return UP;
            default:
                return SELF;
        }
    }

    public static final Direction getHorizontalDirection(Vector velocity)
    {
        int modx = (velocity.getX() == 0 ? 0 : velocity.getX() > 0 ? 1 : -1);
        int modz = (velocity.getZ() == 0 ? 0 : velocity.getZ() > 0 ? 1 : -1);
        return getDirection(modx, 0, modz);
    }

    public static final Direction getVerticalDirection(Vector velocity)
    {
        int mody = (velocity.getY() == 0 ? 0 : velocity.getY() > 0 ? 1 : -1);
        return getDirection(0, mody, 0);
    }
}
