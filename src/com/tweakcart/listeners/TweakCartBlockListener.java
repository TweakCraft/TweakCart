package com.tweakcart.listeners;

import com.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tweakcart.TweakCart;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartBlockListener extends BlockListener {
    private static final Logger log = Logger.getLogger("Minecraft");
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
                Block track = null;
                Direction direction = null;
                switch(event.getBlock().getData()){
                    case 0x2:
                        track = event.getBlock().getRelative(BlockFace.EAST);
                        direction = Direction.EAST;
                        break;
                    case 0x3:
                        track = event.getBlock().getRelative(BlockFace.WEST);
                        direction = Direction.WEST;
                        break;
                    case 0x4:
                        track = event.getBlock().getRelative(BlockFace.NORTH);
                        direction = Direction.NORTH;
                        break;
                    case 0x5:
                        track = event.getBlock().getRelative(BlockFace.SOUTH);
                        direction = Direction.SOUTH;
                        break;
                }
                switch (track.getTypeId()) {
                    case 27:
                    case 28:
                    case 66:
                    log.info("Found a rail!");
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
                        log.info("Setting velocity!");
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
