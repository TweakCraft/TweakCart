package com.edoxile.bukkit.tweakcart.Utils;

import com.edoxile.bukkit.tweakcart.TweakCart;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class TweakCartConfig {
    private TweakCart plugin = null;
    public int switchBlock = 45;

    /**
     * TODO: load config from config file, put it in all the variables.
     */
    public TweakCartConfig(TweakCart instance){
        plugin = instance;
    }
}
