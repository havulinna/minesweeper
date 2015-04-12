package com.havulinna.minesweeper.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.model.Minefield;

public class GameBuilder {
    private enum State {WON, LOST, ONGOING}

    private Minefield minefield = new Minefield(2, 2);
    private State state = State.ONGOING;


    public GameBuilder setLost() {
        this.state = State.LOST;
        return this;
    }

    public GameBuilder setWon() {
        this.state = State.WON;
        return this;
    }

    public GameBuilder setMinefield(Minefield newMinefield) {
        this.minefield = newMinefield;
        return this;
    }


    @SuppressWarnings("boxing")
    public Game build() {
        Game mockGame = mock(Game.class);

        when(mockGame.isOver()).thenReturn(state != State.ONGOING);
        when(mockGame.isWon()).thenReturn(state == State.WON);
        when(mockGame.isLost()).thenReturn(state == State.LOST);

        when(mockGame.getMinefield()).thenReturn(minefield);
        return mockGame;
    }
}
