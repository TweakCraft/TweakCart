package com.edoxile.bukkit.tweakcart.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class BlockMapper {
    public static ArrayList<Block> mapBlocks(Block startBlock, int sw, Material type) {
        ArrayList<Block> list = new ArrayList<Block>();
        for (int dx = -sw; dx <= sw; dx++) {
            for (int dy = -sw; dy <= sw; dy++) {
                for (int dz = -sw; dz <= sw; dz++) {
                    Block keith = startBlock.getRelative(dx, dy, dz);
                    if(keith.getType() == type)
                        list.add(keith);
                }
            }
        }
        return list;
    }
}
