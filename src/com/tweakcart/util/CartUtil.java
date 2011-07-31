package com.tweakcart.util;

import com.tweakcart.model.Direction;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class CartUtil {

    /*
      * The minimal speed needed for a cart to be able to reach the next block.
      */
    private static final double min_movement = 0.04d;

    /**
     * Checks whereather the cart is moving at a very small pace
     * and needs to be stopped to prevent unnececary server load.
     *
     * @param cart
     * @return true if the cart was stopped, false if nothing was changed.
     */
    public static final boolean stoppedSlowCart(Minecart cart, Vector velocity) {
        switch (Direction.getVerticalDirection(cart.getLocation().getYaw())) {
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
