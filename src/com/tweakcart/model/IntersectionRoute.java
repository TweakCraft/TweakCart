package com.tweakcart.model;

import java.util.List;

public class IntersectionRoute {
    private Direction from;
    private Direction to;
    private CartType ct;
    private List<Condition> conditions;

    public IntersectionRoute(Direction from, Direction to, CartType ct, List<Condition> conditions){
        this.from = from;
        this.to = to;
        this.ct = ct;
        this.conditions = conditions;
    }
    
    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }

    public Direction getTo() {
        return to;
    }

    public void setTo(Direction to) {
        this.to = to;
    }

    public CartType getCt() {
        return ct;
    }

    public void setCt(CartType ct) {
        this.ct = ct;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

  
}
