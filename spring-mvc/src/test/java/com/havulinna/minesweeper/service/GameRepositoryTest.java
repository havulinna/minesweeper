package com.havulinna.minesweeper.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.havulinna.minesweeper.exception.NotFoundException;
import com.havulinna.minesweeper.model.Difficulty;
import com.havulinna.minesweeper.model.Game;


public class GameRepositoryTest {

    private GameRepository repository = new GameRepository();
    private Game game1 = new Game(Difficulty.EASY);
    private Game game2 = new Game(Difficulty.FAIR);

    @Test
    public void theRepositoryIsInitiallyEmpty() {
        assertEquals(0, repository.getSize());
    }

    @Test
    public void repositoryAssignsUniqueIdsForEachNewGame() {
        String id1 = repository.store(game1);
        String id2 = repository.store(game2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void gameCanBeRequestedFromRepositoryByItsId() throws NotFoundException {
        String id = repository.store(game1);
        Game storedGame = repository.getGameById(id);

        assertEquals(game1, storedGame);
    }

    @Test
    public void multipleGamesAreReturnedWithMatchingIds() throws NotFoundException {
        String id1 = repository.store(game1);
        String id2 = repository.store(game2);
        assertEquals(2, repository.getSize());

        assertEquals(game1, repository.getGameById(id1));
        assertEquals(game2, repository.getGameById(id2));
    }

    @Test(expected=NotFoundException.class)
    public void requestingNonexistentIdThrowsNotFoundException() throws NotFoundException {
        repository.getGameById("NOT A VALID ID");
    }

    @Test
    public void containsGameReturnsTrueOnlyForExistingIds() {
        String id = repository.store(game1);

        assertTrue(repository.containsGame(id));
        assertFalse(repository.containsGame("NOT A VALID ID"));
    }
}
