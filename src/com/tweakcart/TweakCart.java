package com.tweakcart;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.tweakcart.listeners.TweakCartBlockListener;
import com.tweakcart.listeners.TweakCartVehicleListener;
import com.tweakcart.model.TweakCartConfig;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCart extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private TweakCartVehicleListener vehicleListener = null;
    private TweakCartBlockListener blockListener = null;
    private TweakCartConfig config = null;

    public void onEnable() {
        // Initialising variables
        config = new TweakCartConfig(this);
        vehicleListener = new TweakCartVehicleListener(this);
        blockListener = new TweakCartBlockListener(this);

        // Load plugin-manager and register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DISPENSE, blockListener, Event.Priority.Normal, this);

        // Loaded!
        log.info("[" + getDescription().getName() + "] Enabled! version:" + getDescription().getVersion());
    }

    public void onDisable() {
        log.info("[" + getDescription().getName() + "] Disabled!");
    }

    public TweakCartConfig getConfig(){
        return config;
    }
}