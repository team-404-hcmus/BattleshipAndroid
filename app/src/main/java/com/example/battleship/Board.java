package com.example.battleship;

import java.util.ArrayList;

public class Board {
    private int size;
    private Position[][] board = null;
    private int placesShot=0;

    public Board() {
        this.size = 10;
        this.board = new Position[size][size];
        createBoard();
    }

    public Board(int size) {
        this.size = size;
        this.board = new Position[size][size];
        createBoard();
    }
    //Getter
    public int getSize() {
        return size;
    }
    public Position[][] getBoard() {
        return board;
    }

    public int getPlacesShot() {
        return placesShot;
    }

    /**Initializing all position of board **/
    private void createBoard(){
        for(int y = 0; y < this.board.length; y++){
            for(int x = 0; x < this.board[0].length; x++){
                this.board[y][x] = new Position(x, y);
            }
        }
    }
    /** Places the ship Functions **/
    //Put ship at first pos[x,y], create an <pos> array to store at Ship's positions
    boolean placeShip(Ship ship, int x, int y, boolean direction){
        if(ship == null
                || direction && isOutOfBounds(x+ship.getSize(),y)
                || !direction && isOutOfBounds(x,y+ship.getSize())){
            return false;
        }
        //Get an array of position that placed ship based on ship's size
        ArrayList<Position> shipPos = new ArrayList<Position>();
        Position pos;
        for(int i = 0; i < ship.getSize(); i++){
            int pX=x,pY=y;
            //set placed x,y by direction
            if(direction){
               pX+=i;
            }
            else {
                pY+=i;
            }
            pos=getPosAt(pX, pY);
            if(pos == null || pos.hasShip()) {
                return false;
            }
            shipPos.add(pos);
        }
        for(Position shipContainer: shipPos){
            shipContainer.setShip(ship);
        }
        //update ship information
        ship.setDirection(direction);
        ship.setPosition(shipPos);
        return true;
    }

    /**Shooting  Function **/
    boolean shoot(Position placeToHit){
        if(placeToHit == null){
            return false;
        }
        //If place hasn't been hit before, then hits the place.
        if(!placeToHit.isHit()){
            this.placesShot++;
            placeToHit.setHit();
            return true;
        }
        return false;
    }
    /** Various Functions **/
    //Get position at [x,y]
    Position getPosAt(int x, int y)
    {
        if(this.board == null || board[y][x] == null){
            return null;
        }
        return board[y][x];
    }
    //Check valid input coordinate x,y
    boolean isOutOfBounds(int x, int y){
        return x >= this.size || y >= this.size || x < 0 || y < 0;
    }
    //Return true if all ships are hit
    boolean isAllSunk(){
        for(int y = 0; y < this.size; y++){
            for(int x = 0; x < this.size; x++){
                Position pos = board[y][x];
                if(pos.hasShip() && !pos.isHit()){
                    return false;
                }
            }
        }
        return true;
    }

    // is board over
    public boolean isOver() {
        if(isAllSunk())
            return true;
        return false;
    }
}
