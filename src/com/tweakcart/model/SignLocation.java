package com.tweakcart.model;

public class SignLocation {
    private int x, y, z;

    public SignLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SignLocation) {
            SignLocation l = (SignLocation) other;
            return l.x == x && l.y == y && l.z == z;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (x + "" + y + "" + z).hashCode();
    }
}
