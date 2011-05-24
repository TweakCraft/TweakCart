package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import com.edoxile.bukkit.tweakcart.Utils.CartUtil;
import com.edoxile.bukkit.tweakcart.Utils.TweakMinecart;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartVehicleListener extends VehicleListener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static TweakCart plugin = null;

    public TweakCartVehicleListener(TweakCart p) {
        plugin = p;
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            if (plugin.tweakMinecarts.containsKey(event.getVehicle())) {
                TweakMinecart cart = plugin.tweakMinecarts.get(event.getVehicle());
                if (plugin.lockedMinecarts.containsValue(cart)) {
                    cart.stop();
                    cart.getCart().teleport(event.getFrom());
                    return;
                }
            }

            if (event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                return;
            }

            if (event.getTo().getBlock().getType() == Material.POWERED_RAIL) {
                TweakMinecart cart;
                if (plugin.tweakMinecarts.containsKey(event.getVehicle())) {
                    cart = plugin.tweakMinecarts.get(event.getVehicle());
                } else {
                    cart = new TweakMinecart((Minecart) event.getVehicle());
                    plugin.tweakMinecarts.put((Minecart) event.getVehicle(), cart);
                }
                Block rail = event.getTo().getBlock();
                if ((rail.getData() & 0x8) > 0) {
                    cart.boost();
                } else {
                    cart.stop();
                    plugin.lockedMinecarts.put(rail, cart);
                }
            }
        }
    }
}
