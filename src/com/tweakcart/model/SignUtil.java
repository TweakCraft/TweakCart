package com.tweakcart.model;

import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntIntIterator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */

public class SignUtil {

    public static final Direction getLineItemDirection(String str) {
        Direction direction = Direction.SELF;
        if (str.length() > 2 && str.charAt(1) == '+') {
            char dir = str.charAt(0);
            if (dir == 'n') direction = Direction.NORTH;
            else if (dir == 's') direction = Direction.SOUTH;
            else if (dir == 'e') direction = Direction.EAST;
            else if (dir == 'w') direction = Direction.WEST;
        }
        return direction;
    }

    public static final int getKey(ItemStack item) {
    	return getKey(item.getTypeId(), item.getDurability());
    }
    
    public static final int getKey(int itemid, short itemdata) {
        return ((itemdata << 16) | (itemid & 0xFFFF));
    }

    public static final int getId(int key) {
        return (key & 0xFFFF);
    }

    public static final short getData(int key) {
        return (short) ((key >> 16) & 0xFFFF);
    }

    /**
     * Returns the list of material for each item name or id found in the given array of strings, or an empty array if there was no item names or ids.
     * If the item name is a partial name, it will match the name with the shortest number of letter.
     * If there is a '!' next to a id or item name it will be removed from the list
     * Ex ("reds" will match "redstone" (the wire item) and not redstone ore.
     *
     * @return materials found, or an empty array
     */
    public static final TIntIntHashMap getItemStringListToMaterial(String list, Direction facing) {
        TIntIntHashMap items = new TIntIntHashMap();
            String str = removeBrackets(list.toLowerCase());
            str = str.trim();
            if (str.equals("")) {
                return null;
            }

            //Check the given direction and intended direction from the sign
            Direction direction = getLineItemDirection(str);
            if (direction != Direction.SELF) {
                str = str.substring(2, str.length()); // remove the direction for further parsing.
            }
            if (facing != null && direction != facing && direction != Direction.SELF) {
                return null;
            }

            //short circuit if it's everything
            if (str.contains("all items")) {
                items.put(Material.AIR.getId(), -1);
            }

            String[] keys = str.split(":");
            for (int i = 0; i < keys.length; i++) {
                String part = keys[i].trim();
                TIntIntHashMap parsedset = parsePart(part);


                if (parsedset == null || parsedset.size() < 1)
                    continue;

                TIntIntIterator iterator = parsedset.iterator();
                while (iterator.hasNext()) {
                    items.put(iterator.key(), iterator.value());
                }
            }

        return items;
    }


    protected enum TYPE {
        AMOUNT("@"),
        REMOVE("!"),
        RANGE("-"),
        DATA(";"),
        NONE("");

        private final String tag;

        TYPE(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        @Override
        public String toString() {
            return tag;
        }

        public static TYPE getType(String part) {
            if (part.contains(RANGE.getTag())) // Range is parsed first Always!
                return RANGE;
            if (part.contains(REMOVE.getTag())) // since this 1 doesn't need special priority handling
                return REMOVE;

            return (part.lastIndexOf(DATA.getTag()) > part.lastIndexOf(AMOUNT.getTag()) ? DATA : (part.contains(AMOUNT.getTag()) ? AMOUNT : NONE));
        }
    }

