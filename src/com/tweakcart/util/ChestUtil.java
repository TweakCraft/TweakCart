package com.tweakcart.util;

import com.tweakcart.model.IntMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
 *
 * @author Edoxile
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
                        i1--;

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
    
    public static IntMap moveItems(Inventory iFrom, Inventory iTo, IntMap map) {
        ItemStack[] from = iFrom.getContents();
        ItemStack[] to = iTo.getContents();
        IntMap settings = map.clone();
        //Bukkit.getServer().broadcastMessage("bladieblabla");
        main:for(int index = 0; index < from.length; index++ ) { 
            Bukkit.getServer().broadcastMessage("Main for loop");
            if(from[index] == null) continue;
            byte data = from[index].getDurability() > Byte.MAX_VALUE ? 0 : IntMap.isAllowedMaterial(from[index].getTypeId(), (byte) from[index].getDurability()) ? (byte) from[index].getDurability() : 0;

            if(settings.getInt(from[index].getTypeId(), data) <= 0) continue;
            ItemStack item = from[index];
            /*
             * First we try to append an existing stack.
             */
            append:for(int indexto = 0; indexto < to.length; indexto++ ) {
                if(item.getAmount() <= 0) break;
                if(to[indexto] == null) continue append;
                Bukkit.getServer().broadcastMessage("hai");
                if(to[indexto].getTypeId() != item.getTypeId() || to[indexto].getDurability() != item.getDurability()) continue;
                if(to[indexto].getAmount() >= 64) continue append;

                int maxamount = settings.getInt(from[index].getTypeId(), data);
                if(maxamount <= 0) break;
                ItemStack temp = to[indexto];
                int stackspace = 64 - temp.getAmount();
                int moveamount = (item.getAmount() >= stackspace && maxamount >= stackspace ? stackspace :
                                    item.getAmount() < stackspace && maxamount >= stackspace ? item.getAmount() :
                                        maxamount < stackspace && item.getAmount() >= stackspace ? maxamount :
                                            maxamount > item.getAmount() ? item.getAmount() : maxamount);
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "MoveAmount: " + moveamount);
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "ItemAmount: " + item.getAmount());
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "TempAmount: " + temp.getAmount());
                item.setAmount(item.getAmount() - moveamount);
                temp.setAmount(temp.getAmount() + moveamount);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "ItemAmount: " + item.getAmount());
                Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "TempAmount: " + temp.getAmount());
                if(maxamount != Integer.MAX_VALUE) {
                    settings.setInt(item.getTypeId(), data, maxamount-moveamount);
                }
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Setting: " + settings.getInt(item.getTypeId(), data));
                
            }
            
            if(item.getAmount() <= 0) {
                from[index] = null;
                item.setAmount(0);
                continue;
            }
            
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Setting: " + settings.getInt(item.getTypeId(), data));
            /*
             * Put item in an empty slot
             */
            empty:for(int indexto = 0; indexto < to.length; indexto++ ) {
                Bukkit.getServer().broadcastMessage(ChatColor.DARK_BLUE + "Ik zit in de tweede loop, met index: " + indexto);               
                if(to[indexto] != null) continue empty;
                int maxamount = settings.getInt(item.getTypeId(), data);
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "maxamount: " + maxamount);
                if(maxamount <= 0) continue empty; //FIX, PROFIT
                if(item.getAmount() > maxamount) {
                    item.setAmount(item.getAmount() - maxamount);
                    to[indexto] = new ItemStack(item.getTypeId(), maxamount, data);
                    settings.setInt(item.getTypeId(), data, 0);
                    continue main; // We can't put more of this item type so we skip to the next item.
                } else {
                    to[indexto] = item;
                    from[index] = null;
                    if(maxamount != Integer.MAX_VALUE){
                        maxamount -= item.getAmount();
                        settings.setInt(item.getTypeId(), data, maxamount);
                    }
                    break;
                }
            }
        }
        iFrom.setContents(from);
        iTo.setContents(to);
        return settings;
    }
