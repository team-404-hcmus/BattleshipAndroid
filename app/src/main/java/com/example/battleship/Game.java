package com.example.battleship;
//Main game controller

import android.util.Log;

public class Game {

    private Player activePlayer;
    private Player Player;
    private Player opponentPlayer;

    boolean playerTurn=true;

    //Getter
    public Player getPlayer() {
        return Player;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }
    //Setup
    Game(){
        this.Player  = new Player();
        this.opponentPlayer = new Player();
        this.activePlayer = this.Player;
    }
    Game(Board playerBoard){

        Player  = new Player();
        opponentPlayer = new Player();
        this.activePlayer = this.Player;
    }
    //Getter
    public Player getActivePlayer() {
        return activePlayer;
    }
    public Player getInactivePlayer(){
        if(this.activePlayer == this.Player){
            return this.opponentPlayer;
        }
        return this.Player;
    }
    //shooting
    boolean hitPos(int x, int y){
        //Player who shooting (active) will shoot the waiting player's board
        //* Người bắn sẽ bắn cái bảng của người đang chờ, nên nhớ lấy của InactivePlayer
        Board board = getInactivePlayer().getPlayerBoard();
        return board.shoot(board.getPosAt(x,y));
    }

    // change turn
    void changeTurn(){
        if(this.activePlayer == this.Player){

            activePlayer = this.opponentPlayer;
        }
        else{

            activePlayer = this.Player;
        }
    }

    //Check win lose
    public int isWin()
    {
        if(this.Player.isAllSunk())//Player Lose
        {
            Log.d("d","lose");
            return 1;
        }
        else if(this.opponentPlayer.isAllSunk())//Player win
        {
            Log.d("d","win");
            return 2;
        }
        else
        {
            return 0;
        }
    }
}
