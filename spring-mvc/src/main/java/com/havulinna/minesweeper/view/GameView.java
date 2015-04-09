package com.havulinna.minesweeper.view;

import java.util.ArrayList;
import java.util.List;

import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Square;


public class GameView {

    private final boolean isWon;
    private final boolean isLost;
    private final int moves;
    private final Game game;

    public GameView(Game game) {
        this.game = game;

        this.isWon = game.isWon();
        this.isLost = game.isLost();
        this.moves = game.getMoves();
    }

    public String getStatusText() {
        if (isWon()) {
            return "Game won!";
        } else if (isLost()) {
            return "Game lost :(";
        } else {
            return "Game is on!";
        }
    }

    public List<List<SquareView>> getRows() {
        return generateRows();
    }

    public boolean isWon() {
        return isWon;
    }

    public boolean isLost() {
        return isLost;
    }

    public boolean isEnded() {
        return isLost() || isWon();
    }

    public int getMoves() {
        return moves;
    }

    /**
     * Generates an easily navigable list of lists of {@link SquareView} objects.
     * The outer list contains rows, and each inner list contains the squares
     * for that row.
     */
    private List<List<SquareView>> generateRows() {

        final List<List<SquareView>> rows = new ArrayList<List<SquareView>>();

        for (int i=0; i<game.getMinefield().getHeight(); i++) {
            rows.add(i, new ArrayList<SquareView>());
        }

        // Add each square from the minefield to its corresponding list of squares
        game.getMinefield().getSquares().stream().forEach(s -> {
            rows.get(s.getRow()).add(new SquareView(s));
        });
        return rows;
    }

    public class SquareView {

        private static final String EMPTY_SYMBOL = " ";
        private static final String FLAG_SYMBOL = "?";
        private static final String MINE_SYMBOL = "⁕";
        private Square square;

        public SquareView(Square square) {
            this.square = square;
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

            classes.add(square.isOpen() ? "open" : "closed");

            if (game.isOver() && square.isMine()) {
                classes.add("mine");
            }

            if (square.isFlagged()) {
                classes.add("flagged");
            }

            return String.join(" ", classes);
        }

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
}
