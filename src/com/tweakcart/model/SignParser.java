package com.tweakcart.model;

import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author TheSec, windwarrior, Edoxile
 */
public class SignParser {
    public enum Action {
        NULL,
        COLLECT,
        DEPOSIT,
        ITEM,
        ALL
    }

    private static final Logger log = Logger.getLogger("Minecraft");

    public static String removeBrackets(String line) {
        if (line.length() > 2 && line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
            return line.substring(1, line.length() - 1);
        } else {
            return line;
        }
    }

    public static Action parseAction(String line, Direction direction) {
        if (line == null) {
            return Action.NULL;
        }

        line = line.toLowerCase();

        char firstChar = Character.toLowerCase(line.charAt(0));

        if (line.length() > 0) {
            if (Character.isDigit(firstChar) || firstChar == '!') {
                return Action.ITEM;
            } else if (line.charAt(1) == '+') {
                switch (firstChar) {
                    case 'n':
                    case 's':
                    case 'w':
                    case 'e':
                        if (checkDirection(firstChar, direction)) {
                            if (line.charAt(2) == 'a' && line.contains("all items")) {
                                return Action.ALL;
                            }
                            return Action.ITEM;
                        } else {
                            return Action.NULL;
                        }
                    default:
                        return Action.NULL;
                }
            } else {
                switch (firstChar) {
                    case 'c':
                        if (line.equals("collect items")) {
                            return Action.COLLECT;
                        }
                        return Action.NULL;
                    case 'd':
                        if (line.equals("deposit items")) {
                            return Action.DEPOSIT;
                        }
                        return Action.NULL;
                    case 'a':
                        if (line.equals("all items")) {
                            return Action.ALL;
                        }
                        return Action.NULL;
                    default:
                        return Action.NULL;
                }
            }
        } else {
            return Action.NULL;
        }
    }

