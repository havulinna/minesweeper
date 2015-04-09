package com.havulinna.minesweeper.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class MinefieldTest {

    private Minefield minefield_6x8 = new Minefield(6, 8);

    private Square topLeft = minefield_6x8.getSquare(0, 0);
    private Square topRight = minefield_6x8.getSquare(0, 7);
    private Square bottomLeft = minefield_6x8.getSquare(5, 0);
    private Square bottomRight = minefield_6x8.getSquare(5, 7);

    @Test(expected=IllegalArgumentException.class)
    public void minefieldDimensionsMustBePositiveIntegers() {
        @SuppressWarnings("unused")
        Minefield illegalObject = new Minefield(0, 0);
    }

    @Test
    public void constructorInitializesRightAmountOfSquares() {
        assertEquals(6*8, minefield_6x8.getSquares().size());
    }

    @Test
    public void aSquareIsFoundForAllCombinationsOfCoordinates() {
        for (int row=0; row<6; row++) {
            for (int col=0; col<8; col++) {
                verifySquareIsFoundWithCoordinates(row, col, minefield_6x8);
            }
        }
    }

    @Test
    public void allSquaresAreInitiallyClosedAndWithoutMines() {
        assertFalse(minefield_6x8.getSquares().containsAny(s -> s.isMine() || s.isOpen()));
    }

    @Test
    public void singleSquareHasNoNeighbors() {
        Minefield minefield_1x1 = new Minefield(1, 1);

        Square onlySquare = minefield_1x1.getSquare(0, 0);
        assertTrue(minefield_1x1.getNeighbors(onlySquare).isEmpty());
    }

    @Test
    public void cornerSquaresHaveThreeNeighbors() {
        verifyNumberOfNeighbors(minefield_6x8, topLeft, 3);
        verifyNumberOfNeighbors(minefield_6x8, topRight, 3);
        verifyNumberOfNeighbors(minefield_6x8, bottomLeft, 3);
        verifyNumberOfNeighbors(minefield_6x8, bottomRight, 3);
    }

    @Test
    public void borderSquaresHaveFiveNeighbors() {
        verifyNumberOfNeighbors(minefield_6x8, minefield_6x8.getSquare(0, 3), 5); // Top border
        verifyNumberOfNeighbors(minefield_6x8, minefield_6x8.getSquare(3, 7), 5); // Right border
    }

    @Test
    public void middleSquaresHaveEightNeighbors() {
        verifyNumberOfNeighbors(minefield_6x8, minefield_6x8.getSquare(0, 3), 5);
        verifyNumberOfNeighbors(minefield_6x8, minefield_6x8.getSquare(3, 7), 5);
    }

    @Test(expected=Exception.class)
    public void gettingSquareWithIncorrectCoordinatesThrowsException() {
        minefield_6x8.getSquare(100, 100);
    }

    @Test(expected=Exception.class)
    public void gettingSquareWithNegativeCoordinatesThrowsException() {
        minefield_6x8.getSquare(1, -1);
    }

    private static void verifyNumberOfNeighbors(Minefield field, Square square, int expectedNeighborAmount) {
        assertEquals(expectedNeighborAmount, field.getNeighbors(square).size());
    }

    private static void verifySquareIsFoundWithCoordinates(int findRow, int findCol, Minefield minefield) {
        Square found = minefield.getSquare(findRow, findCol);

        assertEquals(findRow, found.getRow());
        assertEquals(findCol, found.getCol());
    }
}
