package com.tweakcart.listeners;

import com.google.common.collect.MapMaker;
import com.tweakcart.TweakCart;
import com.tweakcart.model.Direction;
import com.tweakcart.model.IntMap;
import com.tweakcart.model.SignLocation;
import com.tweakcart.model.SignParser;
import com.tweakcart.util.CartUtil;
import com.tweakcart.util.ChestUtil;
import com.tweakcart.util.MathUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class TweakCartVehicleListener extends VehicleListener {
    //private static TweakCart plugin = null;
    private static ConcurrentMap<SignLocation, List<IntMap>> softmap;
    private static int softMapHits = 0;
    private static int softMapMisses = 0;
    private int softMapPartialMisses = 0;
    public TweakCartVehicleListener(TweakCart instance) {
        //plugin = instance;
        softmap = new MapMaker().concurrencyLevel(4).softValues().makeMap();
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof StorageMinecart) {
            if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) {
                return;
            }
            StorageMinecart cart = (StorageMinecart) event.getVehicle();

            Vector cartSpeed = cart.getVelocity(); // We are gonna use this 1 object everywhere(a new Vector() is made on every call ;) ).
            Block toBlock = event.getTo().getBlock(); //Use this object everywhere as well.
            Direction horizontalDirection = Direction.getHorizontalDirection(cartSpeed);
            if (CartUtil.stoppedSlowCart(cart, cartSpeed, toBlock, horizontalDirection)) {
                return;
            }

            switch (toBlock.getType()) {
                case RAILS:
                case POWERED_RAIL:
                case DETECTOR_RAIL:
                    switch (horizontalDirection) {
                        case NORTH:
                        case SOUTH:
                            for (int dy = -1; dy <= 0; dy++) {
                                for (int dz = -1; dz <= 1; dz += 2) {
                                    Block tempBlock = toBlock.getRelative(0, dy, dz);
                                    if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                                            || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                                        
                                    	Sign s = (Sign) tempBlock.getState();
                                    	if(tempBlock.getRelative(BlockFace.DOWN).isBlockPowered() || tempBlock.getRelative(BlockFace.DOWN).isBlockIndirectlyPowered()){
                                    		Bukkit.getServer().broadcastMessage("block is gepowered");
                                    		return;
                                    	}
                                    	parseItemSign(s, cart, horizontalDirection);
                                    }
                                }
                            }
                            break;
                        case EAST:
                        case WEST:
                            for (int dy = -1; dy <= 0; dy++) {
                                for (int dx = -1; dx <= 1; dx += 2) {
                                    Block tempBlock = toBlock.getRelative(dx, dy, 0);
                                    if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                                            || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                                        Sign s = (Sign) tempBlock.getState();
                                        parseItemSign(s, cart, horizontalDirection);
                                    }
                                }
                            }
                            break;
                    }
                    Block tempBlock = toBlock.getRelative(0, 1, 0);
                    if (tempBlock.getTypeId() == Material.SIGN_POST.getId()
                            || tempBlock.getTypeId() == Material.WALL_SIGN.getId()) {
                        Sign s = (Sign) tempBlock.getState();
                        parseItemSign(s, cart, horizontalDirection);
                    }
                    break;
                case SIGN_POST:
                case WALL_SIGN:

                    break;
            }
        }
        else{
            
        }
    }

    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        if (event.getVehicle() instanceof Minecart && event.getBlock().getRelative(BlockFace.UP).getTypeId() == Material.DISPENSER.getId()) {
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


    private void parseItemSign(Sign sign, StorageMinecart cart, Direction direction) {
        List<IntMap> intmaps;
        List<Chest> chests;
        SignLocation loc = new SignLocation(sign.getX(), sign.getY(), sign.getZ());
        List<IntMap> temp = softmap.get(loc);
        if(temp != null && containsDirection(temp, direction, sign)){
            intmaps = stripDirection(temp, direction);
            softMapHits++;
        }
        else{
            intmaps = SignParser.parseItemSign(sign, direction);
            if(softmap.get(loc) != null){
                List<IntMap> prevresult = softmap.get(loc);
                for(int i = 0; i < prevresult.size(); i++){
                    if(!intmaps.contains(prevresult.get(i))){
                        intmaps.add(prevresult.get(i));
                    }
                }
                softmap.put(loc, intmaps);
                softMapPartialMisses++;
            }
            else{
                softmap.put(loc, intmaps);
                softMapMisses++;
            }

        }
        for (IntMap map: intmaps) {
            if (map == null)
                continue;
            switch (map.getAction()) {
                case COLLECT:
                    //Collect items (from cart to chest)
                    chests = ChestUtil.getChestsAroundBlock(sign.getBlock(), 1);
                    for (Chest c : chests) {
                        ChestUtil.moveItems(cart.getInventory(), c.getInventory(), map);
                    }
                    break;
                case DEPOSIT:
                    //Deposit items (from chest to cart)
                    chests = ChestUtil.getChestsAroundBlock(sign.getBlock(), 1);
                    for (Chest c : chests) {
                        ChestUtil.moveItems(c.getInventory(), cart.getInventory(), map);
                    }
                    break;
            }
        }
    }

    private List<IntMap> stripDirection(List<IntMap> temp, Direction dir) {
        List<IntMap> result = new ArrayList<IntMap>();
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getDirection() == dir || temp.get(i).getDirection() == Direction.SELF){
                result.add(temp.get(i));
            }
       }
       
       return result;
    }

    private boolean containsDirection(List<IntMap> list, Direction dir, Sign s) {
        int amountofentries = 0;
        for(String line: s.getLines()){
            if(line != null && !line.equals("") && !line.equalsIgnoreCase("deposit items") && !line.equalsIgnoreCase("deposit items")){
                Bukkit.getServer().broadcastMessage("pong");
                amountofentries++;
            }
        }
        if(list.size() == amountofentries) return true;
        
        for(Iterator<IntMap> it = list.iterator(); it.hasNext();){
            Direction dir2 = it.next().getDirection();
            if(dir2 == dir){
                return true;
            }
        }
        return false;
         
    }
    
    public int getSoftMapHits(){
        return softMapHits;
    }
    
    public int getSoftMapMisses(){
        return softMapMisses;
    }
    
    public int getPartialMisses(){
        return softMapPartialMisses;
    }
    
    /**
     * TODO: write this function.
     */
    public void parseRouteSign(Sign sign, Minecart cart, Direction direction) {
        //fill this
    }

    public void removeEntry(SignLocation loc) {
        softmap.remove(loc);      
    }
}
