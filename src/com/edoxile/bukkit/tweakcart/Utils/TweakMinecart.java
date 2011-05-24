package com.edoxile.bukkit.tweakcart.Utils;

import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakMinecart {
    private Minecart minecart;
    private Vector speed;

    public TweakMinecart(Minecart cart){
        minecart = cart;
        speed = cart.getVelocity();
    }

    public void reverse(){
        Vector velocity = minecart.getVelocity();
        velocity.setX(-velocity.getX());
        velocity.setY(-velocity.getY());
        velocity.setZ(-velocity.getZ());
        minecart.setVelocity(velocity);
    }

    public void stop(){
        minecart.setVelocity(new Vector(0.0D,0.0D,0.0D));
    }

    public void boost(){
        Vector velocity = minecart.getVelocity();
        if (Math.abs(velocity.getX()) > 0.0001) {
            velocity.setX((velocity.getX() > 0 ? minecart.getMaxSpeed() : -minecart.getMaxSpeed()));
        } else if (Math.abs(velocity.getZ()) > 0.0001) {
            velocity.setZ((velocity.getZ() > 0 ? minecart.getMaxSpeed() : -minecart.getMaxSpeed()));
        } else {
            //Don't boost at all
        }
        minecart.setVelocity(velocity);
    }

    public void start(){
        minecart.setVelocity(speed);
    }

    public void reloadVelocity(){
        speed = minecart.getVelocity();
    }

    public Minecart getCart(){
        return minecart;
    }
}
