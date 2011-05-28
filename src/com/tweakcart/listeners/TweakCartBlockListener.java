package com.tweakcart.listeners;

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

    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        if (event.getNewCurrent() == event.getOldCurrent() || event.getNewCurrent() > 0 & event.getOldCurrent() > 0)
            return;
        /**
         * TODO: search for either 1) a chest, 2) a rail. The chests can be found by using the BlockState mapper.
         */
    }
}
