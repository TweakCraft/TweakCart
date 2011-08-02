package com.tweakcart;

import com.tweakcart.listeners.TweakCartBlockListener;
import com.tweakcart.listeners.TweakCartVehicleListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

<<<<<<< HEAD
import com.tweakcart.listeners.TweakCartBlockListener;
import com.tweakcart.listeners.TweakCartVehicleListener;
=======
import java.util.logging.Logger;
>>>>>>> 3c045928f79d01fede0255447d6759f4392fbacc

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCart extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private TweakCartVehicleListener vehicleListener = null;
    private TweakCartBlockListener blockListener = null;

    public void onEnable() {
        // Initialising variables
        vehicleListener = new TweakCartVehicleListener(this);
        blockListener = new TweakCartBlockListener(this);

        // Load plugin-manager and register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DISPENSE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, vehicleListener, Event.Priority.Normal, this);

        // Loaded!
        log.info("[" + getDescription().getName() + "] Enabled! version:" + getDescription().getVersion());
    }

    public void onDisable() {
        log.info("[" + getDescription().getName() + "] Disabled!");
    }
<<<<<<< HEAD

=======
>>>>>>> 3c045928f79d01fede0255447d6759f4392fbacc
}
