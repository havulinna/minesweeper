package com.havulinna.minesweeper.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Square;
import com.havulinna.minesweeper.util.GameBuilder;
import com.havulinna.minesweeper.util.SquareBuilder;

public class SquareViewTest {

    private Game ongoingGame = new GameBuilder().build();
    private Game wonGame = new GameBuilder().setWon().build();
    private Game lostGame = new GameBuilder().setLost().build();

    private Square flaggedSquare = new SquareBuilder().setFlagged().build();

    private Square closedMine = new SquareBuilder().setMine().build();
    private Square openMine = new SquareBuilder().setMine().setOpen().build();

    private Square openSquare = new SquareBuilder().setOpen().build();
    private Square closedSquare = new SquareBuilder().build();


    @Test
    public void viewIsDisabledWhenGameHasEnded() {
        verifyViewIsDisabled(closedSquare, lostGame);
        verifyViewIsDisabled(closedMine, lostGame);
    }

    @Test
    public void viewIsDisabledWhenSquareIsOpen() {
        verifyViewIsDisabled(openSquare, lostGame);
    }

    @Test
    public void viewIsEnabledWhenSquareIsClosedAndGameNotEnded() {
        verifyViewIsEnabled(closedSquare, ongoingGame);
        verifyViewIsEnabled(closedMine, ongoingGame);
    }

    @Test
    public void mineCssClassAddedOnlyWhenGameIsLost() {
        verifyNoCssClass(SquareView.MINE_CSS_CLASS, closedMine, ongoingGame);
        verifyNoCssClass(SquareView.MINE_CSS_CLASS, closedMine, wonGame);

        verifyHasCssClass(SquareView.MINE_CSS_CLASS, closedMine, lostGame);
        verifyHasCssClass(SquareView.MINE_CSS_CLASS, openMine, lostGame);
    }

    @Test
    public void openSquaresHaveOpenCssClass() {
        verifyHasCssClass(SquareView.OPEN_CSS_CLASS, openSquare, ongoingGame);
        verifyHasCssClass(SquareView.OPEN_CSS_CLASS, openSquare, lostGame);
        verifyHasCssClass(SquareView.OPEN_CSS_CLASS, openMine, lostGame);
    }

    @Test
    public void closedSquaresHaveClosedCssClass() {
        verifyHasCssClass(SquareView.CLOSED_CSS_CLASS, closedSquare, ongoingGame);
        verifyHasCssClass(SquareView.CLOSED_CSS_CLASS, closedSquare, lostGame);
        verifyHasCssClass(SquareView.CLOSED_CSS_CLASS, closedMine, lostGame);
    }

    @Test
    public void flaggedSquaresHaveFlaggedCssClass() {
        verifyHasCssClass(SquareView.FLAGGED_CSS_CLASS, flaggedSquare, ongoingGame);
        verifyHasCssClass(SquareView.FLAGGED_CSS_CLASS, flaggedSquare, lostGame);
    }

    private static void verifyViewIsEnabled(Square square, Game game) {
        SquareView squareView = new SquareView(square, game);
        assertFalse(squareView.isDisabled());
    }

    private static void verifyViewIsDisabled(Square square, Game game) {
        SquareView squareView = new SquareView(square, game);
        assertTrue(squareView.isDisabled());
    }

    private static void verifyHasCssClass(String expectedClass, Square square, Game game) {
        SquareView view = new SquareView(square, game);
        assertTrue(view.getCssClass().contains(expectedClass));
    }

    private static void verifyNoCssClass(String disallowedCssClass, Square square, Game game) {
        SquareView view = new SquareView(square, game);
        assertFalse(view.getCssClass().contains(disallowedCssClass));
    }
}
