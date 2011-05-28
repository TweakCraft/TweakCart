package com.edoxile.bukkit.tweakcart;

import com.edoxile.bukkit.tweakcart.Listeners.TweakCartBlockListener;
import com.edoxile.bukkit.tweakcart.Listeners.TweakCartVehicleListener;
import com.edoxile.bukkit.tweakcart.Utils.TweakCartConfig;
import org.bukkit.event.Event;
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
    private TweakCartConfig config = null;

    public void onEnable() {
        // Initialising variables
        config = new TweakCartConfig(this);
        vehicleListener = new TweakCartVehicleListener(this);
        blockListener = new TweakCartBlockListener(this);

        // Load plugin-manager and register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.REDSTONE_CHANGE, blockListener, Event.Priority.Normal, this);

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
