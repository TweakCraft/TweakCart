package com.tweakcart.model;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class StackInventory implements Inventory {

    private ItemStack[] items;
    private int size;

    public StackInventory(ItemStack[] data){
        items = data;
        size = data.length;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return "StackInventory";
    }

    @Deprecated
    public ItemStack getItem(int i) {
        return null;
    }

    @Deprecated
    public void setItem(int i, ItemStack itemStack) {
    }

    @Deprecated
    public HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) {
        return null;
    }

    @Deprecated
    public HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) {
        return null;
    }

    public ItemStack[] getContents() {
        return items;
    }

    public void setContents(ItemStack[] itemStacks) {
        items = itemStacks;
    }

    @Deprecated
    public boolean contains(int i) {
        return false;
    }

    @Deprecated
    public boolean contains(Material material) {
        return false;
    }

    @Deprecated
    public boolean contains(ItemStack itemStack) {
        return false;
    }

    @Deprecated
    public boolean contains(int i, int i1) {
        return false;
    }

    @Deprecated
    public boolean contains(Material material, int i) {
        return false;
    }

    @Deprecated
    public boolean contains(ItemStack itemStack, int i) {
        return false;
    }

    @Deprecated
    public HashMap<Integer, ? extends ItemStack> all(int i) {
        return null;
    }

    @Deprecated
    public HashMap<Integer, ? extends ItemStack> all(Material material) {
        return null;
    }

    @Deprecated
    public HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
        return null;
    }

    @Deprecated
    public int first(int i) {
        return -1;
    }

    @Deprecated
    public int first(Material material) {
        return -1;
    }

    @Deprecated
    public int first(ItemStack itemStack) {
        return -1;
    }

    @Deprecated
    public int firstEmpty() {
        return -1;
    }

    @Deprecated
    public void remove(int i) {
    }

    @Deprecated
    public void remove(Material material) {
    }

    @Deprecated
    public void remove(ItemStack itemStack) {
    }

    @Deprecated
    public void clear(int i) {
    }

    @Deprecated
    public void clear() {
    }
}
