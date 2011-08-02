package com.tweakcart.util;

import com.google.common.collect.MapMaker;
import com.tweakcart.model.IntMap;
import com.tweakcart.model.SignLocation;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Deprecated
public class SoftSignMap {
    ConcurrentMap<SignLocation, List<IntMap>> signMap;

    public SoftSignMap() {
        signMap = new MapMaker().concurrencyLevel(4).softKeys().makeMap();
    }

    public void put(SignLocation s, List<IntMap> l) {
        signMap.put(s, l);
    }

    public List<IntMap> get(SignLocation l) {
        return signMap.get(l);
    }
}
