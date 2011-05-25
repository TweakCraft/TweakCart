package com.edoxile.bukkit.tweakcart.Utils;

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultXMLDocumentHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class BlockMapper {
    public static ArrayList<Block> mapPoweredRails(Block startBlock) {
        ArrayList<Block> list = new ArrayList<Block>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0)
                        continue;
                    Block keith = startBlock.getRelative(dx, dy, dz);
                    if (keith.getType() == Material.POWERED_RAIL)
                        list.add(keith);
                }
            }
        }
        return list;
    }

    public static ArrayList<BlockState> mapBlockStates(Block startBlock, BlockFace direction, Material type) {
        ArrayList<BlockState> bagList = new ArrayList<BlockState>();
        switch (direction) {
            case NORTH:
            case SOUTH: {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz += 2) {
                        if (startBlock.getRelative(0, dy, dz).getType() == type) {
                            bagList.add(startBlock.getRelative(0, dy, dz).getState());
                        }
                    }
                }
                if (startBlock.getRelative(0, 1, 0).getType() == type) {
                    bagList.add(startBlock.getRelative(0, 1, 0).getState());
                }
            }
            break;
            case EAST:
            case WEST: {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx += 2) {
                        if (startBlock.getRelative(dx, dy, 0).getType() == type) {
                            bagList.add(startBlock.getRelative(dx, dy, 0).getState());
                        }
                    }
                }
                if (startBlock.getRelative(0, 1, 0).getType() == type) {
                    bagList.add(startBlock.getRelative(0, 1, 0).getState());
                }
            }
            break;
        }
        return bagList;
    }
}