    public static IntMap buildIntMap(String line) {
        IntMap map = new IntMap();
        boolean isNegate = false;
        if (line.length() >= 2 && line.charAt(1) == '+') {
            //TODO Eigenlijk is dit niet net, gezien we deze opvraag net ook al gedaan hebben
            switch(line.charAt(0)){
            case 'n':
                map.setDirection(Direction.NORTH);
                break;
            case 's':
                map.setDirection(Direction.SOUTH);
                break;
            case 'e':
                map.setDirection(Direction.EAST);
                break;
            case 'w':
                map.setDirection(Direction.WEST);
                break;   
            default:
                map.setDirection(Direction.SELF);
                break;   

            }

            line = line.substring(2);
        }
        if (line.charAt(0) == '!') {
            isNegate = true;
            line = line.substring(1);
        }

        String[] commands = line.split(":");

        for (String command : commands) {
            int value = 0;

            String[] splitLine = command.split("@");

            if (splitLine.length == 2) {
                try {
                    value = Integer.parseInt(splitLine[1]);
                    value = (value < 1 ? Integer.MAX_VALUE : value);
                    command = splitLine[0];
                } catch (NumberFormatException e) {
                    return null;
                }
            } else if (splitLine.length != 1) {
                return null;
            }
            splitLine = command.split("-");
            if (splitLine.length == 2) {
                int[] startPair = checkIdData(splitLine[0]);
                int[] endPair = checkIdData(splitLine[1]);
                if (startPair != null && endPair != null) {
                    if (value == 0) {
                        if (isNegate) {
                            value = Integer.MIN_VALUE;
                        } else {
                            value = Integer.MAX_VALUE;
                        }
                    }
                    map.setRange(startPair[0], (byte) (startPair[1] & 0xff), endPair[0], (byte) (endPair[1] & 0xff), value);
                } else {
                    return null;
                }
            } else if (splitLine.length == 1) {
                int[] pair = checkIdData(splitLine[0]);
                if (pair != null) {
                    if (value == 0) {
                        if (isNegate) {
                            value = Integer.MIN_VALUE;
                        } else {
                            value = Integer.MAX_VALUE;
                        }
                    }
                    map.setInt(pair[0], (byte) (pair[1] & 0xff), value);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return map;
    }

    private static int[] checkIdData(String line) {
        int[] result = new int[2];
        String[] splitLine = line.split(";");
        if (splitLine.length == 2) {
            try {
                result[0] = Integer.parseInt(splitLine[0]);
                result[1] = Integer.parseInt(splitLine[1]);
            } catch (NumberFormatException e) {
            }
        } else if (splitLine.length == 1) {
            try {
                result[0] = Integer.parseInt(splitLine[0]);
                result[1] = -1;
            } catch (NumberFormatException e) {
            }
        }

        return result;

    }

    private static boolean checkDirection(char c, Direction d) {
        switch (c) {
            case 'n':
                if (d != Direction.NORTH) {
                    return false;
                }
                break;
            case 's':
                if (d != Direction.SOUTH) {
                    return false;
                }
                break;
            case 'e':
                if (d != Direction.EAST) {
                    return false;
                }
                break;
            case 'w':
                if (d != Direction.WEST) {
                    return false;
                }
        }
        return true;
    }

    /**
     * TODO: implement this function. It automatically teleports the cart to the correct location and in the correct direction (this is why cart is an argument).
     */
    public static boolean parseRouteSign(Sign sign, Direction direction, Minecart cart) {
        return true;
    }

    public static List<IntMap> parseItemSign(Sign sign, Direction direction) {
        Action oldAction = Action.NULL;

        List<IntMap> returndata = new ArrayList<IntMap>();
        IntMap map;

        for (String line : sign.getLines()) {
            line = removeBrackets(line);
            Action newAction = SignParser.parseAction(line, direction);
            if (newAction == Action.NULL) {
                continue;
            } else if (newAction != Action.ITEM && newAction != Action.ALL) {
                oldAction = newAction;
                continue;
            } else if (oldAction != Action.NULL) {
                switch (oldAction) {
                    case DEPOSIT:
                    case COLLECT:
                        switch (newAction) {
                            case ALL:
                                IntMap tempmap = null;
                                int maplocation = -1;
                                boolean running = true;
                                for(int i = 0; i < returndata.size() && running; i++){
                                    IntMap nextmap = returndata.get(i);
                                    if(nextmap.getAction().equals(oldAction)){
                                        tempmap = nextmap;
                                        maplocation = i;
                                        running = false;
                                    }
                                }
                                if (tempmap != null) {
                                    map = tempmap;
                                    map.fillAll();
                                    map.setAction(oldAction);
                                    returndata.set(maplocation, map);
                                } else {
                                    map = new IntMap();
                                    map.fillAll();
                                    map.setAction(oldAction);  
                                    map.setDirection(direction);
                                    returndata.add(map);
                                }
                                break;
                            case ITEM:
                                IntMap parsed = buildIntMap(line);
                                if (parsed != null) {
                                    //OEEEH, daar gebruikte ik control v, dat moet toch netter kunen :)
                                    IntMap tempmap2 = null;
                                    int maplocation2 = -1;
                                    boolean running2 = true;
                                    for(int i = 0; i < returndata.size() && running2; i++){
                                        IntMap nextmap = returndata.get(i);
                                        if(nextmap.getAction().equals(oldAction)){
                                            tempmap2 = nextmap;
                                            maplocation2 = i;
                                            running2 = false;
                                        }
                                    }
                                    
                                    
                                    if (tempmap2 != null) {
                                        map = tempmap2;
                                        map.combine(parsed);
                                        map.setAction(oldAction);
                                        returndata.set(maplocation2, map);
                                    } else {
                                        parsed.setAction(oldAction);
                                        parsed.setDirection(direction);
                                        returndata.add(parsed);
                                    }
                                }
                                break;
                        }
                        break;
                }
            }
        }
        return returndata;
    }
}