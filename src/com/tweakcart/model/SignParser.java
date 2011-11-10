package com.tweakcart.model;

import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

import java.util.ArrayList;
import java.util.List;

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


    public static String removeBrackets(String line) {
        if (line.length() > 2 && line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
            return line.substring(1, line.length() - 1);
        } else {
            return line;
        }
    }

    public static Action parseAction(String line, Direction direction) {
        if (line == null || line.equals("")) {
            return Action.NULL;
        }

        char firstChar = line.charAt(0);

        if (line.length() > 0) {
            if (Character.isDigit(firstChar) || firstChar == '!') {
                return Action.ITEM;
            } else if (line.charAt(1) == '+') {
                switch (firstChar) {
                    case 'n':
                    case 's':
                    case 'w':
                    case 'e':
                    case 'N':
                    case 'S':
                    case 'W':
                    case 'E':
                        if (line.length() > 2) {
                            if (line.charAt(2) == 'a' && line.equals(Character.toString(line.charAt(0)) + "+all items")) {
                                return Action.ALL;
                            } else {
                                return Action.ITEM;
                            }
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
            map.setDirection(getDirection(line.charAt(0)));
            line = line.substring(2);
        }
        else{
            map.setDirection(Direction.SELF); 
        }
        if (line.charAt(0) == '!') {
            isNegate = true;
            line = line.substring(1);
        }

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

        return map;
    }

    private static int[] checkIDData(String line) {
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
        return (getDirection(c) == d || getDirection(c) == Direction.SELF); //Yay, 10 keer zo kort ofzo :)
    }

    /**
     * TODO: implement this function. It automatically teleports the cart to the correct location and in the correct direction (this is why cart is an argument).
     */
    public static List<IntersectionRoute> parseRouteSign(Sign sign, Direction direction, Minecart cart) {
        //BASIC syntax: [Direction from <N,S,E,W>];[Type of cart <S,M,P>],[Full/Empty <F,E>];[Direction to go <N,S,E,W>]
        //Example: S;S;F;N: makes storagecarts from south that are full go north :)      
        List<IntersectionRoute> routelist = new ArrayList<IntersectionRoute>();
        String[] lines = sign.getLines();
        
        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            
            String[] routes = line.split(":");
            for(String route: routes){
                Direction from;
                Direction to;
                CartType ct;
                List<Condition> conditions = new ArrayList<Condition>();
                
                String[] fromSplit = route.split("\\+");
                
                if(fromSplit.length != 2) continue;
                
                from = getDirection(fromSplit[0].charAt(0));
                
                String[] cartSplit = fromSplit[1].split(";");
                
                if(cartSplit.length != 2) continue;
                
                ct = CartType.getCartType(cartSplit[0].charAt(0));
                
                
            }
        }
        
        return routelist;
    }
    
    /**
     * Langste methode EVURR!
     * @param c
     * @return
     */
    
    @Deprecated
    private static boolean getFull(char c) {        
        return c == 'f';
    }

    @Deprecated
    private static boolean checkCartType(String type, Minecart cart) {
        char carttypechar = type.toLowerCase().charAt(0);
        switch(carttypechar){
        case 's':
            if(cart instanceof StorageMinecart) return true;
            break;
        case 'p':
            if(cart instanceof PoweredMinecart) return true;
            break;
        case 'm':
            if(cart instanceof Minecart) return true;
            break;        
        }
        return false;
    }

    private static Direction getDirection(char c){
        switch(c){
        case 'N':
        case 'n':
            return Direction.NORTH;
        case 'S':
        case 's':
            return Direction.SOUTH;
        case 'E':
        case 'e':
            return Direction.EAST;
        case 'W':
        case 'w':
            return Direction.WEST;
        default:
            return Direction.SELF;

        }
    }
    
    public static void parseIntersectionSign(Sign s, StorageMinecart cart,
            Direction horizontalDirection) {
        if(s.getLine(0).equalsIgnoreCase("elevator")){
            
        }
        
    }

    public static List<IntMap> parseItemSign(Sign sign, Direction direction) {
        Action oldAction = Action.NULL;

        List<IntMap> returndata = new ArrayList<IntMap>();
        IntMap map;
        for (String line : sign.getLines()) {
            line = removeBrackets(line);
            line = line.toLowerCase();
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