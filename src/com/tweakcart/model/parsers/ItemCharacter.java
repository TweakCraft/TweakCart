package com.tweakcart.model.parsers;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public enum ItemCharacter {
    FLIP("!"),
    DELIMITER(":"),
    AMOUNT("@"),
    RANGE("-"),
    DATA_VALUE(";"),
    ID("");

    private String character;
    private static HashMap<String, ItemCharacter> itemParserCharMap = new HashMap<String, ItemCharacter>();

    static{
        for(ItemCharacter ip : ItemCharacter.values()){
            itemParserCharMap.put(ip.getCharacter(), ip);
        }
    }

    private ItemCharacter(String c){
        character = c;
    }

    public String getCharacter(){
        return character;
    }

    public ItemCharacter getItemParseCharacter(String character){
        return itemParserCharMap.get(character);
    }
}
