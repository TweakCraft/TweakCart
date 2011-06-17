package com.tweakcart.listeners;

import com.tweakcart.model.Direction;
import com.tweakcart.model.SignUtil;
import com.tweakcart.util.BlockMapper;
import gnu.trove.TIntIntHashMap;
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
import org.bukkit.util.Vector;

import com.tweakcart.TweakCart;
import com.tweakcart.model.TweakCartConfig;
import com.tweakcart.util.CartUtil;
import com.tweakcart.util.MathUtil;
import com.tweakcart.util.ChestUtil;

import javax.naming.LinkException;
import java.util.ArrayList;
import java.util.List;
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
            Vector cartSpeed = cart.getVelocity(); // We are gonna use this 1 object everywhere(a new Vector() is made on every call ;) ).
            if (CartUtil.stoppedSlowCart(cart, cartSpeed)) return;
            if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) return;
            Direction horizontalDirection = Direction.getHorizontalDirection(cartSpeed);
            switch (horizontalDirection) {
                case NORTH:
                case SOUTH:
                    for (int dy = -1; dy <= 0; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            Block tempBlock = event.getTo().getBlock().getRelative(0, dy, dz);
                            if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                                    || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                                Sign s = (Sign) tempBlock.getState();
                                parseSign(s, cart, horizontalDirection);
                            }
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    for (int dy = -1; dy <= 0; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            Block tempBlock = event.getTo().getBlock().getRelative(dx, dy, 0);
                            if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                                    || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                                Sign s = (Sign) tempBlock.getState();
                                parseSign(s, cart, horizontalDirection);
                            }
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

    private void parseSign(Sign sign, Minecart cart, Direction direction) {
        String[] lines = sign.getLines();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].toLowerCase().contains("collect items") && cart instanceof StorageMinecart) {
                //Collect items (cart -> chest)
                //Get chests first, and then move items
                List<BlockState> stateList = BlockMapper.mapBlockStates(sign.getBlock(), direction, Material.CHEST);
                if (!stateList.isEmpty()) {
                    TIntIntHashMap materials = SignUtil.getItemStringListToMaterial(lines[i + 1], direction);
                    if (!(materials == null || materials.isEmpty())) {
                        ItemStack[] cartContent = ((StorageMinecart) cart).getInventory().getContents();
                        for (int itemIndex = 0; itemIndex < cartContent.length; itemIndex++) {
                            if (cartContent[itemIndex] == null) continue;
                            int key = SignUtil.getKey(cartContent[itemIndex]);
                            int amount = materials.get(key);
                            switch (amount) {
                                case -2:
                                    //Don't do this item, so skip to next
                                    continue;
                                default:
                                    for (BlockState s : stateList) {
                                        ContainerBlock chest = (ContainerBlock) s;
                                        cartContent[itemIndex] = ChestUtil.putItems(cartContent[itemIndex], chest)[0];
                                        if (cartContent[itemIndex] == null || cartContent[itemIndex].getAmount() < 1) {
                                            //Contents is null!
                                            break;
                                        }
                                    }
                            }
                        }
                        ((StorageMinecart) cart).getInventory().setContents(cartContent);
                    }
                }
            } else if (lines[i].toLowerCase().contains("deposit items") && cart instanceof StorageMinecart) {
                //Deposit items (chest -> cart)
                //Get chests first, and then move items
                List<BlockState> stateList = BlockMapper.mapBlockStates(sign.getBlock(), direction, Material.CHEST);
                if (!stateList.isEmpty()) {
                    TIntIntHashMap materials = SignUtil.getItemStringListToMaterial(lines[i + 1], direction);
                    if (!(materials == null || materials.isEmpty())) {
                        ItemStack[] chestContent = ((StorageMinecart) cart).getInventory().getContents();
                        for (int itemIndex = 0; itemIndex < cartContent.length; itemIndex++) {
                            if (cartContent[itemIndex] == null) continue;
                            int key = SignUtil.getKey(cartContent[itemIndex]);
                            int amount = materials.get(key);
                            switch (amount) {
                                case -2:
                                    //Don't do this item, so skip to next
                                    continue;
                                default:
                                    for (BlockState s : stateList) {
                                        ContainerBlock chest = (ContainerBlock) s;
                                        cartContent[itemIndex] = ChestUtil.putItems(cartContent[itemIndex], chest)[0];
                                        if (cartContent[itemIndex] == null || cartContent[itemIndex].getAmount() < 1) {
                                            //Contents is null!
                                            break;
                                        }
                                    }
                            }
                        }
                        ((StorageMinecart) cart).getInventory().setContents(cartContent);
                    }
                }
            } else {
                //no action, continue;
            }
        }
    }
}
