package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartBlockListener extends BlockListener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private TweakCart plugin;

    public TweakCartBlockListener(TweakCart instance){
        plugin = instance;
    }

    public void onBlockRedstoneChange(BlockRedstoneEvent event) {}
}
