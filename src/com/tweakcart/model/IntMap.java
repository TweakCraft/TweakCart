package com.tweakcart.model;

import org.bukkit.Bukkit;
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
        if (data.length != 537) {
            mapData = new int[537];
        } else {
            mapData = data;
        }
    }

    public static boolean isAllowedMaterial(int id, byte data) {
        int intLocation = IntMap.getIntIndex(id, data);
        return intLocation != -1;
    }

    public int getInt(int id, byte data) {
        int intLocation = IntMap.getIntIndex(id, data);

        if (intLocation == -1) {
            return 0;
        }

        return mapData[intLocation];
    }

    public int getInt(Material m, byte data) {
        int intLocation = IntMap.getIntIndex(m, data);

        if (intLocation == -1) {
            return 0;
        }

        return mapData[intLocation];
    }

    public boolean setInt(Material m, byte data, int value) {
        return setInt(m.getId(), data, value);
    }

    public boolean setInt(int id, byte data, int value) {
        int intLocation = IntMap.getIntIndex(id, data);
        Bukkit.getServer().broadcastMessage("yay, ik ben hier, en nu?");
        if (intLocation == -1) {
            return false;
        }

        mapData[intLocation] = value;
        Bukkit.getServer().broadcastMessage("heb een waarde in de intmap gezet: " + mapData[intLocation] + "!");

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
                        if (data < 3)
                            return materialSize + (int) data;
                        else
                            return -1;
                    case LOG:
                        if (data < 3)
                            return materialSize + (int) data + 2;
                        else
                            return -1;
                    case LEAVES:
                        if (data < 3)
                            return materialSize + (int) data + 4;
                        else
                            return -1;
                    case WOOL:
                        if (data < 15)
                            return materialSize + (int) data + 18;
                        else
                            return -1;
                    case INK_SACK:
                        if (data < 15)
                            return materialSize + (int) data + 32;
                        else
                            return -1;
                    default:
                        return m.ordinal();
                }
        }
    }

    private boolean hasDataValue(int id) {
        switch (id) {
            case 6:
            case 17:
            case 18:
            case 35:
            case 351:
                return true;
            default:
                return false;
        }
    }

    public void combine(IntMap otherMap) {
        for (int index = 0; index < mapData.length; index++) {
            if (otherMap.mapData[index] != 0)
                mapData[index] = otherMap.mapData[index];
        }
    }

    /**
     * Sets a range of the IntMap
     * prevents multiple calls to IntMap and back
     */
    public boolean setRange(int startId, byte startdata, int endId, byte enddata, int value) {
        int startIndex, endIndex;
        if (startdata < 0 || enddata < 0)
            return false;
        if (startdata > 0 && !hasDataValue(startId))
            startdata = 0;
        if (enddata > 0 && !hasDataValue(endId))
            enddata = 0;
        if (startdata == 0 && enddata == 0) {
            if (startId < endId) {
                startIndex = getIntIndex(startId, startdata);
                endIndex = getIntIndex(endId, startdata);

                if (startIndex == -1 || endIndex == -1)
                    return false;

                for (int i = startIndex; i <= endIndex; i++) {
                    mapData[i] = value;
                }
            }
        } else {
            if (startId < endId) {
                while (true) {
                    int index = getIntIndex(startId, startdata);
                    if (index == -1)
                        break;
                    mapData[index] = value;
                    startdata++;
                }

                while (true) {
                    int index = getIntIndex(endId, enddata);
                    if (index == -1)
                        break;
                    mapData[index] = value;
                    enddata--;
                }

                do {
                    startId++;
                } while (getIntIndex(startId, (byte) 0) != -1);

                do {
                    endId--;
                } while (getIntIndex(endId, (byte) 0) != -1);

                for (int id = startId; id <= endId; id++) {
                    int index = getIntIndex(id, (byte) 0);
                    if (index == -1)
                        continue;
                    mapData[index] = value;
                    if (hasDataValue(id)) {
                        byte data = 1;
                        while (true) {
                            index = getIntIndex(id, data);
                            if (index == -1)
                                break;
                            mapData[index] = value;
                            data++;
                        }
                    }
                }
            }
        }
        return true;
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