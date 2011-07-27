package com.tweakcart.util;


import com.tweakcart.model.IntMap;
import org.bukkit.block.ContainerBlock;
import org.bukkit.inventory.ItemStack;

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

    public static ItemStack[] putItems(ItemStack[] from, ContainerBlock containerBlock, IntMap through) {
        ItemStack[] to = containerBlock.getInventory().getContents();
        for (int i1 = 0; i1 < from.length; i1++) {
            if (from[i1] == null)
                continue;
            int mapAmount = through.amount(from[i1].getType(), (byte) from[i1].getDurability());
            int startAmount = from[i1].getAmount();
            if (mapAmount == 0)
                continue;

            //MAX_VALUE && other values?
            if (mapAmount == Integer.MAX_VALUE || from[i1].getAmount() < mapAmount) {
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
                if (mapAmount != Integer.MAX_VALUE) {
                    if (from[i1] == null) {
                        through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), mapAmount - startAmount);
                    } else {
                        through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), mapAmount - startAmount + from[i1].getAmount());
                    }
                }
            } else {
                //When is the map 0?
                from[i1].setAmount(from[i1].getAmount() - mapAmount);
                for (int i2 = 0; i2 < to.length; i2++) {
                    if (to[i2] == null) {
                        to[i2] = from[i1];
                        to[i2].setAmount(mapAmount);
                        mapAmount = 0;
                        break;
                    } else if (to[i2].getTypeId() == from[i1].getTypeId() && to[i2].getDurability() == from[i1].getDurability() && to[i2].getAmount() < 64) {
                        if (mapAmount + to[i2].getAmount() > 64) {
                            to[i2].setAmount(64);
                            mapAmount = (mapAmount + to[i2].getAmount()) - 64;
                        } else {
                            to[i2].setAmount(mapAmount + to[i2].getAmount());
                            mapAmount = 0;
                        }
                        break;
                    }
                }
                if (mapAmount != 0) {
                    from[i1].setAmount(from[i1].getAmount() + mapAmount);
                }
                through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), mapAmount);
            }
        }
        containerBlock.getInventory().setContents(to);
        return from;
    }
}
