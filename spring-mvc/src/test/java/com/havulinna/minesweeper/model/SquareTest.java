package com.havulinna.minesweeper.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class SquareTest {

    private Square[][] squares = new Square[5][];

    @Before
    public void setUpSquares() {
        for (int row=0; row<squares.length; row++) {
            squares[row] = new Square[5];
            for (int col=0; col<squares[row].length; col++) {
                squares[row][col] = new Square(row, col);
            }
        }
    }

    @Test
    public void squaresDirectlyNextToEachOtherAreNeighbors() {
        Square middle = squares[2][2];

        verifySquareIsNeighborOf(middle, squares[2][1]);
        verifySquareIsNeighborOf(middle, squares[2][3]);
        verifySquareIsNeighborOf(middle, squares[1][3]);
        verifySquareIsNeighborOf(middle, squares[3][2]);
    }

    @Test
    public void squaresWithConnectedCornersAreNeighbors() {
        Square middle = squares[2][2];

        verifySquareIsNeighborOf(middle, squares[1][1]);
        verifySquareIsNeighborOf(middle, squares[3][3]);
        verifySquareIsNeighborOf(middle, squares[1][3]);
        verifySquareIsNeighborOf(middle, squares[3][1]);
    }

    @Test
    public void squaresThatAreNotConnectedAreNotNeighbors() {
        Square middle = squares[2][2];

        verifySquareIsNotNeighborOf(middle, squares[1][4]);
        verifySquareIsNotNeighborOf(middle, squares[4][1]);
        verifySquareIsNotNeighborOf(middle, squares[4][3]);
        verifySquareIsNotNeighborOf(middle, squares[3][4]);
    }

    @Test
    public void squaresAreNotTheirOwnNeighbors() {
        Square self = squares[1][3];
        verifySquareIsNotNeighborOf(self, self);
    }

    @Test
    public void toggleFlagChangesTheFlaggedAttribute() {
        Square square = new Square(0, 0);

        square.toggleFlag();
        assertTrue(square.isFlagged());

        square.toggleFlag();
        assertFalse(square.isFlagged());
    }

    private static void verifySquareIsNotNeighborOf(Square square, Square other) {
        assertFalse(square.isNeighborOf(other));
    }

    private static void verifySquareIsNeighborOf(Square square, Square other) {
        assertTrue(square.isNeighborOf(other));
    }
}
