package com.tweakcart.model;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author TheSec, windwarrior, Edoxile
 */
public class SignParser {
    public enum Action {
        NULL((byte) 0),
        COLLECT((byte) 1),
        DEPOSIT((byte) 2),
        ITEM((byte) 3);

        private byte id;
        private static final Action[] actions = new Action[6];

        private Action(byte id) {
            this.id = id;
        }

        static {
            for (Action act : values()) {
                actions[act.getId()] = act;
            }
        }

        public short getId() {
            return id;
        }

        public static Action fromId(short id) {
            return actions[id];
        }
    }

    private static SignParser _Instance;
    private static final Logger log = Logger.getLogger("Minecraft");

    public static SignParser getInstance() {
        if (_Instance == null) {
            _Instance = new SignParser();
        }

        return _Instance;
    }

    private SignParser() {
        //Private constructor because of singleton.
    }

    public static Action parseAction(String line) {
        if (line.length() > 0) {
            if (Character.isDigit(line.charAt(0)) || line.charAt(0) == '[' || line.charAt(0) == '!') {
                return Action.ITEM;
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
                default:
                    return Action.NULL;
            }
        } else {
            return Action.NULL;
        }
    }

    //TODO: ByteMap vullen.
    public static IntMap buildIntMap(String line, Minecart cart) {
        //Parse next line items ?
        log.info("ik gaat maar eens bouwen " + line + ";");

        String temp = "";
        IntMap map = new IntMap();
        boolean isNegate = false;
        for (int b = 0; b < line.length(); b++) {
            char c = line.charAt(b);

            if (Character.isDigit(c)) {
                temp += c;
                continue;
            }

            //First letter can be a direction, because it isn't a digit.
            if (b == 0) {
                //Check if the second Char is a + with indicates that the first is a direction
                if (line.length() >= 2 && line.charAt(1) == '+') //lazy evaluation is your friend
                {
                    //Get cart current direction
                    Direction d = Direction.getHorizontalDirection(cart.getVelocity());

                    //Check if the cart has the desired direction, if not continue to the next line.
                    switch (c) {
                        case 'n':
                            if (d != Direction.NORTH) {
                                return null;
                            }
                            break;
                        case 's':
                            if (d != Direction.SOUTH) {
                                return null;
                            }
                            break;
                        case 'e':
                            if (d != Direction.EAST) {
                                return null;
                            }
                            break;
                        case 'w':
                            if (d != Direction.WEST) {
                                return null;
                            }
                            break;
                    }

                    b = 1;
                    continue;
                }
            }

            switch (c) {
                case ' ':
                case ':':
                    //hier moet dus wat gebeuren :)
                    //ik ga er eerst even vanuit dat er niet meerdere operaties tegelijk kunnen gebeuren
                    //temp zou er in pseudoregexnotatie zo uitzien: [[!]? \d* [lambda|;|@|-] \d* :])
                    //combinaties als [14;1-14;3] kunnen dus niet


                    String[] tempsplit = temp.split("-");
                    //Oke nu zou het dus een range kunnen zijn
                    Bukkit.getServer().broadcastMessage("Searching for a range");
<<<<<<< HEAD
                    if(tempsplit.length >= 2 && tempsplit.length % 2 == 0){
                    	
                    	try{
                    		int start = Integer.parseInt(tempsplit[0]);
                    		int end = Integer.parseInt(tempsplit[1]);
                    		Bukkit.getServer().broadcastMessage("er is een range van: " + start + " tot " + end + " " + isNegate);
                    		map.setRange(start, (byte) 0, start, (byte) 0, 0, isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return null;
                    	}
=======
                    if (tempsplit.length >= 2 && tempsplit.length % 2 == 0) {

                        try {
                            int start = Integer.parseInt(tempsplit[0]);
                            int end = Integer.parseInt(tempsplit[1]);
                            Bukkit.getServer().broadcastMessage("er is een range van: " + start + " tot " + end + " " + isNegate);
                            map.setRange(start, (byte) 0, start, (byte) 0, 0);
                            break;
                        } catch (NumberFormatException e) {
                            log.severe("Er gaat was mis");
                            Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                            return null;
                        }
>>>>>>> 624cda65ba32f0aaa085a2bf3096f8d49f6750b2
                    }


                    tempsplit = temp.split(";");
                    Bukkit.getServer().broadcastMessage("Searching for a data value");
<<<<<<< HEAD
                    if(tempsplit.length >= 2){
                    	try{
                    		int id = Integer.parseInt(tempsplit[0]);
                    		byte datavalue = Byte.parseByte(tempsplit[1]); 
                    		Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " en value " + datavalue + " " + isNegate);
                    		map.setInt(id, datavalue, 0, isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return null;
                    	}
=======
                    if (tempsplit.length >= 2) {
                        try {
                            int id = Integer.parseInt(tempsplit[0]);
                            byte datavalue = Byte.parseByte(tempsplit[1]);
                            Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " en value " + datavalue + " " + isNegate);
                            map.setInt(id, datavalue, 0);
                            break;
                        } catch (NumberFormatException e) {
                            log.severe("Er gaat was mis");
                            Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                            return null;
                        }
>>>>>>> 624cda65ba32f0aaa085a2bf3096f8d49f6750b2
                    }

                    tempsplit = temp.split("@");
                    Bukkit.getServer().broadcastMessage("Searching for a amount");
<<<<<<< HEAD
                    if(tempsplit.length >= 2){
                    	try{
                    		int id = Integer.parseInt(tempsplit[0]);
                    		int amount = Integer.parseInt(tempsplit[1]);
                    		Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " amount " + amount + " " + isNegate);
                    		map.setInt(id, (byte) 0, amount, isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return null;
                    	}
                    }
                    Bukkit.getServer().broadcastMessage("Searching for numberic statements");
                    try{
                    	int id = Integer.parseInt(temp);
                    	Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " " + isNegate);

                        
                    }catch(NumberFormatException e){
                		log.severe("Er gaat was mis");
                		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                		return null;
                	}
                    
=======
                    if (tempsplit.length >= 2) {
                        try {
                            int id = Integer.parseInt(tempsplit[0]);
                            int amount = Integer.parseInt(tempsplit[1]);
                            Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " amount " + amount + " " + isNegate);
                            map.setInt(id, (byte) 0, amount);
                            break;
                        } catch (NumberFormatException e) {
                            log.severe("Er gaat was mis");
                            Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                            return null;
                        }
                    }
                    Bukkit.getServer().broadcastMessage("Searching for not statements");
                    try {
                        int id = Integer.parseInt(temp);
                        Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " " + isNegate);


                    } catch (NumberFormatException e) {
                        log.severe("Er gaat was mis");
                        Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                        return null;
                    }

>>>>>>> 624cda65ba32f0aaa085a2bf3096f8d49f6750b2
                    isNegate = false;
                    temp = "";
                    break;

                case ';':
                    temp += ";";
                    break;
                case '@':
                    temp += "@";
                    break;
                case '-':
                    temp += "-";
                    break;
                case '!':
                    isNegate = true;
                    break;
                default:
                    break;
            }

        }

