package com.example.battleship;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Board playerBoard = null;
    private ArrayList<Ship> fleet = new ArrayList<>();
    //Getter

    public Board getPlayerBoard() {
        return playerBoard;
    }

    //Create player
    Player() {
        playerBoard=new Board();
        addShip(new Ship(2,"Minesweeper"));
        addShip(new Ship(3,"Submarine"));
        addShip(new Ship(4,"Battleship"));
        addShip(new Ship(5,"Aircraft"));
    }
    //add, create board, ship
    public void setBoard(Board newBoard){
        this.playerBoard=newBoard;
    }
    private void addShip(Ship shipToAdd) {
        for (Ship ship : fleet) {
            //don't add if the ship is already in fleet,
            if (ship == shipToAdd) {
                return;
            }
        }
        fleet.add(shipToAdd);
    }

    //get shoot
    public void getShot(Position shotPlace)
    {
        this.playerBoard.shoot(shotPlace);
    }
    //checking win-lose condition
    public boolean isAllSunk()
    {
        return this.playerBoard.isAllSunk();
    }
}