    /**
     * Please don't change this order as it might screw up certain priorities!
     *
     * @param part
     * @return
     */
    private static final TIntIntHashMap parsePart(String part) {
        try {
            switch (TYPE.getType(part)) {
                case RANGE:
                    return parseRange(part);
                case DATA:
                    return parseData(part);
                case REMOVE:
                    return parseNegative(part);
                case AMOUNT:
                    return parseAmount(part);
                default:
                    return parseNormal(part);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static final TIntIntHashMap parseAmount(String part) {
        String[] split = part.split(TYPE.AMOUNT.getTag());
        TIntIntHashMap items = parsePart(split[0]);

        int amount = Integer.parseInt(split[1]);
        amount = (amount > 0) ? amount : -1;

        TIntIntIterator iterator = items.iterator();
        while (iterator.hasNext()) {
            items.put(iterator.key(), amount);
        }

        return items;
    }

    private static final TIntIntHashMap parseNegative(String part) {
        part = part.replace(TYPE.REMOVE.getTag(), "");
        TIntIntHashMap items = parsePart(part);

        TIntIntIterator iterator = items.iterator();
        while (iterator.hasNext()) {
            items.put(iterator.key(), -2);
        }

        return items;
    }

    /**
     * TODO: this function still needs to be tested.
     *
     * @param part
     * @return
     */
    private static final TIntIntHashMap parseRange(String part) {
        String[] split = part.split(TYPE.RANGE.getTag());

        TIntIntHashMap start = parsePart(split[0]);
        TIntIntHashMap end = parsePart(split[1]);
        TIntIntHashMap items = new TIntIntHashMap();

        int startitem, enditem, startamount, endamount;

        TIntIntIterator iterator = start.iterator();

        if (iterator.hasNext()) {
            startitem = iterator.key();
            startamount = iterator.value();
        } else {
            return items;
        }

        iterator = end.iterator();
        if (iterator.hasNext()) {
            enditem = iterator.key();
            endamount = iterator.value();
        } else {
            return items;
        }


        for (int item = getId(startitem); item <= getId(enditem); item++) {
            for (Material m : Material.values()) {
                MaterialData data = new MaterialData(m);

                if (items.containsKey(getKey(data.getItemTypeId(), (short) -1))) {
                    continue;
                }

                if (data.getItemTypeId() == getId(startitem)) {
                    if ((getData(startitem) < 0)) {
                        items.put(getKey(item, (short) -1), (startamount > 0 ? startamount : endamount));
                    } else {
                        if (data.getData() >= getData(startitem)) {
                            items.put(getKey(item, data.getData()), (startamount > 0 ? startamount : endamount));
                        }
                    }
                } else if (data.getItemTypeId() == getId(enditem)) {
                    if ((getData(enditem) < 0)) {
                        items.put(getKey(item, (short) -1), -1);
                    } else {
                        if (data.getData() <= getData(enditem)) {
                            items.put(getKey(item, data.getData()), (startamount > 0 ? startamount : endamount));
                        }
                    }
                } else {
                    items.put(getKey(data.getItemTypeId(), data.getData()), (startamount > 0 ? startamount : endamount));
                }
            }
        }
        return items;
    }

    private static final TIntIntHashMap parseData(String part) {
        String[] split = part.split(TYPE.DATA.getTag());
        TIntIntHashMap items = parsePart(split[0]);
        short data = Short.parseShort(split[1]);

        TIntIntIterator iterator = items.iterator();
        while (iterator.hasNext()) {
            items.put(getKey(iterator.key(), data), iterator.value());
            items.remove(iterator.key());
        }

        return items;
    }

    private static final TIntIntHashMap parseNormal(String part) {
        TIntIntHashMap item = new TIntIntHashMap();
        try {
            int materialId = Integer.parseInt(part);
            if (Material.getMaterial(materialId) != null) {
                item.put(getKey(materialId, (short) -1), -1);
            }
            return item;
        } catch (NumberFormatException exception) {
        }
        return item;
    }

    private static final String removeBrackets(String s) {
        String str = "";
        boolean isStation = false;
        if (s.toLowerCase().contains("st-")) {
            //see if we need to make sure [ ] in the middle do not get removed.
            //Also lower case because the same sign and line will come in as "st-" AND "St-"
            isStation = true;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ']' || c == '[') {
                if (!isStation) continue;
                // we have a non-station string so remove all brackets
                // we have a station string if we got this far
                if (i == 0 && c == '[') continue; //only strip beginning [ bracket.
                if (i == s.length() - 1 && c == ']' && s.charAt(0) == '[')
                    continue;
                //only strip ending bracket if a beginning bracket exists
            }
            str += c;
        }

        return str;
    }
}
