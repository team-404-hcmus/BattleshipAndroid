package com.example.battleship;
//Class for draw board

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Queue;


public class BoardView extends View {
    public BoardView(Context context) {
        this(context, null);
    }
    public BoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //Board information
    private Board board;
    private int boardSize = 10;
    private boolean showShip=false;
    private int startColor=Color.rgb(224, 201, 166);
    private int endColor=startColor;

    private int lineColor = Color.WHITE;
    private final int  lineWidth = 5;


    Paint drawBoard = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint drawLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    //Setter
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public void setBoardColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
    public void setBoard(Board board) {
        this.board = board;
        this.boardSize= board.getSize();
    }
    public void setShowShip(boolean choice)
    {
        this.showShip=choice;
    }
    /**Touch event  **/
    //Touch Table Listener
    public interface TouchBoardListener
    {
        void onTouch(int x, int y);
    }
    //list used to store clicked position for handling
    private ArrayList<TouchBoardListener> listeners= new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                notifyTouch(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    private void notifyTouch(float x, float y)
    {
        if (x <= maxCoord() && y <= maxCoord()) {
            final float placeSize = lineGap();
            int ix = (int) (x/ placeSize);
            int iy = (int) (y / placeSize);
            for (TouchBoardListener listener: listeners) {
                listener.onTouch(ix,iy);
            }
        }
    }
    //Register the given listener.
    public void addBoardTouchListener(TouchBoardListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**Draw event **/
    //OnDraw Function (main func to draw)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGrid(canvas);
        if(this.showShip)
        {
            drawShip(canvas);
        }
        drawShotPlaces(canvas);

    }

    //Draw Grid (Board Background)
    private void drawGrid(Canvas canvas)
    {
        float maxCoord = maxCoord();
        float placeSize = lineGap();
        //draw grid background
        this.drawBoard.setShader(new LinearGradient(0, 0, 0, getHeight(),
                this.startColor, this.endColor, Shader.TileMode.MIRROR));

        canvas.drawRect(0, 0, maxCoord, maxCoord, drawBoard);
        //draw grid line
        this.drawLine.setColor(this.lineColor); //Line Color
        this.drawLine.setStrokeWidth(this.lineWidth); //Line Weight
        for (int i = 0; i < numOfLines(); i++) {
            float xy = i * placeSize;
            canvas.drawLine(0, xy, maxCoord, xy, drawLine); // horizontal line
            canvas.drawLine(xy, 0, xy, maxCoord, drawLine); // vertical line
        }
    }
    //If the position is hit, draw red
    /**
     *
     * @param canvas
     *
     */
    private void drawShotPlaces(Canvas canvas) {
        if(this.board == null){
            return;
        }
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                if(board.getPosAt(x, y).isHit()){
                    if(board.getPosAt(x, y).hasShip())
                    {
                        drawSquare(canvas, Color.GREEN, x, y);
                    }
                    else
                    {
                        drawSquare(canvas, Color.RED, x, y);
                    }

                }
            }
        }
    }
    //If the position have ship, draw ship ** only draw on waiting board( mean player board)
    private void drawShip(Canvas canvas)
    {
        if(this.board ==null)
        {
            return;
        }
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                if(board.getPosAt(x, y).hasShip()){
                    drawSquare(canvas, Color.GRAY, x, y);
                }
            }
        }
    }

    //Draw Square
    public void drawSquare(Canvas canvas, int color, int x, int y){
        Paint drawSquareTool = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawSquareTool.setColor(color);
        int length = 98;
        float viewSize = maxCoord();
        float tileSize = viewSize / 10;  //10 Is how many tiles there are
        float offSet = 8;
        canvas.drawRect((tileSize* x) + offSet,
                (tileSize*y) + offSet,
                ((tileSize * x)+tileSize) - offSet,
                (((viewSize/10) * y)+tileSize) - offSet,
                drawSquareTool);
    }

    /**Calculating Func **/
    //Calculate the gap between two horizontal/vertical lines.
    protected float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / (float) boardSize;
    }
    // Calculate the number of horizontal/vertical lines.
    private int numOfLines() {
        return this.boardSize + 1;
    }
    //Calculate the maximum screen coordinate
    protected float maxCoord() {
        return lineGap() * (numOfLines() - 1);
    }

    //return array index base on coordirate Oxy
    public int locatePlace(float x, float y) {
        if (x <= maxCoord() && y <= maxCoord()) {
            final float placeSize = lineGap();
            int ix = (int) (x / placeSize);
            int iy = (int) (y / placeSize);
            return ix * 100 + iy;
        }
        return -1;
    }
}
