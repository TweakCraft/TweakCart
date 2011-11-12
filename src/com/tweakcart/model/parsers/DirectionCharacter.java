package com.tweakcart.model.parsers;

import com.tweakcart.model.Direction;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public enum DirectionCharacter {
    NORTH("n", Direction.NORTH),
    EAST("e", Direction.EAST),
    SOUTH("s", Direction.SOUTH),
    WEST("w", Direction.WEST);

    private String character;
    private Direction direction;
    private static HashMap<String, Direction> directionMap = new HashMap<String, Direction>();
    private static HashMap<Direction, DirectionCharacter> directionCharacterMap = new HashMap<Direction, DirectionCharacter>();

    static {
        for (DirectionCharacter d : DirectionCharacter.values()) {
            directionMap.put(d.getCharacter(), d.getDirection());
            directionCharacterMap.put(d.getDirection(), d);
        }
    }

    private DirectionCharacter(String c, Direction d) {
        character = c;
        direction = d;
    }

    public String getCharacter() {
        return character;
    }

    public Direction getDirection() {
        return direction;
    }

    public static Direction getDirection(String c) {
        return directionMap.get(c);
    }

    public static DirectionCharacter getDirectionCharacter(Direction d) {
        return directionCharacterMap.get(d);
    }

    public static DirectionCharacter getDirectionCharacter(String c) {
        Direction d = directionMap.get(c);
        if (d != null) {
            return directionCharacterMap.get(d);
        } else {
            return null;
        }
    }
}
