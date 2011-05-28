package com.tweakcart.listeners;

import com.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tweakcart.TweakCart;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartBlockListener extends BlockListener {
    private TweakCart plugin;

    public TweakCartBlockListener(TweakCart plugin) {
        this.plugin = plugin;
    }

    /*
	    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
	        if (event.getNewCurrent() == event.getOldCurrent() || event.getNewCurrent() > 0 & event.getOldCurrent() > 0)
	            return;
	    }
    */

    /**
     * Called when a block is dispensing an item
     *
     * @param event Relevant event details
     */
    public void onBlockDispense(BlockDispenseEvent event) {
        switch (event.getItem().getTypeId()) {
            case 328:
            case 342:
            case 343:
                Direction direction = Direction.getHorizontalDirection(event.getVelocity());
                Block track = event.getBlock().getRelative(direction.getModX(), direction.getModY(), direction.getModZ());
                switch (track.getTypeId()) {
                    case 27:
                    case 28:
                    case 66:
                        Minecart cart = null;
                        switch (event.getItem().getTypeId()) {
                            case 328:
                                cart = track.getWorld().spawnMinecart(track.getLocation());
                                break;
                            case 342:
                                cart = track.getWorld().spawnStorageMinecart(track.getLocation());
                                break;
                            case 343:
                                cart = track.getWorld().spawnPoweredMinecart(track.getLocation());
                                break;
                            default:
                                break;
                        }
                        cart.setVelocity(direction.mod(cart.getMaxSpeed()));
                        event.setCancelled(true);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
