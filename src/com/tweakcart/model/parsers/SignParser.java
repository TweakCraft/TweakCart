package com.tweakcart.model.parsers;

import com.tweakcart.model.Direction;
import com.tweakcart.model.IntMap;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class SignParser {

    public static int parseDirection(String line, Direction cartDirection) {
        if (line.charAt(1) == DirectionCharacter.DELIMITER.getCharacter().charAt(0)) {
            DirectionCharacter directionCharacter = DirectionCharacter.getDirectionCharacter(line.substring(0, 1));
            if (directionCharacter.getDirection().equals(cartDirection)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public static IntMap parseItems(String line, Direction cartDirection) {
        IntMap intMap = new IntMap();
        boolean isFlipped = false;

        isFlipped = (line.charAt(0) == ItemCharacter.FLIP.getCharacter().charAt(0));
        if (isFlipped) {
            line = line.substring(1);
        }

        int direction = parseDirection(line, cartDirection);
        if (direction == 0) {
            return null;
        } else if (direction == 1) {
            line = line.substring(2);
        }

        String[] splitted = line.split(ItemCharacter.DELIMITER.getCharacter());

        for (String part : splitted) {
            int amount = Integer.MAX_VALUE;
            byte data[] = new byte[2];
            int id[] = new int[2];
            boolean hasRange = false;
            String[] apple = {};

            for (ItemCharacter itemCharacter : ItemCharacter.values()) {
                switch (itemCharacter) {
                    case AMOUNT:
                        apple = part.split(itemCharacter.getCharacter());
                        if (apple.length == 1) {
                            break;
                        } else if (apple.length == 2) {
                            amount = Integer.parseInt(apple[1]);
                            line = line.substring(0, line.length() - apple[1].length());
                        } else {
                            //Syntax error
                            return null;
                        }
                        break;
                    case RANGE:
                        apple = part.split(itemCharacter.getCharacter());
                        if (apple.length == 1) {
                            break;
                        } else if (apple.length == 2) {
                            hasRange = true;
                        } else {
                            return null;
                        }
                        break;
                    case DATA_VALUE:
                        String[] banana = apple[0].split(itemCharacter.getCharacter());
                        if (banana.length == 1) {
                            data[0] = -1;
                        } else if (banana.length == 2) {
                            data[0] = Byte.parseByte(banana[1]);
                            apple[0] = apple[0].substring(0, apple[0].length() - banana[1].length());
                        } else {
                            return null;
                        }
                        if (hasRange) {
                            banana = apple[1].split(itemCharacter.getCharacter());
                            if (banana.length == 1) {
                                data[1] = -1;
                            } else if (banana.length == 2) {
                                data[1] = Byte.parseByte(banana[1]);
                                apple[1] = apple[1].substring(0, apple[1].length() - banana[1].length());
                            } else {
                                return null;
                            }
                        }
                        break;
                    case ID:
                        id[0] = Integer.parseInt(apple[0]);
                        if (hasRange) {
                            id[1] = Integer.parseInt(apple[1]);
                        }
                        return null;
                    default:
                        break;
                }
            }
            if (hasRange) {
                intMap.setRange(id[0], data[0], id[1], data[1], amount);
            } else {
                intMap.setInt(id[0], data[0], amount);
            }
        }

        return intMap;
    }

    public static Direction parseIntersection(String line, Minecart cart, Direction cartDirection, boolean isFull) {
        //Returns the direction to go to. null = parse error, BlockFace.SELF is incompatible direction.
        int direction = parseDirection(line, cartDirection);
        Direction remainder;

        if (direction == -1) {
            return null;
        } else if (direction == 0) {
            return Direction.SELF;
        } else {
            //Chop off direction
            line = line.substring(2);
            //Chop off the remainder statement
            String[] apple = line.split(IntersectionCharacter.REMAINDER_DELIMITER.getCharacter());
            if (apple.length == 1) {
                remainder = cartDirection;
            } else if (apple.length == 2) {
                remainder = DirectionCharacter.getDirection(apple[1]);
                if (remainder == null) {
                    return null;
                }
                line = line.substring(0, line.length() - 1);
            } else {
                return null;
            }

            apple = line.split(IntersectionCharacter.CART_DELIMITER.getCharacter());
            for (String mango : apple) {
                String[] banana = mango.split(IntersectionCharacter.DIRECTION_DELIMITER.getCharacter());
                if (apple.length == 2) {
                    remainder = DirectionCharacter.getDirection(banana[1]);
                    if (remainder == null) {
                        return null;
                    }
                } else {
                    //Syntax error (no to-direction given)
                    return null;
                }

                //0 = no type found, 1 = empty, 2 = full;
                int type;
                boolean cartFound = false;

                for (int i = 0; i < mango.length(); i++) {
                    IntersectionCharacter ic = IntersectionCharacter.getIntersectionCharacter(mango.substring(i, i + 1));
                    switch (ic) {
                        case MINECART:
                            //TODO: checks etc
                            if (!(cart instanceof StorageMinecart) && !(cart instanceof PoweredMinecart)) {
                                cartFound = true;
                                break;
                            } else {
                                break;
                            }
                        case STORAGE_CART:
                            if(cart instanceof StorageMinecart){
                                cartFound = true;
                                break;
                            } else {
                                break;
                            }
                        case POWERED_CART:
                            if(cart instanceof PoweredMinecart){
                                cartFound = true;
                                break;
                            } else {
                                break;
                            }
                        case EMPTY_CART:
                            //TODO: think of implementation;
                            break;
                        case FULL_CART:
                            //TODO: think of implementation;
                            break;
                        //TODO: put more delimiters in here;
                        default:
                            return null;
                    }
                }
            }
            //Remainder
            return remainder;
        }
    }
}
