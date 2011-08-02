package com.tweakcart.model;

import org.bukkit.block.Sign;

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

    public static HashMap<Action, IntMap> parseItemSign(Sign sign, Direction direction) {
        Action oldAction = Action.NULL;

        HashMap<Action, IntMap> returnData = new HashMap<Action, IntMap>();
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
                                IntMap parsed = buildIntMap(line);
                                if (parsed != null) {
                                    if (returnData.containsKey(oldAction)) {
                                        map = returnData.get(oldAction);
                                        map.combine(parsed);
                                        returnData.put(oldAction, map);
                                    } else {
                                        returnData.put(oldAction, parsed);
                                    }
                                }
                                break;
                        }
                        break;
                }
            }
        }

        return returnData;
    }
}