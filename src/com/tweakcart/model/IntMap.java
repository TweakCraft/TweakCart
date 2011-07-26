package com.tweakcart.model;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Created by IntelliJ IDEA.
 *
 * @author TheSec, Edoxile
 */
public class IntMap {
    private static final int materialSize = Material.values().length - 1;
    private int[] mapData;

    public IntMap() {
        mapData = new int[537];
    }

    private IntMap(int[] data) {
        if (data.length != 538) {
            mapData = new int[537];
        } else {
            mapData = data;
        }
    }

    public static boolean allowed(int id, byte data) {
        int intLocation = IntMap.getIntIndex(id, data);
        return intLocation != -1;
    }

    public int amount(int id, byte data) {
        int intLocation = IntMap.getIntIndex(id, data);

        if (intLocation == -1) {
            return 0;
        }

        return mapData[intLocation];
    }

    public boolean setInt(int id, byte data) {
        return setInt(id, data, Integer.MIN_VALUE);
    }

    public boolean setInt(int id, byte data, int value) {
        int intLocation = IntMap.getIntIndex(id, data);
        if (intLocation == -1) {
            return false;
        }
        mapData[intLocation] = value;
        return true;
    }

    private static int getIntIndex(int id, byte data) {
        return getIntIndex(new MaterialData(id, data).getItemType(), data);
    }

    private static int getIntIndex(Material m, byte data) {
        switch (data) {
            case 0:
                //Alle items waarop we .ordinal kunnen doen
                return m.ordinal();
            default:
                //Alle andere gevallen
                switch (m) {
                    case SAPLING:
                        return materialSize + (int)data;
                    case LOG:
                        return materialSize + (int)data + 2;
                    case LEAVES:
                        return materialSize + (int)data + 4;
                    case WOOL:
                        return materialSize + (int)data + 18;
                    case INK_SACK:
                        return materialSize + (int)data + 32;
                    default:
                        return m.ordinal();
                }
        }
    }

    public void combine(IntMap otherMap) {
        for (int index = 0; index <= 537; index++) {
            if (otherMap.mapData[index] != 0)
                mapData[index] = otherMap.mapData[index];
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntMap) {
            IntMap otherMap = (IntMap) other;
            for (int index = 0; index <= 537; index++) {
                if (mapData[index] != otherMap.mapData[index])
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return mapData.hashCode();
    }

    @Override
    public IntMap clone() {
        return new IntMap(mapData.clone());
    }
}
