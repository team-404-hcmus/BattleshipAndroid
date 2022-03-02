package com.example.battleship;
//Main game controller

public class Game {

    private Player Player;
    private Player opponentPlayer;
    private Bot computer;
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
        Player  = new Player();
        opponentPlayer = new Player();
    }

    Game(Board playerBoard, String botType){
        if(botType.equals("Random"))
        {
            computer =new RandomBot();
        }
        Player  = new Player();
        opponentPlayer = new Player();
    }
}
