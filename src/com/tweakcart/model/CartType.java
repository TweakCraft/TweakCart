package com.tweakcart.model;

/**
 * A way to keep track of vehicle types
 * @author lennart
 */
public enum CartType {
    StorageMinecart('s'),
    Minecart('m'),
    PoweredMinecart('p'),
    NONE('n');
    
    private char type;
    
    private CartType(char type){
        this.type = type;
    }
    
    private char getTypeChar(){
        return type;
    }
    
    public static CartType getCartType(char parsechar){
        for(CartType cart: CartType.values()){
            if(cart.getTypeChar() == parsechar){
                return cart;
            }
        }
        return NONE;
    }
    
}
