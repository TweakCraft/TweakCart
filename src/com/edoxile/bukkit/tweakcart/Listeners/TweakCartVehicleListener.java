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
    private static HashMap<Minecart, TweakMinecart> tweakMinecarts = new HashMap<Minecart, TweakMinecart>();
    private static HashMap<Block, TweakMinecart> lockedMinecarts = new HashMap<Block, TweakMinecart>();

    public TweakCartVehicleListener(TweakCart p) {
        plugin = p;
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            if (tweakMinecarts.containsKey(event.getVehicle())) {
                TweakMinecart cart = tweakMinecarts.get(event.getVehicle());
                if (lockedMinecarts.containsValue(cart)) {
                    cart.stop();
                }
            } else if (event.getFrom().getBlock().equals(event.getTo().getBlock())) {
                return;
            } else {
                if (event.getTo().getBlock().getType() == Material.POWERED_RAIL) {
                    TweakMinecart cart;
                    if (tweakMinecarts.containsKey(event.getVehicle())) {
                        cart = tweakMinecarts.get(event.getVehicle());
                    } else {
                        cart = new TweakMinecart((Minecart) event.getVehicle());
                        tweakMinecarts.put((Minecart) event.getVehicle(), cart);
                    }
                    Block rail = event.getTo().getBlock();
                    if ((rail.getData() & 0x8) > 0) {
                        log.info("Block is powered!");
                    } else {
                        log.info("Block is not powered!");
                        cart.stop();
                        lockedMinecarts.put(rail, cart);
                    }
                }
            }
        }
    }
}
