package com.example.battleship;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BoardView activeBoardView,waitingBoardView;
    Button shoot_btn;
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
        shoot_btn=findViewById(R.id.shoot_btn);
        setNewGame(activeBoardView,waitingBoardView,
                gameController.getPlayer().getPlayerBoard(),
                gameController.getOpponentPlayer().getPlayerBoard(),true);


    }
    /**Logic game **/
    //Start new game
    private void setNewGame(BoardView activeBoardView, BoardView waitingBoardView,
                              Board playerBoard, Board opponentBoard, boolean isFirstTime) {
        //active board will get the opponent's board (bảng active là lấy cái bảng của đối thủ nhá!!)
        activeBoardView.setBoard(opponentBoard);
        waitingBoardView.setBoard(playerBoard);
        //Tạo boardTouched 1 lần đầu thôi, tạo nhiều thì cái đầu không mất, khiến cho cái canvas
        //nhận boardtouch event nhiều hơn 1 lần, làm conflict
        if(isFirstTime)
        {
            activeBoardView.addBoardTouchListener(new BoardView.TouchBoardListener() {
                @Override
                public void onTouch(int x, int y) {
                    if(gameController.getActivePlayer()==gameController.getPlayer())
                    {
                        boardTouched(x, y);
                    }
                }
            });
        }

        //updateBoardsView();

    }
    // Board touch function
    public void boardTouched( int x,  int y)
    {
        Position placeToHit = gameController.getOpponentPlayer().getPlayerBoard().getPosAt(x, y);

        boolean opponentTurn = gameController.getActivePlayer() != gameController.getPlayer();
        boolean placeAlreadyHit = placeToHit.isHit();

        //If game is over, or is computer's turn, or place touched was already hit
        if (opponentTurn || placeAlreadyHit) {
            //Ignore button click
            return;
        }
        Board playerBoard=gameController.getPlayer().getPlayerBoard();
        gameController.hitPos(x,y);

        updateBoardsView();
        if(checkOver())
        {
            return;
        };
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
            if(checkOver())
            {
                return;
            };
            gameController.changeTurn();

        }
    }
    //Check is game over
    public boolean checkOver()
    {
        if(gameController.isWin()==1)// player thua
        {

            EndGameDialog("Unfortunately, You lose the game");
            return true;
        }
        else if(gameController.isWin()==2)//player thắng
        {
            EndGameDialog("Congratulation, You won the game");
            return true;
        }
        return false;
    }
    //Reset Game
    public void ResetGame()
    {
        this.gameController=null;
        this.gameController=new Game();
        setNewGame(activeBoardView,waitingBoardView,
                gameController.getPlayer().getPlayerBoard(),
                gameController.getOpponentPlayer().getPlayerBoard(),false);
        updateBoardsView();
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
    /**End Game Dialog, dialog hiện lúc endgame**/
    public void EndGameDialog(String tittle)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(tittle);
        alertDialogBuilder.setMessage("PLAY AGAIN ?");
        // Click yes thì chơi lại, click no thì thoát activity
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ResetGame();
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}