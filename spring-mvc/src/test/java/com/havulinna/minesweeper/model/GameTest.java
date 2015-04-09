package com.havulinna.minesweeper.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.havulinna.collections.SmartList;


public class GameTest {

    @Test
    public void newGameInitializesMinefieldWithCorrectDimensions() {
        int rows = 3, cols = 4;
        Minefield minefield = new Game(rows, cols, 0).getMinefield();

        assertEquals(rows, minefield.getHeight());
        assertEquals(cols, minefield.getWidth());
    }

    @Test
    public void givenAmountOfMinesArePlacedIntoSquares() {
        int rows = 20, cols = 20, numberOfMines = 80;
        Minefield minefield = new Game(rows, cols, numberOfMines).getMinefield();

        SmartList<Square> mines = minefield.getSquares().select(x -> x.isMine());
        assertEquals(numberOfMines, mines.size());
    }

    @Test
    public void gameIsInitiallyNotCompleted() {
        Game game = new Game(2, 2, 0);

        assertFalse(game.isLost());
        assertFalse(game.isWon());
        assertFalse(game.isOver());
    }

    @Test
    public void gameIsLostWhenMineIsOpened() {
        Game game = createGame("  ", "MM");

        game.openSquare(1, 1);
        assertTrue(game.isLost());
    }

    @Test
    public void gameIsWonWhenAllNonMinesAreOpened() {
        Game game = createGame("  ", "MM");

        game.openSquare(0, 0);
        game.openSquare(0, 1);

        assertTrue(game.isWon());
    }

    @Test
    public void openingSquareWithNoMinesNextToItOpensNeighborsRecursively() {
        Game game = createGame("  M ", "    ");
        game.openSquare(1, 0);

        verifyGameState(game, 
                "01??", 
                "01??");
    }

    @Test
    public void openingFlaggedSquareHasNoEffect() {
        Game game = createGame("F  ", "  M");
        game.openSquare(0, 0);
        verifyGameState(game, "F??", "???");
    }

    /**
     * This utility method lets you easily define the minefield of the game that
     * you wish to create. To place a mine in a field, use the char 'M', to
     * place a flag, use char 'F', to open a square, use 'O'.
     * 
     * For example, {@code createGame("M  ", "  M")} will create a new game with
     * two rows and three columns, placing mines on the top left and bottom
     * right corners.
     */
    private static Game createGame(String ... rowsOfSymbols) {
        Game game = new Game(rowsOfSymbols.length, rowsOfSymbols[0].length(), 0);
        for (int row = 0; row < rowsOfSymbols.length; row++) {
            String squares = rowsOfSymbols[row];
            for (int col = 0; col < squares.length(); col++) {
                char ch = squares.charAt(col);

                Square square = game.getMinefield().getSquare(row, col);
                switch (ch) {
                case 'M':
                    square.setMine();
                    break;
                case 'F':
                    square.toggleFlag();
                    break;
                case 'O':
                    square.setOpen();
                    break;
                case ' ':
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported symbol: " + ch);
                }
            }
        }
        return game;
    }

    /**
     * This utility method lets you easily compare a situation in a game to the
     * desired situation by comparing the result of toString from the minefield.
     *
     * @see Minefield#toString()
     */
    private static void verifyGameState(Game game, String ... rowsOfSymbols) {
        String expected = String.join("\n", rowsOfSymbols);

        assertEquals(expected, game.getMinefield().toString());
    }
}
