package com.tweakcart.util;

import com.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.util.Vector;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile, Meaglin
 */
public class CartUtil {

    /*
      * The minimal speed needed for a cart to be able to reach the next block.
      */
    private static final double min_movement = 0.04d;

    /**
     * Checks whether the cart is moving at a very small pace
     * and needs to be stopped to prevent unnecessary server load.
     */
    public static final boolean stoppedSlowCart(StorageMinecart cart, Vector velocity, Block to, Direction horizontalDirection) {
        switch (Direction.getVerticalDirection(to, horizontalDirection)) {
            case DOWN:
            case UP:
                return false;
        }

        if (MathUtil.abs(velocity.getX()) < min_movement && MathUtil.abs(velocity.getZ()) < min_movement) {
            velocity.setX(0);
            velocity.setZ(0);
            cart.setVelocity(velocity);
            return true;
        }
        return false;
    }
}
