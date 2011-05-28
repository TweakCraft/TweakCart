package com.tweakcart.model;

import org.bukkit.util.Vector;

public enum Direction {
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
    SELF(0, 0, 0);

    private static final Direction[][][] directions = new Direction[3][3][3];
    private final int modX;
    private final int modY;
    private final int modZ;

    static {
        for (Direction dir : values()) directions[dir.getModX() + 1][dir.getModY() + 1][dir.getModZ() + 1] = dir;
    }

    private Direction(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private Direction(final Direction dir1, final Direction dir2) {
        this.modX = dir1.getModX() + dir2.getModX();
        this.modY = dir1.getModY() + dir2.getModY();
        this.modZ = dir1.getModZ() + dir2.getModZ();
    }

    /**
     * Get the amount of X-coordinates to modify to get the represented block
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX() {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to get the represented block
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY() {
        return modY;
    }

    /**
     * Get the amount of Z-coordinates to modify to get the represented block
     *
     * @return Amount of Z-coordinates to modify
     */
    public int getModZ() {
        return modZ;
    }

    public Vector mod(double mod) {
        return new Vector(modX * mod, modY * mod, modZ * mod);
    }

    public static Direction getDirection(int modx, int mody, int modz) {
        return directions[modx + 1][mody + 1][modz + 1];
    }

    public static Direction getHorizontalDirection(Vector velocity) {
        int modx = (velocity.getX() == 0 ? 0 : velocity.getX() > 0 ? 1 : -1);
        int modz = (velocity.getZ() == 0 ? 0 : velocity.getZ() > 0 ? 1 : -1);
        return getDirection(modx, 0, modz);
    }

    public static Direction getVerticalDirection(Vector velocity) {
        int mody = (velocity.getY() == 0 ? 0 : velocity.getY() > 0 ? 1 : -1);
        return getDirection(0, mody, 0);
    }
}
