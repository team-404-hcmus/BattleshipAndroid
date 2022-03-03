package com.example.battleship;
//Main game controller

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
}
