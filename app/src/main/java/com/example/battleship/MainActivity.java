package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

                boardTouched(x, y);
            }
        });

    }
    // Board touch function
    public void boardTouched( int x,  int y)
    {
        Position hitPos =gameController.getActivePlayer().getPlayerBoard().getPosAt(x, y);
        gameController.hitPos(x,y);
        updateBoardsView();
        gameController.changeTurn();
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