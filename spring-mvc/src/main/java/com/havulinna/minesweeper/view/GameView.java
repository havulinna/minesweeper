package com.havulinna.minesweeper.view;

import java.util.ArrayList;
import java.util.List;

import com.havulinna.minesweeper.model.Game;


public class GameView {

    protected static final String GAME_ON_MESSAGE = "Game is on!";
    protected static final String GAME_WON_MESSAGE = "Game won!";
    protected static final String GAME_LOST_MESSAGE = "Game lost :(";

    private final Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public String getStatusText() {
        if (game.isWon()) {
            return GAME_WON_MESSAGE;
        } else if (game.isLost()) {
            return GAME_LOST_MESSAGE;
        } else {
            return GAME_ON_MESSAGE;
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
