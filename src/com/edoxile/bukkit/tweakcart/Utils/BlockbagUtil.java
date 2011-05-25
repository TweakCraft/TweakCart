package com.edoxile.bukkit.tweakcart.Utils;

import com.edoxile.bukkit.tweakcart.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class BlockbagUtil {
    public static void doAction(Action action, Block railblock, TweakMinecart cart) {
        ArrayList<BlockState> stateList = BlockMapper.mapBlockStates(railblock, cart.getDirection(), ((action == Action.SMELT) ? Material.FURNACE : Material.CHEST));
        if (stateList.isEmpty())
            return;
        HashMap<MaterialData, Integer> blocksToHandle = getBlocksToMove((Chest) railblock.getRelative(BlockFace.DOWN).getState());
        switch (action) {
            case SMELT:
                smelt(blocksToHandle, cart, stateList);
                break;
            case COLLECT:
                collect(blocksToHandle, cart, stateList);
                break;
            case DEPOSIT:
                deposit(blocksToHandle, cart, stateList);
                break;
        }
    }

    private static void smelt(HashMap<MaterialData, Integer> blocksToHandle, TweakMinecart cart, ArrayList<BlockState> containers) {

    }

    private static void collect(HashMap<MaterialData, Integer> blocksToHandle, TweakMinecart cart, ArrayList<BlockState> containers) {

    }

    private static void deposit(HashMap<MaterialData, Integer> blocksToHandle, TweakMinecart cart, ArrayList<BlockState> containers) {

    }

    private static HashMap<MaterialData, Integer> getBlocksToMove(Chest chest) {
        HashMap<MaterialData, Integer> map = new HashMap<MaterialData, Integer>();
        ItemStack[] items = chest.getInventory().getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack stack = items[i];
            if (stack == null)
                continue;
            MaterialData data = (stack.getData() == null) ? new MaterialData(stack.getType()) : stack.getData();
            if (map.containsKey(stack.getTypeId())) {
                map.put(data, map.get(data) + 1);
            } else {
                map.put(data, (stack.getAmount() == 1) ? 64 : stack.getAmount());
            }
        }
        return map;
    }

    public static HashMap<Integer, ItemStack> removeItems(Chest chest, ItemStack itemStack) {
        HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        return leftover;
    }

    public static HashMap<Integer, ItemStack> addItems(Chest chest, ItemStack itemStack) {
        HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
        return leftover;
    }
}
