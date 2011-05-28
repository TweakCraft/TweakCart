package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import com.edoxile.bukkit.tweakcart.Utils.CartUtil;
import com.edoxile.bukkit.tweakcart.Utils.TweakCartConfig;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartVehicleListener extends VehicleListener {
    private static TweakCart plugin = null;
    private TweakCartConfig config = null;

    public TweakCartVehicleListener(TweakCart instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            /**
             * Any code for locked minecarts should be here, where the entity is to be teleported from event.to() to event.from();
             * The following piece of code is only for storage-minecarts and switches.
             */
            if (!event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                /**
                 * We're moving to a new block, so check for signs on >>predetermined<< spots or a switch block.
                 * Parse sign and follow orders. Also check speed and if it is below a certain value
                 * stop the cart completely to reduce server load (will cause less calls of this event).
                 *
                 * Also, init the cart variable for local usage, and determine the direction we're coming from.
                 */
                Minecart cart = (Minecart)event.getVehicle();
                BlockFace direction = CartUtil.getDirection(cart.getVelocity());
                if (direction == BlockFace.SELF) {
                    cart.setVelocity(new Vector(0.0D,0.0D,0.0D));
                }

                /**
                 * Now we can check if the cart needs to do something
                 */
                if (event.getTo().getBlock().getRelative(BlockFace.DOWN).getTypeId() == config.switchBlock) {
                    /**
                     * Rollin' over a switchblock!
                     */
                } else if (cart instanceof StorageMinecart) {
                    /**
                     * Check for signs, follow the instructions
                     */
                } else {
                    /**
                     * Nice! We don't have to do anything!
                     */
                }
            }
        }
    }
}
