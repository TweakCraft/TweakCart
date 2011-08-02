package com.tweakcart.util;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import com.tweakcart.model.IntMap;
import com.tweakcart.model.SignLocation;

public class SoftSignMap{
    ConcurrentMap<SignLocation, List<IntMap>> signmap;
    
    public SoftSignMap(){
        signmap = new MapMaker().concurrencyLevel(4).softKeys().makeMap();
    }
    
    public ConcurrentMap<SignLocation, List<IntMap>> getSignIntMap(){
        return signmap;
    }
    
    public void addEntry(SignLocation key, List<IntMap> value){
        signmap.put(key, value);
    }
    
    public List<IntMap> getIntMap(SignLocation loc){
        return signmap.get(loc);
    }

}
