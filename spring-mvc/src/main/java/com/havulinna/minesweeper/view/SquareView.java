package com.havulinna.minesweeper.view;

import java.util.ArrayList;
import java.util.List;

import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Square;

public class SquareView {

    private static final String EMPTY_SYMBOL = " ";
    private static final String FLAG_SYMBOL = "?";
    private static final String MINE_SYMBOL = "⁕";

    protected static final String OPEN_CSS_CLASS = "open";
    protected static final String CLOSED_CSS_CLASS = "closed";
    protected static final String MINE_CSS_CLASS = "mine";
    protected static final String FLAGGED_CSS_CLASS = "flagged";

    private Square square;
    private Game game;

    public SquareView(Square square, Game game) {
        this.square = square;
        this.game = game;
    }

    public int getRow() {
        return square.getRow();
    }

    public int getCol() {
        return square.getCol();
    }

    public boolean isDisabled() {
        return square.isOpen() || game.isOver();
    }

    public String getCssClass() {
        List<String> classes = new ArrayList<String>();

        classes.add(square.isOpen() ? OPEN_CSS_CLASS : CLOSED_CSS_CLASS);

        if (game.isLost() && square.isMine()) {
            classes.add(MINE_CSS_CLASS);
        }

        if (square.isFlagged()) {
            classes.add(FLAGGED_CSS_CLASS);
        }

        return String.join(" ", classes);
    }

    /**
     * Returns the text or symbol to be shown in the UI for the current square.
     * Mines display a mine symbol when the game has been lost. Flagged squares
     * have their own symbol, and opened squares display the number of neighbors
     * with mines when that number is over 0.
     * 
     * @return single character String to show in the UI
     */
    public String getText() {
        if (game.isLost() && square.isMine()) {
            return MINE_SYMBOL;
        } else if (square.isFlagged()) {
            return FLAG_SYMBOL;
        } else if (square.isOpen()) {
            int minesInNeighbors = game.getMinefield().getNeighbors(square).select(s -> s.isMine()).size();
            if (minesInNeighbors > 0) {
                return String.valueOf(minesInNeighbors);
            } else {
                return EMPTY_SYMBOL;
            }
        } else {
            return EMPTY_SYMBOL;
        }
    }
}