//    public static void moveItems(Inventory iFrom, Inventory iTo, IntMap through) {
//        ItemStack[] from = iFrom.getContents();
//        ItemStack[] to = iTo.getContents();
//        int i1, i2;
//        for (i1 = 0; i1 < from.length; i1++) {
//            if (from[i1] == null) {
//                //Dat betekent dus dat er geen item in dat slot zit :)
//                continue;
//            }
//            int mapAmount = through.getInt(from[i1].getType(), (byte) from[i1].getDurability());
//            mapAmount =  (mapAmount > 64 && mapAmount < Integer.MAX_VALUE)? 64 : mapAmount; //64 stacksizes :)
//            int startAmount = from[i1].getAmount(); //De hoeveelheid die in de cart of chest zit
//            if (mapAmount == 0 || mapAmount == Integer.MIN_VALUE) {
//                continue;
//            }
//
//            int amountToMove = (mapAmount == Integer.MAX_VALUE ? startAmount : mapAmount); //de hoeveelheid die te moven is
//            from[i1].setAmount(from[i1].getAmount() - amountToMove + 1);
//            boolean hasPutSomethingIn = true;
//            for (i2 = 0; i2 < to.length; i2++) {
//                if (to[i2] == null) {
//                    to[i2] = from[i1].clone();
//                    to[i2].setAmount(amountToMove);
//                    amountToMove = 0;
//                    break;
//                } else if (to[i2].getTypeId() == from[i1].getTypeId() && to[i2].getDurability() == from[i1].getDurability() && to[i2].getAmount() < 64) {
//                    if (amountToMove + to[i2].getAmount() > 64) {
//                        //hier gaat iets mis
//                        
//                        amountToMove += to[i2].getAmount() - 64;
//                        to[i2].setAmount(64);
//                        
//                    } else {
//                        to[i2].setAmount(amountToMove + to[i2].getAmount());
//                        amountToMove = 0;
//                        
//                    }
//                    break;
//                }
//                
//                if(i2 == to.length -1){
//                    //OEEH, we konden dus niets terug plaatsen
//                    hasPutSomethingIn = false;
//                    Bukkit.getServer().broadcastMessage("vol is vol");
//                }
//                
//                
//            }
//            int amountToPlaceBack = from[i1].getAmount() + amountToMove - 1;
//            Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + amountToPlaceBack);
//            from[i1].setAmount(amountToPlaceBack);
//            //through.setInt(from[i1].getType(), (byte) from[i1].getDurability(), amountToMove);
//            //de bovenstaande regel slaat werkelijk waar nergens over
//            
//            if((amountToPlaceBack) > 0 && hasPutSomethingIn){
//                i1--;
//            }
//        }
//        iTo.setContents(to);
//        iFrom.setContents(from);
//    }

    public static List<Chest> getChestsAroundBlock(Block block, int sw) {
        int nsw = -sw;
        List<Chest> chestList = new ArrayList<Chest>();
        for (int dx = nsw; dx <= sw; dx++) {
            for (int dy = nsw; dy <= sw; dy++) {
                for (int dz = nsw; dz <= sw; dz++) {
                    if (block.getRelative(dx, dy, dz).getTypeId() == Material.CHEST.getId()){
                        chestList.add((Chest) block.getRelative(dx, dy, dz).getState());
                        chestList = getChestsAdjacent(chestList, block, dx, dy, dz);
                    }
                }
            }
        }
        
        return chestList;
    }
    
    public static List<Chest> getChestsAdjacent(List<Chest> chestList, Block block, int x, int y, int z){

        if((x == 1 || x == -1) && (z == 1 || z == -1)){
            if(block.getRelative(x+x, y, z).getTypeId() == Material.CHEST.getId()){
                chestList.add((Chest) block.getRelative(x+x, y, z).getState());
                //Bukkit.getServer().broadcastMessage("ik heb een kist gevonden op X: " + x + " Y: " + y + " Z: " + z);
            }
            else if(block.getRelative(x, y, z+z).getTypeId() == Material.CHEST.getId()){
                chestList.add((Chest) block.getRelative(x, y, z+z).getState());
                //Bukkit.getServer().broadcastMessage("ik heb een kist gevonden op X: " + x + " Y: " + y + " Z: " + z);
            }
        }else if(x == 1 || x == -1){
            if(block.getRelative(x+x, y, z).getTypeId() == Material.CHEST.getId()){
                chestList.add((Chest) block.getRelative(x+x, y, z).getState());
                //Bukkit.getServer().broadcastMessage("ik heb een kist gevonden op X: " + x + " Y: " + y + " Z: " + z);
            }
        }else if(z == 1 || z == -1){
            if(block.getRelative(x, y, z+z).getTypeId() == Material.CHEST.getId()){
                chestList.add((Chest) block.getRelative(x, y, z+z).getState());
                //Bukkit.getServer().broadcastMessage("ik heb een kist gevonden op X: " + x + " Y: " + y + " Z: " + z);
            }
        }
        
        return chestList;
    }
}
