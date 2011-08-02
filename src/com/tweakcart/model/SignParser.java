package com.tweakcart.model;

import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

import java.util.HashMap;
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

    public static Action parseAction(String line) {
        if (line == null) {
            return Action.NULL;
        }

        line = line.toLowerCase();

        if (line.length() > 0) {
            if (Character.isDigit(line.charAt(0))) {
                return Action.ITEM;
            } else {

                switch (line.charAt(0)) {
                    case '[':
                    case '!':
                    case 'n':
                    case 's':
                    case 'w':
                    case 'e':
                    case 'N':
                    case 'S':
                    case 'W':
                    case 'E':
                        return Action.ITEM;
                    default:
                }
            }


            switch (line.charAt(0)) {
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
        } else {
            return Action.NULL;
        }
    }

    public static IntMap buildIntMap(String line, Direction d) {
        IntMap map = new IntMap();
        boolean isNegate = false;

        if (checkDirection(line, d)) {
            if (line.length() >= 2 && line.charAt(1) == '+') {
                line = line.substring(2);
            }
            if (line.charAt(0) == '!') {
                isNegate = true;
                line = line.substring(1);
            }

            String[] commands = line.split(":");

            for (String command : commands) {
                int value = 0;

                String[] splitline = command.split("@");

                if (splitline.length == 2) {
                    try {
                        value = Integer.parseInt(splitline[1]);
                        value = (value < 1 ? Integer.MAX_VALUE : value);
                        command = splitline[0];
                    } catch (NumberFormatException e) {
                        return null;
                    }
                } else if (splitline.length != 1) {
                    return null;
                }

                splitline = command.split("-");
                if (splitline.length == 2) {
                    int[] startPair = checkIDData(splitline[0]);
                    int[] endPair = checkIDData(splitline[1]);
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
                } else if (splitline.length == 1) {
                    int[] pair = checkIDData(splitline[0]);
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
                        //Ah er is dus iets mis gegaan bij het parsen
                        return null;
                    }

                } else {
                    //De gebruiker heeft meerdere '-' tekens aangegeven, en dat kan niet
                    return null;
                }
            }


        }

        return map;
    }

    private static int[] checkIDData(String line) {
        int[] result = new int[2];
        String[] linesplit = line.split(";");
        if (linesplit.length == 2) {
            try {
                result[0] = Integer.parseInt(linesplit[0]);
                result[1] = Integer.parseInt(linesplit[1]);
            } catch (NumberFormatException e) {

            }
        } else if (linesplit.length == 1) {
            try {
                result[0] = Integer.parseInt(linesplit[0]);
                result[1] = -1;
            } catch (NumberFormatException e) {

            }
        }

        return result;

    }

    private static boolean checkDirection(String line, Direction d) {
        if (line.length() >= 2 && line.charAt(1) == '+') {
            char c = line.charAt(0);
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
                    break;
            }

        }
        return true;

    }

    public static HashMap<Action, IntMap> parseSign(Sign sign, Minecart cart, Direction direction) {

        Action oldAction = Action.NULL;

        HashMap<Action, IntMap> returnData = new HashMap<Action, IntMap>();
        IntMap map;

        for (String line : sign.getLines()) {
            line = removeBrackets(line);
            Action newAction = SignParser.parseAction(line);
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
                                if (returnData.containsKey(oldAction)) {
                                    map = returnData.get(oldAction);
                                    map.fillAll();
                                    returnData.put(oldAction, map);
                                } else {
                                    map = new IntMap();
                                    map.fillAll();
                                    returnData.put(oldAction, map);
                                }
                                break;
                            case ITEM:
                                IntMap parsed = buildIntMap(line, direction);

                                if (parsed != null) {
                                    // Mooi het is gelukt! Maps combinen dan maar!
                                    if (returnData.containsKey(oldAction)) {
                                        map = returnData.get(oldAction);
                                        map.combine(parsed);
                                        returnData.put(oldAction, map);
                                    } else {
                                        if (parsed != null)
                                            returnData.put(oldAction, parsed);
                                    }
                                }
                                break;
                            default:
                                //WTH?
                                break;
                        }
                        break;
                    //case ELEVATE?
                    default:
                        //Weird stuff is going on!
                        break;
                }
            } else {
                //Oldaction == Null and newAction is Item, so don't do anything.
                continue;
            }
        }

        //Yay, we hebben een IntMap
        //For simplicity sake, gaan we er vanuit dat het of collect of deposit is, oke :)

        return returnData;
    }


    public static boolean checkStorageCart(Minecart cart) {
        return (cart instanceof StorageMinecart);
    }

    public static boolean checkCart(Minecart cart) {
        return !((cart instanceof StorageMinecart) || (cart instanceof PoweredMinecart));
    }
}