        return map;
    }

    public static void parseSign(Sign sign, Minecart cart) {
        Bukkit.getServer().broadcastMessage("HALLO");
        Action oldAction = Action.NULL;

        IntMap map = new IntMap();

        for (String line : sign.getLines()) {

            Action newAction = SignParser.parseAction(line);
            Action retrieveAction = Action.NULL;
            Bukkit.getServer().broadcastMessage(newAction.toString());
            if (newAction == Action.NULL) {
                continue;
            } else if (newAction != Action.ITEM) {
                oldAction = newAction;
                continue;
            } else if (oldAction != Action.NULL) {
                switch (oldAction) {
                    case DEPOSIT:
                        retrieveAction = Action.DEPOSIT;
                    case COLLECT:
                        retrieveAction = retrieveAction == Action.NULL ? Action.COLLECT : Action.DEPOSIT;
                        Bukkit.getServer().broadcastMessage("Action: " + oldAction.toString());
                        Bukkit.getServer().broadcastMessage("  -> " + line);
                        IntMap parsed = buildIntMap(line, cart);
                        if (parsed != null) {
                            // Mooi het is gelukt! Maps combinen dan maar!
                            map.combine(parsed);
                        }

                        //Fetching items
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


    }

    public static boolean checkStorageCart(Minecart cart) {
        return (cart instanceof StorageMinecart);
    }

    public static boolean checkCart(Minecart cart) {
        return !((cart instanceof StorageMinecart) || (cart instanceof PoweredMinecart));
    }
}