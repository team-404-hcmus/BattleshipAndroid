package com.example.battleship;

import java.util.ArrayList;

public class Ship {
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

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
        this.isSunk=false;
        this.totalShot=0;
        position=new ArrayList<Position>();
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setPosition(ArrayList<Position> position) {
        this.position = position;
    }
}
