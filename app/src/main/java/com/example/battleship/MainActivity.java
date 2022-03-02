package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BoardView activeBoardView,waitingBoardView;
    private Game gameController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Set up game
        gameController=new Game();
        activeBoardView=findViewById(R.id.activeBoardView);
        waitingBoardView=findViewById(R.id.waitingBoardView);

        setNewBoards(activeBoardView,waitingBoardView,
                gameController.getPlayer().getPlayerBoard(),
                gameController.getOpponentPlayer().getPlayerBoard());
    }

    private void setNewBoards(BoardView playerBoardView, BoardView opponentBoardView,
                              Board playerBoard, Board opponentBoard) {
        playerBoardView.setBoard(playerBoard);
        opponentBoardView.setBoard(opponentBoard);
        opponentBoardView.addBoardTouchListener(new BoardView.TouchBoardListener() {
            @Override
            public void onTouch(int x, int y) {
                //boardTouched(x, y);
            }
        });
        //updateBoards();
    }
}