package com.example.battleship;

public class Position {
    // x,y coordinate
    private int x;
    private int y;
    //is shot or not
    private boolean  isHit;
    //have ship or not
    private Ship ship_container=null;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        isHit=false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isHit() {
        return isHit;
    }
    public Ship getShip() {
        return this.ship_container;
    }

    //placed ship on this position
    public void setShip(Ship ship) {
        this.ship_container = ship;
    }
    // mark position is hit
    public void setHit() {
        this.isHit = true;
    }

    //Returns true if place has a ship
    boolean hasShip() {
        return this.ship_container != null;
    }

    //Checks if place contains the ship which want to check
    boolean hasShip(Ship shipToCheck) {
        return ship_container == shipToCheck;
    }
}
