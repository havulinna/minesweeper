package com.havulinna.minesweeper.view;

import java.util.ArrayList;
import java.util.List;

import com.havulinna.collections.SmartList;
import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Square;
import com.havulinna.minesweeper.view.GameView.SquareView;


public class GameView {

    private final List<List<SquareView>> rows;
    private final boolean isWon;
    private final boolean isLost;
    private final int moves;
    private final Game game;

    public GameView(Game game) {
        this.game = game;
        this.rows = generateRows();

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
        return rows;
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
        SmartList<Square> allSquares = game.getMinefield().getSquares();

        final List<List<SquareView>> rowsList = new ArrayList<List<SquareView>>();

        for (int i=0; i<game.getMinefield().getHeight(); i++) {
            rowsList.add(i, new ArrayList<SquareView>());
        }

        allSquares.stream().forEach(s -> rowsList.get(s.getRow()).add(new SquareView(s)));
        return rowsList;
    }

    public class SquareView {

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

        public String getCssClass() {
            if (square.isOpen()) {
                return square.isMine() ? "mine" : "open";
            } else {
                return square.isFlagged() ? "flagged" : "closed";
            }
        }

        public String getText() {
            if (game.isLost() && square.isMine()) {
                return "M";
            } else if (square.isFlagged()) {
                return "F";
            } else if (square.isOpen()) {
                int minesInNeighbors = game.getMinefield().getNeighbors(square).select(s -> s.isMine()).size();
                return String.valueOf(minesInNeighbors);
            } else {
                return " ";
            }
        }
    }
}
