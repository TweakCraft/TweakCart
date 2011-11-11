package com.tweakcraft.parsers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;

import com.tweakcart.model.CartType;
import com.tweakcart.model.Condition;
import com.tweakcart.model.Direction;
import com.tweakcart.model.IntersectionRoute;

public class RouteSignParser extends BasicParser{
    public static void parseIntersectionSign(Sign s, StorageMinecart cart,
            Direction horizontalDirection) {
        if(s.getLine(0).equalsIgnoreCase("elevator")){
            
        }
        
    }
    //testje
    /**
     * TODO: implement this function. It automatically teleports the cart to the correct location and in the correct direction (this is why cart is an argument).
     */
    
    public static List<IntersectionRoute> parseRouteSign(Sign sign, Direction direction, Minecart cart) {
        //BASIC syntax: [Direction from <N,S,E,W>];[Type of cart <S,M,P>],[Full/Empty <F,E>];[Direction to go <N,S,E,W>]
        //Example: S;S;F;N: makes storagecarts from south that are full go north :)      
        List<IntersectionRoute> routelist = new ArrayList<IntersectionRoute>();
        String[] lines = sign.getLines();
        
        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            
            String[] routes = line.split(":");
            for(String route: routes){
                Direction from;
                Direction to;
                CartType ct;
                List<Condition> conditions = new ArrayList<Condition>();
                
                String[] fromSplit = route.split("\\+");
                
                if(fromSplit.length != 2) continue;
                
                from = Direction.getDirection(fromSplit[0].charAt(0));
                
                String[] cartSplit = fromSplit[1].split(";");
                
                if(cartSplit.length != 2) continue;
                
                ct = CartType.getCartType(cartSplit[0].charAt(0));
                
                
            }
        }
        
        return routelist;
    }
}
