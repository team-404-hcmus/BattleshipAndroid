package com.example.battleship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class PlaceShipActivity extends AppCompatActivity {
    Button doneBtn,rotateBtn;
    ImageView minesweeper2,frigate3,battleship4, aircraftcarrier5;
    private ShipView shipBeingChoose= null;
    private BoardView boardView;
    private Board playerBoard;
    private List<ShipView> fleetView = new LinkedList<>();
    private Game gameController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ship);

        //Setup BoardView và Board
        boardView=findViewById(R.id.placeShipBoardView);
        playerBoard = new Board();
        boardView.setBoard(playerBoard);
        boardView.setShowShip(true);
        //Setup Button Done
        doneBtn=findViewById(R.id.placeShipDoneBtn);
        doneBtn.setEnabled(false);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                Bundle myBundle = new Bundle();
                gameController=new Game(playerBoard);
                myBundle.putSerializable("GameManager",gameController);
                intent.putExtra("GameManager", myBundle);
                view.getContext().startActivity(intent);
            }
        });

        //Setup 4 con thuyền
        minesweeper2=findViewById(R.id.minesweeper2);
        frigate3=findViewById(R.id.frigate3);
        battleship4=findViewById(R.id.battleship4);
        aircraftcarrier5=findViewById(R.id.aircraftcarrier5);


        fleetView.add(new ShipView(minesweeper2, new Ship(2, "minesweeper")));
        fleetView.add(new ShipView(frigate3, new Ship( 3,"frigate")));
        fleetView.add(new ShipView(battleship4, new Ship(4,"battleship" )));
        fleetView.add(new ShipView(aircraftcarrier5, new Ship(5,"aircraftcarrier")));

        for(ShipView shipV : fleetView)
        {
            //ScalingImage(shipV.getShipImage());
            setImageTouchListener(shipV);
        }
        setBoardViewDragListener(boardView,playerBoard);
        //Setup Reset Btn
        rotateBtn = findViewById(R.id.placeShipRotateBtn);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
    }
    /** **/
    //Event thả thuyền trên boardView (drag)
    private void setBoardViewDragListener(BoardView boardView, Board board)
    {
        boardView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction())
                {
                    case DragEvent.ACTION_DROP:
                        if(shipBeingChoose==null)
                            break;
                        Log.d("Drag","Done");
                        float x = dragEvent.getX();
                        float y = dragEvent.getY();
                        float width;
                        float height;

                        if (!shipBeingChoose.getShip().getDirection()) {
                            width = shipBeingChoose.getShipImage().getHeight();
                            height = shipBeingChoose.getShipImage().getWidth();

                        } else {
                            width = shipBeingChoose.getShipImage().getWidth();
                            height = shipBeingChoose.getShipImage().getHeight();
                        }
                        //x and y coordinates of top-left of image, relative to the board
                        float boardX = x - (width / 2);
                        float boardY = y - (height / 2);

                        int xy = boardView.locatePlace(boardX, boardY);
                        if (xy == -1) {
                            return true;
                        }
                        //Lấy vị trí x,y theo index
                        int xGrid = xy / 100;
                        int yGrid = xy % 100;
                        int delta=1;
                        if(shipBeingChoose.getShip().getSize()==2 || shipBeingChoose.getShip().getSize()==3 )
                        {
                            delta=2;
                        }

                        if (!board.placeShip(shipBeingChoose.getShip(), xGrid+delta, yGrid,
                                shipBeingChoose.getShip().getDirection())) {
                            return true;
                        }
                        //redraw Boardview
                        boardView.invalidate();
                        shipBeingChoose.getShipImage().setBackgroundColor(Color.RED);
                        shipBeingChoose=null;
                        if(isAllShipPlaced())
                        {
                            doneBtn.setEnabled(true);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_STARTED:
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_LOCATION:
                    case DragEvent.ACTION_DRAG_ENDED:
                    default:
                        break;
                }
                return true;
            }
        });
    }
    //Event kéo thả mấy con thuyền
    @SuppressLint("ClickableViewAccessibility")
    private void setImageTouchListener(ShipView shipView)
    {
        ImageView image=shipView.getShipImage();
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {
                    if (shipView.getShip().isPlaced()) {
                        return false;
                    }
                    ClipData data = ClipData.newPlainText("", "");
                    double rotationRad = Math.toRadians(image.getRotation());
                    final int w = (int) (image.getWidth() * image.getScaleX());
                    final int h = (int) (image.getHeight() * image.getScaleY());
                    double s = Math.abs(Math.sin(rotationRad));
                    double c = Math.abs(Math.cos(rotationRad));
                    final int width = (int) (w * c + h * s);
                    final int height = (int) (w * s + h * c);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(image){
                        @Override
                        public void onDrawShadow(Canvas canvas) {
                            canvas.scale(image.getScaleX(), image.getScaleY(), width / 2,
                                    height / 2);
                            canvas.rotate(image.getRotation(), width / 2, height / 2);
                            canvas.translate((width - image.getWidth()) / 2,
                                    (height - image.getHeight()) / 2);
                            super.onDrawShadow(canvas);
                        }

                        @Override
                        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
                            outShadowSize.set(width, height);
                            outShadowTouchPoint.set(outShadowSize.x / 2, outShadowSize.y / 2);
                        }
                    };
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        image.startDragAndDrop(data,shadowBuilder, image,0);
                    }
                    else
                    {
                        image.startDrag(data,shadowBuilder, image,0);
                    }
                    shipBeingChoose=shipView;

                    shipView.setSelected(true);
                    shipView.getShipImage().setBackgroundColor(Color.GREEN);

                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    //Scale ảnh thuyền
    private void ScalingImage(ImageView image) {

        image.setAdjustViewBounds(true);

        ViewTreeObserver vto = image.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                image.setMaxHeight(boardView.getMeasuredHeight() / 10);
            }

        });
    }

    //Check xem tất cả thuyền đã được đặt chưa
    private boolean isAllShipPlaced()
    {
        for(ShipView shipV : fleetView)
        {
            if (shipV.getShip() == null) {
                return false;
            }
            if (!shipV.getShip().isPlaced()) {
                return false;
            }
        }
        return true;
    }



}