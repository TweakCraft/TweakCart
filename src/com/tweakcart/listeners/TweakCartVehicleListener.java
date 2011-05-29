package com.tweakcart.listeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.tweakcart.TweakCart;
import com.tweakcart.model.TweakCartConfig;
import com.tweakcart.util.CartUtil;
import com.tweakcart.util.MathUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Dispenser;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartVehicleListener extends VehicleListener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static TweakCart plugin = null;
    private TweakCartConfig config = null;

    public TweakCartVehicleListener(TweakCart instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    public void onVehicleCreate(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            ((Minecart) event.getVehicle()).setSlowWhenEmpty(false);
        }
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart cart = (Minecart) event.getVehicle();
            if (CartUtil.stoppedSlowCart(cart)) return;
            if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) return;

        }
    }

    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        if (event.getBlock().getRelative(BlockFace.UP).getTypeId() == 23 && event.getVehicle() instanceof Minecart) {
            ItemStack item;
            if (event.getVehicle() instanceof PoweredMinecart) {
                item = new ItemStack(Material.POWERED_MINECART, 1);
            } else if (event.getVehicle() instanceof StorageMinecart) {
                item = new ItemStack(Material.STORAGE_MINECART, 1);
            } else {
                item = new ItemStack(Material.MINECART, 1);
            }
            Dispenser dispenser = (Dispenser) event.getBlock().getRelative(BlockFace.UP).getState();
            if (dispenser.getInventory().addItem(item).isEmpty()) {
                if (event.getVehicle() instanceof StorageMinecart) {
                    StorageMinecart cart = (StorageMinecart) event.getVehicle();
                    HashMap<Integer, ItemStack> map = dispenser.getInventory().addItem(cart.getInventory().getContents());
                    for (ItemStack i : map.values()) {
                        if (i != null)
                            cart.getWorld().dropItem(cart.getLocation(), i);
                    }
                }
                event.getVehicle().remove();
                return;
            }
        }
    }
}
