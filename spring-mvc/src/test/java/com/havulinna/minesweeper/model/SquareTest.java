package com.havulinna.minesweeper.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class SquareTest {

    @Test
    public void squaresDirectlyNextToEachOtherAreNeighbors() {
        Square middle = square(2, 2);

        verifySquaresAreNeighbors(middle, square(2, 1));
        verifySquaresAreNeighbors(middle, square(2, 3));

        verifySquaresAreNeighbors(middle, square(1, 2));
        verifySquaresAreNeighbors(middle, square(3, 2));
    }

    @Test
    public void squaresWithConnectedCornersAreNeighbors() {
        Square middle = square(2, 2);

        verifySquaresAreNeighbors(middle, square(1, 1));
        verifySquaresAreNeighbors(middle, square(3, 3));
        verifySquaresAreNeighbors(middle, square(1, 3));
        verifySquaresAreNeighbors(middle, square(3, 1));
    }

    @Test
    public void squaresFurtherOnTheSameRowAreNotNeighbors() {
        Square middle = square(6, 6);

        verifySquaresAreNotNeighbors(middle, square(6, 2));
        verifySquaresAreNotNeighbors(middle, square(6, 4));
        verifySquaresAreNotNeighbors(middle, square(6, 8));
        verifySquaresAreNotNeighbors(middle, square(6, 10));
    }

    @Test
    public void squaresFurtherOnTheSameColumnAreNotNeighbors() {
        Square middle = square(6, 6);

        verifySquaresAreNotNeighbors(middle, square(2, 6));
        verifySquaresAreNotNeighbors(middle, square(4, 6));
        verifySquaresAreNotNeighbors(middle, square(8, 6));
        verifySquaresAreNotNeighbors(middle, square(10, 6));
    }

    @Test
    public void diagonalSquaresThatAreNotConnectedAreNotNeighbors() {
        Square middle = square(6, 6);

        verifySquaresAreNotNeighbors(middle, square(4, 4));
        verifySquaresAreNotNeighbors(middle, square(8, 8));
        verifySquaresAreNotNeighbors(middle, square(8, 4));
        verifySquaresAreNotNeighbors(middle, square(4, 8));
    }

    @Test
    public void squaresAreNotTheirOwnNeighbors() {
        Square self = square(1, 3);

        verifySquaresAreNotNeighbors(self, self);
    }


    @Test
    public void toggleFlagChangesTheFlaggedAttribute() {
        Square square = square(0, 0);

        square.toggleFlag();
        assertTrue(square.isFlagged());

        square.toggleFlag();
        assertFalse(square.isFlagged());
    }


    private static void verifySquaresAreNotNeighbors(Square first, Square second) {
        // Tests the relationship both ways: a is not neighbor of b == b is not neighbor of a
        assertFalse(first.isNeighborOf(second));
        assertFalse(second.isNeighborOf(first));
    }

    private static void verifySquaresAreNeighbors(Square first, Square second) {
        // Tests the relationship both ways: a is neighbor of b == b is neighbor of a
        assertTrue(first.isNeighborOf(second));
        assertTrue(second.isNeighborOf(first));
    }

    private static Square square(int row, int col) {
        return new Square(row, col);
    }
}
