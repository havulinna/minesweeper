package com.havulinna.minesweeper.model;

public enum Difficulty {

    EASY(10, 10, 6),
    FAIR(15, 15, 30),
    HARD(20, 20, 40);

    protected final int width;
    protected final int height;
    protected final int mineCount;

    Difficulty(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        
    }
}
