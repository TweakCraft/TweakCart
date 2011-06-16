package com.tweakcart.util;

import org.bukkit.Location;

public class MathUtil {

    public static final int floor(double d) {
        int rt = (int) d;
        return rt > d ? rt - 1 : rt;
    }

    public static final boolean isSameBlock(Location from, Location to) {
        return floor(from.getX()) == floor(to.getX()) && floor(from.getY()) == floor(to.getY()) && floor(from.getZ()) == floor(to.getZ());
    }

    public static final double abs(double d) {
        return d < 0 ? -d : d;
    }
}
