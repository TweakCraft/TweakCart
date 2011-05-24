package com.edoxile.bukkit.tweakcart.Utils;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class CartUtil {
    public static BlockFace getDirection(Minecart cart) {
        Vector velocity = cart.getVelocity();
        if (Math.abs(velocity.getX()) > 0.0001) {
            return (velocity.getX() > 0 ? BlockFace.SOUTH : BlockFace.NORTH);
        } else if (Math.abs(velocity.getZ()) > 0.0001) {
            return (velocity.getZ() > 0 ? BlockFace.WEST : BlockFace.EAST);
        } else if (Math.abs(velocity.getY()) > 0.0001) {
            return (velocity.getY() > 0 ? BlockFace.UP : BlockFace.DOWN);
        } else {
            return BlockFace.SELF;
        }
    }
}
