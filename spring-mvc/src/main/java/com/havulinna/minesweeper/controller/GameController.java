package com.havulinna.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.havulinna.minesweeper.controller.response.GameModelAndView;
import com.havulinna.minesweeper.exception.NotFoundException;
import com.havulinna.minesweeper.model.Difficulty;
import com.havulinna.minesweeper.model.Game;
import com.havulinna.minesweeper.service.GameRepository;

@Controller
public class GameController {

    private static final String GAME_TEMPLATE = "game";

    private final GameRepository repository;

    @Autowired
    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new {@link Game} with the given difficulty, stores the game,
     * and redirects the user to that game.
     * 
     * @param difficulty
     * @return redirect to the newly created game
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public RedirectView startNewGame(
            @RequestParam("difficulty") Difficulty difficulty) {

        Game newGame = new Game(difficulty);
        String gameId = repository.store(newGame);
        return new RedirectView("/game/" + gameId);
    }

    /**
     * Renders the game matching given id. If the game is ongoing, the user will
     * be able to play the game on this page.
     * 
     * @param id The unique ID of the user's game
     * @return view containing the requested game
     * @throws NotFoundException if the given ID matches no game
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public ModelAndView showGame(@PathVariable("gameId") String id) throws NotFoundException {
        Game game = repository.getGameById(id);

        return new GameModelAndView(GAME_TEMPLATE, game);
    }

    /**
     * Handles a move on the game if the game is still ongoing and redirects the
     * user back to the game view.
     * 
     * @throws NotFoundException If no game matching the id is found
     */
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.POST)
    public RedirectView openSquare(
            @PathVariable("gameId") String id,
            @RequestParam("row") int row,
            @RequestParam("col") int col) throws NotFoundException {

        Game game = repository.getGameById(id);
        if (!game.isOver()) {
            game.openSquare(row, col);
        }
        return new RedirectView("/game/" + id);
    }
}
