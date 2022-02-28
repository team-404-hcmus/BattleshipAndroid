package com.example.battleship;

public class Board {
    private int size;
    private Position[][] grid = null;
    private int placesShot;
    private boolean isOver;

    public Board(int size) {
        this.size = size;
        this.grid = new Position[size][size];
        createBoard();
    }

    //Initializing all position of board
    private void createBoard(){
        for(int y = 0; y < this.grid.length; y++){
            for(int x = 0; x < this.grid[0].length; x++){
                this.grid[y][x] = new Position(x, y);
            }
        }
    }
}
