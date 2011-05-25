package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.Action;
import com.edoxile.bukkit.tweakcart.TweakCart;
import com.edoxile.bukkit.tweakcart.Utils.BlockbagUtil;
import com.edoxile.bukkit.tweakcart.Utils.TweakMinecart;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

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
            if (plugin.lockedMinecarts.containsKey(event.getFrom().getBlock())) {
                TweakMinecart cart = plugin.lockedMinecarts.get(event.getFrom().getBlock());
                cart.stop();
                cart.getCart().teleport(event.getFrom());
                return;
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
                if (rail.getRelative(BlockFace.DOWN).getType() != Material.CHEST) {
                    if ((rail.getData() & 0x8) > 0) {
                        cart.boost();
                    } else {
                        cart.reloadVelocity();
                        cart.stop();
                        plugin.lockedMinecarts.put(rail, cart);
                    }
                }
            } else {
                if (event.getFrom().getBlock().getType() == Material.POWERED_RAIL && (event.getTo().getBlock().getType() == Material.DETECTOR_RAIL || event.getTo().getBlock().getType() == Material.RAILS)) {
                    if (plugin.lockedMinecarts.containsKey(event.getFrom().getBlock())) {
                        plugin.lockedMinecarts.remove(event.getFrom().getBlock());
                    }
                } else if ((event.getTo().getBlock().getType() == Material.WOOD_PLATE || event.getTo().getBlock().getType() == Material.STONE_PLATE)) {
                    Minecart cart = (Minecart) event.getVehicle();
                    cart.setDerailedVelocityMod(new Vector(1, 1, 1));
                    if (event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.CHEST && (event.getVehicle() instanceof StorageMinecart)) {
                        Action action;
                        if (event.getTo().getBlock().getRelative(BlockFace.UP).getType() == Material.FURNACE) {
                            action = Action.SMELT;
                        } else {
                            action = (event.getTo().getBlock().getType() == Material.WOOD_PLATE) ? Action.COLLECT : Action.DEPOSIT;
                        }
                        TweakMinecart minecart;
                        if (plugin.tweakMinecarts.containsKey(event.getVehicle())) {
                            minecart = plugin.tweakMinecarts.get(event.getVehicle());
                        } else {
                            minecart = new TweakMinecart((Minecart) event.getVehicle());
                            plugin.tweakMinecarts.put((Minecart) event.getVehicle(), minecart);
                        }
                        BlockbagUtil.doAction(action, event.getTo().getBlock(), minecart);
                    }
                } else {
                    Minecart cart = (Minecart) event.getVehicle();
                    cart.setDerailedVelocityMod(new Vector(0.5D, 0.5D, 0.5D));
                }
            }
        }
    }
}
