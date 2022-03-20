package com.example.battleship;

import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {
    // type, size of ship
    private String type;
    private int size;
    //ship status: sunk, how many shot
    private boolean isSunk;
    private int totalShot ;
    //ship direction: true= horizontal, false= vertical
    private boolean direction=true;
    //Position that ship is placed
    private ArrayList<Position> position;

    public Ship(int size, String type) {
        this.type = type;
        this.size = size;
        this.isSunk=false;
        this.totalShot=0;
        position=new ArrayList<Position>();
    }
    //Getter
    public String getType() {
        return type;
    }
    public int getSize() {
        return size;
    }
    //Setter
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setPosition(ArrayList<Position> position) {
        this.position = position;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public int getTotalShot() {
        return totalShot;
    }

    public boolean getDirection() {
        return direction;
    }

    public ArrayList<Position> getPosition() {
        return position;
    }

    boolean isPlaced(){
        return !position.isEmpty();
    }

    public void clearPosition()
    {
        this.position=new ArrayList<Position>();
    }
}
