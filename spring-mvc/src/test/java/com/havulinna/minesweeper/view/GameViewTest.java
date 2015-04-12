package com.havulinna.minesweeper.view;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Minefield;
import com.havulinna.minesweeper.util.GameBuilder;


public class GameViewTest {
    
    private Game lostGame = new GameBuilder().setLost().build();
    private Game wonGame = new GameBuilder().setWon().build();
    private Game ongoingGame = new GameBuilder().build();


    @Test
    public void gameOverMessageShownForLostGame() {
        verifyStatusMessageForGame(GameView.State.LOST.message, lostGame);
    }

    @Test
    public void congratulationsMessageShownForWonGame() {
        verifyStatusMessageForGame(GameView.State.WON.message, wonGame);
    }

    @Test
    public void gameOnMessageShownWhenGameHasNotEnded() {
        verifyStatusMessageForGame(GameView.State.ON.message, ongoingGame);
    }

    @Test
    public void getRowsReturnsEachRowAsItsOwnList() {
        Game game = new GameBuilder().setMinefield(new Minefield(3, 4)).build();
        GameView view = new GameView(game);

        List<List<SquareView>> rows = view.getRows();

        assertEquals(3, rows.size());
        assertEquals(4, rows.get(0).size());
    }

    @Test
    public void verifySquaresAreInRightPositionsInNestedLists() {
        Minefield minefield_3x4 = new Minefield(3, 4);
        Game game = new GameBuilder().setMinefield(minefield_3x4).build();

        GameView view = new GameView(game);

        List<List<SquareView>> rows = view.getRows();
        for (int row = 0; row < minefield_3x4.getHeight(); row++) {
            for (int col = 0; col < minefield_3x4.getWidth(); col++) {
                SquareView currentSquare = rows.get(row).get(col);

                assertEquals(row, currentSquare.getRow());
                assertEquals(col, currentSquare.getCol());
            }
        }
    }


    /**
     * Creates a GameView for the given game, and verifies that the status text
     * for that GameView matches the expected status. If it does not match, an
     * assertion error is thrown.
     */
    private static void verifyStatusMessageForGame(String expectedStatus, Game game) {
        GameView view = new GameView(game);
        assertEquals(expectedStatus, view.getStatusText());
    }
}
