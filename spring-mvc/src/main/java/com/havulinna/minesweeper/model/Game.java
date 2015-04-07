package com.havulinna.minesweeper.model;

import org.springframework.util.Assert;

import com.havulinna.collections.SmartList;

public class Game {

    private final Minefield minefield;
    private int moves = 0;

    public Game(int rows, int cols, int mineCount) {
        this.minefield = new Minefield(rows, cols);

        setMinesRandomly(mineCount);
    }

    public Game(Difficulty difficulty) {
        this(difficulty.height, difficulty.width, difficulty.mineCount);
    }

    /**
     * Places the given amount of mines to random squares.
     * @param mineCount The maximum amount of mines to place
     */
    private void setMinesRandomly(int mineCount) {
        new SmartList<Square>(minefield.getSquares())
            .shuffle()
            .stream()
            .limit(mineCount)
            .forEach(x -> x.setMine());
    }

    public boolean isWon() {
        return gameIsCompleted();
    }

    public boolean isLost() {
        return minefield.getSquares().containsAny(
                square -> square.isOpen() && square.isMine());
    }

    public boolean isOver() {
        return isWon() || isLost();
    }

    public void toggleFlag(Square square) {
        Assert.isTrue(!isOver() && !square.isOpen());

        square.toggleFlag();
    }

    public int getMoves() {
        return this.moves;
    }

    private void incrementMoves() {
        this.moves++;
    }

    /**
     * @see Game#openSquare(Square)
     */
    public void openSquare(int row, int col) {
        openSquare(getMinefield().getSquare(row, col));
    }

    /**
     * Attempts to open the given square. Opening a square that has no mines
     * next to it leads to its neighbors being opened as well with a recursive
     * call to openSquare.
     * 
     * @param square The Square object to open
     */
    public void openSquare(Square square) {
        if (isOver() || square.isFlagged()) {
            return;
        }
        incrementMoves();
        openRecursively(square);
    }

    private void openRecursively(Square square) {
        square.setOpen();

        SmartList<Square> neighbors = minefield.getNeighbors(square);

        /*
         * If there game is still on and there are no mines next to the opened
         * square, the neighbors are also opened recursively.
         */
        if (!isOver() && !neighbors.containsAny(s -> s.isMine())) {
            neighbors
                .select(s -> !s.isOpen() && !s.isFlagged())
                .forEach(s -> openRecursively(s));
        }
    }

    /**
     * Returns true if the game is over or if there are no squares without mines left to open.
     * @return true if the game is over
     */
    private boolean gameIsCompleted() {
        return !minefield.getSquares().containsAny(square -> !square.isOpen() && !square.isMine());
    }

    public Minefield getMinefield() {
        return minefield;
    }

    @Override
    public String toString() {
        return "Moves " + getMoves() + "\n\n" + getMinefield().toString();
    }
}
