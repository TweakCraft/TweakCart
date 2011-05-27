package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import com.edoxile.bukkit.tweakcart.Utils.TweakCartConfig;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

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
            if(!event.getFrom().getBlock().equals(event.getTo().getBlock())){
                /**
                 * We're moving to a new block, so check for signs on >>predetermined<< spots or a switch block. Parse sign and follow orders.
                 */
                if(event.getTo().getBlock().getRelative(BlockFace.DOWN).getTypeId() == config.switchBlock){
                    /**
                     * Rollin' over a switchblock!
                     */
                } else if(event.getVehicle() instanceof StorageMinecart) {
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
