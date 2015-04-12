package com.havulinna.minesweeper.model;

public class Square {
    private boolean isMine;
    private boolean isOpen;
    private boolean isFlagged;

    private final int row;
    private final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine() {
        this.isMine = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isNeighborOf(Square other) {
        return this != other && Math.abs(this.col - other.col) <= 1 && Math.abs(this.row - other.row) <= 1;
    }

    public void toggleFlag() {
        this.isFlagged = !isFlagged;
    }
}
