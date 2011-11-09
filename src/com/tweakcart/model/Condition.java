package com.tweakcart.model;

/**
 * A way to simply add more conditions, without altering everything
 * @author lennart
 *
 */
public enum Condition {
    Full('&'),
    Empty('*'),
    NONE(' ');
    
    private char condition;

    private Condition(char condition){
        this.condition = condition;
    }
    
    private char getConditionChar() {
        // TODO Auto-generated method stub
        return condition;
    }
    
    //Public parts
    
    public static Condition getCondition(char parsedchar){
        for(Condition condition: Condition.values()){
            if(parsedchar == condition.getConditionChar()){
                return condition;
            }
        }
        return NONE;
    }


}
