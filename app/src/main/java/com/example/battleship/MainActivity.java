package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BoardView activeBoardView,waitingBoardView;

    private Game gameController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();

        //Set up game
        gameController=new Game();
        activeBoardView=findViewById(R.id.activeBoardView);
        waitingBoardView=findViewById(R.id.waitingBoardView);
        waitingBoardView.setShowShip(true);
        
        setNewGame(activeBoardView,waitingBoardView,
                gameController.getPlayer().getPlayerBoard(),
                gameController.getOpponentPlayer().getPlayerBoard());


    }
    //Start new game
    private void setNewGame(BoardView activeBoardViewView, BoardView waitingBoardView,
                              Board playerBoard, Board opponentBoard) {
        //active board will get the opponent's board (bảng active là lấy cái bảng của đối thủ nhá!!)
        activeBoardViewView.setBoard(opponentBoard);
        waitingBoardView.setBoard(playerBoard);
        activeBoardViewView.addBoardTouchListener(new BoardView.TouchBoardListener() {
            @Override
            public void onTouch(int x, int y) {
                if(gameController.getActivePlayer()==gameController.getPlayer())
                {
                    boardTouched(x, y);
                }
            }
        });

    }
    // Board touch function
    public void boardTouched( int x,  int y)
    {
        Board playerBoard=gameController.getActivePlayer().getPlayerBoard();

        gameController.hitPos(x,y);
        updateBoardsView();
        gameController.changeTurn();
        //Random pick after change turn, than change turn again
        if(gameController.getActivePlayer()!=gameController.getPlayer())
        {

            Random rng = new Random();
            int boardSize =playerBoard.getSize();
            Position toHit = null;

            while (toHit == null || toHit.isHit()){

                toHit = playerBoard.getPosAt(rng.nextInt(boardSize), rng.nextInt(boardSize));
            }
            gameController.hitPos(toHit.getX(),toHit.getY());
            updateBoardsView();
            gameController.changeTurn();
        }
    }
    // Update UI canvas board
    public void updateBoardsView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activeBoardView.invalidate();
                waitingBoardView.invalidate();
            }
        });
    }
}