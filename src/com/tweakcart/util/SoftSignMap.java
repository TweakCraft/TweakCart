package com.tweakcart.util;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import com.tweakcart.model.IntMap;
import com.tweakcart.model.SignLocation;

@Deprecated
public class SoftSignMap{
    ConcurrentMap<SignLocation, IntMap> signmap;
    
    public SoftSignMap(){
        signmap = new MapMaker().concurrencyLevel(4).softKeys().makeMap();
    }

}
