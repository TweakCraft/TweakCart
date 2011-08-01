package com.tweakcart.model;

import com.tweakcart.TweakCart;


/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
@Deprecated
public class TweakCartConfig {
    private final TweakCart plugin;
    public int switchBlock = 45;

    /**
     * TODO: load config from config file, put it in all the variables.
     */
    public TweakCartConfig(TweakCart plugin) {
        this.plugin = plugin;
    }
}
