package com.edoxile.bukkit.tweakcart;

import com.edoxile.bukkit.tweakcart.Listeners.TweakCartBlockListener;
import com.edoxile.bukkit.tweakcart.Listeners.TweakCartVehicleListener;
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

    public void onEnable() {
        vehicleListener = new TweakCartVehicleListener(this);
        blockListener = new TweakCartBlockListener(this);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.REDSTONE_CHANGE, blockListener, Event.Priority.Normal, this);
        log.info("[" + getDescription().getName() + "] Enabled! version:" + getDescription().getVersion());
    }

    public void onDisable() {
        log.info("[" + getDescription().getName() + "] Disabled!");
    }
}
