package com.tweakcart.model.parsers;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public enum IntersectionCharacter {
    FULL_CART("#"),
    EMPTY_CART("&"),

    MINECART("m"),
    STORAGE_CART("c"),
    POWERED_CART("p"),
    ANY_CART("a"),

    DIRECTION_DELIMITER(";"),
    CART_DELIMITER(":"),
    REMAINDER_DELIMITER("!");

    private static HashMap<String, IntersectionCharacter> intersectionCharacterMap = new HashMap<String, IntersectionCharacter>();

    static {
        for (IntersectionCharacter ic : IntersectionCharacter.values()) {
            intersectionCharacterMap.put(ic.getCharacter(), ic);
        }
    }

    public static IntersectionCharacter getIntersectionCharacter(String character) {
        return intersectionCharacterMap.get(character);
    }

    private String character;

    private IntersectionCharacter(String c) {
        character = c;
    }

    public String getCharacter() {
        return character;
    }
}