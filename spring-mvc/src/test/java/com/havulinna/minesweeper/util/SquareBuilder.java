package com.havulinna.minesweeper.util;

import com.havulinna.minesweeper.model.Square;

/**
 * Utility class for easily creating Square objects in tests.
 */
public class SquareBuilder {

    private int row = 0;
    private int col = 0;
    private boolean open = false;
    private boolean mine = false;
    private boolean flagged = false;

    public SquareBuilder setCoordinates(int row, int col) {
        this.row = row;
        this.col = col;
        return this;
    }

    public SquareBuilder setFlagged() {
        flagged = true;
        return this;
    }

    public SquareBuilder setMine() {
        mine = true;
        return this;
    }

    public SquareBuilder setOpen() {
        open = true;
        return this;
    }

    public Square build() {
        Square square = new Square(row, col);

        ifThen(open, () -> square.setOpen());
        ifThen(mine, () -> square.setMine());
        ifThen(flagged, () -> square.toggleFlag());

        return square;
    }

    private static void ifThen(boolean condition, SquareOperation operation) {
        if (condition) {
            operation.execute();
        }
    }

    @FunctionalInterface
    private interface SquareOperation {
        void execute();
    }

}
