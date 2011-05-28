package com.tweakcart.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class BlockMapper {
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
                for (int dy = -2; dy <= 1; dy += 3) {
                    if (startBlock.getRelative(0, 1, 0).getType() == type) {
                        bagList.add(startBlock.getRelative(0, 1, 0).getState());
                    }
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
                for (int dy = -2; dy <= 1; dy += 3) {
                    if (startBlock.getRelative(0, 1, 0).getType() == type) {
                        bagList.add(startBlock.getRelative(0, 1, 0).getState());
                    }
                }
            }
            break;
        }
        return bagList;
    }
}
