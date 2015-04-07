package com.havulinna.minesweeper.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.havulinna.minesweeper.model.Game;

@Service
public class GameRepository {

    private Map<String, Game> storedGames = new HashMap<String, Game>();

    /**
     * Stores the given Game in memory. The game can later be requested
     * with the returned unique id.
     *
     * @param newGame the game to store
     * @return the ID assigned to the given game
     */
    public String store(Game newGame) {
        String uniqueId = UUID.randomUUID().toString();
        storedGames.put(uniqueId, newGame);
        return uniqueId;
    }

    /**
     * Returns the game stored with the given id. If no matching game is found,
     * null is returned.
     *
     * @param id the unique id received from {@link GameRepository#store(Game)}
     * @return Game or null, if no game is found
     */
    public Game getGameById(String id) {
        return storedGames.get(id);
    }
}
