package com.tweakcart.listeners;

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
    }
    
}
