package com.tweakcart.util;

import com.tweakcart.model.IntMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ContainerBlock;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */
public class ChestUtil {
    public static final ItemStack[] putItems(ItemStack[] from, ContainerBlock containerBlock) {
        ItemStack[] to = containerBlock.getInventory().getContents();
        for (int i1 = 0; i1 < from.length; i1++) {
            if (from[i1] == null)
                continue;
            for (int i2 = 0; i2 < to.length; i2++) {
                if (to[i2] == null) {
                    to[i2] = from[i1];
                    from[i1] = null;
                    break;
                } else if (to[i2].getTypeId() == from[i1].getTypeId() && to[i2].getDurability() == from[i1].getDurability() && to[i2].getAmount() < 64) {
                    if ((from[i1].getAmount() + to[i2].getAmount()) > 64) {
                        from[i1].setAmount((to[i2].getAmount() + from[i1].getAmount()) - 64);
                        to[i2].setAmount(64);

                    } else {
                        to[i2].setAmount(to[i2].getAmount() + from[i1].getAmount());
                        from[i1] = null;

                    }
                    break;
                }
            }
        }
        containerBlock.getInventory().setContents(to);
        return from;
    }

    public static ItemStack[] putItems(ItemStack from, ContainerBlock containerBlock) {
        ItemStack[] stacks = {from};
        return putItems(stacks, containerBlock);
    }

    public static void moveItems(Inventory iFrom, Inventory iTo, IntMap through) {
        ItemStack[] from = iFrom.getContents();
        ItemStack[] to = iTo.getContents();
        int i1, i2;
        for (i1 = 0; i1 < from.length; i1++) {
            if (from[i1] == null)
                continue;
            int mapAmount = through.getInt(from[i1].getType(), (byte) from[i1].getDurability());
            int startAmount = from[i1].getAmount();
            if (mapAmount == 0 || mapAmount == Integer.MIN_VALUE)
                continue;

            //MAX_VALUE && other values? -> other values = from.amount < mapAmount (aka, the map has more than the current stack)
            if (mapAmount == Integer.MAX_VALUE || from[i1].getAmount() <= mapAmount) {
                for (i2 = 0; i2 < to.length; i2++) {
                    if (to[i2] == null) {
                        to[i2] = from[i1];
                        from[i1] = null;
                        break;
                    } else if (to[i2].getTypeId() == from[i1].getTypeId() && to[i2].getDurability() == from[i1].getDurability() && to[i2].getAmount() < 64) {
                        if ((from[i1].getAmount() + to[i2].getAmount()) > 64) {
                            from[i1].setAmount((to[i2].getAmount() + from[i1].getAmount()) - 64);
                            to[i2].setAmount(64);
                        } else {
                            to[i2].setAmount(to[i2].getAmount() + from[i1].getAmount());
                            from[i1] = null;
                        }
                        break;
                    }
                }
                if (mapAmount != Integer.MAX_VALUE) {
                    if (from[i1] == null) {
                        through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), mapAmount - startAmount);
                    } else {
                        //TODO: Incorrect, fix pl0x
                        through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), mapAmount - startAmount + to[i2].getAmount());
                    }
                }
            } else {
                //When is the map 0? -> map has less than current stack
                int amountToMove = mapAmount;
                from[i1].setAmount(from[i1].getAmount() - amountToMove);
                Bukkit.getServer().broadcastMessage("I'm moving: " + amountToMove + "!");
                for (i2 = 0; i2 < to.length; i2++) {
                    if (to[i2] == null) {
                        to[i2] = from[i1];
                        to[i2].setAmount(amountToMove);
                        amountToMove = 0;
                        break;
                    } else if (to[i2].getTypeId() == from[i1].getTypeId() && to[i2].getDurability() == from[i1].getDurability() && to[i2].getAmount() < 64) {
                        if (amountToMove + to[i2].getAmount() > 64) {
                            amountToMove += to[i2].getAmount() - 64;
                            to[i2].setAmount(64);
                        } else {
                            to[i2].setAmount(amountToMove + to[i2].getAmount());
                            amountToMove = 0;
                        }
                        break;
                    }
                }
                from[i1].setAmount(from[i1].getAmount() + amountToMove);
                through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), amountToMove);
            }
        }
        iTo.setContents(to);
        iFrom.setContents(from);
    }

    public static List<Chest> getChestsAroundBlock(Block block, int sw) {
        int nsw = -sw;
        List<Chest> chestList = new ArrayList<Chest>();
        for (int dx = nsw; dx <= sw; dx++) {
            for (int dy = nsw; dy <= sw; dy++) {
                for (int dz = nsw; dz <= sw; dz++) {
                    if (block.getRelative(dx, dy, dz).getTypeId() == Material.CHEST.getId())
                        chestList.add((Chest) block.getRelative(dx, dy, dz).getState());
                }
            }
        }
        return chestList;
    }
}
