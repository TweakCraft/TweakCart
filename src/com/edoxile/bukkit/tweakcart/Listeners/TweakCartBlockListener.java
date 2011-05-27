package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartBlockListener extends BlockListener {
    private TweakCart plugin;

    public TweakCartBlockListener(TweakCart instance) {
        plugin = instance;
    }

    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        if (event.getNewCurrent() == event.getOldCurrent() || event.getNewCurrent() > 0 & event.getOldCurrent() > 0)
            return;
        /**
         * TODO: search for either 1) a chest, 2) a rail. The chests can be found by using the BlockState mapper.
         */
    }
}
