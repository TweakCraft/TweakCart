package com.tweakcart;
//Hai

import com.tweakcart.listeners.TweakCartBlockListener;
import com.tweakcart.listeners.TweakCartVehicleListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCart extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private TweakCartVehicleListener vehicleListener = null;
    private TweakCartBlockListener blockListener = null;

    public void onEnable() {
        vehicleListener = new TweakCartVehicleListener();
        blockListener = new TweakCartBlockListener();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(vehicleListener, this);
        pm.registerEvents(blockListener, this);
        // Loaded!
        log.info("[" + getDescription().getName() + "] Enabled! version:" + getDescription().getVersion());
    }

    public void onDisable() {
        log.info("[" + getDescription().getName() + "] Disabled!");
    }
}
