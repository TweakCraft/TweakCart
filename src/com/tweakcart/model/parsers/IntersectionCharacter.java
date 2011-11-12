package com.tweakcart.model.parsers;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public enum IntersectionCharacter {
    FULL_CART("#"),
    EMPTY_CART("&"),

    MINECART("m"),
    STORAGE_CART("s"),
    POWERED_CART("p"),
    ANY_CART("a");

    private String character;

    private IntersectionCharacter(String c) {
        character = c;
    }

    public String getCharacter(){
        return character;
    }
}
