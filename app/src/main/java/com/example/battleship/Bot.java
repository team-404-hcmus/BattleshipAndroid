package com.example.battleship;

public interface Bot {
    public String getBotName();
    public Position pickMove(Board board);
}
