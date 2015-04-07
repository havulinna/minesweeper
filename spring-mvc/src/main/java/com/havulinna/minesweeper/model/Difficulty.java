package com.havulinna.minesweeper.model;

public enum Difficulty {

    EASY(10, 10, 10),
    NORMAL(15, 15, 30),
    HARD(20, 20, 60);

    protected final int width;
    protected final int height;
    protected final int mineCount;

    Difficulty(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        
    }
}
