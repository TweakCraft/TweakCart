package com.tweakcart.model.parsers;

import com.tweakcart.model.Direction;
import com.tweakcart.model.IntMap;

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

    public static IntMap parseItems(String line) {
        IntMap intMap = new IntMap();
        boolean isFlipped = false;

        isFlipped = (line.charAt(0) == ItemCharacter.FLIP.getCharacter().charAt(0));
        if (isFlipped)
            line = line.substring(1);

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
                            continue;
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
                            continue;
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

    public static int[] parseIntersection(){
        //TODO: implement, think about return type
        return null;
    }
}
