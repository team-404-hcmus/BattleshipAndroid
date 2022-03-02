package com.example.battleship;

import java.util.Random;

public class RandomBot implements Bot{
    static final String strategyName = "RANDOM STRATEGY";

    public String getBotName(){
        return strategyName;
    }

    //pick random move
    public Position pickMove(Board board) {
        if (board == null || board.isOver()) {
            return null;
        }
        Random rand = new Random();
        int boardSize = board.getSize();

        Position hitPos = null;
        //check if pos is hit or not
        while (hitPos == null || hitPos.isHit()){
            //Get a random position on board
            hitPos = board.getPosAt(rand.nextInt(boardSize), rand.nextInt(boardSize));
        }

        return hitPos;
    }


}
