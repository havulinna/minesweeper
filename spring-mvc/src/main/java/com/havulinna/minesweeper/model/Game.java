package com.havulinna.minesweeper.model;

import org.springframework.util.Assert;

import com.havulinna.collections.SmartList;

public class Game {
    private enum Status { WON, LOST, ONGOING }

    private final Minefield minefield;
    private Status status = Status.ONGOING;
    private int moves = 0;

    public Game(int rows, int cols, int mineCount) {
        this.minefield = new Minefield(rows, cols);

        setMinesRandomly(mineCount);
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
        return status == Status.WON;
    }

    public boolean isLost() {
        return status == Status.LOST;
    }

    public boolean isOver() {
        return isWon() || isLost();
    }

    public void toggleFlag(Square square) {
        Assert.isTrue(!isOver() && !square.isOpen());

        square.toggleFlag();
    }

    public void incrementMoves() {
        moves++;
    }

    public int getMoves() {
        return moves;
    }

    /**
     * @see Game#openSquare(Square)
     */
    public boolean openSquare(int row, int col) {
        return openSquare(getMinefield().getSquare(row, col));
    }

    /**
     * Attempts to open the given square. Returns false if the square is already
     * open or if it is flagged. Opening a square that has no mines next to it
     * leads to its neighbors being opened as well with a recursive call to
     * openSquare.
     * 
     * @param square The Square object to open
     * @return true if the square was opened
     */
    public boolean openSquare(Square square) {
        Assert.isTrue(!isOver());

        if (square.isOpen() || square.isFlagged()) {
            return false;
        }

        square.setOpen();

        if (square.isMine()) {
            status = Status.LOST;
        } else if (gameIsCompleted()) {
            status = Status.WON;
        } else {
            // If there are no mines next to the opened square, the neighbors are also opened
            SmartList<Square> neighbors = minefield.getNeighbors(square);

            if (!neighbors.containsAny(s -> s.isMine())) {
                // Recursively open neighbors that are not already open or flagged
                neighbors
                    .select(s -> !s.isOpen() && !s.isFlagged())
                    .forEach(s -> this.openSquare(s));
            }
        }
        return true;
    }

    /**
     * Returns true if the game is over or if there are no squares without mines left to open.
     * @return true if the game is over
     */
    private boolean gameIsCompleted() {
        return isOver() || !minefield.getSquares().containsAny(square -> !square.isOpen() && !square.isMine());
    }

    protected Minefield getMinefield() {
        return minefield;
    }
}
