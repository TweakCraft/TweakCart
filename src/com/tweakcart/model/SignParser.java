package com.tweakcart.model;

import com.tweakcart.util.Bitwise;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Robert FH Groeneveld(rgroeneveld@rsdd.nl)
 * Date: 27-6-11
 * Time: 20:45
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

    public int[] IntMap = null;
    private static SignParser _Instance;
    private static final Logger log = Logger.getLogger("Minecraft");

    public static SignParser getInstance() {
        if (_Instance == null) {
            _Instance = new SignParser();
        }

        return _Instance;
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

//    @Deprecated
//    public static short getFirstAction(String[] lines, Minecart cart) {
//        Action first;
//        short _return;
//
//        for (int a = 0; a < lines.length; a++) {
//            //first = ParseLine(SignParser.removeBracers(lines[a].toLowerCase()), cart);
//            log.info(first.toString());
//            if (first != Action.NULL) {
//                return Bitwise.packBits((short) a, (short) first.getId(), (short) 2);
//            }
//        }
//
//        return 255;
//    }



    //TODO: ByteMap vullen.
    public static boolean buildIntMap(String line, Minecart cart) {
        //Parse next line items ?
        log.info("ik gaat maar eens bouwen " + line + ";");
        String temp = "";
        SignParser.getInstance().IntMap = new int[512];
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
                    if(tempsplit.length >= 2 && tempsplit.length % 2 == 0){
                    	
                    	try{
                    		int start = Integer.parseInt(tempsplit[0]);
                    		int end = Integer.parseInt(tempsplit[1]);
                    		Bukkit.getServer().broadcastMessage("er is een range van: " + start + " tot " + end + " " + isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return false;
                    	}
                    }
                    
                    
                    tempsplit = temp.split(";");
                    Bukkit.getServer().broadcastMessage("Searching for a data value");
                    if(tempsplit.length >= 2){
                    	try{
                    		int id = Integer.parseInt(tempsplit[0]);
                    		byte datavalue = Byte.parseByte(tempsplit[1]); 
                    		Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " en value " + datavalue + " " + isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return false;
                    	}
                    }
                    
                    tempsplit = temp.split("@");
                    Bukkit.getServer().broadcastMessage("Searching for a amount");
                    if(tempsplit.length >= 2){
                    	try{
                    		int id = Integer.parseInt(tempsplit[0]);
                    		int amount = Integer.parseInt(tempsplit[1]); 
                    		Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " amount " + amount + " " + isNegate);
                    		break;
                    	}catch(NumberFormatException e){
                    		log.severe("Er gaat was mis");
                    		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                    		return false;
                    	}
                    }
                    Bukkit.getServer().broadcastMessage("ik ben nu hier terecht gekomen");
                    try{
                    	int id = Integer.parseInt(temp);
                    	Bukkit.getServer().broadcastMessage("er is een item met id: " + id + " " + isNegate);

                        
                    }catch(NumberFormatException e){
                		log.severe("Er gaat was mis");
                		Bukkit.getServer().broadcastMessage("Er gaat wat mis");
                		return false;
                	}
                    
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

        return true;
    }

    //TODO: Hoe gaan we dat oplossen als er zo wel collect als deposit op 1 sign staat.......


    public static void parseSign(Sign sign, Minecart cart) {
    	Bukkit.getServer().broadcastMessage("HALLO");
        Action oldAction = Action.NULL;
        for (String line : sign.getLines()) {
        	
            Action newAction = SignParser.parseAction(line);
            Bukkit.getServer().broadcastMessage(newAction.toString());
            if(newAction == Action.NULL) {
                continue;
            } else if(newAction != Action.ITEM){
                oldAction = newAction;
                continue;
            } else if(oldAction != Action.NULL) {
                switch(oldAction){
                    case DEPOSIT:
                    case COLLECT:
                    	Bukkit.getServer().broadcastMessage("Action: " + oldAction.toString());
                    	Bukkit.getServer().broadcastMessage(" " + line);
                        if(buildIntMap(line, cart)){
                        	// Mooi het is gelukt !
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

//        //TODO: Move this inside the switch above;
//        boolean running = true;
//
//        short result = SignParser.getFirstAction(lines, cart);
//        log.info("" + result);
//        short index = Bitwise.getHighBits(result, (short) 2);
//        Action action = Action.fromId(Bitwise.getLowBits(result, (short) 2));
//
//        System.out.println("First result on index " + index + " Action: " + action.toString());
//
//        short a = (short) (index + 1);
//
//        do {
//            log.info("Entering Do loop");
//            //TODO: Finish buildByteMap
//            if (!(SignParser.buildByteMap(SignParser.removeBracers(lines[a].toLowerCase()), cart))) {
//                //Sign didn't match requirements.
//                //May by log this or not ?
//
//                //Maybe altering the signs content, to attent the user on the error
//                //problem: signs that where not meant for TweakCart
//
//            } else {
//                //TODO: What to do XD ?
//                //ByteMap ready (SignParser.getInstance().ByteMap)
//                //Execute Action ? (Collect/Deposit)
//            }
//
//            do {
//                a++;
//                action = SignParser.parseLine(SignParser.removeBracers(lines[a].toLowerCase()), cart);
//            }
//            while ((action != Action.NULL) || (a != (lines.length - 1)));
//
//            if (a == (lines.length - 1)) {
//                running = false;
//            }
//        }
//        while (running);
    }

    public static boolean CheckStorageCart(Minecart cart) {
        return (cart instanceof StorageMinecart);
    }

    public static boolean CheckCart(Minecart cart) {
        return !((cart instanceof StorageMinecart) || (cart instanceof PoweredMinecart));
    }
    
    public static boolean CheckDirection(Minecart cart){
    	return false;
    }
}