package com.havulinna.minesweeper.view;

import java.util.ArrayList;
import java.util.List;

import com.havulinna.minesweeper.model.Game;


public class GameView {

    public enum State {
        ON("Game is on!", "ongoing"),
        WON("Game won!", "won"),
        LOST("Game lost :(", "lost");

        public final String message;
        public final String cssClass;

        State(String message, String cssClass) {
            this.message = message;
            this.cssClass = cssClass;
        }
    }

    private final Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public String getCssClass() {
        return resolveState().cssClass;
    }

    public String getStatusText() {
        return resolveState().message;
    }

    private State resolveState() {
        if (game.isWon()) {
            return State.WON;
        } else if (game.isLost()) {
            return State.LOST;
        } else {
            return State.ON;
        }
    }

    public List<List<SquareView>> getRows() {
        return generateRows();
    }

    public int getMoves() {
        return game.getMoves();
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
            rows.get(s.getRow()).add(new SquareView(s, game));
        });
        return rows;
    }
}
