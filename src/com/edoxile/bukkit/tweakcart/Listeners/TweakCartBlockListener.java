package com.edoxile.bukkit.tweakcart.Listeners;

import com.edoxile.bukkit.tweakcart.TweakCart;
import com.edoxile.bukkit.tweakcart.Utils.BlockMapper;
import com.edoxile.bukkit.tweakcart.Utils.TweakMinecart;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.ArrayList;

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
        ArrayList<Block> blockList = BlockMapper.mapBlocks(event.getBlock(), 1, Material.POWERED_RAIL);
        for (Block b : blockList) {
            if ((b.getData() & 0x8) == 0) {
                if (plugin.lockedMinecarts.containsKey(b)) {
                    TweakMinecart cart = plugin.lockedMinecarts.get(b);
                    plugin.lockedMinecarts.remove(b);
                    cart.start();
                    cart.boost();
                }
            }
        }
    }
}
