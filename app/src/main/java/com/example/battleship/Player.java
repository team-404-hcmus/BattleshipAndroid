package com.example.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
//        addShip(new Ship(2,"Minesweeper"));
//        addShip(new Ship(3,"Submarine"));
//        addShip(new Ship(4,"Battleship"));
//        addShip(new Ship(5,"Aircraft"));
        placeShipRandomly(playerBoard,new Ship(2,"Minesweeper"));
        placeShipRandomly(playerBoard,new Ship(3,"Submarine"));
        placeShipRandomly(playerBoard,new Ship(4,"Battleship"));
        placeShipRandomly(playerBoard,new Ship(5,"Aircraft"));
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

    //place ship randomly (only for debug)
    private void placeShipRandomly(Board board,Ship ship) {
        Random rng = new Random();
        boolean dir = rng.nextBoolean();
        // if dir is true then ship will be placed horizontally

        int[] maxCoordinates = findMaxLocation(board.getSize(), ship.getSize(), dir);

        //Then can't place ship
        if (maxCoordinates == null) {
            return ;
        }

        int maxX = maxCoordinates[0];
        int maxY = maxCoordinates[1];

        boolean placedShip = false;

        while (!placedShip) {

            int x = rng.nextInt(maxX);
            int y = rng.nextInt(maxY);

            //if was able to place ship on board
            if (board.placeShip(ship, x, y, dir)) {
                placedShip = true;
            }
        }

        this.fleet.add(ship);
    }
    private int[] findMaxLocation(int boardSize, int shipSize, boolean dir) {

        int maxX = boardSize;
        int maxY = boardSize;
        if (dir) {
            maxX = boardSize - shipSize;
        } else {
            maxY = boardSize - shipSize;
        }

        if (maxX < 0 || maxY < 0) {
            return null;
        }

        return new int[]{maxX, maxY};
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
