package com.tweakcart.listeners;

import org.bukkit.entity.Minecart;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import com.tweakcart.TweakCart;
import com.tweakcart.model.TweakCartConfig;
import com.tweakcart.util.CartUtil;
import com.tweakcart.util.MathUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartVehicleListener extends VehicleListener {
    private static TweakCart plugin = null;
    private TweakCartConfig config = null;

    public TweakCartVehicleListener(TweakCart instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
        	Minecart cart = (Minecart)event.getVehicle();
        	if(CartUtil.stoppedSlowCart(cart)) return;
        	if(MathUtil.isSameBlock(event.getFrom(), event.getTo())) return;
        	
        }
    }
}
