package com.havulinna.minesweeper.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.havulinna.minesweeper.exception.NotFoundException;
import com.havulinna.minesweeper.model.Game;

@Service
public class GameRepository {

    private Map<String, Game> storedGames = new HashMap<String, Game>();

    /**
     * @return <code>true</code> if this repository contains a game with the
     *         given id
     */
    public boolean containsGame(String id) {
        return storedGames.containsKey(id);
    }

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
     * a new {@link NotFoundException} is thrown
     *
     * @param id the unique id received from {@link GameRepository#store(Game)}
     * @return the game matching the id
     * @throws NotFoundException when id does not match any game
     */
    public Game getGameById(String id) throws NotFoundException {
        Game game = storedGames.get(id);
        if (game == null) {
            throw new NotFoundException("No game found with id " + id);
        }
        return game;
    }

    /**
     * @return the number of games in the repository. May contain duplicates, if
     * the same {@link Game} object is stored multiple times.
     */
    public int getSize() {
        return storedGames.size();
    }
}
