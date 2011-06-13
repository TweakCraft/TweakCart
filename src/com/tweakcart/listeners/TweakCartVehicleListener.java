package com.tweakcart.listeners;

import com.tweakcart.model.Direction;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.tweakcart.TweakCart;
import com.tweakcart.model.TweakCartConfig;
import com.tweakcart.util.CartUtil;
import com.tweakcart.util.MathUtil;
import com.tweakcart.util.ChestUtil;

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

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart cart = (Minecart) event.getVehicle();
            if (CartUtil.stoppedSlowCart(cart)) return;
            if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) return;
            Direction horizontalDirection = Direction.getHorizontalDirection(cart.getVelocity());
            switch (horizontalDirection) {
                case NORTH:
                case SOUTH:
                    for (int dy = -1; dy <= 0; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            Block tempBlock = event.getTo().getBlock().getRelative(0, dy, dz);
                            if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                                    || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                                Sign s = (Sign)tempBlock.getState();
                            }
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    for (int dy = -1; dy <= 0; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {

                        }
                    }
                    break;
            }
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
            ItemStack cartItemStack = ChestUtil.putItems(item, dispenser)[0];
            if (cartItemStack == null) {
                if (event.getVehicle() instanceof StorageMinecart) {
                    StorageMinecart cart = (StorageMinecart) event.getVehicle();
                    ItemStack[] leftovers = ChestUtil.putItems(cart.getInventory().getContents(), dispenser);
                    cart.getInventory().clear();
                    for (ItemStack i : leftovers) {
                        if (i == null)
                            continue;
                        cart.getWorld().dropItem(cart.getLocation(), i);
                    }
                }
                event.getVehicle().remove();
                return;
            }
        }
    }
}
